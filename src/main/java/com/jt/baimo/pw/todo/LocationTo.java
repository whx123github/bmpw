package com.jt.baimo.pw.todo;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.experimental.Accessors;

/**
 * @author Forever丶诺
 * @data 2019/7/19 13:26
 */
@Data
@Accessors(chain = true)
public class LocationTo {

    @ApiModelProperty(value = "经度")
    private Double lng;

    @ApiModelProperty(value = "纬度")
    private Double lat;

    @ApiModelProperty(value = "城市编码")
    private String cityCode;

    @ApiModelProperty(value = "城市名称")
    private String cityName;

    @ApiModelProperty(value = "登陆IP")
    private String loginIP;
}
