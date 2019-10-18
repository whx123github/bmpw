package com.jt.baimo.pw.todo;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.experimental.Accessors;
import org.hibernate.validator.constraints.Range;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

/**
 * @author Forever丶诺
 * @data 2019/7/19 9:29
 */
@Data
@Accessors(chain = true)
public class ReviewInitTo {

    @ApiModelProperty(value = "类型, 1头像，2语音", allowableValues = "1,2")
    @NotNull
    @Range(min = 1, max = 2)
    private Integer type;

    @ApiModelProperty(value = "地址")
    @NotBlank
    private String url;


}
