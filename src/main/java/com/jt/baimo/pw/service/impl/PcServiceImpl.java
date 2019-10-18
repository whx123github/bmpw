package com.jt.baimo.pw.service.impl;

import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import com.jt.baimo.pw.mapper.UserOrderMapper;
import com.jt.baimo.pw.model.UserOrder;
import com.jt.baimo.pw.service.CommonService;
import com.jt.baimo.pw.service.JpushService;
import com.jt.baimo.pw.service.PcService;
import com.jt.baimo.pw.util.Assert;
import com.jt.baimo.pw.vo.ResultData;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;

/**
 * @author Forever丶诺
 * @data 2019/9/25 10:47
 */
@Slf4j
@Service
public class PcServiceImpl implements PcService {
    @Autowired
    private UserOrderMapper userOrderMapper;


    @Autowired
    private CommonService commonService;

    @Autowired
    private JpushService jpushService;

    @Override
    public ResultData adminCancelPwOrder(String id, String cancelReason, String aid) {

        UserOrder userOrder = userOrderMapper.selectById(id);

        int update = userOrderMapper.update(new UserOrder().setStatus(-1).setCancelType(3).setAdminTime(new Date()).setAid(aid).setCancelReason(cancelReason), new LambdaUpdateWrapper<UserOrder>().eq(UserOrder::getId, id).notIn(UserOrder::getStatus, -1, 0));
        Assert.isZero(update, "取消订单失败");
        //进行退款
        //-1 已经取消 0 完成
        //通知到玩家
        String buyerId = userOrder.getBuyerId();
        Integer status = userOrder.getStatus();

        if (!status.equals(1)) {
            commonService.updateAddUserBmb(id, userOrder.getPayAmonut(), buyerId, 2, buyerId);
            // 通知(玩家):  进行退
            jpushService.idPush(buyerId, null, 20, id,null);
        }
        // 通知(陪玩):  客服取消
        if (status > 2) {
            //通知陪玩
            jpushService.idPush(userOrder.getSellerId(), null, 13, id,id);
        }

        //通知玩家 客服取消
        jpushService.idPush(buyerId, null, 13, id,id);

        return new ResultData();

    }
}