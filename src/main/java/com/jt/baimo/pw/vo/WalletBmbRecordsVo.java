package com.jt.baimo.pw.vo;

import com.baomidou.mybatisplus.annotation.FieldFill;
import com.baomidou.mybatisplus.annotation.TableField;
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
public class WalletBmbRecordsVo {
    @ApiModelProperty(value = "主键id")
    private Integer id;



    @ApiModelProperty(value = "(充值: +1 ,退款: +2 , 相册支出: -1 ,陪玩: -2)")
    private Integer type;


    @ApiModelProperty(value = "金额")
    private BigDecimal money;

    @ApiModelProperty(value = "剩余bmb")
    private BigDecimal balance;

    @ApiModelProperty(value = "新增时间")
    private Date addTime;


}
