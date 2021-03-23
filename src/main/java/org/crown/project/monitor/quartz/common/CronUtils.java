package org.crown.project.monitor.quartz.common;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Objects;

import org.quartz.CronExpression;

/**
 * cron expression tool class
 *
 * @author Caratacus
 */
public class CronUtils {

    private static final SimpleDateFormat sdf = new SimpleDateFormat("ss mm HH dd MM ? yyyy");

    /***
     *  Function description: date conversion cron expression
     * @param date
     * @return
     */
    public static String formatDateByPattern(Date date) {
        String formatTimeStr = null;
        if (Objects.nonNull(date)) {
            formatTimeStr = sdf.format(date);
        }
        return formatTimeStr;
    }

    /***
     * convert Date to cron, eg "0 07 10 15 1 ? 2016"
     * @param date  : Point in time
     * @return
     */
    public static String getCron(Date date) {
        return formatDateByPattern(date);
    }

    /**
     * Returns a boolean value representing the validity of a given cron expression
     *
     * @param cronExpression Cron expression
     * @return boolean Is the expression valid
     */
    public static boolean isValid(String cronExpression) {
        return CronExpression.isValidExpression(cronExpression);
    }

    /**
     * Return a string value, indicating that the message is invalid Cron expression gives validity
     *
     * @param cronExpression Cron expression
     * @return String Return expression error description when invalid, or null if valid
     */
    public static String getInvalidMessage(String cronExpression) {
        try {
            new CronExpression(cronExpression);
            return null;
        } catch (ParseException pe) {
            return pe.getMessage();
        }
    }

    /**
     * Return the next execution time according to the given cron expression
     *
     * @param cronExpression Cron expression
     * @return Date Next Cron expression execution time
     */
    public static Date getNextExecution(String cronExpression) {
        try {
            CronExpression cron = new CronExpression(cronExpression);
            return cron.getNextValidTimeAfter(new Date(System.currentTimeMillis()));
        } catch (ParseException e) {
            throw new IllegalArgumentException(e.getMessage());
        }
    }
}
