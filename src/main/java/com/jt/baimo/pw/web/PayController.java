package com.jt.baimo.pw.web;


import com.jt.baimo.pw.constant.TagCst;
import com.jt.baimo.pw.service.PayService;
import com.jt.baimo.pw.vo.ResultData;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.hibernate.validator.constraints.Range;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

/**
 * @author Forever丶诺
 * @data 2019/7/23 17:11
 */
@RestController
@RequestMapping("pay")
@Validated
@Slf4j
public class PayController {


    @Autowired
    private PayService payService;

    @ApiOperation(value = "00-支付接口-沙增达", tags = TagCst.ORDER, notes = "唯一key: payOrder: 支付order")
    @PostMapping
    @ApiImplicitParams({
            @ApiImplicitParam(name = "orderNo", value = "订单号", paramType = "query", required = true, example = "11"),
            @ApiImplicitParam(name = "payType", value = "支付方式:支付宝1,微信2", paramType = "query", required = true, allowableValues = "1,2"),
    })
    public ResultData pay(@NotBlank String orderNo, @NotNull @Range(min = 1, max = 2) Integer payType) {
        return payService.pay(orderNo, payType);
    }


    @ApiOperation(value = "00-iosPay的支付确认-沙增达", tags = TagCst.ORDER, notes = "唯一key: payOrder: 支付order")
    @PostMapping("iosPay")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "orderNo", value = "订单号", paramType = "query", required = true),
            @ApiImplicitParam(name = "receipt", value = "收据", paramType = "query", required = true),
            @ApiImplicitParam(name = "transactionId", value = "苹果的交易id", paramType = "query", required = true),
    })
    public ResultData iosPay(@NotBlank String orderNo, @NotBlank String receipt, @NotBlank String transactionId) {
        return payService.iosPay(orderNo, receipt, transactionId);
    }

    @PostMapping("notifyUrl")
    @ApiOperation(value = "支付宝异步通知接口",hidden = true)
    public String notifyUrl(HttpServletRequest request) {
        log.info("zhixing zhifubao huidiao ...................................");
        return payService.notifyUrl(request);
    }


    @GetMapping("payNative")
    @ApiOperation(value = "支付宝二维码接口",hidden = true)
    @ApiImplicitParam(name = "orderNo", value = "订单号")
    public ResponseEntity payNative(@RequestParam(required = false) String orderNo, HttpServletResponse response) {
        orderNo = "baimo-" + System.currentTimeMillis();
        return payService.payNative(orderNo, response);
    }


}
