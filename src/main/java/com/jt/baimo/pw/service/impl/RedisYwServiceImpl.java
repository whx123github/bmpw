package com.jt.baimo.pw.service.impl;

import com.jt.baimo.pw.constant.FailMsgCst;
import com.jt.baimo.pw.constant.RedisPrefixCst;
import com.jt.baimo.pw.service.RedisService;
import com.jt.baimo.pw.service.RedisYwService;
import com.jt.baimo.pw.util.Assert;
import com.jt.baimo.pw.util.DateUtil;
import org.joda.time.DateTime;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
import java.util.Set;

import static com.jt.baimo.pw.constant.CommonCst.REDIS_LOGIN_EXPIRE;
import static com.jt.baimo.pw.constant.RedisPrefixCst.REDIS_RC_TOKEN_PRE;


/**
 * @author Forever丶诺
 * @data 2019/9/17 20:15
 */
@Service
public class RedisYwServiceImpl implements RedisYwService {

    @Autowired
    RedisService redisService;


    /**
     * 完成每天的短信上限功能
     *
     * @param userTel
     */
    @Override
    public void setSmsEveryDayTimes(String userTel) {
        String dateTime = DateTime.now().toString(DateUtil.NYR);//生成当前时间
        String key = RedisPrefixCst.SMS_EVERY_DAY_TIMES + userTel + dateTime;
        long num = redisService.incr(key);
        Assert.isTrue(num > 30, "短信发送上限");
        if(num==1){
            redisService.expireAtTomorrow(key);
        }
    }


    @Override
    public void setSmsType(String smsType, String userTel, String code) {
        redisService.set(smsType + userTel, code, 5 * 60);//验证码登录
    }


    @Override
    public void validSmsCodeForget(String identifyCode, String userTel) {
        validSmsCode(RedisPrefixCst.SMS_FORGET, identifyCode, userTel);
    }


    @Override
    public void validSmsCodeRegister(String identifyCode, String userTel) {
        validSmsCode(RedisPrefixCst.SMS_REGISTERED, identifyCode, userTel);
    }


    private void validSmsCode(String smsType, String userTel, String identifyCode) {
        Assert.isTrue(!identifyCode.equals(redisService.get(smsType + userTel)), FailMsgCst.TEL_IDF_CODE_NOT_MATCH);
        redisService.del(smsType + userTel);
    }


    @Override
    public Long getInitUid() {
        return redisService.incr("USER_UID", 2483, 3);
    }

    /*********************************************容云相关**********************************************/

    @Override
    public void setRcToken(String userId, String rcToken) {
        redisService.set(RedisPrefixCst.REDIS_RC_TOKEN_PRE + userId, rcToken);
    }


    @Override
    public String getRcToken(String userId) {
        return redisService.get(REDIS_RC_TOKEN_PRE + userId);
    }





    /********************************************在线用户的key*************************************/

    @Override
    public void setLoginKey(String userId) {
        redisService.set(RedisPrefixCst.REDIS_LOGIN_PRE + userId, null, REDIS_LOGIN_EXPIRE);
    }


    @Override
    public Set<String> getLoginKeys() {
        return redisService.keys(RedisPrefixCst.REDIS_LOGIN_PRE + "*");
    }



    @Override
    public boolean hasLoginKey(String userId) {
        return redisService.hasKey(RedisPrefixCst.REDIS_LOGIN_PRE + userId);
    }


    /*****************************************登录JWT_TOKEN的处理***************************/


    @Override
    public void setJwtToken(String userId, String token) {
        redisService.set(RedisPrefixCst.REDIS_JWT_TOKEN_PRE + userId, token, RedisPrefixCst.REDIS_JWT_TOKEN_EXPIRE);
    }


    @Override
    public void deleteJwtToken(String userId) {
        redisService.del(RedisPrefixCst.REDIS_JWT_TOKEN_PRE + userId);
    }

    @Override
    public String getJwtToken(String userId) {
        return redisService.get(RedisPrefixCst.REDIS_JWT_TOKEN_PRE + userId);
    }

    /*******************************************未支付pw订单的处理*****************************/

    @Override
    public void addPwNotPay(String orderNo, Date addTime) {
        redisService.lRightPush(RedisPrefixCst.PW_NOT_PAY, orderNo + "_" +addTime.getTime());
    }

    @Override
    public List<String> getPwNotPays() {
        return redisService.lRange(RedisPrefixCst.PW_NOT_PAY, 0, 100);
    }


    @Override
    public void deletePwNotPays(Integer needDeleteSize) {
        Long redisSize = redisService.lLen(RedisPrefixCst.PW_NOT_PAY);
        redisService.lTrim(RedisPrefixCst.PW_NOT_PAY, needDeleteSize, redisSize + 10000);
    }


    @Override
    public void addPwNotFinish(String orderNo) {
        redisService.lRightPush(RedisPrefixCst.PW_NOT_FINISH, orderNo + "_" + DateTime.now().getMillis());
    }


    @Override
    public List<String> getPwNotAllFinish() {
        Long size = redisService.lLen(RedisPrefixCst.PW_NOT_FINISH);
        return redisService.lRange(RedisPrefixCst.PW_NOT_FINISH, 0, size);
    }

    @Override
    public void deletePwNotFinishes(Integer needDeleteSize) {
        Long redisSize = redisService.lLen(RedisPrefixCst.PW_NOT_FINISH);
        redisService.lTrim(RedisPrefixCst.PW_NOT_FINISH, needDeleteSize, redisSize + 10000);
    }


}
