package com.jt.baimo.pw.vo;

import com.fasterxml.jackson.annotation.JsonIgnore;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.experimental.Accessors;

import java.util.Date;

/**
 * @author Forever丶诺
 * @data 2019/9/18 22:44
 */
@Data
@Accessors(chain = true)
public class HomeUserListVo {

    @ApiModelProperty(value = "头像地址")
    private String headerUrl;


    @ApiModelProperty(value = "昵称")
    private String nickname;


    @ApiModelProperty(value = "是否在线")
    private Integer isLogin;

    @ApiModelProperty(value = "技能名称")
    private String skillName;

    @ApiModelProperty(value = "技能名称")
    private String skillColor;

    @ApiModelProperty(value = "性别:(1:男,0:女)")
    private Integer sex;

    @ApiModelProperty(value = "年龄")
    private Integer age;


    @ApiModelProperty(value = "关注的数量")
    private String attentionNum;

    @ApiModelProperty(value = "距离")
    private double distance;


    @ApiModelProperty(value = "id")
    private String id;


    @ApiModelProperty(value = "uid")
    private Integer uid;


    @ApiModelProperty(value = "sql的距离")
    private double sqlDistance;


    @ApiModelProperty(value = "生日")
    @JsonIgnore
    private Date birthday;


    @JsonIgnore
    @ApiModelProperty(value = "经度", hidden = true)
    private Double lng;


    @JsonIgnore
    @ApiModelProperty(value = "纬度", hidden = true)
    private Double lat;

}
