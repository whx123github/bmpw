package com.jt.baimo.pw.vo;

import com.fasterxml.jackson.annotation.JsonIgnore;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.experimental.Accessors;

import java.math.BigDecimal;

/**
 * @author Forever丶诺
 * @data 2019/9/19 15:04
 */
@Data
@Accessors(chain = true)
public class HomeOneAlbumTargetListVo {

    @ApiModelProperty("主键id")
    private Integer id;


    @ApiModelProperty("地址: 类型为空表示要解锁")
    private String url;


    @ApiModelProperty("类型，1图片，2视频")
    private Integer type;

    @ApiModelProperty("价格")
    private BigDecimal price;




    @JsonIgnore
    @ApiModelProperty(value = "我的id",hidden = true)
    private String myId;

}
