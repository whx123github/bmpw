package com.jt.baimo.pw.web;

import com.jt.baimo.pw.constant.TagCst;
import com.jt.baimo.pw.model.Intimity;
import com.jt.baimo.pw.service.PageMySettingService;
import com.jt.baimo.pw.todo.MySettingIntimityTo;
import com.jt.baimo.pw.util.AppUtil;
import com.jt.baimo.pw.vo.MySettingIntimityVo;
import com.jt.baimo.pw.vo.ResultData;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.hibernate.validator.constraints.Length;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.validation.constraints.NotBlank;

/**
 * @author Forever丶诺
 * @data 2019/9/18 14:18
 */
@RestController
@Validated
@RequestMapping("setting")
public class PageMySettingController {


    @Autowired
    private PageMySettingService pageMySettingService;

    @PutMapping("password")
    @ApiOperation(tags = TagCst.MY_SETTING, value = "03-修改密码-沙增达")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "userPwd", value = "密码", paramType = "query", required = true, example = "123213")
    })
    public ResultData updatePassword(@NotBlank @Length(min = 8, max = 16, message = "{length.in.between}") String userPwd, HttpServletRequest request) {
        return pageMySettingService.updatePassword(userPwd, AppUtil.getUserId(request));
    }


    @PostMapping("exit")
    @ApiOperation(tags = {TagCst.MY_SETTING}, value = "04-退出-沙增达")
    public ResultData exit( HttpServletRequest request) {
        return pageMySettingService.exit(AppUtil.getUserId(request));
    }


    @GetMapping("pushSetting")
    @ApiOperation(tags = {TagCst.MY_SETTING}, value = "01-获取推送设置")
    public ResultData<MySettingIntimityVo> getPushSetting(HttpServletRequest request) {
        return pageMySettingService.getPushSetting(AppUtil.getUserId(request));
    }


    @PutMapping("pushSetting")
    @ApiOperation(tags = {TagCst.MY_SETTING}, value = "02-修改推送设置")
    public ResultData updatePushSetting(@RequestBody MySettingIntimityTo intimity, HttpServletRequest request) {
        return pageMySettingService.updatePushSetting(AppUtil.getUserId(request),intimity);
    }


}
