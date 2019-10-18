package com.jt.baimo.pw.constant;

/**
 * Redis key值的前缀
 * redis相关的信息
 *
 * @author Forever丶诺
 * @data 2019/7/18 17:43
 */
public interface RedisPrefixCst {


    String SMS_EVERY_DAY_TIMES = "SMS_EVERY_DAY_TIMES_"; //短信每天的次数
    String REDIS_JWT_TOKEN_PRE = "JWT_TOKEN_"; // token的redis的key
    Long REDIS_JWT_TOKEN_EXPIRE = 2592000L;
    String REDIS_LOGIN_PRE = "LOGIN_"; // 登录用户的key
    String REDIS_RC_TOKEN_PRE = "RC_TOKEN_"; // 容云的token
    String PW_NOT_PAY = "PW_NOT_PAY"; //陪玩订单未支付的集合的key值
    String PW_NOT_FINISH = "PW_NOT_FINISH"; //玩家未确认的订单
    String SMS_REGISTERED = "SMS_REGISTER_";//注册
    String SMS_FORGET = "SMS_FORGET_";//忘记密码







    String USER_LOGIN_ERROR_PRE = "USER_LOGIN_ERROR_";
    String CAN_WATCH_USER_TIME_PRE = "CAN_WATCH_USER_TIME_"; //每天可看用户的次数

}
