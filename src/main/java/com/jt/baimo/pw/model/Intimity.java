package com.jt.baimo.pw.model;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.experimental.Accessors;

import java.io.Serializable;

/**
 * <p>
 * 系统设置表
 * </p>
 *
 * @author Ms.WenJing
 * @since 2019-09-17
 */
@Data
@Accessors(chain = true)
public class Intimity implements Serializable {

    @ApiModelProperty(value = "主键")
    private Integer id;

    @ApiModelProperty(value = "用户唯一标识")
    private String uid;

    @ApiModelProperty(value = "动态发布提醒:1:开 0:关")
    private Integer dynamicType;

    @ApiModelProperty(value = "私聊提醒开关:1:开 0:关")
    private Integer intimityType;

}
