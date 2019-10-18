package com.jt.baimo.pw.enumerate;

/**
 * @author Forever丶诺
 * @data 2019/9/22 15:21
 */
public enum WeekEnum implements EnumCommon {

    MONDAY("周一", "1"),
    TUESDAY("周二", "2"),
    WEDNESDAY("周三", "3"),
    THURSDAY("周四", "4"),
    FRIDAY("周五", "5"),
    SATURDAY("周六", "6"),
    SUNDAY("周日", "7");


    private String value; //枚举value字段
    private String desc; //枚举描述字段


    WeekEnum(String value, String desc) {
        this.value = value;
        this.desc = desc;
    }

    @Override
    public String getValue() {
        return value;
    }

    @Override
    public String getDesc() {
        return desc;
    }
}
