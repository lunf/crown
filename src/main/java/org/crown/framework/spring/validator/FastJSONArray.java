package org.crown.framework.spring.validator;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import javax.validation.Constraint;
import javax.validation.Payload;

/**
 * <p>
 * FastJson JSONArray validation annotation
 * </p>
 *
 * @author Caratacus
 */
@Target({ElementType.FIELD})
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = FastJSONArrayConstraintValidator.class)
public @interface FastJSONArray {

    /**
     * @Description: Error message
     */
    String message() default "Please enter the correct JSONArray format";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};
}
