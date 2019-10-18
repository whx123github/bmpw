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
 * 用户绑定的支付宝账号
 * </p>
 *
 * @author Forever丶诺
 * @since 2019-09-19
 */
@Data
@Accessors(chain = true)
public class UserBindAlipay implements Serializable {

    @ApiModelProperty(value = "主键id")
    private Integer id;

    @ApiModelProperty(value = "用户id")
    private String uId;

    @ApiModelProperty(value = "账号")
    private String account;

    @ApiModelProperty(value = "真实姓名")
    private String realName;

}
