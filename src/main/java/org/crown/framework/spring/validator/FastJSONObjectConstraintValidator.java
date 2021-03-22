package org.crown.framework.spring.validator;

import java.util.Objects;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

import com.alibaba.fastjson.JSONObject;

/**
 * <p>
 * FastJson JSONObject verification
 * </p>
 *
 * @author Caratacus
 */
public class FastJSONObjectConstraintValidator implements ConstraintValidator<FastJSONObject, JSONObject> {

    @Override
    public void initialize(FastJSONObject jsonObject) {
    }

    /**
     * @Description: Custom check logic
     */
    @Override
    public boolean isValid(JSONObject jsonObject, ConstraintValidatorContext constraintValidatorContext) {
        return Objects.nonNull(jsonObject);
    }
}
