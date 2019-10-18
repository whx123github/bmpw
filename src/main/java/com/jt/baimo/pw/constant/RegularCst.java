package com.jt.baimo.pw.constant;

/**
 * 用来存放常用的正则表达式
 * <p>
 * Created by yb_li on 2019/1/10.
 */
public interface RegularCst {

    /**
     * 用来匹配手机号码的正则
     */
    String MOBILE_PHONE_REGULAR = "^[1][0-9]{10}$";

    /**
     * 用来做效验密码的正则
     */
    String PASSWORD_REGULAR = "^(?![0-9]+$)(?![a-zA-Z]+$)[0-9A-Za-z]{4,16}$";
    /**
     * 姓名正则
     */
    String REALNAME_REGULAR = "^[\\u4e00-\\u9fa5_a-zA-Z0-9_]{0,16}$";
    String REALNAME_MSG = "姓名格式错误!";

    /**
     * 身份证的校验
     */
    String ID_CARD_REGULAR = "^[1-9]{1}[0-9]{14}$|^[1-9]{1}[0-9]{16}([0-9]|[X])$";
    String ID_CARD_MSG = "身份证证件号格式错误,X支持大写!";


    /**
     * 验证码的6位校验
     */
    String IDENTIFY_CODE_REGULAR = "\\d{6}";

    String INTEGER_REG ="^[1-9]\\d*$";





}
