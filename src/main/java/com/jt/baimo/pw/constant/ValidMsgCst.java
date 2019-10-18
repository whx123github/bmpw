package com.jt.baimo.pw.constant;

/**
 * 前缀控制层_字段_验证类型
 * @author Forever丶诺
 * @data 2019/5/27 13:18
 */
public interface ValidMsgCst {

    String UUID_NOT_BANK = "uuid不能为空";

    String NOT_NULL ="不能为空";
    String UUID_IS_NOTNULL="对方ID不能为空";
    String LAST_ID ="lastId";
    String SIZE ="size";

    String TEL_NOT_RIGHT= "请输入正确的手机号码";
    String SEX_RANG= "输入正确的性别";

    /**
     * 动态模块
     */
    String CONTENT_MSG ="请输入动态内容1到150字符";
    String PICTURE_MSG ="请上传动态配图1到9张照片";






}
