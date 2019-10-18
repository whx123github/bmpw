package com.jt.baimo.pw.web;

import com.jt.baimo.pw.constant.TagCst;
import com.jt.baimo.pw.service.UserOrderService;
import com.jt.baimo.pw.util.AppUtil;
import com.jt.baimo.pw.util.DesUtil;
import com.jt.baimo.pw.vo.ResultData;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

import java.math.BigDecimal;
import java.util.Date;

import static com.jt.baimo.pw.util.DesUtil.AIR_TICKET_SECRET_KEY;

/**
 * @author Forever丶诺
 * @data 2019/9/19 19:35
 */
@RestController
@RequestMapping("order")
@Validated
public class UserOrderController {


    @Autowired
    private UserOrderService userOrderService;


    @PostMapping("recharge")
    @ApiOperation(tags = {TagCst.ORDER, TagCst.MY_WALLET}, value = "01-02-充值-沙增达")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "rechargeId", value = "充值的id", paramType = "form", required = true, example = "示例值")
    })
    public ResultData recharge(@NotNull Integer rechargeId, HttpServletRequest request) {
        return userOrderService.recharge(rechargeId, AppUtil.getUserId(request));
    }






    @PostMapping("buyAlbum")
    @ApiOperation(tags = TagCst.HOME_ONE_ALBUM, value = "03-购买相册-沙增达")
    @ApiImplicitParam(name = "albumId", value = "照片或视频的id", paramType = "form", dataType = "int", required = true)
    public ResultData buyAlbum(Integer albumId, HttpServletRequest request) {
        return userOrderService.buyAlbum(albumId, AppUtil.getUserId(request));
    }


    @PostMapping("notPayPw")
    @ApiOperation(tags = TagCst.HOME_ONE_SKILL, value = "02-创建一个未支付的陪玩订单-沙增达", notes = "唯一字段 orderNo:订单号")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "skillPwId", value = "陪玩技能的id", paramType = "form", dataType = "int", required = true),
            @ApiImplicitParam(name = "quantity", value = "购买的数量", paramType = "form", dataType = "int", required = true),
            @ApiImplicitParam(name = "serviceTime", value = "服务的时间", paramType = "form", dataType = "datetime", required = true),
    })
    public ResultData creatNotPayPw(Integer skillPwId, Integer quantity, Date serviceTime, HttpServletRequest request) {
        return userOrderService.creatNotPayPw(skillPwId, AppUtil.getUserId(request), quantity, serviceTime);
    }

    @PostMapping("payPw")
    @ApiOperation(tags = {TagCst.HOME_ONE_SKILL, TagCst.My_Skill_PW}, value = "04-支付陪玩订单-沙增达")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "skillPwId", value = "陪玩技能的id", paramType = "form", dataType = "int", required = true),
    })
    public ResultData payPw(String orderNo, HttpServletRequest request) {
        return userOrderService.payPw(orderNo, AppUtil.getUserId(request));
    }

}
