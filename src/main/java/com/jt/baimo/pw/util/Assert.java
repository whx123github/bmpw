package com.jt.baimo.pw.util;

import com.jt.baimo.pw.exception.YwException;

/**
 * @author Forever丶诺
 * @data 2019/7/30 9:34
 */
public class Assert {


    public static void isNull(Object obj, String msg) {
        if (obj == null)
            throw new YwException(msg);
    }


    public static void isNotNull(Object obj, String msg) {
        if (obj != null)
            throw new YwException(msg);
    }


    /***
     * 不等于0
     * @param num
     * @param message
     */
    public static void notZero(Integer num, String message) {
        if (num.intValue() != 0)
            throw new YwException(message);
    }


    public static void isZero(Integer num, String message) {
        if (num.intValue() == 0)
            throw new YwException(message);
    }

    /***
     * 如果是true 就抛出异常
     * @param expression
     * @param message
     */
    public static void isTrue(boolean expression, String message) {
        if (expression)
            throw new YwException(message);
    }


    public static void isFalse(boolean expression, String message) {
        if (!expression)
            throw new YwException(message);
    }

    public static void isTrue(String message) {
        throw new YwException(message);
    }


    public static void isEquals(Object obj1, Object obj2, String message) {
        if (obj1.equals(obj2))
            throw new YwException(message);
    }

    public static void isNotEquals(Object obj1, Object obj2, String message) {
        if (!obj1.equals(obj2))
            throw new YwException(message);
    }


}
