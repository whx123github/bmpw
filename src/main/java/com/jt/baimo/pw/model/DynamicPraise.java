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
 * 动态点赞表
 * </p>
 *
 * @author Ms.WenJing
 * @since 2019-09-17
 */
@Data
@Accessors(chain = true)
public class DynamicPraise implements Serializable {

    @ApiModelProperty(value = "唯一ID")
    private Integer id;

    @ApiModelProperty(value = "动态表ID")
    private Integer dynamicId;

    @ApiModelProperty(value = "点赞人的ID")
    private String uid;

    @ApiModelProperty(value = "点赞时间")
    @TableField(fill = FieldFill.INSERT)
    private Date addTime;

}
