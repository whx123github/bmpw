package com.jt.baimo.pw.model;

import com.baomidou.mybatisplus.annotation.FieldFill;
import com.baomidou.mybatisplus.annotation.TableField;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.experimental.Accessors;

import java.io.Serializable;
import java.util.Date;

/**
 * <p>
 * 照片视频购买记录
 * </p>
 *
 * @author Ms.WenJing
 * @since 2019-09-17
 */
@Data
@Accessors(chain = true)
public class UserUnLockAlbum implements Serializable {

    @ApiModelProperty(value = "自增主键")
    private Integer id;

    @ApiModelProperty(value = "相册表ID")
    private Integer albumId;

    @ApiModelProperty(value = "购买者ID")
    private String uid;

    @ApiModelProperty(value = "购买时间")
    @TableField(fill = FieldFill.INSERT)
    private Date addTime;

}
