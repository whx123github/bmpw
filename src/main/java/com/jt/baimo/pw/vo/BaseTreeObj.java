package com.jt.baimo.pw.vo;


import com.fasterxml.jackson.annotation.JsonIgnore;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.util.List;

@Data
public class BaseTreeObj<T, ID> implements Serializable {

    private static final long serialVersionUID = 1110125018058743325L;
    @ApiModelProperty(value = "id")
    ID id;  //当前id

    @ApiModelProperty(value = "父id")
    @JsonIgnore
    ID pid;  //父id


    @ApiModelProperty(value = "子元素集合")
    List<T> childList; //子元素

    @JsonIgnore
    T parent; //父元素

}