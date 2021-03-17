package org.crown.common.utils;

import org.crown.framework.spring.ApplicationUtils;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;

/**
 * Obtain i18n resource files
 *
 * @author Crown
 */
public class MessageUtils {

    /**
     * Obtain the message according to the message key and parameters and delegate to spring messageSource
     *
     * @param code Message key
     * @param args parameter
     * @return Get internationalized translation value
     */
    public static String message(String code, Object... args) {
        MessageSource messageSource = ApplicationUtils.getBean(MessageSource.class);
        return messageSource.getMessage(code, args, LocaleContextHolder.getLocale());
    }
}
