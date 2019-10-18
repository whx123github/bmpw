package com.jt.baimo.pw.service.impl;

import com.alibaba.fastjson.JSONObject;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.jt.baimo.pw.mapper.*;
import com.jt.baimo.pw.model.*;
import com.jt.baimo.pw.service.CommonService;
import com.jt.baimo.pw.service.RedisService;
import com.jt.baimo.pw.service.UserOrderService;
import com.jt.baimo.pw.util.Assert;
import com.jt.baimo.pw.vo.ResultData;
import com.jt.baimo.pw.vo.SelectListVo;
import com.jt.baimo.pw.vo.SelectMultiListVo;
import com.jt.baimo.pw.vo.TargetCommonInfoVo;
import org.joda.time.LocalDateTime;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.security.SecurityProperties;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;


/**
 * @author Forever丶诺
 * @data 2019/7/19 14:12
 */
@Service
public class CommonServiceImpl implements CommonService {

    @Autowired
    private CommonMapper commonMapper;


    @Autowired
    private UserOrderService userOrderService;


    @Autowired
    private BmbRecordMapper bmbRecordMapper;

    @Autowired
    private RedisService redisService;

    @Autowired
    private UserMapper userMapper;

    @Autowired
    private TreeServiceImpl<SelectMultiListVo, Integer> treeService;

    @Autowired
    private UserOrderMapper userOrderMapper;


    @Autowired
    private TxbRecordMapper txbRecordMapper;


    @Override
    public ResultData<List<SelectListVo>> listSelect(String typeName) {
        List<SelectListVo> selectListVos = commonMapper.listSelect(typeName);
        return new ResultData<List<SelectListVo>>().setData(selectListVos);
    }

    @Override
    public ResultData<List<SelectMultiListVo>> listMultiSelect(String typeName) {
        List<SelectMultiListVo> selectMultiListVos = commonMapper.listMultiSelect(typeName);
        List<SelectMultiListVo> childTreeList = treeService.getChildTreeList(selectMultiListVos, 0);
        return new ResultData<List<SelectMultiListVo>>().setData(childTreeList);
    }


    @Override
    public boolean existUser(String userTel) {
        Integer count = userMapper.selectCount(new LambdaQueryWrapper<User>().eq(User::getUserTel, userTel));
        return count >= 1;
    }


    @Override
    @Transactional(rollbackFor = Exception.class)
    public void rechargeSuccess(UserOrder userOrder, Integer payType) {
        String orderNo = userOrder.getId();
        String detail = userOrder.getDetail();
        RechargeConfig rechargeConfig = JSONObject.parseObject(detail, RechargeConfig.class);
        BigDecimal bmCoin = rechargeConfig.getBmCoin();
        updateAddUserBmb(orderNo, bmCoin, userOrder.getBuyerId(), 1, null);
        userOrderMapper.updateById(new UserOrder().setId(orderNo).setPayType(payType).setStatus(0).setPayTime(new Date()));
    }

    @Override
    public ResultData getTargetCommonInfo(String targetId) {
        TargetCommonInfoVo targetCommonInfoVo = commonMapper.getTargetCommonInfo(targetId);
        return new ResultData().setData(targetCommonInfoVo);
    }


    @Override
    @Transactional(rollbackFor = Exception.class)
    public void buyGoods(String orderNo, BigDecimal price, String buyerId, String sellerId, Integer type) {
        BigDecimal buyerBalance = updateSubUserBmb(buyerId, price);
        bmbRecordMapper.insert(new BmbRecord().setOrderId(orderNo).setType(type).setUid(buyerId).setMoney(price).setBalance(buyerBalance).setTargetId(sellerId));
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void buyGoods(UserOrder userOrder, Integer type) {
        buyGoods(userOrder.getId(), userOrder.getOrderAmount(), userOrder.getBuyerId(), userOrder.getSellerId(), type);
    }


    /***
     * 该方法暂时 购买相册可以使用
     * @param userOrder
     * @param type
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public void buyGoodsAndSellGoods(UserOrder userOrder, Integer type) {
        String orderNo = userOrder.getId(), buyerId = userOrder.getBuyerId(), sellerId = userOrder.getSellerId();
        BigDecimal price = userOrder.getOrderAmount();
        buyGoods(orderNo, price, buyerId, sellerId, type);
        sellGoods(orderNo, price, buyerId, sellerId, Math.abs(type));
    }


    @Override
    @Transactional(rollbackFor = Exception.class)
    public void sellGoods(String orderNo, BigDecimal price, String buyerId, String sellerId, Integer type) {
        BigDecimal sellerBalance = updateAddUserTxb(sellerId, price);
        txbRecordMapper.insert(new TxbRecord().setOrderId(orderNo).setType(type).setUid(sellerId).setMoney(price).setBalance(sellerBalance).setTargetId(buyerId));
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void sellGoods(UserOrder userOrder, Integer type) {
        sellGoods(userOrder.getId(), userOrder.getOrderAmount(), userOrder.getBuyerId(), userOrder.getSellerId(), type);
    }


    /***
     * 只有用户bmb增加
     * @param userId
     * @param price
     * @return
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public BigDecimal updateAddUserBmb(String userId, BigDecimal price) {
        int isUpdate = 0;
        BigDecimal nowMoney = null;
        while (isUpdate == 0) {
            User user = userMapper.selectById(userId);
            nowMoney = user.getBmCoin().add(price);
            isUpdate = userMapper.updateById(new User().setId(userId).setBmCoin(nowMoney).setVersion(user.getVersion()));
        }
        return nowMoney;
    }


    /***
     * 用户bmb增加 , 同时bmb流水表添加一条记录
     * @param orderNo 订单号
     * @param price   价格
     * @param userId  用户id
     * @param type    bmb的type :充值: +1 ,退款: +2 , 相册支出: -1 ,陪玩: -2
     * @param targetId 对方的id
     * @param detail  详细内容
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public void updateAddUserBmb(String orderNo, BigDecimal price, String userId, Integer type, String targetId, String detail) {
        BigDecimal nowMoney = updateAddUserBmb(userId, price);
        bmbRecordMapper.insert(new BmbRecord().setMoney(price).setBalance(nowMoney).setType(type).setOrderId(orderNo).setUid(userId).setTargetId(targetId).setDetail(detail));
    }

    /***
     * 用户bmb增加 , 同时bmb流水表添加一条记录 不需要detail
     * @param orderNo 订单号
     * @param price   价格
     * @param userId  用户id
     * @param type    bmb的type :充值: +1 ,退款: +2 , 相册支出: -1 ,陪玩: -2
     * @param targetId 对方的id
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public void updateAddUserBmb(String orderNo, BigDecimal price, String userId, Integer type, String targetId) {
        updateAddUserBmb(orderNo, price, userId, type, targetId, null);
    }


    /***
     * 用户bmb减少, 同时bmb流水表添加一条记录
     * @param price   价格
     * @param userId  用户id
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public BigDecimal updateSubUserBmb(String userId, BigDecimal price) {
        int isUpdate = 0;
        BigDecimal nowMoney = null;
        BigDecimal oldMoney = null;
        while (isUpdate == 0) {
            User user = userMapper.selectById(userId);
            //判断余额是否足够
            oldMoney = user.getBmCoin();
            Assert.isTrue(oldMoney.compareTo(price) < 0, "余额不足");
            nowMoney = oldMoney.subtract(price);
            isUpdate = userMapper.updateById(user.setBmCoin(nowMoney));
        }
        return nowMoney;
    }


    @Override
    @Transactional(rollbackFor = Exception.class)
    public BigDecimal updateAddUserTxb(String userId, BigDecimal price) {
        int isUpdate = 0;
        BigDecimal nowMoney = null;
        while (isUpdate == 0) {
            User user = userMapper.selectById(userId);
            nowMoney = user.getTxCoin().add(price);
            isUpdate = userMapper.updateById(new User().setId(userId).setTxCoin(nowMoney).setVersion(user.getVersion()));
        }

        return nowMoney;
    }


    @Override
    @Transactional(rollbackFor = Exception.class)
    public JSONObject updateSubUserTxb(String userId, BigDecimal price) {
        int isUpdate = 0;
        BigDecimal nowMoney = null;
        BigDecimal oldMoney = null;
        while (isUpdate == 0) {
            User user = userMapper.selectById(userId);
            //判断余额是否足够
            oldMoney = user.getTxCoin();
            Assert.isTrue(oldMoney.compareTo(price) < 0, "余额不足");
            nowMoney = oldMoney.subtract(price);
            isUpdate = userMapper.updateById(user.setTxCoin(nowMoney));
        }
        JSONObject result = new JSONObject();
        result.put("nowMoney", nowMoney);
        result.put("oldMoney", oldMoney);
        return result;
    }


    @Override
    public String createOrderNo(String module, String userId) {
        User user = userMapper.selectById(userId);
        return module + "-" + user.getUid() + "-" + new LocalDateTime().toString("yyyyMMddHHmmss");
    }


}
