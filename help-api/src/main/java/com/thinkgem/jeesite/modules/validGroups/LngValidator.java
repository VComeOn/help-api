package com.thinkgem.jeesite.modules.validGroups;

import com.thinkgem.jeesite.modules.annotation.Lng;
import com.thinkgem.jeesite.modules.annotation.Number;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @Description: 经度验证器
 * @Date: 2018/3/1
 * @Author: wcf
 */
public class LngValidator implements ConstraintValidator<Lng, Double> {

    private Lng operator;
    @Override
    public void initialize(Lng constraintAnnotation) {
        operator = constraintAnnotation;
    }

    @Override
    public boolean isValid(Double value, ConstraintValidatorContext context) {
        if(value != null){
            Pattern pattern = Pattern.compile("^-?((0|1?[0-7]?[0-9]?)(([.][0-9]{1,6})?)|180(([.][0]{1,6})?))$");
            Matcher isLng = pattern.matcher(String.valueOf(value));
            if( !isLng.matches() ){
                return false;
            }
        }
        return true;
    }

}
