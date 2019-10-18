package com.jt.baimo.pw.vo;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.experimental.Accessors;

import java.math.BigDecimal;

/**
 * @author Forever丶诺
 * @data 2019/7/29 10:31
 */

@Data
@Accessors(chain = true)
public class AlbumMyVo {


    @ApiModelProperty("主键id")
    private Integer id;


    @ApiModelProperty("地址")
    private String url;


    @ApiModelProperty("类型，1图片，2视频")
    private Integer type;

    @ApiModelProperty("价格")
    private BigDecimal price;



}
