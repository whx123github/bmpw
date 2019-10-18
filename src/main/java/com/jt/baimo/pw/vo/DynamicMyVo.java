package com.jt.baimo.pw.vo;

import com.baomidou.mybatisplus.annotation.FieldFill;
import com.baomidou.mybatisplus.annotation.TableField;
import com.fasterxml.jackson.annotation.JsonIgnore;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.experimental.Accessors;

import java.util.Date;

/**
 * @author Ms.WenJing
 * @Description:
 * @Date 2019/10/17 13:46
 */
@Data
@Accessors(chain = true)
public class DynamicMyVo {
    @ApiModelProperty(value = "动态id")
    private Integer id;

    @ApiModelProperty(value = "发布动态人UID")
    private String uid;

    @ApiModelProperty(value = "动态内容")
    private String content;

    @ApiModelProperty(value = "动态配图")
    private String picture;

    @ApiModelProperty(value = "动态发布时间")
    @TableField(fill = FieldFill.INSERT)
    private Date addTime;

    @ApiModelProperty(value = "是否点赞(1:是,0:不是)")
    private Integer isLike;

    @ApiModelProperty(value = "动态点赞的数量")
    private Integer count;

    @ApiModelProperty(value = "是否在线(1:在线,0:不在线)")
    private Integer isLogin;

    @ApiModelProperty(value = "距离")
    private double distance;

    @ApiModelProperty(value = "年龄")
    private Integer age;

    @ApiModelProperty(value = "性别:(1:男,0:女)")
    private Integer sex;

    @ApiModelProperty(value = "昵称")
    private String nickname;

    @ApiModelProperty(value = "头像地址")
    private String headerUrl;

    @ApiModelProperty(value = "城市名称")
    private String cityName;

    @JsonIgnore
    private Date birthday;

    @JsonIgnore
    private Double lng;

    @JsonIgnore
    private Double lat;

    @ApiModelProperty(value = "是否顾问认证: 0 是没有 其他认证")
    private Integer isAuth;
}
