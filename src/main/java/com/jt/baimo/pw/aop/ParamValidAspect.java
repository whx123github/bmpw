package com.jt.baimo.pw.aop;


import com.jt.baimo.pw.exception.BaimoException;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 用来做参数效验的aop
 * <p>
 *
 * @author Forever丶诺
 * @date 2019/1/7
 */
@Aspect
@Component
@Slf4j
@Order(-1)
public class ParamValidAspect {


    @Pointcut("@within(org.springframework.web.bind.annotation.RestController)&&args(..,org.springframework.validation.BindingResult)")
    public void point() {
    }

    @Before("point()")
    public void checkValid(JoinPoint joinPoint) {
        Object[] args = joinPoint.getArgs();
        Map resultMap = new HashMap<>(args.length);
        for (Object arg : args) {
            if (arg instanceof BindingResult) {
                BindingResult bindingResult = (BindingResult) arg;
                List<FieldError> fieldErrors = bindingResult.getFieldErrors();
                fieldErrors.forEach(fieldError -> {
                    resultMap.put(fieldError.getField(), fieldError.getDefaultMessage());
                });
            }
        }
        if (!resultMap.isEmpty()) {
            throw new BaimoException("403", "验证失败", resultMap);
        }
    }


}
