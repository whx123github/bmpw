package com.jt.baimo.pw.constant;

/***
 * 常量
 */
public interface CommonCst {

    String USER_ID = "userId";


    Long REDIS_JWT_TOKEN_EXPIRE = 2592000L;  // jwtToken的过期时间
    Long FIFTEEN_MINUTES_MS = 15 * 60 * 1000L;  // jwtToken的过期时间
    Long ONE_DAY_MS = 24 * 60 * 60 * 1000L;  // 每天的时间


    Long REDIS_LOGIN_EXPIRE = 60 * 30L; //登录的过期时间 60*60


    String IOS_URL_VERIFY_RECEIPT_SANDBOX = "https://sandbox.itunes.apple.com/verifyReceipt"; //ios沙箱支付
    String IOS_URL_VERIFY_RECEIPT = "https://buy.itunes.apple.com/verifyReceipt"; //正式


}
