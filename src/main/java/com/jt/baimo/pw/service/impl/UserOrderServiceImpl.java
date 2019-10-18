package com.jt.baimo.pw.service.impl;

import cn.hutool.core.date.Week;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.serializer.SerializerFeature;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import com.jt.baimo.pw.constant.OrderModuleCst;
import com.jt.baimo.pw.constant.RedisPrefixCst;
import com.jt.baimo.pw.enumerate.SmsCodeEnum;
import com.jt.baimo.pw.enumerate.WeekEnum;
import com.jt.baimo.pw.mapper.*;
import com.jt.baimo.pw.model.*;
import com.jt.baimo.pw.service.*;
import com.jt.baimo.pw.util.AppUtil;
import com.jt.baimo.pw.util.Assert;
import com.jt.baimo.pw.util.EnumUtil;
import com.jt.baimo.pw.vo.ResultData;
import com.jt.baimo.pw.vo.UserSkillPwAllInfoVo;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.joda.time.DateTime;
import org.joda.time.LocalDateTime;
import org.joda.time.LocalTime;
import org.joda.time.Weeks;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.*;

/**
 * @author Forever丶诺
 * @data 2019/9/19 20:34
 */
@Service
@Slf4j
public class UserOrderServiceImpl implements UserOrderService {

    @Autowired
    private UserOrderMapper userOrderMapper;


    @Autowired
    private UserMapper userMapper;


    @Autowired
    private RechargeConfigMapper rechargeConfigMapper;


    @Autowired
    private BmbRecordMapper bmbRecordMapper;

    @Autowired
    private AlbumMapper albumMapper;


    @Autowired
    private TxbRecordMapper txbRecordMapper;


    @Autowired
    private UserUnLockAlbumMapper userUnLockAlbumMapper;


    @Autowired
    private AuthenticationMapper authenticationMapper;

    @Autowired
    private CommonMapper commonMapper;


    @Autowired
    private CommonService commonService;


    @Autowired
    private RedisYwService redisYwService;


    @Autowired
    private UserUnLockAlbumService userUnLockAlbumService;

    @Autowired
    private JpushService jpushService;

    @Override
    public ResultData recharge(Integer rechargeId, String userId) {
        //根据充值订单id 获取充值金额
        RechargeConfig rechargeConfig = rechargeConfigMapper.selectById(rechargeId);
        //生产一个订单号
        String orderNo = commonService.createOrderNo(OrderModuleCst.CZ, userId);
        //初始化订单对象
        UserOrder userOrder = initOrder(userId, rechargeConfig.getMoney(), orderNo);
        userOrder.setType(1).setDetail(JSONObject.toJSONString(rechargeConfig));
        userOrderMapper.insert(userOrder);
        return getOrderNoResult(orderNo);
    }


    @Override
    public ResultData createAirTicketOrder(BigDecimal price, String airNo, String userId) {
        String orderNo = commonService.createOrderNo(OrderModuleCst.AIR_TICKET, userId);
        UserOrder userOrder = initOrder(userId, price, orderNo);
        JSONObject detail = new JSONObject();
        detail.put("airNo" , airNo);
        detail.put("price" , price);
        userOrder.setType(2).setDetail(JSON.toJSONString(detail, SerializerFeature.WriteMapNullValue));
        userOrderMapper.insert(userOrder);
        return getOrderNoResult(orderNo);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public ResultData buyAlbum(Integer albumId, String userId) {
        //创建一个订单
        Album album = albumMapper.selectById(albumId);
        BigDecimal price = album.getPrice();
        String sellerId = album.getUid();

        String orderNo = commonService.createOrderNo(OrderModuleCst.ALBUM, userId);
        //初始化订单对象
        UserOrder userOrder = initHasSellerOrder(userId, price, orderNo, sellerId);
        userOrder.setStatus(0).setPayTime(new Date()).setPayType(4).setType(-1).setDetail(JSON.toJSONString(album));
        userOrderMapper.insert(userOrder);


        commonService.buyGoodsAndSellGoods(userOrder, -1);

        //照片解锁表添加一条数据
        UserUnLockAlbum existUnLock = userUnLockAlbumService.getOne(new LambdaQueryWrapper<UserUnLockAlbum>().eq(UserUnLockAlbum::getUid, userId).eq(UserUnLockAlbum::getAlbumId, albumId));
        UserUnLockAlbum userUnLockAlbum = new UserUnLockAlbum().setUid(userId).setAlbumId(albumId);
        if (existUnLock != null) {
            userUnLockAlbum.setId(existUnLock.getId());
        }
        userUnLockAlbumService.saveOrUpdate(userUnLockAlbum);
        // 通知(用户到账)
        try {
            jpushService.idPush(sellerId, null, 19, orderNo, null);
        } catch (Exception e) {
            log.error("推送异常", e);
        }
        return new ResultData();
    }


    @Override
    @Transactional(rollbackFor = Exception.class)
    public ResultData creatNotPayPw(Integer skillPwId, String userId, Integer quantity, Date serviceTime) {
        //判断是不是一个小时之后的订单
        Assert.isTrue(DateTime.now().plusMinutes(59).isAfter(new DateTime(serviceTime)), "下单的服务时间不在当前一小时之后");


        //判断是不是时间内的订单
        UserSkillPwAllInfoVo authentication = commonMapper.getUserSkillPwAllInfo(skillPwId);
        // Assert.isFalse(AppUtil.isServerTime(authentication.getStartTime(), authentication.getEndTime(), serviceTime, authentication.getWeek()), "不在服务时间内");

        String sellerId = authentication.getUid();
        Assert.isTrue(sellerId.equals(userId), "自己不能购买自己的订单");
        //todo 怎么判断大神是在陪玩中
  /*      Integer count = userOrderMapper.selectCount(new LambdaQueryWrapper<UserOrder>().eq(UserOrder::getSellerId, sellerId).eq(UserOrder::getStatus, 4));
        Assert.notZero(count, "这位游戏大神正在陪玩中,请稍后再来");*/
        BigDecimal price = authentication.getChargeAmount();
        BigDecimal allPrice = price.multiply(new BigDecimal(quantity)).setScale(2, RoundingMode.HALF_DOWN);
        String orderNo = commonService.createOrderNo(OrderModuleCst.PW, userId);
        UserOrder userOrder = initHasSellerOrder(userId, allPrice, orderNo, sellerId);
        userOrder.setType(-2).setQuantity(quantity).setServiceTime(serviceTime).setBuySkillId(authentication.getGameId()).setDetail(JSON.toJSONString(authentication));
        userOrderMapper.insert(userOrder);
        redisYwService.addPwNotPay(orderNo, userOrder.getAddTime());
        return getOrderNoResult(orderNo);
    }


    @Override
    @Transactional(rollbackFor = Exception.class)
    public ResultData payPw(String orderNo, String userId) {
        UserOrder userOrder = userOrderMapper.selectOne(new LambdaQueryWrapper<UserOrder>().eq(UserOrder::getId, orderNo).eq(UserOrder::getBuyerId, userId));
        Assert.isNull(userOrder, "订单不存在");
        //用户的金额减少
        commonService.buyGoods(userOrder, -2);
        //修改订单为已支付状态
        int update = userOrderMapper.update(new UserOrder().setPayTime(new Date()).setStatus(2).setPayType(4), new LambdaUpdateWrapper<UserOrder>().eq(UserOrder::getStatus, 1).eq(UserOrder::getId, orderNo).eq(UserOrder::getBuyerId, userId));
        Assert.isZero(update, "支付失败");
        // 通知(陪玩者): 玩家已经支付 陪玩者需要接待

        jpushService.idPush(userOrder.getSellerId(), userId, 11, orderNo, null);

        return new ResultData();
    }


    /***
     * 初始化 订单 订单金额 != 总金额 != 支付金额
     * @param buyerId

     * @param totalAmount
     * @param orderNo
     * @return
     */
    private UserOrder initOrder(String buyerId, BigDecimal orderAmount, BigDecimal totalAmount, BigDecimal payAmonut, String orderNo) {
        UserOrder userOrder = new UserOrder().setBuyerId(buyerId).setOrderAmount(orderAmount).setTotalAmount(totalAmount).setPayAmonut(payAmonut)
                .setStatus(1).setId(orderNo);
        return userOrder;
    }


    /***
     * 初始化 订单 订单金额 != 总金额 != 支付金额 有出售者的
     * @param buyerId

     * @param totalAmount
     * @param orderNo
     * @return
     */
    private UserOrder initHasSellerOrder(String buyerId, BigDecimal orderAmount, BigDecimal totalAmount, BigDecimal payAmonut, String orderNo, String sellerId) {
        UserOrder userOrder = initOrder(buyerId, orderAmount, totalAmount, payAmonut, orderNo);
        return userOrder.setSellerId(sellerId);
    }


    /***
     * 初始化 订单 订单金额= 总金额 = 支付金额 有出售者的
     * @param buyerId

     * @param totalAmount
     * @param orderNo
     * @return
     */
    private UserOrder initHasSellerOrder(String buyerId, BigDecimal totalAmount, String orderNo, String sellerId) {
        UserOrder userOrder = initOrder(buyerId, totalAmount, orderNo);
        return userOrder.setSellerId(sellerId);
    }

    /***
     * 初始化 订单 订单金额= 总金额 = 支付金额
     * @param buyerId

     * @param totalAmount
     * @param orderNo
     * @return
     */
    private UserOrder initOrder(String buyerId, BigDecimal totalAmount, String orderNo) {
        return initOrder(buyerId, totalAmount, totalAmount, totalAmount, orderNo);
    }


    private ResultData getOrderNoResult(String orderNo) {
        return new ResultData().oneKeyDate("orderNo", orderNo);
    }


}
