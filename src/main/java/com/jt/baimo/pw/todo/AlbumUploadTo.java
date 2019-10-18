package com.jt.baimo.pw.todo;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.experimental.Accessors;
import org.hibernate.validator.constraints.Range;

import javax.validation.constraints.Digits;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.math.BigDecimal;

/**
 * @author Forever丶诺
 * @data 2019/7/26 16:09
 */
@Data
@Accessors(chain = true)
public class AlbumUploadTo {

    @ApiModelProperty("地址")
    @NotBlank
    private String url;


    @NotNull
    @Range(min = 1, max = 2)
    @ApiModelProperty("类型，1图片，2视频")
    private Integer type;


    @NotNull
    @Range(min = 0, max = 10)
    @Digits(integer = 2, fraction = 2)
    @ApiModelProperty("价格")
    private BigDecimal price;

}
