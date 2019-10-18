package com.jt.baimo.pw.model;

import java.math.BigDecimal;
import com.baomidou.mybatisplus.annotation.Version;
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
 * 百陌币流水记录明细
 * </p>
 *
 * @author Forever丶诺
 * @since 2019-09-19
 */
@Data
@Accessors(chain = true)
public class BmbRecord implements Serializable {

    @ApiModelProperty(value = "主键id")
    private Integer id;

    @ApiModelProperty(value = "对应的订单id")
    private String orderId;

    @ApiModelProperty(value = "(充值: +1 ,退款: +2 , 相册支出: -1 ,陪玩: -2)")
    private Integer type;

    @ApiModelProperty(value = "用户id")
    private String uid;

    @ApiModelProperty(value = "状态")
    private Integer status;

    @ApiModelProperty(value = "金额")
    private BigDecimal money;

    @ApiModelProperty(value = "剩余bmb")
    private BigDecimal balance;

    @ApiModelProperty(value = "新增时间")
    @TableField(fill = FieldFill.INSERT)
    private Date addTime;

    @ApiModelProperty(value = "更新")
    @TableField(fill = FieldFill.INSERT_UPDATE)
    private Date updateTime;

    @ApiModelProperty(value = "详情")
    private String detail;

    @ApiModelProperty(value = "对方id")
    private String targetId;

}
