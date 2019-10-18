package com.jt.baimo.pw.vo;

import com.baomidou.mybatisplus.annotation.FieldFill;
import com.baomidou.mybatisplus.annotation.TableField;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.experimental.Accessors;

import java.math.BigDecimal;
import java.util.Date;

/**
 * @author Ms.WenJing
 * @Description:
 * @Date 2019/9/19 17:50
 */
@Data
@Accessors(chain = true)
public class UserAlbumVo {
    @ApiModelProperty(value = "主键id ")
    private Integer id;

    @ApiModelProperty(value = "为一用户id")
    private String uid;

    @ApiModelProperty(value = "url地址")
    private String url;

    @ApiModelProperty(value = "类型，1图片，2视频")
    private Integer type;

    @ApiModelProperty(value = "审核状态，1审核中,2已通过,3已拒绝")
    private Integer status;

    @ApiModelProperty(value = "审核不通过原因")
    private String reason;

    @ApiModelProperty(value = "初次生成时间")
    @TableField(fill = FieldFill.INSERT)
    private Date addTime;

    @ApiModelProperty(value = "最后修改时间")
    @TableField(fill = FieldFill.INSERT_UPDATE)
    private Date updateTime;

    @ApiModelProperty(value = "价格")
    private BigDecimal price;

    @ApiModelProperty(value = "审核时间")
    private Date auditAddTime;

    @ApiModelProperty(value = "审核管理员id")
    private Integer aid;

    @ApiModelProperty(value = "审核不通过原因")
    private String auditReason;

    @ApiModelProperty(value = "照片视频购买ID")
    private Integer userId;
}
