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
 * 动态表
 * </p>
 *
 * @author Ms.WenJing
 * @since 2019-09-17
 */
@Data
@Accessors(chain = true)
public class Dynamic implements Serializable {

    @ApiModelProperty(value = "主键")
    private Integer id;

    @ApiModelProperty(value = "审核不通过原因")
    private String auditReason;

    @ApiModelProperty(value = "用户唯一编号")
    private String uid;

    @ApiModelProperty(value = "动态内容")
    private String content;

    @ApiModelProperty(value = "动态配图")
    private String picture;

    @ApiModelProperty(value = "审核状态，0不展示1审核中,2已通过,3未通过")
    private Integer status;

    @ApiModelProperty(value = "审核时间")
    private Date auditAddTime;

    @ApiModelProperty(value = "动态发布时间")
    @TableField(fill = FieldFill.INSERT)
    private Date addTime;

    @ApiModelProperty(value = "审核管理员id")
    private Integer aid;

}
