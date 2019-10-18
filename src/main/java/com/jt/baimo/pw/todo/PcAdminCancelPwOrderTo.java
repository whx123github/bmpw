package com.jt.baimo.pw.todo;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.experimental.Accessors;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.NotBlank;

/**
 * @author Forever丶诺
 * @data 2019/9/25 15:08
 */
@Data
@Accessors
public class PcAdminCancelPwOrderTo {

    @ApiModelProperty("id")
    @NotBlank
    private String id;


    @ApiModelProperty("取消理由")
    @NotBlank
    @Length(max = 100)
    private String cancelReason;


    @ApiModelProperty("取消理由")
    @NotBlank
    private String aid;
}
