package com.was.core.utils;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

/**
 * Created by Administrator on 2017/3/16.
 * 时间工具类
 */
public class DateUtils {

    public static final String yyyy_MM_dd_HH_mm_ss = "yyyy-MM-dd HH:mm:ss";
    public static final String yyyyMMddHHmmss = "yyyyMMddHHmmss";

    public static final String yyyy_MM_dd_HH_mm = "yyyy-MM-dd HH:mm";
    public static final String yyyy_MM_dd = "yyyy-MM-dd";

    public static final int DAY_DIFFERENCE = 864000;  // 一天有多少毫秒
    public static final int HOURS_DIFFERENCE = 3600000; //一小时有多少毫秒
    public static final int MINUTES_DIFFERENCE = 60000; //一分钟有多少毫秒
    public static final int SECONDS_DIFFERENCE = 1000;  //一秒有多少毫秒

    /**
     * 返回特定格式的日期
     *
     * @param format
     * @param date
     * @return
     */
    public static String getFormatDate(long date, String format) {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat(format);
        return simpleDateFormat.format(date);
    }


    /**
     * 得到  yyyy_MM_dd_HH_mm_ss 格式的当前日期
     *
     * @return
     */
    public static String getCurrentFormatDate() {
        return getCurrentFormatDate(yyyy_MM_dd_HH_mm_ss);
    }

    /**
     * 得到 特定格式的日期  当前日期
     *
     * @param format
     * @return
     */
    public static String getCurrentFormatDate(String format) {
        return getFormatDate(System.currentTimeMillis(), format);
    }


    /**
     * 得到 特定格式的日期  当前日期
     *
     * @param date
     * @return
     */
    public static String getDifferenceFormatDate(long date) {
        return getFormatDate(date, yyyy_MM_dd_HH_mm_ss);
    }


    /**
     * 得到date
     *
     * @param date
     * @param format
     * @return
     */
    public static Date getDate(String date, String format) {
        DateFormat dateFormat = new SimpleDateFormat(format);
        try {
            return dateFormat.parse(date);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * 得到当前日期对应的time
     *
     * @param date
     * @param format
     * @return
     */
    public static long getTime(String date, String format) {
        Date d = getDate(date, format);
        if (d != null) {
            return d.getTime();
        }
        return 0;
    }


    /**
     * 得到日历
     *
     * @param date
     * @param format
     * @return
     */
    public static Calendar getCalendar(String date, String format) {
        Date d = getDate(date, format);
        if (d != null) {
            Calendar instance = Calendar.getInstance();
            instance.setTime(d);
            return instance;
        }
        return null;
    }


    /**
     * 检查date 是否是format 模式下的
     *
     * @param date
     * @param format
     * @return
     */
    public static boolean checkFormat(String date, String format) {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat(format, Locale.CHINA);
        try {
            simpleDateFormat.setLenient(false);
            simpleDateFormat.parse(date);
        } catch (Exception e) {
            return false;
        }
        return true;
    }


    /**
     * 得到时间戳
     *
     * @return
     */
    public static String getTimestamp() {
        return getCurrentFormatDate();
    }


    /**
     * 得到日历
     *
     * @param time
     */
    public static Calendar getCalendar(long time) {
        Calendar instance = Calendar.getInstance();
        instance.setTime(new Date(time));
        return instance;
    }

    /**
     * 得到该时间的年份
     *
     * @param time
     * @return
     */
    public int getDateYear(long time) {
        return getCalendar(time).get(Calendar.YEAR);
    }

    /**
     * 得到该时间的月份
     *
     * @param time
     * @return
     */
    public int getDateMonth(long time) {
        return getCalendar(time).get(Calendar.MONTH) + 1;
    }

    /**
     * 得到该时间的日
     */
    public static int getDateDay(long time) {
        return getCalendar(time).get(Calendar.DAY_OF_MONTH);
    }


    /**
     * 比较两个日期的大小 true date1比date2前 false date1比date2后
     *
     * @param date1
     * @param date2
     * @return
     */
    public static boolean dateCompare(Date date1, Date date2) {
        return date2.compareTo(date1) == 1;
    }


    /**
     * 返回两时间之差
     *
     * @param startTime
     * @param endTime
     * @return
     */
    public static long getTimeDifference(Date startTime, Date endTime) {
        return endTime.getTime() - startTime.getTime();
    }

    /**
     * 获取时间差
     *
     * @param startTime
     * @param endTime
     * @return
     */

    public static long getDateDifference(String startTime, String endTime, String format) throws ParseException {
        SimpleDateFormat sdf = new SimpleDateFormat(format);// 格式化时间
        Date startDate = sdf.parse(endTime);
        Date endDate = sdf.parse(startTime);
        return getTimeDifference(startDate, endDate);
    }


}
