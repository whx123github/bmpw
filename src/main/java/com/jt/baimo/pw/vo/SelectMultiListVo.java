package com.jt.baimo.pw.vo;


import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.experimental.Accessors;

/**
 * @author Forever丶诺
 * @data 2019/7/24 17:03
 */
@Data
@Accessors(chain = true)
public class SelectMultiListVo extends BaseTreeObj<SelectMultiListVo, Integer> {

    private static final long serialVersionUID = 4300117126817778613L;


    @ApiModelProperty(value = "名称")
    private String name;


}
