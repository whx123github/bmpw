package com.jt.baimo.pw.vo;

import com.fasterxml.jackson.annotation.JsonIgnore;
import io.swagger.annotations.ApiModelProperty;
import jdk.nashorn.internal.ir.annotations.Ignore;
import lombok.Data;
import lombok.experimental.Accessors;

import java.util.Date;


/**
 * @author Forever丶诺
 * @data 2019/7/19 12:13
 */
@Data
@Accessors(chain = true)
public class AttentionPageListVo {

    @ApiModelProperty(value = "关注表Id")
    private Integer attentionId;

    @ApiModelProperty(value = "用户主键id")
    private String id;



    @ApiModelProperty(value = "头像地址")
    private String headerUrl;


    @ApiModelProperty(value = "用户昵称")
    private String nickname;



    @ApiModelProperty(value = "性别 0女，1男")
    private Integer sex;

    @ApiModelProperty(value = "城市名称")
    private String cityName;


    @ApiModelProperty(value = "生日", hidden = true)
    @Ignore
    private Date birthday;

    @ApiModelProperty(value = "年龄")
    private Integer age;

    @ApiModelProperty(value = "星座")
    private String constellation;


    @ApiModelProperty(value = "经度", hidden = true)
    @JsonIgnore
    private Double lng;

    @ApiModelProperty(value = "纬度", hidden = true)
    @JsonIgnore
    private Double lat;

    @ApiModelProperty(value = "距离")
    private Double distance;

    @ApiModelProperty(value = "0不在线  1在线")
    private Integer isLogin;


    @JsonIgnore
    private Date vipLastTime;   //会员的到期使劲



    @ApiModelProperty(value = "是否顾问认证: 0 是没有 其他认证")
    private Integer authNum;


}
