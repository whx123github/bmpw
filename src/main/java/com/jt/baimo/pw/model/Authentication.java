package com.jt.baimo.pw.model;

import java.math.BigDecimal;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.Version;
import com.baomidou.mybatisplus.annotation.TableId;
import java.time.LocalDateTime;
import com.baomidou.mybatisplus.annotation.FieldFill;
import com.baomidou.mybatisplus.annotation.TableField;
import java.io.Serializable;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.experimental.Accessors;
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
public class Authentication implements Serializable {

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


    @ApiModelProperty(value = "认证图片(旅游的,健身的认证图片)")
    private String image2;

    @ApiModelProperty(value = "技能id")
    private Integer gameId;

    @ApiModelProperty(value = "接单日期-周")
    private String week;

    @ApiModelProperty(value = "接单开始时间")
    private Date startTime;

    @ApiModelProperty(value = "接单结束时间")
    private Date endTime;

    @ApiModelProperty(value = "审核状态，1待审核，2已通过，3未通过")
    private Integer status;

    @ApiModelProperty(value = "添加时间")
    @TableField(fill = FieldFill.INSERT)
    private Date addTime;

    @ApiModelProperty(value = "更新时间")
    @TableField(fill = FieldFill.INSERT_UPDATE)
    private Date updateTime;

    @ApiModelProperty(value = "审核原因")
    private String auditReason;

    @ApiModelProperty(value = "操作管理员id")
    private Integer aid;


    @ApiModelProperty(value = "gw的分类")
    private Integer gwTypeId;

}
