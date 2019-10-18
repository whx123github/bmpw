package com.jt.baimo.pw.vo;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.experimental.Accessors;

import java.math.BigDecimal;

/**
 * @author Forever丶诺
 * @data 2019/9/19 15:37
 */
@Data
@Accessors(chain = true)
public class MyCommonInfoVo {

    @ApiModelProperty(value = "主键")
    private String id;

    @ApiModelProperty(value = "百陌币")
    private BigDecimal bmCoin;

    @ApiModelProperty(value = "提现币")
    private BigDecimal txCoin;

    @ApiModelProperty(value = "头像地址")
    private String headerUrl;



    @ApiModelProperty(value = "昵称")
    private String nickname;



    @ApiModelProperty(value = "状态:1(注册:没有跳过),3(设置了性别切设置其他必填信息)")
    private Integer status;

}
