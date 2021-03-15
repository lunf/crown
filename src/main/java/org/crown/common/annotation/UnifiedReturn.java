package org.crown.common.annotation;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import org.springframework.http.HttpStatus;

/**
 * Wrapping API return annotation
 *
 * @author Crown
 */
@Target({ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface UnifiedReturn {

    /**
     * Whether to wrap the return
     */
    boolean wrapper() default false;

    /**
     * Return http code normally
     */
    HttpStatus status() default HttpStatus.OK;
}