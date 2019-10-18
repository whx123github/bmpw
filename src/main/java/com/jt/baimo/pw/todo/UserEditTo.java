package com.jt.baimo.pw.todo;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.experimental.Accessors;
import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.Range;

import javax.validation.constraints.*;
import java.util.Date;


/**
 * @author Forever丶诺
 * @data 2019/7/19 9:17
 */
@Data
@Accessors(chain = true)
public class UserEditTo {


    @NotBlank
    @Length(min = 1, max = 10)
    @ApiModelProperty(value = "昵称", example = "王五")
    private String nickname;

    @Past
    @NotNull
    @ApiModelProperty(value = "生日")
    private Date birthday;

    @NotBlank
    @ApiModelProperty(value = "职业", example = "1")
    private String professionId;



    @ApiModelProperty(value = "性别:(1:男,0:女)")
    @NotNull
    private Integer sex;

    @ApiModelProperty(value = "语音地址")
    private String voiceUrl;


    @ApiModelProperty(value = "头像地址")
    @NotBlank
    private String headerUrl;


    @NotBlank
    @Digits(integer = 3, fraction = 2)
    @Range(min = 120, max = 240)
    @ApiModelProperty(value = "身高", example = "100")
    private String height;

    @NotBlank
    @Digits(integer = 3, fraction = 2)
    @Range(min = 35, max = 100)
    @ApiModelProperty(value = "体重", example = "50")
    private String weight;


    @Length(max = 300)
    @ApiModelProperty(value = "个人介绍", example = "没有介绍")
    private String presentation;


}
