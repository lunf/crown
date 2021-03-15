package org.crown.common.annotation;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import org.crown.common.enums.BusinessType;
import org.crown.common.enums.OperatorType;

/**
 * Custom operation log record annotation
 *
 * @author Crown
 */
@Target({ElementType.PARAMETER, ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface Log {

    /**
     * Module
     */
    String title() default "";

    /**
     * Features
     */
    BusinessType businessType() default BusinessType.OTHER;

    /**
     * Operator category
     */
    OperatorType operatorType() default OperatorType.MANAGE;

    /**
     * Whether to save the requested parameters
     */
    boolean isSaveRequestData() default true;
}
