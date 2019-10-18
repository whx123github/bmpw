package com.jt.baimo.pw.web;

import com.jt.baimo.pw.constant.TagCst;
import com.jt.baimo.pw.constant.ThirdPartyConfigCst;
import com.jt.baimo.pw.service.CommonService;
import com.jt.baimo.pw.service.SmsService;
import com.jt.baimo.pw.service.ThirdPartyService;
import com.jt.baimo.pw.util.AppUtil;
import com.jt.baimo.pw.vo.*;
import com.qiniu.api.auth.AuthException;
import com.qiniu.api.auth.digest.Mac;
import com.qiniu.api.rs.PutPolicy;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.json.JSONException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import java.util.List;

import static com.jt.baimo.pw.constant.RegularCst.MOBILE_PHONE_REGULAR;

/**
 * 一些通用的接口
 *
 * @author Forever丶诺
 * @data 2019/7/19 14:04
 */
@RestController
@Validated
public class CommonController {


    @Autowired
    private CommonService commonService;


    @Autowired
    private ThirdPartyService thirdPartyService;

    @Autowired
    private SmsService smsService;

    @GetMapping("/{typeName}/list")
    @ApiOperation(tags = {TagCst.LOGIN_REGISTER, TagCst.MY}, value = "获取下拉列表(编辑个人信息,注册初始化)-沙增达", hidden = true)
    @ApiImplicitParam(name = "typeName", value = "类别:(datingProgram:约会节目,expectedType:期望对象,skill:技能标签)", paramType = "path", allowableValues = "datingProgram,expectedType,skill", required = true)
    public ResultData<List<SelectListVo>> listSelect(@PathVariable @NotBlank String typeName) {
        return commonService.listSelect(typeName);
    }


    @GetMapping("/{typeName}/listMulti")
    @ApiOperation(tags = {TagCst.LOGIN_REGISTER, TagCst.MY}, value = "获取下拉列表多层(编辑个人信息,注册初始化)-沙增达")
    @ApiImplicitParam(name = "typeName", value = "类别:(profession:职业)", paramType = "path", allowableValues = "profession", required = true)
    public ResultData<List<SelectMultiListVo>> listMultiSelect(@PathVariable @NotBlank String typeName) {
        return commonService.listMultiSelect(typeName);
    }


    /**
     * 发送验证码
     *
     * @param userTel 用户手机号
     * @param smsType 发送类型
     * @return 返回状态
     */
    @GetMapping("/smsCode")
    @ApiOperation(tags = TagCst.COMMON, value = "短信验证码-沙增达")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "smsType", required = true, paramType = "query", dataType = "string", value = "短信类型: 注册:SMS_REGISTER_ ,忘记密码:SMS_FORGET_", allowableValues = "SMS_REGISTER_,SMS_FORGET_"),
            @ApiImplicitParam(name = "userTel", required = true, paramType = "query", dataType = "string", value = "发送的手机号")
    })
    public ResultData sendMsg(@NotBlank(message = "发送类型不能为空") String smsType,
                              @NotBlank @Pattern(regexp = MOBILE_PHONE_REGULAR, message = "{incorrect.phone.number}") String userTel) {
        return smsService.sendMsg(userTel, smsType);
    }


    /**
     * 获取七牛token
     *
     * @return
     * @throws AuthException
     * @throws JSONException
     */
    @GetMapping("qnToken/{typeName}")
    @ApiOperation(tags = TagCst.DSF, value = "01-获取七牛云的token-沙增达")
    @ApiImplicitParam(name = "typeName", value = "bmpw-img:图片的token,bmpw-video:视频的token", required = true, allowableValues = "bmpw-img,bmpw-video", paramType = "path")
    public ResultData getQnToken(@PathVariable String typeName) throws AuthException, JSONException {
        PutPolicy putPolicy = new PutPolicy(typeName);
        String token = putPolicy.token(new Mac(ThirdPartyConfigCst.QiNiuConfig.ACCESS_KEY, ThirdPartyConfigCst.QiNiuConfig.SECRET_KEY));
        return new ResultData().oneKeyDate("qnToken", token);
    }


    @GetMapping("token")
    @ApiOperation(tags = TagCst.DSF, value = "02-获取融云token-沙增达")
    public ResultData getToken(HttpServletRequest request) {
        String uuid = AppUtil.getUserId(request);
        return thirdPartyService.getToken(uuid);
    }

    @GetMapping("targetCommonInfo/{targetId}")
    @ApiOperation(tags = TagCst.COMMON, value = "02-获取融云token-沙增达")
    public ResultData<TargetCommonInfoVo> getTargetCommonInfo(@PathVariable @NotBlank String targetId) {

        return commonService.getTargetCommonInfo(targetId);
    }








}
