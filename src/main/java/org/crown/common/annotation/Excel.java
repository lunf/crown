package org.crown.common.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Custom export Excel data annotation
 *
 * @author Crown
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.FIELD)
public @interface Excel {

    /**
     * Name exported to Excel.
     */
    String name() default "";

    /**
     * Date format, such as: yyyy-MM-dd
     */
    String dateFormat() default "";

    /**
     * Read content to expression (e.g.: 0=male, 1=female, 2=unknown)
     */
    String readConverterExp() default "";

    /**
     * The height of each column in excel when exporting in characters
     */
    double height() default 14;

    /**
     * The width of each column in excel when exporting in characters
     */
    double width() default 16;

    /**
     * Text suffix, such as% 90 becomes 90%
     */
    String suffix() default "";

    /**
     * When the value is empty, the default value of the field
     */
    String defaultValue() default "";

    /**
     * Prompt information
     */
    String prompt() default "";

    /**
     * You can only select the column content that cannot be entered.
     */
    String[] combo() default {};

    /**
     * Whether to export data and respond to requirements: sometimes it is needed to export a template, which is required for the title but the content needs to be filled in manually by the user.
     */
    boolean isExport() default true;

    /**
     * The attribute name in another class supports multi-level acquisition, separated by decimal points
     */
    String targetAttr() default "";

    /**
     * Field type (0: export and import; 1: only export; 2: only import)
     */
    Type type() default Type.ALL;

    enum Type {
        ALL(0), EXPORT(1), IMPORT(2);
        private final int value;

        Type(int value) {
            this.value = value;
        }

        int value() {
            return this.value;
        }
    }
}