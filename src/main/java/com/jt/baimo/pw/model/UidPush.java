package com.jt.baimo.pw.model;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.Version;
import com.baomidou.mybatisplus.annotation.TableId;
import java.time.LocalDateTime;
import com.baomidou.mybatisplus.annotation.FieldFill;
import com.baomidou.mybatisplus.annotation.TableField;
import java.io.Serializable;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.experimental.Accessors;
import java.util.Date;

/**
 * <p>
 * 激光推送(某个人)
 * </p>
 *
 * @author Ms.WenJing
 * @since 2019-09-19
 */
@Data
@Accessors(chain = true)
public class UidPush implements Serializable {

    @ApiModelProperty(value = "主键")
    private Integer id;

    @ApiModelProperty(value = "内容")
    private String content;

    @ApiModelProperty(value = "添加时间")
    @TableField(fill = FieldFill.INSERT)
    private Date addTime;

    @ApiModelProperty(value = "更新时间")
    @TableField(fill = FieldFill.INSERT_UPDATE)
    private Date updateTime;

    @ApiModelProperty(value = "推送类型:1.游戏大神审核通过2.游戏大神审核不通过3.动态不通过4.照片不通过5.头像不通过6.昵称屏蔽7.语音不通过8.个人介绍不通过9.发布动态11.待接单12.待服务13.已取消客服取消14.已取消支付超时15.已取消用户取消16.进行中17.已完成 陪玩点击【完成服务】18.已完成 玩家点击【确认完成】19.百陌币到账20.退款")
    private Integer type;

    @ApiModelProperty(value = "发布用户")
    private String sUid;

    @ApiModelProperty(value = "接收用户")
    private String rUid;

    @ApiModelProperty(value = "订单ID")
    private String did;

}
