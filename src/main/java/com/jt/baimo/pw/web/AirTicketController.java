package com.jt.baimo.pw.web;

import com.jt.baimo.pw.constant.TagCst;
import com.jt.baimo.pw.service.AirTicketService;
import com.jt.baimo.pw.service.UserOrderService;
import com.jt.baimo.pw.util.AppUtil;
import com.jt.baimo.pw.util.DesUtil;
import com.jt.baimo.pw.vo.ResultData;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.validation.constraints.NotBlank;

import java.math.BigDecimal;

import static com.jt.baimo.pw.util.DesUtil.AIR_TICKET_SECRET_KEY;

/**
 * 票务的接口
 *
 * @author Forever丶诺
 * @data 2019/5/28 13:48
 */
@RestController
@RequestMapping("airTicket")
public class AirTicketController {

    @Autowired
    private AirTicketService airTicketService;


    @Autowired
    private UserOrderService userOrderService;

    /***
     * 获得擎天机票的认证
     * @return
     */
    @GetMapping("portalParam")
    @ApiOperation(tags = {TagCst.FOREIGN_API_AT}, value = "01-获得擎天机票的认证")
    public ResultData getPortal(HttpServletRequest request) {
        return airTicketService.getPortal(AppUtil.getUserId(request));
    }


    /**
     * 创建一个机票订单
     *
     * @return
     */
    @PostMapping("atOrder")
    @ApiOperation(tags = {TagCst.FOREIGN_API_AT}, value = "02-创建一个机票订单号")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "priceEn", value = "加密的价格", paramType = "form", required = true),
            @ApiImplicitParam(name = "airNoEn", value = "加密的机票号", paramType = "form", required = true),
    })
    public ResultData createAirTicketOrder(@NotBlank String priceEn, @NotBlank String airNoEn, HttpServletRequest request) {

        BigDecimal price = new BigDecimal(DesUtil.decode(AIR_TICKET_SECRET_KEY, priceEn));
        String airNo = DesUtil.decode(AIR_TICKET_SECRET_KEY, airNoEn);
        return userOrderService.createAirTicketOrder(price, airNo, AppUtil.getUserId(request));
    }





}
