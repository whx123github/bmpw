package com.jt.baimo.pw.todo;


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
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

/**
 * <p>
 *
 * </p>
 *
 * @author Forever丶诺
 * @since 2019-09-18
 */
@Data
@Accessors(chain = true)
public class AuthenticationTo implements Serializable {


    @ApiModelProperty(value = "认证真实名称")
    @Length(max = 30)
    private String realName;

    @ApiModelProperty(value = "身份证号码")
    @Pattern(regexp = RegularCst.ID_CARD_REGULAR, message = "请输入正确身份证号码")
    private String idCard;


    @Length(max = 20)
    @ApiModelProperty(value = "段位")
    @NotBlank
    private String rank;

    @Length(max = 20)
    @ApiModelProperty(value = "所在游戏大区")
    @NotBlank
    private String region;

    @Length(max = 20)
    @ApiModelProperty(value = "擅长位置")
    private String position;

    @ApiModelProperty(value = "收费金额")
    @Digits(integer = 3, fraction = 2)
    @NotNull
    private BigDecimal chargeAmount;


    @ApiModelProperty(value = "收费标准 0免费；1按小时收费；2按单局收费；3按天收费")
    @NotNull
    private Integer type;

    @ApiModelProperty(value = "认证图片")
    @NotBlank
    private String image;

    @ApiModelProperty(value = "技能id")
    @NotNull
    private Integer gameId;

    @ApiModelProperty(value = "接单日期-周")
    @NotBlank
    private String week;

    @ApiModelProperty(value = "接单开始时间")
    @NotNull
    private Date startTime;

    @ApiModelProperty(value = "接单结束时间")
    @NotNull
    private Date endTime;

    @ApiModelProperty(value = "顾问大分类id")
    private Integer gwTypeId;

    @ApiModelProperty(value = "认证图片(旅游的,健身的认证图片)")
    private String image2;



}
