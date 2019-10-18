package com.jt.baimo.pw.config;

import org.hibernate.validator.messageinterpolation.ResourceBundleMessageInterpolator;
import org.hibernate.validator.resourceloading.AggregateResourceBundleLocator;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.validation.beanvalidation.MethodValidationPostProcessor;

import javax.validation.Validation;
import javax.validation.Validator;
import java.util.Arrays;
import java.util.List;

/**
 * 方法验证后处理器
 *
 * @author szd
 * @Description: 类描述
 * @data 2019/2/28 14:09
 */
@Configuration
public class ValidatorConfig {

    @Bean
    public MethodValidationPostProcessor methodValidationPostProcessor(){
        MethodValidationPostProcessor methodValidationPostProcessor = new MethodValidationPostProcessor();
        methodValidationPostProcessor.setValidator(validator());
        return methodValidationPostProcessor;
    }


    @Bean
    public Validator validator() {
        List<String> sourceList = Arrays.asList("msg/valid_szd_zh","msg/valid_zh");
        Validator validator = Validation.byDefaultProvider().
                configure().
                messageInterpolator(new ResourceBundleMessageInterpolator(new AggregateResourceBundleLocator(sourceList))).
                buildValidatorFactory().getValidator();
        return validator;
    }
}
