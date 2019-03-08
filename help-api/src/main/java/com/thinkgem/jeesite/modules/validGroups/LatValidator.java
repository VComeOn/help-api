package com.thinkgem.jeesite.modules.validGroups;

import com.thinkgem.jeesite.modules.annotation.Lat;
import com.thinkgem.jeesite.modules.annotation.Lng;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @Description: 纬度验证器
 * @Date: 2018/3/1
 * @Author: wcf
 */
public class LatValidator implements ConstraintValidator<Lat, Double> {

    private Lat operator;
    @Override
    public void initialize(Lat constraintAnnotation) {
        operator = constraintAnnotation;
    }

    @Override
    public boolean isValid(Double value, ConstraintValidatorContext context) {
        if(value != null){
            Pattern pattern = Pattern.compile("^-?((0|[1-8]?[0-9]?)(([.][0-9]{1,6})?)|90(([.][0]{1,6})?))$");
            Matcher isLat = pattern.matcher(String.valueOf(value));
            if( !isLat.matches() ){
                return false;
            }
        }
        return true;
    }

}
