package com.jt.baimo.pw.vo;

import com.fasterxml.jackson.annotation.JsonView;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.experimental.Accessors;

/**
 * @author Forever丶诺
 * @data 2019/10/10 11:08
 */
@Data
@Accessors(chain = true)
public class ForeignUserInfoVo {



    @ApiModelProperty("用户标识主键  唯一uid")
    private String id;


    @ApiModelProperty("用户昵称")
    private String userName;



    @ApiModelProperty(value = "头像地址")
    private String headerUrl;
}
