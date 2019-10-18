package com.jt.baimo.pw.vo;

import com.fasterxml.jackson.annotation.JsonIgnore;
import io.swagger.annotations.ApiModelProperty;
import jdk.nashorn.internal.ir.annotations.Ignore;
import lombok.Data;
import lombok.experimental.Accessors;
import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.Range;

import javax.validation.constraints.Digits;
import javax.validation.constraints.NotBlank;
import java.util.Date;

/**
 * @author Forever丶诺
 * @data 2019/7/19 12:13
 */
@Data
@Accessors(chain = true)
public class UserSimpleInfoVo {


    @ApiModelProperty(value = "用户id")
    private String id;

    @ApiModelProperty(value = "头像地址")
    private String headerUrl;


    @ApiModelProperty(value = "昵称")
    private String nickname;




    @ApiModelProperty(value = "年龄")
    private Integer age;


    @ApiModelProperty(value = "星座")
    private String constellation;



    @ApiModelProperty(value = "城市名称")
    private String cityName;



    @ApiModelProperty(value = "性别:(1:男,0:女)")
    private Integer sex;



    @ApiModelProperty(value = "生日")
    private Date birthday;


    @ApiModelProperty(value = "职业Id")
    private String professionId;



    @ApiModelProperty(value = "职业")
    private String professionName;


    @ApiModelProperty(value = "语音地址")
    private String voiceUrl;





    @ApiModelProperty(value = "身高")
    private String height;


    @ApiModelProperty(value = "体重")
    private String weight;



    @ApiModelProperty(value = "个人介绍")
    private String presentation;





    @ApiModelProperty(value = "uid")
    private String uid;


    @ApiModelProperty(value = "手机号")
    private String userTel;




}
