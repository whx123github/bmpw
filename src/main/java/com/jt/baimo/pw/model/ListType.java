package com.jt.baimo.pw.model;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.Version;
import com.baomidou.mybatisplus.annotation.TableId;
import java.io.Serializable;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.experimental.Accessors;
import java.util.Date;

/**
 * <p>
 * 
 * </p>
 *
 * @author Forever丶诺
 * @since 2019-09-20
 */
@Data
@Accessors(chain = true)
public class ListType implements Serializable {

    @ApiModelProperty(value = "主键id")
    private Integer id;

    @ApiModelProperty(value = "名称")
    private String name;

    @ApiModelProperty(value = "1:取消订单原因类型")
    private String type;

}
