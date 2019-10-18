package com.jt.baimo.pw.vo;

import com.fasterxml.jackson.annotation.JsonView;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.experimental.Accessors;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

/**
 * @author Forever丶诺
 * @data 2019/7/18 10:28
 */
@Data
@Accessors(chain = true)
@JsonView(ResultData.ObjectView.class)
public class ResultData<T> implements Serializable {

    public  interface ObjectView {}

    private static final long serialVersionUID = -4085876824834195051L;
    @ApiModelProperty("业务数据")
    private T data;
    @ApiModelProperty("提示消息")
    private String msg;

    @ApiModelProperty("分页用到的总数量")
    private Long count = -1L;

    @ApiModelProperty("返回状态码:(200:成功,403:参数验证错误,404:业务逻辑错误)")
    private Integer code = 200; // 404业务错误

    public ResultData setValidResult(T validData) {
        return this.setCode(403).setData(validData);
    }


    public ResultData oneKeyDate(String name , Object value){
        Map  resultMap = new HashMap(1);
        resultMap.put(name,value);
        return mapDate(resultMap);
    }


    public ResultData mapDate(Map resultMap){
        return this.setData((T) resultMap);
    }



    public ResultData failMsgResult(String msg) {
        return this.setCode(404).setMsg(msg);
    }




}
