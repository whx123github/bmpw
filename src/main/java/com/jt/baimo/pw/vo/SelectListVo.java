package com.jt.baimo.pw.vo;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.experimental.Accessors;

/**
 * @author Forever丶诺
 * @data 2019/7/19 14:05
 */
@Data
@Accessors(chain = true)
public class SelectListVo {

    @ApiModelProperty(value = "主键")
    private Integer id;


    @ApiModelProperty(value = "名称")
    private String name;
}
