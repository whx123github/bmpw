package com.jt.baimo.pw.service.impl;

import cn.hutool.system.UserInfo;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.serializer.SerializerFeature;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.fcibook.quick.http.QuickHttp;
import com.jt.baimo.pw.mapper.UserMapper;
import com.jt.baimo.pw.mapper.UserOrderMapper;
import com.jt.baimo.pw.model.User;
import com.jt.baimo.pw.model.UserOrder;
import com.jt.baimo.pw.service.AirTicketService;
import com.jt.baimo.pw.util.MD5Util;
import com.jt.baimo.pw.vo.ResultData;
import org.joda.time.DateTime;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.io.UnsupportedEncodingException;
import java.math.BigDecimal;
import java.net.URLEncoder;
import java.util.Base64;

import static com.jt.baimo.pw.constant.AirTicketCst.*;


/**
 * @author Forever丶诺
 * @data 2019/6/13 11:23
 */
@Service
public class AirTicketServiceImpl implements AirTicketService {






    @Autowired
    private UserMapper userMapper;


    @Autowired
    private UserOrderMapper userOrderMapper;






    @Override
    public ResultData getPortal(String userId) {
        String timestamp = String.valueOf(System.currentTimeMillis());
        String signSource = AGENT_ID + AGENT_KEY + userId + timestamp;
        String sign = MD5Util.encrypt(signSource);
        JSONObject param = new JSONObject();
        param.put("AgentId", AGENT_ID);
        param.put("UserId", userId);
        User user = userMapper.selectById(userId);
        param.put("UserName", user.getNickname());
        param.put("Mobile", user.getUserTel());
        param.put("Timestamp", timestamp);
        param.put("Sign", sign);
        String paramJson = Base64.getEncoder().encodeToString(param.toJSONString().getBytes());
        String encode = null;
        try {
            encode = URLEncoder.encode(paramJson, "utf-8");
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        return  new ResultData<>().oneKeyDate("param",encode);
    }




    @Override
    public void execNotifyQt(String orderNo) {
        for (int i = 0; i < 10; i++) {
            int result = notifyQt(orderNo);
            if (result == 1) {
                break;
            }
        }

    }


    @Override
    public int notifyQt(String orderNo) {
        int resultVal = 0;
        try {
            JSONObject params = new JSONObject();
            params.put("Header", headData());
            JSONObject jsonObject = detailRequestBoy(orderNo);
            params.put("RequestBody", jsonObject.toJSONString());
            String bodyJson = JSON.toJSONString(params, SerializerFeature.WriteMapNullValue);
            String responseData = new QuickHttp().post().url(URL_NOTIFY)
                    .addHeader("Content-type", "application/json; charset=utf-8").setBodyContent(bodyJson)
                    .text();
            JSONObject returnData = JSON.parseObject(responseData);
            resultVal = returnData.getJSONObject("responseBody").getIntValue("Result");
        } catch (Exception e) {
            e.printStackTrace();
            return resultVal;
        }
        return resultVal;
    }


    private JSONObject detailRequestBoy(String orderNo) {
        UserOrder userOrder = userOrderMapper.selectById(orderNo);
        String detail = userOrder.getDetail();
        JSONObject jsonObject = JSONObject.parseObject(detail);
        String airNo = jsonObject.getString("airNo");
        BigDecimal price = userOrder.getPayAmonut();
        JSONObject detailBody = new JSONObject();
        detailBody.put("MainOrderNo", airNo);
        detailBody.put("Price", price);
        detailBody.put("IsSuccess", true);
        detailBody.put("Message", "支付成功");
        return detailBody;
    }


    /**
     * 请求的Header数据
     *
     * @return
     */
    private static JSONObject headData() {
        JSONObject headData = new JSONObject();
        String requestTime = new DateTime(System.currentTimeMillis()).toString();
        String signSource = requestTime + AGENT_KEY + AGENT_ID;
        String sign = MD5Util.encrypt(signSource);

        headData.put("Action", 703);
        headData.put("AgentId", AGENT_ID);
        headData.put("RequestId", "76a02974-1e4c-4013-aa57-27f96b82ee94");

        headData.put("RequestTime", requestTime);
        headData.put("Signature", sign);
        return headData;
    }


}
