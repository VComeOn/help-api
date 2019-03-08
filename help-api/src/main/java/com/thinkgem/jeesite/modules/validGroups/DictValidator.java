package com.thinkgem.jeesite.modules.validGroups;

import com.thinkgem.jeesite.common.utils.StringUtils;
import com.thinkgem.jeesite.modules.annotation.Dict;
import com.thinkgem.jeesite.modules.annotation.Number;
import com.thinkgem.jeesite.modules.sys.service.DictService;
import com.thinkgem.jeesite.modules.sys.service.SystemService;

import javax.annotation.Resource;
import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import java.util.List;

/**
 * @Description:
 * @Date: 2018/3/1
 * @Author: wcf
 */
public class DictValidator implements ConstraintValidator<Dict, String> {

    private Dict operator;

    @Resource
    private DictService dictService;
    @Override
    public void initialize(Dict constraintAnnotation) {
        operator = constraintAnnotation;
    }

    @Override
    public boolean isValid(String value, ConstraintValidatorContext context) {
        if(StringUtils.isBlank(value)){
            return true;
        }
        List<com.thinkgem.jeesite.modules.sys.entity.Dict> dictList = dictService.findByType(operator.type());
        for(com.thinkgem.jeesite.modules.sys.entity.Dict dict : dictList){
            if(dict.getValue().equals(value)){
                return true;
            }
        }
        return false;
    }

}
