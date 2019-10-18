package com.jt.baimo.pw.vo;

import com.fasterxml.jackson.annotation.JsonIgnore;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.experimental.Accessors;

import java.util.Date;

/**
 * @author Forever丶诺
 * @data 2019/9/19 10:58
 */
@Data
@Accessors(chain = true)
public class HomeUserSearchListVo {


    @ApiModelProperty(value = "用户id")
    private String id;


    @ApiModelProperty(value = "头像地址")
    private String headerUrl;


    @ApiModelProperty(value = "昵称")
    private String nickname;




    @ApiModelProperty(value = "生日")
    @JsonIgnore
    private Date birthday;

    @ApiModelProperty(value = "年龄")
    private Integer age;


    @ApiModelProperty(value = "是否在线")
    private Integer isLogin;




    @ApiModelProperty(value = "性别:(1:男,0:女)")
    private Integer sex;




    @ApiModelProperty(value = "审核时间")
    private Date auditTime;


    @ApiModelProperty(value = "uid")
    private Integer uid;


}
