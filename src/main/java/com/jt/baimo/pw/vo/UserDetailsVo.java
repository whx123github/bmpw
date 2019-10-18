package com.jt.baimo.pw.vo;

import com.fasterxml.jackson.annotation.JsonIgnore;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.experimental.Accessors;

import java.util.Date;

/**
 * 个人详情
 * @author Ms.WenJing
 * @Description:
 * @Date 2019/9/19 10:36
 */
@Data
@Accessors(chain = true)
public class UserDetailsVo {
    @ApiModelProperty(value = "用户id")
    private String id;

    @ApiModelProperty(value = "性别:(1:男,0:女)")
    private Integer sex;

    @ApiModelProperty(value = "昵称")
    private String nickname;

    @ApiModelProperty(value = "年龄")
    private Integer age;

    @ApiModelProperty(value = "身高")
    private String height;

    @ApiModelProperty(value = "体重")
    private String weight;

    @ApiModelProperty(value = "个人介绍")
    private String presentation;

    @ApiModelProperty(value = "个人介绍审核状态，1审核中,2已通过,3未通过")
    private String presentationStatus;

    @ApiModelProperty(value = "城市编码")
    private String cityCode;

    @ApiModelProperty(value = "城市名称")
    private String cityName;

    @ApiModelProperty(value = "是否游戏认证(1:是,0:不是)")
    private Integer isVideoCert;

    @ApiModelProperty(value = "是否在线(1:在线,0:不在线)")
    private Integer isLogin;

    @ApiModelProperty(value = "职业ID")
    private String professionId;

    @ApiModelProperty(value = "职业名称")
    private String professionName;

    @ApiModelProperty(value = "封禁时间")
    private Date closedTime;

    @ApiModelProperty(value = "封禁理由")
    private String closedReason;

    @ApiModelProperty(value = "语音地址")
    private String voiceUrl;

    @ApiModelProperty(value = "语音审核状态，1审核中,2已通过,3未通过")
    private String voiceUrlStatus;

    @ApiModelProperty(value = "头像地址")
    private String headerUrl;

    @ApiModelProperty(value = "头像审核状态，1审核中,2已通过,3未通过")
    private String headerUrlStatus;

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

    @JsonIgnore
    @ApiModelProperty(value = "图片地址")
    private String imageUrl;

    @JsonIgnore
    @ApiModelProperty(value = "视频地址")
    private String videoUrl;

    @JsonIgnore
    @ApiModelProperty(value = "图片审核状态，1审核中,2已通过,3已拒绝")
    private Integer imageUrlStatus;

    @JsonIgnore
    @ApiModelProperty(value = "视频审核状态，1审核中,2已通过,3已拒绝")
    private Integer videoUrlStatus;

    @ApiModelProperty(value = "游戏的颜色")
    private String color;

    @ApiModelProperty(value = "是否关注")
    private String isAttention;

    @ApiModelProperty(value = "关注数量")
    private Integer attentionNum;

    @ApiModelProperty(value = "背景地址")
    private String backgroundUrl;

    @ApiModelProperty(value = "背景类型:(3是视频,1和2都是图片)")
    private String backgroundType;

    @JsonIgnore
    private Date birthday;

    @JsonIgnore
    private Double lng;

    @JsonIgnore
    private Double lat;

}
