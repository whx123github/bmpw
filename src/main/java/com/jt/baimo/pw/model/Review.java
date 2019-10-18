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
 * 认证头像语音表
 * </p>
 *
 * @author Ms.WenJing
 * @since 2019-09-17
 */
@Data
@Accessors(chain = true)
public class Review implements Serializable {

    @ApiModelProperty(value = "主键")
    private Integer id;

    @ApiModelProperty(value = "用户唯一标识")
    private String uid;

    @ApiModelProperty(value = "地址")
    private String url;

    @ApiModelProperty(value = "创建时间")
    @TableField(fill = FieldFill.INSERT)
    private Date addTime;

    @ApiModelProperty(value = "最后修改时间")
    @TableField(fill = FieldFill.INSERT_UPDATE)
    private Date updateTime;

    @ApiModelProperty(value = "类型, 1头像，2语音，3: 4昵称，5个人介绍")
    private Integer type;

    @ApiModelProperty(value = "审核状态，1审核中,2已通过,3未通过")
    private Integer status;

    @ApiModelProperty(value = "不通过原因")
    private String auditReason;

    @ApiModelProperty(value = "审核时间")
    private Date auditAddTime;

    @ApiModelProperty(value = "审核管理员id")
    private Integer aid;

}
