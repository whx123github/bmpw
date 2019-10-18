package com.jt.baimo.pw.model;

import com.baomidou.mybatisplus.annotation.TableField;
import com.jt.baimo.pw.vo.BaseTreeObj;
import com.jt.baimo.pw.vo.SelectMultiListVo;
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
public class Skill implements Serializable {
    @ApiModelProperty(value = "id")
    private Integer id;

    @ApiModelProperty(value = "id")
    private Integer pid;

    @ApiModelProperty(value = "名称")
    private String name;

    @ApiModelProperty(value = "排序字段")
    private Integer sort;

    @ApiModelProperty(value = "游戏的图标")
    private String url;

    @ApiModelProperty(value = "游戏对应的示例图")
    private String exampleImage;
    
    @ApiModelProperty(value = "技能显示的颜色")
    private String color;

    @ApiModelProperty(value = "技能所属的顾问")
    private String gwTypeId;

}
