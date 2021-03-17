package org.crown.common.utils;

import java.math.BigDecimal;
import java.math.RoundingMode;

/**
 * Precise floating-point arithmetic
 *
 * @author Crown
 */
public class Arith {

    /**
     * Default division precision
     */
    private static final int DEF_DIV_SCALE = 10;

    /**
     * This class cannot be instantiated
     */
    private Arith() {
    }

    /**
     * Provide precise addition operations。
     *
     * @param v1 Summand
     * @param v2 Addend
     * @return The sum of the two parameters
     */
    public static double add(double v1, double v2) {
        BigDecimal b1 = new BigDecimal(Double.toString(v1));
        BigDecimal b2 = new BigDecimal(Double.toString(v2));
        return b1.add(b2).doubleValue();
    }

    /**
     * Provide precise subtraction。
     *
     * @param v1 Minute
     * @param v2 Minus
     * @return Difference between two parameters
     */
    public static double sub(double v1, double v2) {
        BigDecimal b1 = new BigDecimal(Double.toString(v1));
        BigDecimal b2 = new BigDecimal(Double.toString(v2));
        return b1.subtract(b2).doubleValue();
    }

    /**
     * Provide precise multiplication。
     *
     * @param v1 Multiplicand
     * @param v2 multiplier
     * @return Product of two parameters
     */
    public static double mul(double v1, double v2) {
        BigDecimal b1 = new BigDecimal(Double.toString(v1));
        BigDecimal b2 = new BigDecimal(Double.toString(v2));
        return b1.multiply(b2).doubleValue();
    }

    /**
     * Provide (relatively) accurate division operation, when the division is inexhaustible, accurate to
     * 10 digits after the decimal point, and subsequent numbers are rounded。
     *
     * @param v1 Dividend
     * @param v2 divisor
     * @return Quotient of two parameters
     */
    public static double div(double v1, double v2) {
        return div(v1, v2, DEF_DIV_SCALE);
    }

    /**
     * Provides (relatively) precise division operations. When there is an inexhaustible division, the scale parameter refers to
     * Set the precision, and the subsequent figures are rounded。
     *
     * @param v1    Dividend
     * @param v2    divisor
     * @param scale Indicates that it needs to be accurate to a few decimal places.
     * @return Quotient of two parameters
     */
    public static double div(double v1, double v2, int scale) {
        if (scale < 0) {
            throw new IllegalArgumentException(
                    "The scale must be a positive integer or zero");
        }
        BigDecimal b1 = new BigDecimal(Double.toString(v1));
        BigDecimal b2 = new BigDecimal(Double.toString(v2));
        if (b1.compareTo(BigDecimal.ZERO) == 0) {
            return BigDecimal.ZERO.doubleValue();
        }
        return b1.divide(b2, scale, RoundingMode.HALF_UP).doubleValue();
    }

    /**
     * Provide accurate rounding of decimal places。
     *
     * @param v     Number that needs to be rounded
     * @param scale Keep a few digits after the decimal point
     * @return Rounded result
     */
    public static double round(double v, int scale) {
        if (scale < 0) {
            throw new IllegalArgumentException(
                    "The scale must be a positive integer or zero");
        }
        BigDecimal b = new BigDecimal(Double.toString(v));
        BigDecimal one = new BigDecimal("1");
        return b.divide(one, scale, RoundingMode.HALF_UP).doubleValue();
    }
}
