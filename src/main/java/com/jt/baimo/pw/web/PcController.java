package com.jt.baimo.pw.web;

import com.jt.baimo.pw.constant.TagCst;
import com.jt.baimo.pw.service.PcService;
import com.jt.baimo.pw.todo.PcAdminCancelPwOrderTo;
import com.jt.baimo.pw.vo.ResultData;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

/**
 * 后台请求的接口
 *
 * @author Forever丶诺
 * @data 2019/9/25 10:41
 */
@RestController
@RequestMapping("pc")
@Validated
@Slf4j
@Api(tags = {TagCst.PC})
public class PcController {


    @Autowired
    private PcService pcService;


    @PutMapping("adminCancelPwOrder")
    @ApiOperation(value = "01-客服取消订单-沙增达")
/*    @ApiImplicitParams({
            @ApiImplicitParam(name = "id", value = "订单主键id", paramType = "form", required = true),
            @ApiImplicitParam(name = "cancelReason", value = "取消订单原因", paramType = "form", required = true),
            @ApiImplicitParam(name = "aid", value = "系统管理员id", paramType = "form", required = true),
    })*/
    public ResultData adminCancelPwOrder(@RequestBody @Valid PcAdminCancelPwOrderTo pcAdminCancelPwOrderTo, BindingResult result) {
        return pcService.adminCancelPwOrder(pcAdminCancelPwOrderTo.getId(), pcAdminCancelPwOrderTo.getCancelReason(), pcAdminCancelPwOrderTo.getAid());
    }


}
