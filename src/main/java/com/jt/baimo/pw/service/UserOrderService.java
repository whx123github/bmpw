package com.jt.baimo.pw.service;

import com.alibaba.fastjson.JSONObject;
import com.jt.baimo.pw.vo.ResultData;

import java.math.BigDecimal;
import java.util.Date;

/**
 * @author Forever丶诺
 * @data 2019/9/19 20:34
 */
public interface UserOrderService {
    ResultData recharge(Integer rechargeId, String userId);



    ResultData buyAlbum(Integer albumId, String userId);

    ResultData creatNotPayPw(Integer skillPwId, String userId, Integer quantity, Date serviceTime);




    ResultData payPw(String orderNo, String userId);

    ResultData createAirTicketOrder(BigDecimal price, String airNo, String userId);
}
