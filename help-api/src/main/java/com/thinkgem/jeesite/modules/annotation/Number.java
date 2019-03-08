package com.thinkgem.jeesite.modules.annotation;

import com.thinkgem.jeesite.modules.validGroups.NumberValidator;
import org.hibernate.validator.constraints.NotEmpty;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import static java.lang.annotation.ElementType.*;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

/**
 * @Description:
 * @Date: 2018/3/1
 * @Author: wcf
 */
@Target({METHOD, FIELD, ANNOTATION_TYPE, CONSTRUCTOR, PARAMETER})
@Retention(RUNTIME)
@Constraint(validatedBy = {NumberValidator.class})
@Documented
public @interface Number {

    int integer() default 8;    //数值的整数部分

    int decimal() default 0;    //数值的小数部分

    double min() default 0;               //最小值

    double max() default Double.MAX_VALUE; //最大值

    String message() default "请传递正确的数值，只允许{integer}位整数和{decimal}位小数，最小值为{min}，最大值为{max}";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};

    /**
     * Defines several {@code @NotEmpty} annotations on the same element.
     */
    @Target({ ElementType.METHOD, ElementType.FIELD })
    @Retention(RUNTIME)
    @Documented
    public @interface List {
        NotEmpty[] value();
    }
}
