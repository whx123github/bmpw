package com.jt.baimo.pw.vo;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.experimental.Accessors;

import java.math.BigDecimal;
import java.util.Date;

/**
 * @author Forever丶诺
 * @data 2019/9/20 13:56
 */
@Data
@Accessors(chain = true)
public class MySkillPwTargetSkillVo {


    @ApiModelProperty(value = "pw技能的id")
    private Integer id;

    @ApiModelProperty(value = "技能名称")
    private String skillName;

    @ApiModelProperty(value = "段位")
    private String rank;

    @ApiModelProperty(value = "接单日期-周")
    private String week;

    @ApiModelProperty(value = "接单开始时间")
    private Date startTime;

    @ApiModelProperty(value = "接单结束时间")
    private Date endTime;

    @ApiModelProperty(value = "所在游戏大区")
    private String region;

    @ApiModelProperty(value = "擅长位置")
    private String position;

    @ApiModelProperty(value = "收费金额")
    private BigDecimal chargeAmount;

    @ApiModelProperty(value = "收费标准，1按小时收费，2按单局收费")
    private Integer type;


    @ApiModelProperty(value = "用户头像")
    private String headerUrl;

    @ApiModelProperty(value = "用户头像")
    private String nickname;


}
