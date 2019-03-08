package com.thinkgem.jeesite.modules.validGroups;

import com.thinkgem.jeesite.common.utils.StringUtils;
import com.thinkgem.jeesite.modules.annotation.Number;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

/**
 * @Description:
 * @Date: 2018/3/1
 * @Author: wcf
 */
public class NumberValidator implements ConstraintValidator<Number, Double> {

    private Number operator;
    @Override
    public void initialize(Number constraintAnnotation) {
        operator = constraintAnnotation;
    }

    @Override
    public boolean isValid(Double value, ConstraintValidatorContext context) {
        boolean result = StringUtils.rightNumber(operator.integer(), operator.decimal(), String.valueOf(value));
        if(!result){
            return result;
        }
        if(value < operator.min() || value > operator.max()){
            return false;
        }
        return true;
    }

}
