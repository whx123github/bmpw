package com.jt.baimo.pw.aop;


import com.alibaba.fastjson.JSONObject;
import com.jt.baimo.pw.exception.BaimoException;
import com.jt.baimo.pw.exception.ValidException;
import com.jt.baimo.pw.exception.YwException;
import com.jt.baimo.pw.vo.ResultData;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import javax.validation.ConstraintViolation;
import javax.validation.ConstraintViolationException;
import javax.validation.Path;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

/**
 * 全局的异常拦截器（拦截所有的控制器）
 * （带有@RequestMapping注解的方法都会被拦截）
 * <p>
 *
 * @author Forever丶诺
 * @date 2019/1/5
 */
@Slf4j
@RestControllerAdvice
public class BaseControllerExceptionHandler {

    /**
     * 拦截业务异常
     */
    @ExceptionHandler(BaimoException.class)
    public ResultData notFount(BaimoException e) {
        log.debug(e.getMsg());
        return new ResultData().setValidResult(JSONObject.toJSONString(e.getData()));
    }


    @ExceptionHandler(YwException.class)
    public ResultData notFount(YwException e) {
        return new ResultData().failMsgResult(e.getMessage());
    }

    @ExceptionHandler(ValidException.class)
    public ResultData validFail(ValidException e) {
        return new ResultData().setValidResult(e.getMessage());
    }






    @ExceptionHandler(value = ConstraintViolationException.class)
    public ResultData ConstraintViolationException(ConstraintViolationException ex) {
        Set<ConstraintViolation<?>> constraintViolations = ex.getConstraintViolations();
        Map resultMap = new HashMap<>();
        constraintViolations.forEach(data -> {
            Path propertyPath = data.getPropertyPath();
            resultMap.put(StringUtils.substringAfterLast(propertyPath.toString(), "."), data.getMessage());
        });
        return new ResultData().setValidResult(resultMap);
    }


}
