package com.jt.baimo.pw.enumerate;

import static com.jt.baimo.pw.constant.RedisPrefixCst.*;

/**
 * 短信验证码
 *
 * @author Forever丶诺
 * @data 2019/9/17 19:59
 */
public enum SmsCodeEnum implements EnumCommon {

    REGISTERED(SMS_REGISTERED, "注册"),
    FORGET(SMS_FORGET, "忘记密码");



    private String value; //枚举value字段
    private String desc; //枚举描述字段


    SmsCodeEnum(String value, String desc) {
        this.value = value;
        this.desc = desc;
    }

    @Override
    public String getValue() {
        return value;
    }

    @Override
    public String getDesc() {
        return desc;
    }


}
