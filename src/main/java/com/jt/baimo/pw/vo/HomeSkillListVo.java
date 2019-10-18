package com.jt.baimo.pw.vo;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.experimental.Accessors;

/**
 * @author Forever丶诺
 * @data 2019/9/18 22:44
 */
@Data
@Accessors(chain = true)
public class HomeSkillListVo {

    @ApiModelProperty(value = "id")
    private Integer id;


    @ApiModelProperty(value = "名称")
    private String name;
}
