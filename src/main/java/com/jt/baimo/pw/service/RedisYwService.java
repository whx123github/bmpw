package com.jt.baimo.pw.service;

import java.util.Date;
import java.util.List;
import java.util.Set;

/**
 * @author Forever丶诺
 * @data 2019/9/17 20:14
 */
public interface RedisYwService {
    void setSmsEveryDayTimes(String userTel);

    void setSmsType(String smsType, String userTel, String code);


    void validSmsCodeForget(String userTel, String identifyCode);


    void validSmsCodeRegister(String userTel, String identifyCode);


    /***
     * 注册时,获取用户的uid
     * @return
     */
    Long getInitUid();

    /***************************************************容云相关的***************************/

    /***
     * 设置用户对应的容云token
     * @param userId
     * @param rcToken
     */
    void setRcToken(String userId, String rcToken);

    /**
     * 获取容云token
     * @param userId
     * @return
     */
    String getRcToken(String userId);

    /********************************************在线用户的key





    /**
     * 设置登录用户的key
     * @param userId
     */
    void setLoginKey(String userId);

    /***
     * 获取在线用户的key
     * @return
     */
    Set<String> getLoginKeys();

    /***
     * 是否有登录的肯
     * @param userId
     * @return
     */
    boolean hasLoginKey(String userId);




    /*****************************************登录JWT_TOKEN的处理***************************/



    /**
     * 设置用户的JWT_TOKEN
     * @param userId
     */
    void setJwtToken(String userId,String token);

    /**
     * 删除JWT_TOKEN
     * @param userId
     */
    void deleteJwtToken(String userId);

    String getJwtToken(String userId);

    /*******************************************未支付pw订单的处理*****************************/
    /**
     * 添加未支付的订单的到redis中
     *
     * @param orderNo 订单号
     * @param addTime 下单时间
     */
    void addPwNotPay(String orderNo, Date addTime);


    /***
     * 获取未支付的订单 前100个
     * @return
     */
    List<String> getPwNotPays();





    /***
     *  批量删除过期的 pw的订单
     *  其他地方不需要删除redis中过期的 在此处统一删除
     *
     * @param needDeleteSize 过期的pw的个数
     */
    void deletePwNotPays(Integer needDeleteSize);

    void addPwNotFinish(String orderNo);

    List<String> getPwNotAllFinish();

    void deletePwNotFinishes(Integer needDeleteSize);
}
