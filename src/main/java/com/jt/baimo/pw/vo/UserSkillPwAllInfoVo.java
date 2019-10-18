package com.jt.baimo.pw.vo;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.experimental.Accessors;

import java.math.BigDecimal;
import java.util.Date;

/**
 * @author Forever丶诺
 * @data 2019/9/20 18:01
 */
@Data
@Accessors(chain = true)
public class UserSkillPwAllInfoVo {

    @ApiModelProperty(value = "主键id")
    private Integer id;

    @ApiModelProperty(value = "用户的id")
    private String uid;

    @ApiModelProperty(value = "认证真实名称")
    private String realName;

    @ApiModelProperty(value = "身份证号码")
    private String idCard;

    @ApiModelProperty(value = "段位")
    private String rank;

    @ApiModelProperty(value = "所在游戏大区")
    private String region;

    @ApiModelProperty(value = "擅长位置")
    private String position;

    @ApiModelProperty(value = "收费金额")
    private BigDecimal chargeAmount;

    @ApiModelProperty(value = "收费标准，1按小时收费，2按单局收费")
    private Integer type;

    @ApiModelProperty(value = "认证图片")
    private String image;

    @ApiModelProperty(value = "技能id")
    private Integer gameId;

    @ApiModelProperty(value = "接单日期-周")
    private String week;

    @ApiModelProperty(value = "接单开始时间")
    private Date startTime;

    @ApiModelProperty(value = "接单结束时间")
    private Date endTime;





    @ApiModelProperty(value = "技能名称")
    private String skillName;

    @ApiModelProperty(value = "技能的图标")
    private String skillUrl;

    @ApiModelProperty(value = "技能的示例图标")
    private String skillExampleImage;


    @ApiModelProperty(value = "技能的颜色")
    private String skillColor;


    @ApiModelProperty(value = "顾问大分类id")
    private Integer gwTypeId;

    @ApiModelProperty(value = "认证图片(旅游的,健身的认证图片)")
    private String image2;



}
