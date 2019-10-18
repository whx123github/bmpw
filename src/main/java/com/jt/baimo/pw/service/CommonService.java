package com.jt.baimo.pw.service;



import com.alibaba.fastjson.JSONObject;
import com.jt.baimo.pw.model.UserOrder;
import com.jt.baimo.pw.vo.ResultData;
import com.jt.baimo.pw.vo.SelectListVo;
import com.jt.baimo.pw.vo.SelectMultiListVo;
import com.jt.baimo.pw.vo.UserInfoVo;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.List;

/**
 * @author Forever丶诺
 * @data 2019/7/19 14:12
 */
public interface CommonService {
    ResultData<List<SelectListVo>> listSelect(String typeName);

    ResultData<List<SelectMultiListVo>> listMultiSelect(String typeName);


    boolean existUser(String userTel);


    void rechargeSuccess(UserOrder userOrder, Integer payType);

    ResultData getTargetCommonInfo(String targetId);

    void  buyGoods(String orderNo, BigDecimal price, String buyerId, String sellerId, Integer type);

    void  buyGoods(UserOrder userOrder, Integer type);


    void buyGoodsAndSellGoods(UserOrder userOrder, Integer type);

    void  sellGoods(String orderNo, BigDecimal price, String buyerId, String sellerId, Integer type);


    void  sellGoods(UserOrder userOrder, Integer type);


    BigDecimal updateAddUserBmb(String userId, BigDecimal price);


    void updateAddUserBmb(String orderNo, BigDecimal price, String userId, Integer type, String targetId, String detail);

    /***
     * 新增用户的bmb
     * @param orderNo 订单号
     * @param price
     * @param userId
     * @param type
     * @param targetId
     */
    void updateAddUserBmb(String orderNo, BigDecimal price, String userId, Integer type, String targetId);

    BigDecimal updateSubUserBmb(String userId, BigDecimal price);


    BigDecimal updateAddUserTxb(String userId, BigDecimal price);


    JSONObject updateSubUserTxb(String userId, BigDecimal price);

    String createOrderNo(String module, String userId);
}
