package org.crown.framework.spring.validator;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import javax.validation.Constraint;
import javax.validation.Payload;

/**
 * <p>
 * Cron verification annotation
 * </p>
 *
 * @author Caratacus
 */
@Target({ElementType.FIELD})
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = CronConstraintValidator.class)
public @interface Cron {

    /**
     * @Description: Error message
     */
    String message() default "Please enter the correct cron expression";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};
}