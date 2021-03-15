package org.crown.common.annotation;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Data permission filter annotation
 *
 * @author Crown
 */
@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface DataScope {

    /**
     * Alias of department table
     */
    String deptAlias() default "sys_dept";

    /**
     * Alias of user table
     */
    String userAlias() default "sys_user";
}
