package com.jt.baimo.pw.vo;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.experimental.Accessors;

import java.math.BigDecimal;
import java.util.Date;

/**
 * @author Forever丶诺
 * @data 2019/9/19 23:27
 */
@Data
@Accessors(chain = true)
public class WalletTxbOutRecordsVo {
    @ApiModelProperty(value = "主键id")
    private Integer id;


    @ApiModelProperty(value = "对应的订单id")
    private String orderId;

    @ApiModelProperty(value = "支付宝返回的状态消息")
    private String msg;

    @ApiModelProperty(value = "支付宝返回的消息")
    private String subMsg;


    @ApiModelProperty(value = "金额")
    private BigDecimal money;

    @ApiModelProperty(value = "剩余bmb")
    private BigDecimal balance;

    @ApiModelProperty(value = "新增时间")
    private Date addTime;




}
