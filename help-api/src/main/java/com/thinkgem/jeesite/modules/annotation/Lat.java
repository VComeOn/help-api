package com.thinkgem.jeesite.modules.annotation;

import com.thinkgem.jeesite.modules.validGroups.LatValidator;
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
 * @Description: 纬度验证
 * @Date: 2018/3/1
 * @Author: wcf
 */
@Target({METHOD, FIELD, ANNOTATION_TYPE, CONSTRUCTOR, PARAMETER})
@Retention(RUNTIME)
@Constraint(validatedBy = {LatValidator.class})
@Documented
public @interface Lat {

    String message() default "请传递正确的经度";

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
