package org.crown.framework.spring.validator;

import java.util.Objects;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

import com.alibaba.fastjson.JSONArray;

/**
 * <p>
 * FastJson JSONArray verification
 * </p>
 *
 * @author Caratacus
 */
public class FastJSONArrayConstraintValidator implements ConstraintValidator<FastJSONArray, JSONArray> {

    @Override
    public void initialize(FastJSONArray json) {
    }

    /**
     * @Description: Custom check logic
     */
    @Override
    public boolean isValid(JSONArray jsonArray, ConstraintValidatorContext constraintValidatorContext) {
        return Objects.nonNull(jsonArray);
    }
}