package com.jt.baimo.pw.service.impl;


import cn.hutool.extra.qrcode.QrCodeUtil;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.alipay.api.AlipayApiException;
import com.alipay.api.AlipayClient;
import com.alipay.api.DefaultAlipayClient;
import com.alipay.api.domain.AlipayTradeAppPayModel;
import com.alipay.api.internal.util.AlipaySignature;
import com.alipay.api.request.AlipayTradeAppPayRequest;
import com.alipay.api.request.AlipayTradePrecreateRequest;
import com.alipay.api.response.AlipayTradePrecreateResponse;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.fcibook.quick.http.QuickHttp;

import com.jt.baimo.pw.config.properties.PayProperties;
import com.jt.baimo.pw.constant.AliPayConfig;
import com.jt.baimo.pw.mapper.BmbRecordMapper;
import com.jt.baimo.pw.mapper.RechargeConfigMapper;
import com.jt.baimo.pw.mapper.UserMapper;
import com.jt.baimo.pw.mapper.UserOrderMapper;
import com.jt.baimo.pw.model.BmbRecord;
import com.jt.baimo.pw.model.RechargeConfig;
import com.jt.baimo.pw.model.UserOrder;
import com.jt.baimo.pw.service.AirTicketService;
import com.jt.baimo.pw.service.CommonService;
import com.jt.baimo.pw.service.PayService;
import com.jt.baimo.pw.service.UserOrderService;
import com.jt.baimo.pw.util.AliPayUtil;
import com.jt.baimo.pw.util.Assert;
import com.jt.baimo.pw.vo.ResultData;
import io.netty.util.internal.UnstableApi;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.apache.http.entity.ContentType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.math.BigDecimal;
import java.util.Date;
import java.util.Map;

import static com.jt.baimo.pw.constant.CommonCst.IOS_URL_VERIFY_RECEIPT;
import static com.jt.baimo.pw.constant.CommonCst.IOS_URL_VERIFY_RECEIPT_SANDBOX;


/**
 * @author Forever丶诺
 * @data 2019/6/4 19:26
 */
@Service
@Slf4j
public class PayServiceImpl implements PayService {


    @Autowired
    private UserOrderMapper userOrderMapper;


    @Autowired
    private PayProperties payProperties;


    @Autowired
    private CommonService commonService;


    @Autowired
    private AirTicketService airTicketService;

    /**
     * 支付接口
     *
     * @param orderNo
     * @return
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public ResultData pay(String orderNo, Integer payType) {

        UserOrder userOrder = userOrderMapper.selectById(orderNo);
        Assert.isNull(userOrder, "该订单不存在");
        Assert.isTrue(userOrder.getStatus() != 1, "该订单已经支付");
        ResultData resultData = new ResultData();
        try {        //查看订单是否存在
            if (1 == payType) {
                resultData = aliPay(userOrder);
            }
        } catch (Exception e) {
            Assert.isTrue("支付失败");
        }
        userOrderMapper.updateById(new UserOrder().setPayType(payType).setId(userOrder.getId()).setPayTime(new Date()));
        return resultData;
    }


    @Override
    @Transactional(rollbackFor = Exception.class)
    public ResultData iosPay(String orderNo, String receipt, String transactionId) {
        UserOrder userOrder = userOrderMapper.selectById(orderNo);
        Assert.isNull(userOrder, "该订单不存在");
        Assert.isTrue(userOrder.getStatus() != 1, "该订单已经支付");

        JSONObject jsonObject = new JSONObject();
        jsonObject.put("receipt-data", receipt);
        QuickHttp quickHttp = new QuickHttp().post().setContentType(ContentType.APPLICATION_JSON).setBodyContent(jsonObject.toJSONString());
        String result = quickHttp.url(IOS_URL_VERIFY_RECEIPT).text();


        Assert.isTrue(result == null, "苹果验证失败，返回数据为空");
        JSONObject appResult = JSONObject.parseObject(result);
        String status = appResult.getString("status");
        if ("21007".equals(status)) {
            result = quickHttp.url(IOS_URL_VERIFY_RECEIPT_SANDBOX).text();
            appResult = JSONObject.parseObject(result);
            status = appResult.getString("status");
        }

        // 前端所提供的收据是有效的    验证成功
        Assert.isTrue(!"0".equals(status), "支付失败，错误码：" + status);

        JSONArray inApps = appResult.getJSONObject("receipt").getJSONArray("in_app");
        for (Object inApp : inApps) {
            JSONObject inAppJson = JSONObject.parseObject(JSONObject.toJSONString(inApp));
            String appTransactionId = inAppJson.getString("transaction_id");
            if (transactionId.equals(appTransactionId)) {

                commonService.rechargeSuccess(userOrder, 3);
                return new ResultData();
            }
        }
        return new ResultData().failMsgResult("当前交易不在交易列表中");
    }


    /**
     * 支付宝支付
     *
     * @param
     * @return
     */
    private ResultData aliPay(UserOrder userOrder) throws AlipayApiException {
        AlipayClient alipayClient = new DefaultAlipayClient(AliPayConfig.SERVICE_ULR, AliPayConfig.APP_ID, AliPayConfig.APP_PRIVATE_KEY, AliPayConfig.FORMAT, AliPayConfig.CHARSET_UTF8, AliPayConfig.PUBLIC_KEY, AliPayConfig.SIGN_TYPE);
        AlipayTradeAppPayRequest request = new AlipayTradeAppPayRequest();
        String price = userOrder.getPayAmonut().toString();
        AlipayTradeAppPayModel payModel = AliPayUtil.getPayModel(userOrder.getId(), price);
        request.setNotifyUrl(payProperties.getAliNotifyUrl());
        request.setBizModel(payModel);
        String resultData = alipayClient.sdkExecute(request).getBody();//可以直接给客户端请求，无需再做处理。
        log.info("diao yong zhifu jiekou");
        return new ResultData().oneKeyDate("payOrder", resultData);
    }


    @Override
    @Transactional(rollbackFor = Exception.class)
    public String notifyUrl(HttpServletRequest request) {
        Map<String, String> params = AliPayUtil.getNotifyRequestMap(request);
        log.info("++++++++++++++++++++:" + JSON.toJSONString(params));
        try {
            boolean flag = AlipaySignature.rsaCheckV1(params, AliPayConfig.PUBLIC_KEY, AliPayConfig.CHARSET_UTF8, AliPayConfig.SIGN_TYPE);
            log.info("++++++++++++++++++++++++++flag:" + params);
            if (flag) {
                // 验证订单信息
                String orderNo = params.get("out_trade_no");
                BigDecimal notifyMoney = new BigDecimal(params.get("total_amount")); //通知的支付金额
                UserOrder userOrder = userOrderMapper.selectOne(new LambdaQueryWrapper<UserOrder>().eq(UserOrder::getId, orderNo).eq(UserOrder::getPayAmonut, notifyMoney));
                if (userOrder == null) {
                    return "fail";
                }
                Integer type = userOrder.getType();
                if (type == 1) {   //  充值的
                    commonService.rechargeSuccess(userOrder, 1);
                } else if (type == 2) { //购买机票的
                    userOrderMapper.updateById(userOrder.setStatus(0));
                    airTicketService.execNotifyQt(orderNo);
                }
                return "success";
            } else {
                return "fail";
            }
        } catch (AlipayApiException e) {
            Assert.isTrue(true, "通知执行失败");
            e.printStackTrace();
            return "fail";
        }
    }


    @Override
    public ResponseEntity payNative(String orderNo, HttpServletResponse response) {
        try {
            String money = "0.01";
            log.info("获取的金额:{}" + money);

            //根据订单查询出金额
            AlipayClient alipayClient = new DefaultAlipayClient(AliPayConfig.SERVICE_ULR, AliPayConfig.APP_ID, AliPayConfig.APP_PRIVATE_KEY, AliPayConfig.FORMAT, AliPayConfig.CHARSET_UTF8, AliPayConfig.PUBLIC_KEY, AliPayConfig.SIGN_TYPE);
            AlipayTradePrecreateRequest request = new AlipayTradePrecreateRequest();
            request.setBizModel(AliPayUtil.getNativePayModel(orderNo, money));
            AlipayTradePrecreateResponse aliResponse = alipayClient.execute(request);
            // QrCodeUtil.encode(aliResponse.getQrCode(),300, 300,"png");
            QrCodeUtil.generate(aliResponse.getQrCode(), 300, 300, "png" , response.getOutputStream());
            // GenerateQrCodeUtil.encodeQrcode(aliResponse.getQrCode(), response);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }




}
