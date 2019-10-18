package com.jt.baimo.pw.constant;

/**
 * 提示信息的常量
 * @author Forever丶诺
 */
public interface FailMsgCst {


    /****注册**********************************/
    String TEL_EXIST_MSG = "手机号已注册";
    String TEL_IDF_CODE_NOT_MATCH = "您的验证码已失效，请重新获取验证码";

    String TEL_IDF_CODE_NOT_MATCH_MSG2 = "密码连续输错超过次数,请5分钟后重新输入";
    String TEL_IDF_CODE_NOT_MATCH_FORMAT = "密码连续输错超过次数,请{0}后重新输入";

    String TEL_PWD_NOT_MATCH = "您输入的账号或者密码错误";
    String UPDATE_FAIL = "修改失败";
    String USER_NOT_EXIST = "用户不存在";


    /*******************************订单先关的*/


    String RY_TOKEN_FAIL ="容云token获取失败";


    String ORDER_PAY_MONEY_NOT_RIGHT = "支付金额不对";




}
