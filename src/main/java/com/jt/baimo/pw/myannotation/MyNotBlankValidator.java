package com.jt.baimo.pw.myannotation;





import org.hibernate.validator.internal.constraintvalidators.bv.NotBlankValidator;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

/**
 * @author Forever丶诺
 * @data 2019/7/25 11:57
 */
public class MyNotBlankValidator implements ConstraintValidator<MyNotBank, CharSequence> {
    @Override
    public boolean isValid(CharSequence charSequence, ConstraintValidatorContext constraintValidatorContext) {
        return new NotBlankValidator().isValid(charSequence, constraintValidatorContext);
    }
}
