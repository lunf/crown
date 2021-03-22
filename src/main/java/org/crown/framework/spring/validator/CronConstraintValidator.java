package org.crown.framework.spring.validator;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

import org.crown.common.utils.StringUtils;
import org.crown.project.monitor.quartz.common.CronUtils;

/**
 * <p>
 * Cron check
 * </p>
 *
 * @author Caratacus
 */
public class CronConstraintValidator implements ConstraintValidator<Cron, String> {

    @Override
    public void initialize(Cron cron) {
    }

    /**
     * @Description: Custom check logic
     */
    @Override
    public boolean isValid(String cron, ConstraintValidatorContext constraintValidatorContext) {
        if (StringUtils.isBlank(cron)) {
            return true;
        }
        return CronUtils.isValid(cron);
    }
}