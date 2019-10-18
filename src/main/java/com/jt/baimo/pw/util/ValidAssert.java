package com.jt.baimo.pw.util;

import com.jt.baimo.pw.exception.ValidException;
import com.jt.baimo.pw.exception.YwException;

/**
 * 验证断言
 *
 * @author Forever丶诺
 * @data 2019/7/30 9:34
 */
public class ValidAssert {


    public static void isNull(Object obj, String msg) {
        if (obj == null)
            throw new ValidException(msg);
    }


    public static void isNotNull(Object obj, String msg) {
        if (obj != null)
            throw new ValidException(msg);
    }


    /***
     * 不等于0
     * @param num
     * @param message
     */
    public static void notZero(Integer num, String message) {
        if (num.intValue() != 0) throw new ValidException(message);
    }

    /***
     * 如果是true 就抛出异常
     * @param expression
     * @param message
     */
    public static void isTrue(boolean expression, String message) {
        if (expression)
            throw new ValidException(message);
    }

    public static void isTrue(String message) {
        throw new ValidException(message);
    }
}
