package cn.Botter.validation;

import cn.Botter.anno.State;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

/**
 * @author Botter
 * @date 2024/7/14
 * @Description
 */
public class StateValidation implements ConstraintValidator<State,String> {
    /**
     *
     * @param s 将来要校验的数据
     * @param constraintValidatorContext
     * @return 如果返回false则校验不通过，否则通过
     */
    @Override
    public boolean isValid(String s, ConstraintValidatorContext constraintValidatorContext) {
        if (s == null || s.length() == 0)
            return false;
        if (s.equals("已发布") || s.equals("草稿")){
            return true;
        }
        return false;
    }
}
