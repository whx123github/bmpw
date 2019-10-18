package com.jt.baimo.pw.vo;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.experimental.Accessors;

import java.io.Serializable;

/**
 * <p>
 * 技能分类表
 * </p>
 *
 * @author Ms.WenJing
 * @since 2019-09-18
 */
@Data
@Accessors(chain = true)
public class SkillVo extends BaseTreeObj<SkillVo, Integer> implements Serializable {

    @ApiModelProperty(value = "名称")
    private String name;

    @ApiModelProperty(value = "排序字段")
    private Integer sort;

    @ApiModelProperty(value = "游戏的图标")
    private String url;

    @ApiModelProperty(value = "游戏对应的示例图")
    private String exampleImage;

}
