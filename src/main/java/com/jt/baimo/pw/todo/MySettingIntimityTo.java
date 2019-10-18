package com.jt.baimo.pw.todo;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.experimental.Accessors;

/**
 * @author Forever丶诺
 * @data 2019/9/18 16:36
 */
@Data
@Accessors(chain = true)
public class MySettingIntimityTo {

    @ApiModelProperty(value = "动态发布提醒:1:开 0:关")
    private Integer dynamicType;

    @ApiModelProperty(value = "私聊提醒开关:1:开 0:关")
    private Integer intimityType;
}
