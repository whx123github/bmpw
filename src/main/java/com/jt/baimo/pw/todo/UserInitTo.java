package com.jt.baimo.pw.todo;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.experimental.Accessors;

import javax.validation.Valid;
import javax.validation.constraints.Size;
import java.util.List;

/**
 * @author Forever丶诺
 * @data 2019/7/19 9:17
 */
@Data
@Accessors(chain = true)
public class UserInitTo extends UserEditTo {


    @ApiModelProperty(value = "需要认证的头像和语音")
    @Size(min = 1, max = 2, message = "头像不能为空")
    @Valid
    private List<ReviewInitTo> reviews;


}
