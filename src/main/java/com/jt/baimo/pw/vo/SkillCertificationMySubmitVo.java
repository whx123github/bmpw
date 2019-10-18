package com.jt.baimo.pw.vo;

import com.jt.baimo.pw.constant.RegularCst;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.experimental.Accessors;
import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.Range;

import javax.validation.constraints.Digits;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import java.math.BigDecimal;
import java.util.Date;

/**
 * @author Forever丶诺
 * @data 2019/9/18 20:52
 */
@Data
@Accessors(chain = true)
public class SkillCertificationMySubmitVo {


    @ApiModelProperty(value = "技能图标")
    private String skillUrl;

    @ApiModelProperty(value = "技能名称")
    private String skillName;

    @ApiModelProperty(value = "段位")
    private String rank;

    @ApiModelProperty(value = "所在游戏大区")
    private String region;


    @ApiModelProperty(value = "接单日期-周")
    private String week;


    @ApiModelProperty(value = "接单开始时间")
    private Date startTime;

    @ApiModelProperty(value = "接单结束时间")
    private Date endTime;


    @ApiModelProperty(value = "收费金额")
    private BigDecimal chargeAmount;


    @ApiModelProperty(value = "收费标准，1按小时收费，2按单局收费")
    private Integer type;

    @ApiModelProperty(value = "认证图片")
    private String image;

    @ApiModelProperty(value = "认证图片(旅游的,健身的认证图片)")
    private String image2;


    @ApiModelProperty(value = "顾问分类名称(全部返回)")
    private String gwTypeName;


}
