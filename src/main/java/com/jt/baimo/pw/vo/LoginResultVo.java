package com.jt.baimo.pw.vo;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.experimental.Accessors;

/**
 * @author Forever丶诺
 * @data 2019/7/18 18:14
 */
@Data
@Accessors(chain = true)
public class LoginResultVo {
    @ApiModelProperty("token值")
    private String token;
    
    @ApiModelProperty("用户id")
    private String userId;

    @ApiModelProperty("状态:1(注册,未设置性别),2(设置性别,未设置其他必填信息),3(设置了性别切设置其他必填信息)")
    private Integer status;

    @ApiModelProperty(value = "性别:(1:男,0:女)")
    private Integer sex;

}
