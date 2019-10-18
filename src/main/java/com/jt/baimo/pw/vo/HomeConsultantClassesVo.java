package com.jt.baimo.pw.vo;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.experimental.Accessors;

/**
 * @author Forever丶诺
 * @data 2019/10/14 14:23
 */
@Data
@Accessors(chain = true)
public class HomeConsultantClassesVo {
    @ApiModelProperty(value = "主键id")
    private Integer id;

    @ApiModelProperty(value = "名称")
    private String name;
}
