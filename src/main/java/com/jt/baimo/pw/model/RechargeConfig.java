package com.jt.baimo.pw.model;

import java.math.BigDecimal;
import com.baomidou.mybatisplus.annotation.Version;
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
 * @since 2019-09-19
 */
@Data
@Accessors(chain = true)
public class RechargeConfig implements Serializable {

    @ApiModelProperty(value = "主键")
    private Integer id;

    @ApiModelProperty(value = "充值的金额")
    private BigDecimal money;

    @ApiModelProperty(value = "百陌币")
    private BigDecimal bmCoin;

    @ApiModelProperty(value = "苹果id")
    private String productId;

}
