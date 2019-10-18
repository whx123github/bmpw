package com.jt.baimo.pw.myannotation;

import javax.validation.Constraint;
import java.lang.annotation.*;

/**
 * @author Forever丶诺
 * @data 2019/7/25 11:48
 */
@Documented
@Constraint(
        validatedBy = {MyNotBlankValidator.class}
)
@Target({ElementType.METHOD, ElementType.FIELD, ElementType.ANNOTATION_TYPE, ElementType.CONSTRUCTOR, ElementType.PARAMETER, ElementType.TYPE_USE})
@Retention(RetentionPolicy.RUNTIME)
@Repeatable(MyNotBank.List.class)
public @interface MyNotBank {

    String message() default "自义定不能为空";

    String name() default ""; //字段的名称

    Class<?>[] groups() default {};

    Class<? extends javax.validation.Payload>[] payload() default {};

    @Target({ElementType.METHOD, ElementType.FIELD, ElementType.ANNOTATION_TYPE, ElementType.CONSTRUCTOR, ElementType.PARAMETER, ElementType.TYPE_USE})
    @Retention(RetentionPolicy.RUNTIME)
    @Documented
    public @interface List {
        MyNotBank[] value();
    }

}
