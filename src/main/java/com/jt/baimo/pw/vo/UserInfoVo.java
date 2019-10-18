package com.jt.baimo.pw.vo;

import com.fasterxml.jackson.annotation.JsonIgnore;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.experimental.Accessors;

import java.util.Date;

/**
 * @author Ms.WenJing
 * @Description:
 * @Date 2019/9/19 9:58
 */
@Data
@Accessors(chain = true)
public class UserInfoVo {

    @ApiModelProperty(value = "uid")
    private String id;

    @ApiModelProperty(value = "年龄")
    private Integer age;

    @ApiModelProperty(value = "性别:(1:男,0:女)")
    private Integer sex;

    @ApiModelProperty(value = "昵称")
    private String nickname;

    @ApiModelProperty(value = "昵称审核状态，1审核中,2已通过,3未通过")
    private String nicknameStatus;

    @ApiModelProperty(value = "头像地址")
    private String headerUrl;

    @ApiModelProperty(value = "头像审核状态，1审核中,2已通过,3未通过")
    private String headerUrlStatus;

    @ApiModelProperty(value = "是否在线(1:在线,0:不在线)")
    private Integer isLogin;

    @ApiModelProperty(value = "是否游戏认证(1:是,0:不是)")
    private Integer isVideoCert;

    @ApiModelProperty(value = "城市名称")
    private String cityName;

    @ApiModelProperty(value = "名称")
    private String name;

    @ApiModelProperty(value = "排序字段")
    private Integer sort;

    @ApiModelProperty(value = "游戏的图标")
    private String url;

    @ApiModelProperty(value = "游戏对应的示例图")
    private String exampleImage;

    @ApiModelProperty(value = "游戏技能审核状态，1待审核，2已通过，3未通过")
    private String aStatus;

    @ApiModelProperty(value = "游戏的颜色")
    private String color;

    @JsonIgnore
    private Date birthday;

    @JsonIgnore
    private Double lng;

    @JsonIgnore
    private Double lat;
}
