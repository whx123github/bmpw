package com.jt.baimo.pw.util;

/**
 * 生成随机数的工具类
 * <p>
 * Created by yb_li on 2019/1/7.
 */
public class RandomNumberUtil {

    public static String getRandomNumber(int length) {
        String num = "";
        for (int i = 0; i < length; i++) {
            num = num + ((int) Math.floor(Math.random() * 9 + 1));
        }
        return num;
    }
}
