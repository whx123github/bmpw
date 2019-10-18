package com.jt.baimo.pw.model;

import java.math.BigDecimal;

import com.baomidou.mybatisplus.annotation.*;

import java.time.LocalDateTime;

import java.io.Serializable;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.experimental.Accessors;

import java.util.Date;

/**
 * <p>
 * 用户表
 * </p>
 *
 * @author Forever丶诺
 * @since 2019-09-18
 */
@Data
@Accessors(chain = true)
public class User implements Serializable {

    @TableId(type = IdType.UUID)

    @ApiModelProperty(value = "主键")
    private String id;

    @ApiModelProperty(value = "手机号")
    private String userTel;

    @ApiModelProperty(value = "密码")
    private String userPwd;

    @ApiModelProperty(value = "新增时间")
    @TableField(fill = FieldFill.INSERT)
    private Date addTime;

    @ApiModelProperty(value = "更新时间")
    @TableField(fill = FieldFill.INSERT_UPDATE)
    private Date updateTime;

    @ApiModelProperty(value = "状态:1(注册,未设置性别),2(设置性别,未设置其他必填信息),3(设置了性别切设置其他必填信息)")
    private Integer status;

    @ApiModelProperty(value = "最后登录时间")
    private Date lastTime;

    @ApiModelProperty(value = "百陌币")
    private BigDecimal bmCoin;

    @ApiModelProperty(value = "版本控制")
    @Version
    private Integer version;

    @ApiModelProperty(value = "性别:(1:男,0:女)")
    private Integer sex;

    @ApiModelProperty(value = "昵称")
    private String nickname;

    @ApiModelProperty(value = "生日")
    private Date birthday;

    @ApiModelProperty(value = "身高")
    private String height;

    @ApiModelProperty(value = "体重")
    private String weight;

    @ApiModelProperty(value = "个人介绍")
    private String presentation;

    @ApiModelProperty(value = "经度")
    private Double lng;

    @ApiModelProperty(value = "纬度")
    private Double lat;

    @ApiModelProperty(value = "城市编码")
    private String cityCode;

    @ApiModelProperty(value = "城市名称")
    private String cityName;

    @ApiModelProperty(value = "是否游戏认证(1:是,0:不是)")
    private Integer isVideoCert;

    @ApiModelProperty(value = "是否在线(1:在线,0:不在线)")
    private Integer isLogin;

    @ApiModelProperty(value = "职业")
    private String professionId;

    @ApiModelProperty(value = "封禁时间")
    private Date closedTime;

    @ApiModelProperty(value = "解封原因")
    private String openReason;

    @ApiModelProperty(value = "用户uid")
    private Long uid;

    @ApiModelProperty(value = "极光推送id")
    private String jPushId;

    @ApiModelProperty(value = "封禁理由")
    private String closedReason;

    @ApiModelProperty(value = "语音地址")
    private String voiceUrl;

    @ApiModelProperty(value = "头像地址")
    private String headerUrl;

    @ApiModelProperty(value = "提现币")
    private BigDecimal txCoin;

    @ApiModelProperty(value = "注册IP")
    private String registerIP;

    @ApiModelProperty(value = "登陆IP")
    private String loginIP;


    @ApiModelProperty(value = "渠道Id")
    private String placeId;
}
