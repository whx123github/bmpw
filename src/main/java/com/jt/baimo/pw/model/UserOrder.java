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
 * 用户订单表
 * </p>
 *
 * @author Forever丶诺
 * @since 2019-09-19
 */
@Data
@Accessors(chain = true)
public class UserOrder implements Serializable {

    @ApiModelProperty(value = "主键id")
    @TableId(type = IdType.INPUT)
    private String id;


    @ApiModelProperty(value = "购买者id")
    private String buyerId;

    @ApiModelProperty(value = "出售者id")
    private String sellerId;

    @ApiModelProperty(value = "支付状态 (支付状态 ( 1,未支付 2,待接单 3 待服务 4进行中 5陪玩完成  -1:取消 0: 完成))")
    private Integer status;

    @ApiModelProperty(value = "订单金额")
    private BigDecimal orderAmount;

    @ApiModelProperty(value = "总金额")
    private BigDecimal totalAmount;

    @ApiModelProperty(value = "实际付款金额")
    private BigDecimal payAmonut;

    @ApiModelProperty(value = "支付方式(1:支付宝,2微信,3ios支付,4:bmb)")
    private Integer payType;

    @ApiModelProperty(value = "订单类型(1:充值 2:机票购买 -1:相册 -2:陪玩 )")
    private Integer type;

    @ApiModelProperty(value = "新增时间")
    @TableField(fill = FieldFill.INSERT)
    private Date addTime;

    @ApiModelProperty(value = "更新时间")
    @TableField(fill = FieldFill.INSERT_UPDATE)
    private Date updateTime;

    @ApiModelProperty(value = "支付时间")
    private Date payTime;

    @ApiModelProperty(value = "订单详情")
    private String detail;


    @ApiModelProperty(value = "订单数量")
    private Integer quantity;

    @ApiModelProperty(value = "服务时间")
    private Date serviceTime;


    @ApiModelProperty(value = "取消类型(1:待付款超时 ,2: 玩家取消 3:客服取消 )")
    private Integer cancelType;

    @ApiModelProperty(value = "购买的技能Id")
    private Integer buySkillId;

    @ApiModelProperty(value = "陪玩的图片证明")
    private String pwImgs;



    @ApiModelProperty(value = "取消订单原因类型")
    private Integer cancelReasonType;


    @ApiModelProperty(value = "取消订单原因")
    private String cancelReason;


    @ApiModelProperty(value = "管理员id")
    private String aid;


    @ApiModelProperty(value = "操作时间")
    private Date adminTime;

}
