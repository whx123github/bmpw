package com.jt.baimo.pw.model;

import com.baomidou.mybatisplus.annotation.FieldFill;
import com.baomidou.mybatisplus.annotation.TableField;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.experimental.Accessors;

import java.io.Serializable;
import java.util.Date;

/**
 * <p>
 *菜单图标
 * </p>
 *
 * @author Ms.WenJing
 * @since 2019-09-23
 */
@Data
@Accessors(chain = true)
public class Menu implements Serializable {

    @ApiModelProperty(value = "主键id")
    private Integer id;

    @ApiModelProperty(value = "父级id")
    private Integer pid;

    @ApiModelProperty(value = "名称")
    private String name;

    @ApiModelProperty(value = "图片地址")
    private String imageUrl;

    @ApiModelProperty(value = "连接地址")
    private String linkUrl;

    @ApiModelProperty(value = "状态，1有效，2无效")
    private Integer status;

    @ApiModelProperty(value = "新增时间")
    @TableField(fill = FieldFill.INSERT)
    private Date addTime;

    @ApiModelProperty(value = "更新时间")
    @TableField(fill = FieldFill.INSERT_UPDATE)
    private Date updateTime;

    @ApiModelProperty(value = "位置:1.首页2.更多3工具")
    private Integer type;

    @ApiModelProperty(value = "排序字段")
    private Integer sort;

    @ApiModelProperty(value = "参数ID")
    private Integer parameId;

}
