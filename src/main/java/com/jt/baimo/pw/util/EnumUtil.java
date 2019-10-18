package com.jt.baimo.pw.util;

import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Map;

/**
 * @author Forever丶诺
 * @data 2019/9/17 19:22
 */
public class EnumUtil {

    private static <T> Object getMethodValue(String methodName, T obj, Object... args) {
        Object resut = "";
        try {
            Method[] methods = obj.getClass().getMethods(); //获取方法数组，这里只要共有的方法
            if (methods.length <= 0) {
                return resut;
            }
            Method method = null;
            for (Method myMethods : methods) {
                if (myMethods.getName().equalsIgnoreCase(methodName)) { //忽略大小写取方法
                    method = myMethods;
                    break;
                }
            }
            if (method == null) {
                return resut;
            }
            resut = method.invoke(obj, args); //方法执行
            if (resut == null) {
                resut = "";
            }
            return resut; //返回结果
        } catch (Exception e) {
            e.printStackTrace();
        }
        return resut;
    }



    public static <T> Object getDescByVal(Object value,
                                          Class<T> enumT, String... methodNames) {
        if (!enumT.isEnum()) { //不是枚举则返回""
            return "";
        }
        T[] enums = enumT.getEnumConstants(); //获取枚举的所有枚举属性，似乎这几句也没啥用，一般既然用枚举，就一定会添加枚举属性
        if (enums == null || enums.length <= 0) {
            return "";
        }
        int count = methodNames.length;
        String valueMathod = "getValue"; //改成自己的获取value值的方法名
        String desMathod = "getDesc"; //改成自己的获取des值得方法名
        if (count >= 1 && !"".equals(methodNames[0])) {
            valueMathod = methodNames[0];
        }
        if (count == 2 && !"".equals(methodNames[1])) {
            desMathod = methodNames[1];
        }
        for (int i = 0, len = enums.length; i < len; i++) {
            T t = enums[i];
            try {
                Object resultValue = getMethodValue(valueMathod, t);//获取枚举对象value
                if (resultValue.toString().equals(value + "")) {
                    Object resultDes = getMethodValue(desMathod, t); //存在则返回对应描述
                    return resultDes;
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return "";
    }


    public static <T> String getEnumKeyByValue(Object value, Class<T> enumT,
                                               String... methodNames) {
        if (!enumT.isEnum()) {
            return "";
        }
        T[] enums = enumT.getEnumConstants();
        if (enums == null || enums.length <= 0) {
            return "";
        }
        int count = methodNames.length;
        String valueMathod = "getValue"; //默认方法
        if (count >= 1 && !"".equals(methodNames[0])) { //独立方法
            valueMathod = methodNames[0];
        }
        for (int i = 0, len = enums.length; i < len; i++) {
            T tobj = enums[i];
            try {
                Object resultValue = getMethodValue(valueMathod, tobj);
                if (resultValue != null
                        && resultValue.toString().equals(value + "")) { //存在则返回对应值
                    return tobj + "";
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return "";
    }


    public static <T> Map<String, String> EnumToMap(Class<T> enumT,
                                                    String... methodNames) {
        Map<String, String> enummap = new HashMap<String, String>();
        if (!enumT.isEnum()) {
            return enummap;
        }
        T[] enums = enumT.getEnumConstants();
        if (enums == null || enums.length <= 0) {
            return enummap;
        }
        int count = methodNames.length;
        String valueMathod = "getValue"; //默认接口value方法
        String desMathod = "getDesc";//默认接口description方法
        if (count >= 1 && !"".equals(methodNames[0])) { //扩展方法
            valueMathod = methodNames[0];
        }
        if (count == 2 && !"".equals(methodNames[1])) {
            desMathod = methodNames[1];
        }


        for (T tobj : enums) {
            try {
                Object resultValue = getMethodValue(valueMathod, tobj); //获取value值
                if ("".equals(resultValue)) {
                    continue;
                }
                Object resultDes = getMethodValue(desMathod, tobj); //获取description描述值
                if ("".equals(resultDes)) { //如果描述不存在获取属性值
                    resultDes = tobj;
                }
                enummap.put(String.valueOf(resultValue), resultDes + "");
            } catch (Exception e) {
                e.printStackTrace();
            }
        }


        return enummap;
    }


}

