package com.jt.baimo.pw.vo;

import io.swagger.annotations.ApiModelProperty;
import jdk.nashorn.internal.ir.annotations.Ignore;
import lombok.Data;
import lombok.experimental.Accessors;

import java.util.Date;

/**
 * @author Forever丶诺
 * @data 2019/9/19 14:05
 */
@Data
@Accessors(chain = true)
public class HomeOneUserInfoVo {


    @ApiModelProperty(value = "背景地址")
    private String backgroundUrl;


    @ApiModelProperty(value = "背景类型:(3是视频,1和2都是图片)")
    private String backgroundType;


    @ApiModelProperty(value = "头像地址")
    private String headerUrl;




    @ApiModelProperty(value = "关注数")
    private Integer attentionNum;


    @ApiModelProperty(value = "是否关注: 1:关注, 0:未关注")
    private Integer isAttention;


    @ApiModelProperty(value = "语音地址")
    private String voiceUrl;




    @ApiModelProperty(value = "昵称")
    private String nickname;





    @ApiModelProperty(value = "是否在线")
    private String isLogin;


    @ApiModelProperty(value = "生日", hidden = true)
    @Ignore
    private Date birthday;

    @ApiModelProperty(value = "年龄")
    private Integer age;


    @ApiModelProperty(value = "性别:(1:男,0:女)")
    private Integer sex;


    @ApiModelProperty(value = "城市名称")
    private String cityName;

    @ApiModelProperty(value = "身高")
    private String height;

    @ApiModelProperty(value = "体重")
    private String weight;


    @ApiModelProperty(value = "职业")
    private String professionName;


    @ApiModelProperty(value = "个人介绍")
    private String presentation;


    @ApiModelProperty(value = "主键id")
    private String id;


    @ApiModelProperty(value = "是否顾问认证: 0 是没有 其他认证")
    private Integer authNum;


}
