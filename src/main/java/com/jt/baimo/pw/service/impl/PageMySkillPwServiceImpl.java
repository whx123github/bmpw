package com.jt.baimo.pw.service.impl;

import com.alibaba.fastjson.JSON;
import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import com.jt.baimo.pw.mapper.PageMySkillPwMapper;
import com.jt.baimo.pw.mapper.UserOrderMapper;
import com.jt.baimo.pw.model.UserOrder;
import com.jt.baimo.pw.service.CommonService;
import com.jt.baimo.pw.service.JpushService;
import com.jt.baimo.pw.service.PageMySkillPwService;
import com.jt.baimo.pw.service.RedisYwService;
import com.jt.baimo.pw.util.Assert;
import com.jt.baimo.pw.vo.*;
import lombok.extern.slf4j.Slf4j;
import org.joda.time.DateTime;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;

/**
 * @author Forever丶诺
 * @data 2019/9/20 13:34
 */
@Service
@Slf4j
public class PageMySkillPwServiceImpl implements PageMySkillPwService {

    @Autowired
    private PageMySkillPwMapper pageMySkillPwMapper;


    @Autowired
    private UserOrderMapper userOrderMapper;


    @Autowired
    private CommonService commonService;


    @Autowired
    private RedisYwService redisYwService;

    @Autowired
    private JpushService jpushService;



    @Override
    public ResultData<List<MySkillPwTargetSkillVo>> getTargetSkills(String targetId) {
        List<MySkillPwTargetSkillVo> result = pageMySkillPwMapper.getTargetSkills(targetId);
        return new ResultData().setData(result);
    }

    @Override
    public ResultData getBuyPwList(Integer pageSize, Date lastTime, String userId) {
        //最近3个月
        Date recentlyTime = DateTime.now().minusMonths(3).toDate();
        List<MySkillPwBuyPwListVo> result = pageMySkillPwMapper.getBuyPwList(pageSize, lastTime, userId, recentlyTime);
        for (MySkillPwBuyPwListVo data : result) {
            String detail = data.getDetail();
            data.setDetailData(JSON.parseObject(detail, UserSkillPwAllInfoVo.class));
        }
        return new ResultData().setData(result);
    }

    @Override
    public ResultData getSellPwList(Integer pageSize, Date lastTime, String userId) {
        Date recentlyTime = DateTime.now().minusMonths(3).toDate();
        List<MySkillPwSellPwListVo> result = pageMySkillPwMapper.getSellPwList(pageSize, lastTime, userId, recentlyTime);
        for (MySkillPwSellPwListVo data : result) {
            String detail = data.getDetail();
            data.setDetailData(JSON.parseObject(detail, UserSkillPwAllInfoVo.class));
        }
        return new ResultData().setData(result);
    }


    @Override
    public ResultData getOnePwInfo(String id) {
        MySkillPwOnePwInfoVo result = pageMySkillPwMapper.getOnePwInfo(id);
        String detail = result.getDetail();
        result.setDetailData(JSON.parseObject(detail, UserSkillPwAllInfoVo.class));
        return new ResultData().setData(result);
    }


    @Override
    @Transactional(rollbackFor = Exception.class)
    public ResultData sellerTakePwOrder(String id, String userId) {
        int update = userOrderMapper.update(new UserOrder().setStatus(3), new LambdaUpdateWrapper<UserOrder>().eq(UserOrder::getId, id).eq(UserOrder::getSellerId, userId).eq(UserOrder::getStatus, 2));
        Assert.isZero(update, "接单失败");
        // 通知(玩家):  陪玩者已经接单
        UserOrder userOrder = userOrderMapper.selectById(id);
        jpushService.idPush(userOrder.getBuyerId(), userId, 12, id, null);
        return new ResultData();
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public ResultData sellerStartPwOrder(String id, String pwImgs, String userId) {
        int update = userOrderMapper.update(new UserOrder().setStatus(4).setPwImgs(pwImgs), new LambdaUpdateWrapper<UserOrder>().eq(UserOrder::getId, id).eq(UserOrder::getSellerId, userId).eq(UserOrder::getStatus, 3));
        Assert.isZero(update, "开始游戏失败");
        // 通知(玩家):  陪玩者已经开始游戏
        UserOrder userOrder = userOrderMapper.selectById(id);

        jpushService.idPush(userOrder.getBuyerId(), userId, 16, id, null);

        return new ResultData();
    }


    @Override
    @Transactional(rollbackFor = Exception.class)
    public ResultData sellerFinishPwOrder(String id, String userId) {
        int update = userOrderMapper.update(new UserOrder().setStatus(5), new LambdaUpdateWrapper<UserOrder>().eq(UserOrder::getId, id).eq(UserOrder::getSellerId, userId).eq(UserOrder::getStatus, 4));
        Assert.isZero(update, "完成服务失败");
        redisYwService.addPwNotFinish(id);
        UserOrder userOrder = userOrderMapper.selectById(id);
        // 通知(玩家):  陪玩者已经完成服务 需要玩家确认订单
        jpushService.idPush(userOrder.getBuyerId(), userId, 17, id, id);
        return new ResultData();
    }


    @Override
    @Transactional(rollbackFor = Exception.class)
    public ResultData buyerFinishPwOrder(String id, String userId) {
        int update = userOrderMapper.update(new UserOrder().setStatus(0), new LambdaUpdateWrapper<UserOrder>().eq(UserOrder::getId, id).eq(UserOrder::getBuyerId, userId).eq(UserOrder::getStatus, 5));
        Assert.isZero(update, "确认完成失败");
        UserOrder userOrder = userOrderMapper.selectById(id);
        commonService.sellGoods(userOrder, 2);
        // 通知(陪玩者):  玩家已经确认订单 陪玩者接受到金额

        jpushService.idPush(userOrder.getSellerId(), userId, 18, id, id);
        jpushService.idPush(userOrder.getSellerId(), userId, 19, id, null);

        return new ResultData();
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public ResultData buyerCancelPwOrder(String id, Integer cancelReasonType, String cancelReason, String userId) {

        UserOrder userOrder = userOrderMapper.selectById(id);

        int update = userOrderMapper.update(new UserOrder().setStatus(-1).setCancelReasonType(cancelReasonType).setCancelType(2).setCancelReason(cancelReason), new LambdaUpdateWrapper<UserOrder>().eq(UserOrder::getId, id).eq(UserOrder::getBuyerId, userId).in(UserOrder::getStatus, 1, 2));
        Assert.isZero(update, "取消订单失败");
        if (userOrder.getStatus().equals(2)) {
            commonService.updateAddUserBmb(id, userOrder.getPayAmonut(), userId, 2, userId);
            // 通知(玩家):  进行退款
            jpushService.idPush(userId, null, 20, id, null);
            // 通知(陪玩):  已取消用户取消
//            jpushService.idPush(userOrder.getSellerId(), null, 15, id,id);
        }
        return new ResultData();
    }
}
