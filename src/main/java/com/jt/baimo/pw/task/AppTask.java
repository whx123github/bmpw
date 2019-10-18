package com.jt.baimo.pw.task;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import com.jt.baimo.pw.constant.CommonCst;
import com.jt.baimo.pw.constant.RedisPrefixCst;
import com.jt.baimo.pw.mapper.UserMapper;
import com.jt.baimo.pw.mapper.UserOrderMapper;
import com.jt.baimo.pw.model.User;
import com.jt.baimo.pw.model.UserOrder;
import com.jt.baimo.pw.service.CommonService;
import com.jt.baimo.pw.service.JpushService;
import com.jt.baimo.pw.service.RedisYwService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import static com.jt.baimo.pw.constant.CommonCst.FIFTEEN_MINUTES_MS;

/**
 * @author Forever丶诺
 * @data 2019/7/24 9:56
 */
@Service
@Slf4j
public class AppTask {

    @Autowired
    private UserMapper userMapper;

    @Autowired
    private UserOrderMapper userOrderMapper;


    @Autowired
    private CommonService commonService;


    @Autowired
    private RedisYwService redisYwService;

    @Autowired
    private JpushService jpushService;

    @Scheduled(cron = "0 0/15 * * * ?")
    public void updateUserNotLogin() {
        // 查看redis中在线的用户
        Set<String> keys = redisYwService.getLoginKeys();

        //查看数据库中在线的用   数据库在线但是redis中没有的就是 不在的用户了
        List<User> users = userMapper.selectList(new LambdaQueryWrapper<User>().eq(User::getIsLogin, 1).select(User::getId));
        List<String> notLoginId = new ArrayList<>();
        for (User user : users) {
            String id = user.getId();
            if (!keys.contains(RedisPrefixCst.REDIS_LOGIN_PRE + id)) {
                notLoginId.add(id);
            }
        }
        if (notLoginId.size() != 0) {
            userMapper.update(new User().setIsLogin(0), new LambdaUpdateWrapper<User>().in(User::getId, notLoginId));
        }
    }


    /***
     * 陪玩未支付的订单的删除
     */
    @Scheduled(cron = "0/2 * * * * ?")
    public void updatePwOrderPayOutTime() {

        //获取100个未支付的订单


        List<String> notPayOrders = redisYwService.getPwNotPays();

        List<String> needDeleteIds = getNeedDeleteIds(notPayOrders, FIFTEEN_MINUTES_MS);
        int size = needDeleteIds.size();
        if (size != 0) {
            //更新数据库中的订单
            List<UserOrder> userOrders = userOrderMapper.selectList(new LambdaQueryWrapper<UserOrder>().in(UserOrder::getId, needDeleteIds).eq(UserOrder::getStatus, 1));

            userOrderMapper.update(new UserOrder().setStatus(-1).setCancelType(1)
                    , new LambdaUpdateWrapper<UserOrder>().in(UserOrder::getId, needDeleteIds).eq(UserOrder::getStatus, 1));
            //移除redis中的未支付的key
            for (UserOrder userOrder : userOrders) {
                if (needDeleteIds.contains(userOrder.getId())) {
                    jpushService.idPush(userOrder.getBuyerId(), null, 14, userOrder.getId(), userOrder.getId());
                }
            }
            redisYwService.deletePwNotPays(size);
        }
    }

    private List<String> getNeedDeleteIds(List<String> notPayOrders, long distance) {
        List<String> needDeleteIds = new ArrayList<>();
        long currentTimeMillis = System.currentTimeMillis();
        for (String notPayOrder : notPayOrders) {
            String[] data = notPayOrder.split("_");
            String datum = data[1];
            if (currentTimeMillis - Long.parseLong(datum) < distance) {
                break;
            }
            needDeleteIds.add(data[0]);
        }
        return needDeleteIds;
    }


    @Scheduled(cron = " 0 0 9,12,15,18,21 * * ?")
    //@Scheduled(cron = "* 0/5 * * * ?")
    public void updatePwOrderNotFinish() {
        List<String> pwdNotAllFinish = redisYwService.getPwNotAllFinish();
        List<String> needDeleteIds = getNeedDeleteIds(pwdNotAllFinish, CommonCst.ONE_DAY_MS);
        int size = needDeleteIds.size();
        if (size != 0) {
            for (String needDeleteId : needDeleteIds) {
                int update = userOrderMapper.update(new UserOrder().setStatus(0)
                        , new LambdaUpdateWrapper<UserOrder>().eq(UserOrder::getId, needDeleteId).eq(UserOrder::getStatus, 5));
                if (update > 0) {
                    UserOrder userOrder = userOrderMapper.selectById(needDeleteId);
                    commonService.sellGoods(userOrder, 2);
                    // 通知(陪玩者):  系统自动已经确认订单 陪玩者接受到金额
                    jpushService.idPush(userOrder.getSellerId(), null, 19, needDeleteId, null);
                }
            }
            redisYwService.deletePwNotFinishes(size);
        }
    }
}
