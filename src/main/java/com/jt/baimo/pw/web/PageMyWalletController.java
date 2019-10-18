package com.jt.baimo.pw.web;

import com.jt.baimo.pw.constant.TagCst;
import com.jt.baimo.pw.model.RechargeConfig;
import com.jt.baimo.pw.model.UserBindAlipay;
import com.jt.baimo.pw.service.PageMyWalletService;
import com.jt.baimo.pw.util.AppUtil;
import com.jt.baimo.pw.vo.ResultData;
import com.jt.baimo.pw.vo.WalletBmbRecordsVo;
import com.jt.baimo.pw.vo.WalletTxbInRecordsVo;
import com.jt.baimo.pw.vo.WalletTxbOutRecordsVo;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.validation.constraints.Digits;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

/**
 * @author Forever丶诺
 * @data 2019/9/19 18:38
 */
@RestController
@Validated
@RequestMapping("wallet")
public class PageMyWalletController {


    @Autowired
    private PageMyWalletService pageMyWalletService;


    @GetMapping("rechargeConfigList")
    @ApiOperation(tags = {TagCst.MY_WALLET}, value = "01-01-获取充值配置-沙增达")
    public ResultData<RechargeConfig> getRechargeConfigList() {
        return pageMyWalletService.getRechargeConfigList();
    }

    @GetMapping("bmbRecords")
    @ApiOperation(tags = {TagCst.MY_WALLET}, value = "01-03-获取百陌币的流动明细-沙增达")
    @ApiImplicitParams(
            {
                    @ApiImplicitParam(name = "pageSize", value = "每页显示的个数", required = true, dataType = "int", paramType = "query"),
                    @ApiImplicitParam(name = "lastAddTime", value = "最后一个数据的时间", dataType = "dateTime"),

            }
    )
    public ResultData<List<WalletBmbRecordsVo>> getBmbRecords(@NotNull Integer pageSize, Date lastAddTime, HttpServletRequest request) {
        return pageMyWalletService.getBmbRecords(AppUtil.getUserId(request), pageSize, lastAddTime);
    }


    @PostMapping("bindAli")
    @ApiOperation(tags = TagCst.MY_WALLET, value = "02-01-绑定支付宝账号-沙增达")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "account", value = "账号", paramType = "query", required = true, example = "123"),
            @ApiImplicitParam(name = "realName", value = "真实姓名", paramType = "query", required = true, example = "张三")
    })
    public ResultData bindAli(@NotBlank String account, @NotBlank String realName, HttpServletRequest request) {
        String userId = AppUtil.getUserId(request);
        return pageMyWalletService.bindAli(userId, account, realName);
    }


    @GetMapping("bindAli")
    @ApiOperation(tags = TagCst.MY_WALLET, value = "02-02-获取绑定的支付宝账号信息-沙增达")
    public ResultData<UserBindAlipay> getBindAli(HttpServletRequest request) {
        return pageMyWalletService.getBindAli(AppUtil.getUserId(request));
    }


    @PostMapping("transfer")
    @ApiOperation(tags = TagCst.MY_WALLET, value = "02-03-提现-沙增达", notes = "404:1,请先绑定支付宝账号再进行提现;2,余额不足;4:提现失败")
    @ApiImplicitParams({

            @ApiImplicitParam(name = "money", value = "手续后的金额", paramType = "query", dataType = "BigDecimal", required = true, example = "0.1")
    })
    public ResultData transfer(@Digits(integer = 65, fraction = 2) BigDecimal money, HttpServletRequest request) {
        return pageMyWalletService.transfer(AppUtil.getUserId(request), money);
    }


    @GetMapping("txbInRecords")
    @ApiOperation(tags = {TagCst.MY_WALLET}, value = "02-04-获取提现币的收入记录-沙增达")
    @ApiImplicitParams(
            {
                    @ApiImplicitParam(name = "pageSize", value = "每页显示的个数", required = true, dataType = "int", paramType = "query"),
                    @ApiImplicitParam(name = "lastAddTime", value = "最后一个数据的时间", dataType = "dateTime"),

            }
    )
    public ResultData<List<WalletTxbInRecordsVo>> getTxbInRecords(@NotNull Integer pageSize, Date lastAddTime, HttpServletRequest request) {
        return pageMyWalletService.getTxbInRecords(AppUtil.getUserId(request), pageSize, lastAddTime);
    }


    @GetMapping("txbOutRecords")
    @ApiOperation(tags = {TagCst.MY_WALLET}, value = "02-05-获取提现币的提出记录-沙增达")
    @ApiImplicitParams(
            {
                    @ApiImplicitParam(name = "pageSize", value = "每页显示的个数", required = true, dataType = "int", paramType = "query"),
                    @ApiImplicitParam(name = "lastAddTime", value = "最后一个数据的时间", dataType = "dateTime"),

            }
    )
    public ResultData<List<WalletTxbOutRecordsVo>> getTxbOutRecords(@NotNull Integer pageSize, Date lastAddTime, HttpServletRequest request) {
        return pageMyWalletService.getTxbOutRecords(AppUtil.getUserId(request), pageSize, lastAddTime);
    }


}
