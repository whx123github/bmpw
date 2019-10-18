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
public class TargetCommonInfoVo {

    @ApiModelProperty(value = "主键")
    private String id;


    @ApiModelProperty(value = "头像地址")
    private String headerUrl;



    @ApiModelProperty(value = "昵称")
    private String nickname;



}
