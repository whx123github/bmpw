package com.jt.baimo.pw.util;


import com.jt.baimo.pw.constant.CommonCst;
import com.jt.baimo.pw.enumerate.WeekEnum;
import org.apache.commons.lang3.StringUtils;
import org.gavaghan.geodesy.*;
import org.joda.time.DateTime;
import org.joda.time.LocalDateTime;
import org.joda.time.LocalTime;

import javax.servlet.http.HttpServletRequest;
import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;


/**
 * 常用通用的公用方法
 *
 * @author Forever丶诺
 * @data 2019/5/16 19:18
 */
public class AppUtil {


    /**
     * 获取用户的id
     *
     * @param request
     * @return
     */

    public static String getUserId(HttpServletRequest request) {
        return (String) request.getAttribute(CommonCst.USER_ID);
    }


    public static String getUserVideoCode(String userId) {
        return userId.substring(userId.length() - 6);
    }


    public static String createOrderNo(String module, String userId) {
        return module + "-" + userId + "-" + new LocalDateTime().toString("yyyyMMddHHmmss");
    }


    /**
     * 根据出生日期 得到年龄
     *
     * @param birthDay
     * @return
     * @author Ms.WenJing
     */
    public static Integer getAge(Date birthDay) {
        DateTime birthDate = new DateTime(birthDay);
        if (!birthDate.isBeforeNow()) {
            return 18;
        }

        //得到当前 日期的年月日
        DateTime nowDate = DateTime.now();
        int yearNow = nowDate.getYear();
        int monthNow = nowDate.getMonthOfYear();
        int dayNow = nowDate.getDayOfMonth();
        int yearBirth = birthDate.getYear();
        int monthBirth = birthDate.getMonthOfYear();
        int dayBirth = birthDate.getDayOfMonth();
        int age = yearNow - yearBirth;
        /*如果出当前月小与出生月，或者当前月等于出生月但是当前日小于出生日，那么年龄age就减一岁*/
        if (monthNow < monthBirth || (monthNow == monthBirth && dayNow < dayBirth)) {
            age--;
        }
        return age;
    }


    public static String getConstellation(Date birthDay) {
        int[] dayArr = new int[]{20, 19, 21, 20, 21, 22, 23, 23, 23, 24, 23, 22};
        String[] constellationArr = new String[]{"摩羯座", "水瓶座", "双鱼座", "白羊座", "金牛座", "双子座", "巨蟹座", "狮子座", "处女座", "天秤座", "天蝎座", "射手座", "摩羯座"};
        DateTime birthDate = new DateTime(birthDay);
        if (!birthDate.isBeforeNow()) {
            return "";
        }
        int monthBirth = birthDate.getMonthOfYear();
        int dayBirth = birthDate.getDayOfMonth();
        return dayBirth < dayArr[monthBirth - 1] ? constellationArr[monthBirth - 1] : constellationArr[monthBirth];
    }


    public static double distance(Double lngMy, Double latMy, Double lngAll, Double latAll) {
        if (lngMy == null || latMy == null || latAll == null || latAll == null) {
            return 0;
        }
        GlobalCoordinates source = new GlobalCoordinates(latMy, lngMy);
        GlobalCoordinates target = new GlobalCoordinates(latAll, lngAll);
//        double meter1 = getDistanceMeter (source, target, Ellipsoid.Sphere);
        double meter2 = getDistanceMeter(source, target, Ellipsoid.WGS84);
        return meter2 / 1000;
    }

    private static double getDistanceMeter(GlobalCoordinates gpsFrom, GlobalCoordinates gpsTo, Ellipsoid ellipsoid) {
        //创建GeodeticCalculator，调用计算方法，传入坐标系、经纬度用于计算距离
        double distance = new GeodeticCalculator()
                .calculateGeodeticCurve(ellipsoid, gpsFrom, gpsTo)
                .getEllipsoidalDistance();
        return distance;
    }


    public static boolean isBankAndNull(String data) {
        return StringUtils.isEmpty(data) || StringUtils.isBlank(data);
    }


    /**
     * 判断是不是在服务时间内
     *
     * @param startTime
     * @param endTime
     * @param serviceTime
     * @param week
     * @return
     */
    public static boolean isServerTime(Date startTime, Date endTime, Date serviceTime, String week) {

        String[] dayWeeks = StringUtils.split(week, ",");
        Map<String, String> map = EnumUtil.EnumToMap(WeekEnum.class);
        List<Integer> intWeeks = new ArrayList<>();
        for (int i = 0; i < dayWeeks.length; i++) {
            intWeeks.add(Integer.valueOf(map.get(dayWeeks[i])));
        }
        DateTime newServiceTime = new DateTime(serviceTime);
        //判断在不在星期内
        if (!intWeeks.contains(newServiceTime.getDayOfWeek())) {
            return false;
        }
        LocalTime startLocalTime = new LocalTime(startTime);
        LocalTime endLocalTime = new LocalTime(endTime);
        LocalTime serviceLocalTime = newServiceTime.toLocalTime();
        return startLocalTime.isAfter(serviceLocalTime) && endLocalTime.isAfter(serviceLocalTime);
    }

    public static void getFormat(String template, Object... arguments) {
        MessageFormat.format(template, arguments);
    }


    /**
     * 替换
     * @param oldStr
     * @param replaceContent
     * @return
     */
    public static String replace(String oldStr, String replaceContent) {
        StringBuffer result = new StringBuffer(oldStr).replace(3, 7, replaceContent);
        return result.toString();
    }



}





