package cn.Botter.anno;

import cn.Botter.validation.StateValidation;
import jakarta.validation.Constraint;
import jakarta.validation.Payload;

import java.lang.annotation.*;
import static java.lang.annotation.ElementType.*;
import static java.lang.annotation.ElementType.FIELD;
import static java.lang.annotation.ElementType.TYPE_USE;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

/**
 * @author Botter
 * @date 2024/7/14
 * @Description
 */
@Documented //元注解
@Target({FIELD})
@Retention(RUNTIME)
@Constraint(validatedBy = {StateValidation.class}) //提供校验规则的类
public @interface State {

    //提供校验失败后的提示信息
    String message() default "state参数只能是已发布或者草稿";
    // 指定分组
    Class<?>[] groups() default {};
    //负载
    Class<? extends Payload>[] payload() default {};
}
