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
 * 审核原因表
 * </p>
 *
 * @author Ms.WenJing
 * @since 2019-09-17
 */
@Data
@Accessors(chain = true)
public class AuditReason implements Serializable {

    @ApiModelProperty(value = "主键id")
    private Integer id;

    @ApiModelProperty(value = "标签内容")
    private String reason;

    @ApiModelProperty(value = "推送的模板内容")
    private String content;

    @ApiModelProperty(value = "排序")
    private Integer sort;

    @ApiModelProperty(value = "0:隐藏 1:显示")
    private Integer status;

    @ApiModelProperty(value = "父级id")
    private Integer pid;

    @ApiModelProperty(value = "添加时间")
    @TableField(fill = FieldFill.INSERT)
    private Date addTime;

    @TableField(fill = FieldFill.INSERT_UPDATE)
    private Date updateTime;

}
