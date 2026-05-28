package com.jesper.seckill.annotation;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import org.apache.commons.lang3.StringUtils;

public class IsMobileValidator implements ConstraintValidator<IsMobile, String> {
    
    private boolean required;
    
    @Override
    public void initialize(IsMobile constraintAnnotation) {
        required = constraintAnnotation.required();
    }
    
    @Override
    public boolean isValid(String value, ConstraintValidatorContext context) {
        if (required) {
            return isMobile(value);
        } else {
            if (StringUtils.isEmpty(value)) {
                return true;
            } else {
                return isMobile(value);
            }
        }
    }
    
    private boolean isMobile(String value) {
        if (StringUtils.isEmpty(value)) {
            return false;
        }
        return value.matches("^1[3-9]\\d{9}$");
    }
}
