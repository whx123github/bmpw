package com.jt.baimo.pw.web;

import com.jt.baimo.pw.constant.TagCst;
import com.jt.baimo.pw.constant.ValidMsgCst;
import com.jt.baimo.pw.model.User;
import com.jt.baimo.pw.myannotation.MyNotBank;
import com.jt.baimo.pw.service.UserService;
import com.jt.baimo.pw.todo.LocationTo;
import com.jt.baimo.pw.todo.UserEditTo;
import com.jt.baimo.pw.util.AppUtil;
import com.jt.baimo.pw.vo.LoginResultVo;
import com.jt.baimo.pw.vo.ResultData;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.Range;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;

import static com.jt.baimo.pw.constant.RegularCst.MOBILE_PHONE_REGULAR;
import static com.jt.baimo.pw.constant.TagCst.LOGIN_REGISTER;


/**
 * @author Forever丶诺
 * @data 2019/7/18 15:07
 */
@RestController
@RequestMapping("user")
@Validated
public class UserController {

    @Autowired
    private UserService userService;


    @ApiOperation(tags = LOGIN_REGISTER, value = "01-注册-01-手机密码-沙增达")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "userPwd", value = "密码", paramType = "form"),
            @ApiImplicitParam(name = "userTel", value = "手机号", paramType = "form", required = true),
            @ApiImplicitParam(name = "identifyCode", value = "验证码", paramType = "form", required = true),
            @ApiImplicitParam(name = "jPushId", value = "极光推送的id", paramType = "form"),
            @ApiImplicitParam(name = "registerIP", value = "注册IP", paramType = "form")
    })
    @PostMapping("register")
    public ResultData<LoginResultVo> register(
            @NotBlank @Pattern(regexp = MOBILE_PHONE_REGULAR, message = "{incorrect.phone.number}") String userTel,
            @MyNotBank(name = "密码", message = "{MyNotBank}") @Length(min = 8, max = 16, message = "{length.in.between}") String userPwd,
            @NotBlank @Length(min = 6, max = 6, message = "{length.must.be}") String identifyCode,
            String jPushId, String registerIP, String placeId) {
        return userService.register(userTel, userPwd, identifyCode, jPushId, registerIP, placeId);
    }


    @PutMapping("sex")
    @ApiOperation(tags = LOGIN_REGISTER, value = "01-注册-02性别设置-沙增达")
    @ApiImplicitParam(name = "sex", value = "性别:1男0女", allowableValues = "1,0", required = true, paramType = "form", dataType = "int")
    public ResultData initSex(@NotNull @Range(max = 1, message = "{incorrect.sex}") Integer sex, HttpServletRequest request) {
        String userId = AppUtil.getUserId(request);
        return userService.updateSex(userId, sex);
    }

    @PostMapping("skip")
    @ApiOperation(tags = {LOGIN_REGISTER, TagCst.NEW_VERSION_3_ADD}, value = "01-注册-跳过-沙增达")
    public ResultData skip(HttpServletRequest request) {
        userService.updateById(new User().setStatus(2).setId(AppUtil.getUserId(request)));
        return new ResultData();
    }


    @PutMapping("initInfo")
    @ApiOperation(tags = {LOGIN_REGISTER, TagCst.NEW_VERSION_3_UPDATE}, value = "01-注册-03-初始化必填信息-沙增达", notes = "(版本3修改:新增参数:sex)")
    public ResultData initInfo(HttpServletRequest request, @Valid @RequestBody UserEditTo userEditTo, BindingResult result) {
        String userId = AppUtil.getUserId(request);
        return userService.initInfo(userId, userEditTo);
    }


    @ApiOperation(tags = LOGIN_REGISTER, value = "02-登录-沙增达")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "userPwd", value = "密码", paramType = "form"),
            @ApiImplicitParam(name = "userTel", value = "手机号", paramType = "form"),
            @ApiImplicitParam(name = "jPushId", value = "极光推送的id", paramType = "form")
    })
    @PostMapping("pwdLogin")
    public ResultData pwdLogin(
            @NotBlank @Pattern(regexp = MOBILE_PHONE_REGULAR, message = "{incorrect.phone.number}") String userTel,
            @NotBlank @Length(min = 8, max = 16, message = "{length.in.between}") String userPwd, String jPushId) {
        return userService.pwdLogin(userTel, userPwd, jPushId);
    }


    @PutMapping("forgetPwd")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "userPwd", value = "密码", paramType = "form"),
            @ApiImplicitParam(name = "userTel", value = "手机号", paramType = "form"),
            @ApiImplicitParam(name = "identifyCode", value = "验证码", paramType = "form"),
            @ApiImplicitParam(name = "jPushId", value = "极光推送的id", paramType = "form")
    })
    @ApiOperation(tags = {LOGIN_REGISTER}, value = "03-忘记密码(表单)-沙增达")
    public ResultData forgetPwd(@NotBlank @Pattern(regexp = MOBILE_PHONE_REGULAR, message = ValidMsgCst.TEL_NOT_RIGHT) String userTel,
                                @NotBlank @Length(min = 8, max = 16, message = "{length.in.between}") String userPwd,
                                @NotBlank @Length(min = 6, max = 6, message = "{length.must.be}") String identifyCode,
                                String jPushId
    ) {
        return userService.forgetPwd(userTel, userPwd, identifyCode, jPushId);
    }


    @PutMapping("location")
    @ApiOperation(tags = TagCst.COMMON, value = "01-上传位置信息-沙增达")
    public ResultData uploadLocation(@RequestBody LocationTo locationTo, HttpServletRequest request) {
        String userId = AppUtil.getUserId(request);
        return userService.uploadLocation(userId, locationTo);
    }


}
