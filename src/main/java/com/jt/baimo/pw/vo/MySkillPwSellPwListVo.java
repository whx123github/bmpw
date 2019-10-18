package com.jt.baimo.pw.vo;

import com.baomidou.mybatisplus.annotation.FieldFill;
import com.baomidou.mybatisplus.annotation.TableField;
import com.fasterxml.jackson.annotation.JsonIgnore;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.experimental.Accessors;

import java.math.BigDecimal;
import java.util.Date;

/**
 * @author Forever丶诺
 * @data 2019/9/20 18:30
 */
@Data
@Accessors(chain = true)
public class MySkillPwSellPwListVo {

    @ApiModelProperty(value = "主键id")
    private String id;



    @ApiModelProperty(value = "购买者用户id")
    private String buyerId;

    @ApiModelProperty(value = "购买者昵称")
    private String buyerNickname;

    @ApiModelProperty(value = "购买者昵称的审核状态 : 审核状态，1审核中,2已通过,3已拒绝")
    private String buyerNicknameStatus;

    @ApiModelProperty(value = "出售者用户id")
    private String sellerId;

    @ApiModelProperty(value = "出售者昵称")
    private String sellerNickname;

    @ApiModelProperty(value = "出售者昵称的审核状态 : 审核状态，1审核中,2已通过,3已拒绝")
    private String sellerNicknameStatus;


    @ApiModelProperty(value = "技能名称")
    private String skillName;


    @ApiModelProperty(value = "技能图标")
    private String skillUrl;


    @ApiModelProperty(value = "服务时间")
    private Date serviceTime;


    @ApiModelProperty(value = "订单数量")
    private Integer quantity;


    @ApiModelProperty(value = "付款金额")
    private BigDecimal payAmonut;


    @ApiModelProperty(value = "支付状态 (支付状态 ( 1,未支付 2,待接单 3 待服务 4进行中 5陪玩完成  -1:取消 0: 完成))")
    private Integer status;

    @ApiModelProperty(value = "取消类型(1:待付款超时 ,2: 玩家取消 3:客户取消 )")
    private Integer cancelType;

    @ApiModelProperty(value = "新增时间")
    private Date addTime;

    @ApiModelProperty(value = "支付时间")
    private Date payTime;


    @ApiModelProperty(value = "订单详情",hidden = true)
    @JsonIgnore
    private String detail;

    @ApiModelProperty(value = "订单详情对象")
    private UserSkillPwAllInfoVo detailData;


}
