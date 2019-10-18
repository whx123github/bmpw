package com.jt.baimo.pw.util;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class DateUtil {
    public static final long ONE_HOUR_TIME_LONG = 3600000;
    public static final String NYR = "yyyy-MM-dd";//年月日
    public static final String NYR_SFM = "yyyy-MM-dd HH:mm:ss";//年月日时分秒

    /**
     * 时间转字符串
     */
    public static String toString(Date date, String format) {
        String dateStr = null;
        try {
            SimpleDateFormat sdf = new SimpleDateFormat(format);
            dateStr = sdf.format(date);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return dateStr;
    }

    /**
     * 字符串转时间 自定义
     */
    public static Date parseDate(String dateStr, String format) {
        Date date = null;
        try {
            SimpleDateFormat sdf = new SimpleDateFormat(format);
            date = sdf.parse(dateStr);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return date;
    }


    /**
     * 获取当前时间 自定义
     */
    public static Date getDate(String format) {
        Date date = null;
        try {
            SimpleDateFormat sdf = new SimpleDateFormat(format);
            date = parseDate(sdf.format(new Date()), format);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return date;
    }

//-------------------------------------------------------------------

    /**
     * 获取日期当天的最小时间日期,0点
     */
    public static Date getMinTimeDateByDate(Date date) {
        if (date == null)
            return null;
        String datestr = toString(date, "yyyyMMdd");
        return parseDate(datestr, "yyyyMMdd");
    }

    /**
     * 获取日期当天的最大时间日期,12点整
     */
    public static Date getMaxTimeDateByDate(Date date) {
        if (date == null)
            return null;
        String datestr = toString(date, "yyyyMMdd");
        Date d = parseDate(datestr, "yyyyMMdd");
        return new Date(d.getTime() + 24l * 60l * 60l * 1000l - 1l);
    }

    public static long subTime(Date startDate, Date endDate) {
        return endDate.getTime() - startDate.getTime();
    }

    /**
     * 获取上月第一天最早时间
     *
     * @return Date
     */
    public static Date getLastMonthFirstDay() {
        Calendar cal_1 = Calendar.getInstance();// 获取当前日期
        cal_1.setTime(getMinTimeDateByDate(new Date()));
        cal_1.add(Calendar.MONTH, -1);
        cal_1.set(Calendar.DAY_OF_MONTH, 1);
        return cal_1.getTime();
    }

    /**
     * 获取上月最后一天最晚时间
     *
     * @return Date
     */
    public static Date getLastMonthLastDay() {
        Calendar cale = Calendar.getInstance();
        cale.setTime(getMinTimeDateByDate(new Date()));
        cale.add(Calendar.MONTH, -1);
        cale.set(Calendar.DAY_OF_MONTH, cale.getActualMaximum(Calendar.DAY_OF_MONTH));
        return new Date(cale.getTime().getTime() + 1000l * 60l * 60l * 24l - 1l);
    }

    /**
     * 获取本月第一天最早时间
     *
     * @return Date
     */
    public static Date getNowMonthFirstDay() {
        Calendar cal_1 = Calendar.getInstance();// 获取当前日期
        cal_1.setTime(getMinTimeDateByDate(new Date()));
        cal_1.add(Calendar.MONTH, 0);
        cal_1.set(Calendar.DAY_OF_MONTH, 1);
        return cal_1.getTime();
    }

    /**
     * 获取本月最后一天最晚时间
     *
     * @return Date
     */
    public static Date getNowMonthLastDay() {
        Calendar cale = Calendar.getInstance();
        cale.setTime(getMinTimeDateByDate(new Date()));
        cale.set(Calendar.DAY_OF_MONTH, cale.getActualMaximum(Calendar.DAY_OF_MONTH));
        return new Date(cale.getTime().getTime() + 1000l * 60l * 60l * 24l - 1l);
    }

    /**
     * 获取本月最后一天
     *
     * @return Date
     */
    public static Date getTheMonthLastDay(Date date) {
        if (date == null) {
            return null;
        }
        Calendar cale = Calendar.getInstance();
        cale.setTime(date);
        cale.set(Calendar.DAY_OF_MONTH, cale.getActualMaximum(Calendar.DAY_OF_MONTH));
        cale.set(Calendar.HOUR, 0);
        cale.set(Calendar.HOUR_OF_DAY, 0);
        cale.set(Calendar.MINUTE, 0);
        cale.set(Calendar.SECOND, 0);
        cale.set(Calendar.MILLISECOND, 0);
        return cale.getTime();
    }


    /***
     * 将毫秒值转成天/小时/分钟/秒
     * @param mss
     * @return
     */
    public static String formatDateTime(long mss) {
        String dateTimes = null;
        long days = mss / (60 * 60 * 24);
        long hours = (mss % (60 * 60 * 24)) / (60 * 60);
        long minutes = (mss % (60 * 60)) / 60;
        long seconds = mss % 60;
        if (days > 0) {
            dateTimes = days + "天" + hours + "小时" + minutes + "分钟" + seconds + "秒";
        } else if (hours > 0) {
            dateTimes = hours + "小时" + minutes + "分钟" + seconds + "秒";
        } else if (minutes > 0) {
            dateTimes = minutes + "分钟" + seconds + "秒";
        } else {
            dateTimes = seconds + "秒";
        }
        return dateTimes;
    }

}
