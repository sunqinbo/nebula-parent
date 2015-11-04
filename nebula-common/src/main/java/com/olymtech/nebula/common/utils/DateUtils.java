/**
 * Olymtech.com Inc.
 * Copyright (c) 2002-2015 All Rights Reserved.
 */
package com.olymtech.nebula.common.utils;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * Created by Gavin on 2015-09-11 16:25.
 */
public class DateUtils {
    private static Logger logger = LoggerFactory.getLogger(DateUtils.class);

    /** 完整时间 */
    public static final String simple = "yyyy-MM-dd HH:mm:ss";

    /** 完整事件（中文） */
    public static final String simpleChinese = "yyyy年MM月dd日 HH:mm:ss";

    /** 年月日 */
    public static final String dtSimple = "yyyy-MM-dd";

    /** 年月日(中文) */
    public static final String dtSimpleChinese = "yyyy年MM月dd日";

    public static final String week = "EEEE";

    /** 年月日(无下划线) */
    public static final String dtShort = "yyyyMMdd";

    /** 年月日时分秒(无下划线) */
    public static final String dtLong = "yyyyMMddHHmmss";

    /** 年月日时分秒(无下划线) */
    public static final String dtLongMinute = "mm";

    public static final String dtLongSimple = "yyyyMMddHHmm";

    /** 时分秒 */
    public static final String hmsFormat = "HH:mm:ss";

    /** 年-月-日 小时:分钟 */
    public static final String simpleFormat = "yyyy-MM-dd HH:mm";

    /** 年 */
    public static final String yearFormat = "yyyy";

    /** WEB页面日期格式 */
    public static final String webFormat = "yyyy-MM-dd HH:mm";

    /**  ISO8601时间格式 */
    public static final String simpleISO8601 = "yyyy-MM-dd\'T\'HH:mm:ss\'Z\'";
    public static final String simpleFormatISO8601 = "yyyy-MM-dd\'T\'HH:mm\'Z\'";

    /** 时间key格式*/
    public static final String SimpleKey="yyyyMMdd.HHmmss";

    /**
     * 获取格式
     *
     * @param format
     * @return
     */
    public static final DateFormat getFormat(String format) {
        return new SimpleDateFormat(format);
    }

    /**
     * yyyy-MM-dd HH:mm:ss
     *
     * @param date
     * @return
     */
    public static final String simpleFormat(Date date) {
        if (date == null) {
            return "";
        }
        return getFormat(simple).format(date);
    }

    public static final String getKeyDate(Date date){
        if(date==null){
            return "";
        }
        return getFormat(SimpleKey).format( date );
    }

    /**
     * 获取某日期的WEB网页所需格式 yyyy-MM-dd HH:mm"
     *
     * @param date
     * @return
     */
    public static final String getWebDateString(Date date) {
        if (date == null) {
            return "";
        }
        return getFormat(webFormat).format(date);
    }

    /**
     * yyyy年MM月dd HH:mm:ss
     *
     * @param date
     * @return
     */
    public static final String simpleChineseFormat(Date date) {
        if (date == null) {
            return "";
        }
        return getFormat(simpleChinese).format(date);
    }

    public static Date addMinutes(Date date1, long minute) {
        return new Date(date1.getTime() + (minute * 1000 * 60));
    }

    /**
     * yyyy-MM-dd HH:mm 日期格式转换为日期
     *
     * @param strDate
     *
     * @return
     */
    public static final Date strToSimpleFormat(String strDate) {
        if (strDate == null) {
            return null;
        }

        try {
            return getFormat(simpleFormat).parse(strDate);

        } catch (Exception e) {
        }

        return null;
    }

    public static Date setSecondsToEmpty(Date date) {
        Calendar calendar = Calendar.getInstance();
        int timeTypes[] = { Calendar.SECOND, Calendar.MILLISECOND };
        calendar.setTime(date);
        for (int i = 0; i < timeTypes.length; i++) {
            calendar.set(timeTypes[i], 0);
        }
        return calendar.getTime();
    }

    /**
     * yyyy-MM-dd HH:mm:ss 或者 yyyy-MM-dd HH:mm 或者yyyy-MM-dd 转换为日期格式
     *
     * @param strDate
     * @return
     */
    public static final Date strToDate(String strDate) {
        if (string2DateTime(strDate) != null) {
            return string2DateTime(strDate);
        } else if (strToSimpleFormat(strDate) != null) {
            return strToSimpleFormat(strDate);
        } else {
            return strToDtSimpleFormat(strDate);
        }

    }

    /**
     * 获取输入日期的相差日期
     *
     * @param dt
     * @param idiff
     *
     * @return
     */
    public static final String getDiffDate(Date dt, int idiff) {
        Calendar c = Calendar.getInstance();

        c.setTime(dt);
        c.add(Calendar.DATE, idiff);
        return dtSimpleFormat(c.getTime());
    }

    /**
     * 获取输入日期月份的相差日期
     *
     * @param dt
     * @param idiff
     * @return
     */
    public static final String getDiffMon(Date dt, int idiff) {
        Calendar c = Calendar.getInstance();
        c.setTime(dt);
        c.add(Calendar.MONTH, idiff);
        return dtSimpleFormat(c.getTime());
    }

    /**
     * yyyy年MM月dd日
     *
     * @param date
     *
     * @return
     */
    public static final String dtSimpleChineseFormat(Date date) {
        if (date == null) {
            return "";
        }

        return getFormat(dtSimpleChinese).format(date);
    }

    /**
     * yyyy-MM-dd到 yyyy年MM月dd日 转换
     *
     * @param date
     *
     * @return
     */
    public static final String dtSimpleChineseFormatStr(String date)
            throws ParseException {
        if (date == null) {
            return "";
        }

        return getFormat(dtSimpleChinese).format(string2Date(date));
    }

    /**
     * yyyy-MM-dd 日期字符转换为时间
     *
     * @param stringDate
     *
     * @return
     *
     * @throws ParseException
     */
    public static final Date string2Date(String stringDate)
            throws ParseException {
        if (stringDate == null) {
            return null;
        }

        return getFormat(dtSimple).parse(stringDate);
    }

    /**
     *
     * @param stringDate
     *
     * @return
     *
     * @throws ParseException
     */
    public static final Date string2DateTime(String stringDate) {
        if (stringDate == null) {
            return null;
        }
        try {
            return getFormat(simple).parse(stringDate);
        } catch (Exception e) {
            return null;
        }
    }

    /**
     * 返回日期时间
     *
     * @param stringDate
     *
     * @return
     *
     * @throws ParseException
     */
    public static final Date string2DateTimeByAutoZero(String stringDate)
            throws ParseException {
        if (stringDate == null) {
            return null;
        }
        if (stringDate.length() == 11) {
            stringDate = stringDate + "00:00:00";
        } else if (stringDate.length() == 13) {
            stringDate = stringDate + ":00:00";
        } else if (stringDate.length() == 16) {
            stringDate = stringDate + ":00";
        } else if (stringDate.length() == 10) {
            stringDate = stringDate + " 00:00:00";
        }

        return getFormat(simple).parse(stringDate);
    }

    /**
     * 返回日期时间(时分秒：23:59:59)
     *
     * @param stringDate
     *            String 型
     *
     * @return
     *
     * @throws ParseException
     */
    public static final Date string2DateTimeBy23(String stringDate)
            throws ParseException {
        if (stringDate == null) {
            return null;
        }
        if (stringDate.length() == 11) {
            stringDate = stringDate + "23:59:59";
        } else if (stringDate.length() == 13) {
            stringDate = stringDate + ":59:59";
        } else if (stringDate.length() == 16) {
            stringDate = stringDate + ":59";
        } else if (stringDate.length() == 10) {
            stringDate = stringDate + " 23:59:59";
        }

        return getFormat(simple).parse(stringDate);
    }

    /**
     * 返回日期时间(时分秒：23:59:59)
     *
     * @param date
     *            Date 型
     *
     * @return
     *
     * @throws ParseException
     */
    @SuppressWarnings("deprecation")
    public static final Date dateTimeTo23(Date date) {
        if (date == null) {
            return null;
        }
        date.setHours(23);
        date.setMinutes(59);
        date.setSeconds(59);

        return date;
    }

    /**
     * 返回日期时间(时分秒：00:00:00 000)
     *
     * @param date
     * @return
     */
    public static final Date dateTimeToZero(Date date) {
        if (date == null) {
            return null;
        }
        return strToDtSimpleFormat(dtSimpleFormat(date));
    }

    /**
     * 计算日期差值
     *
     * @param beforDate
     * @param afterDate
     * @return int（天数）
     */
    public static final int calculateDecreaseDate(String beforDate,
                                                  String afterDate) throws ParseException {
        Date date1 = getFormat(dtSimple).parse(beforDate);
        Date date2 = getFormat(dtSimple).parse(afterDate);
        long decrease = getDateBetween(date1, date2) / 1000 / 3600 / 24;
        int dateDiff = (int) decrease;
        return dateDiff;
    }

    /**
     * 计算时间差
     *
     * @param dBefor
     *            首日
     * @param dAfter
     *            尾日
     * @return 时间差(毫秒)
     */
    public static final long getDateBetween(Date dBefor, Date dAfter) {
        long lBefor = 0;
        long lAfter = 0;
        long lRtn = 0;

        /** 取得距离 1970年1月1日 00:00:00 GMT 的毫秒数 */
        lBefor = dBefor.getTime();
        lAfter = dAfter.getTime();

        lRtn = lAfter - lBefor;

        return lRtn;
    }

    /**
     * 返回日期时间
     *
     * @param stringDate
     *            (yyyyMMdd)
     *
     * @return
     *
     * @throws ParseException
     */
    public static final Date shortstring2Date(String stringDate) {
        if (stringDate == null) {
            return null;
        }
        try {
            return getFormat(dtShort).parse(stringDate);
        } catch (Exception e) {
            return null;
        }
    }

    public static final String shortStringToString(String stringDate)
            throws ParseException {
        if (stringDate == null) {
            return null;
        }
        return shortDate(strToDtSimpleFormat(stringDate));
    }

    /**
     * 返回短日期格式（yyyyMMdd格式）
     *
     * @param Date
     *
     * @return 返回短日期格式（yyyyMMdd格式）
     *
     * @throws ParseException
     */
    public static final String shortDate(Date Date) {
        if (Date == null) {
            return null;
        }

        return getFormat(dtShort).format(Date);
    }

    /**
     * 返回长日期格式（yyyyMMddHHmmss格式）
     *
     * @param Date
     *
     * @return 返回长日期格式（yyyyMMddHHmmss格式）
     *
     * @throws ParseException
     */
    public static final String longDate(Date Date) {
        if (Date == null) {
            return null;
        }

        return getFormat(dtLong).format(Date);
    }


    /**
     * @Title: LongMinute
     * @Description: 返回长日期格式（mm）   分钟
     * @param @param Date
     * @param @return
     * @return String
     * @throws
     */
    public static final String longMinute(Date Date) {
        if (Date == null) {
            return null;
        }
        return getFormat(dtLongMinute).format(Date);
    }

    /**
     * 返回长日期格式（yyyyMMddHHmm格式）
     *
     * @param Date
     *
     * @return 返回长日期格式（yyyyMMddHHmm格式）
     *
     * @throws ParseException
     */
    public static final String longDateSimple(Date Date) {
        if (Date == null) {
            return null;
        }

        return getFormat(dtLongSimple).format(Date);
    }

    /**
     * yyyy-MM-dd 日期字符转换为长整形
     *
     * @param stringDate
     *
     * @return
     *
     * @throws ParseException
     */
    public static final Long string2DateLong(String stringDate)
            throws ParseException {
        Date d = string2Date(stringDate);

        if (d == null) {
            return null;
        }

        return Long.valueOf(d.getTime());
    }

    /**
     * 日期转换为字符串 HH:mm:ss
     *
     * @param date
     *
     * @return
     */
    public static final String hmsFormat(Date date) {
        if (date == null) {
            return "";
        }

        return getFormat(hmsFormat).format(date);
    }

    /**
     * 时间转换字符串 2005-06-30 15:50
     *
     * @param date
     *
     * @return
     */
    public static final String simpleDate(Date date) {
        if (date == null) {
            return "";
        }

        return getFormat(simpleFormat).format(date);
    }

    /**
     * 字符串 2005-06-30 15:50 转换成时间
     *
     * @param dateString
     *            String
     * @return
     * @throws ParseException
     */
    public static final Date simpleFormatDate(String dateString)
            throws ParseException {
        if (dateString == null) {
            return null;
        }
        return getFormat(simpleFormat).parse(dateString);
    }

    /**
     * 获取当前日期的日期差 now= 2005-07-19 diff = 1 -> 2005-07-20 diff = -1 -> 2005-07-18
     *
     * @param diff
     *
     * @return
     */
    public static final String getDiffDate(int diff) {
        Calendar c = Calendar.getInstance();

        c.setTime(new Date());
        c.add(Calendar.DATE, diff);
        return dtSimpleFormat(c.getTime());
    }

    public static final Date getDiffDateTime(int diff) {
        Calendar c = Calendar.getInstance();

        c.setTime(new Date());
        c.add(Calendar.DATE, diff);
        return c.getTime();
    }

    public static final String getDiffDate(String srcDate, String format,
                                           int diff) {
        DateFormat f = new SimpleDateFormat(format);

        try {
            Date source = f.parse(srcDate);
            Calendar c = Calendar.getInstance();

            c.setTime(source);
            c.add(Calendar.DATE, diff);
            return f.format(c.getTime());
        } catch (Exception e) {
            return srcDate;
        }
    }

    /**
     * 获取前一天
     *
     * @param StringDate
     *
     * @return
     *
     * @throws ParseException
     */
    public static String getBeforeDay(String StringDate) throws ParseException {
        Date tempDate = DateUtils.string2Date(StringDate);
        Calendar cad = Calendar.getInstance();

        cad.setTime(tempDate);
        cad.add(Calendar.DATE, -1);
        return DateUtils.dtSimpleFormat(cad.getTime());
    }

    /**
     * 获取前一天 返回 dtSimple 格式字符
     *
     * @param date
     *
     * @return
     *
     * @throws ParseException
     */
    public static String getBeforeDay(Date date) throws ParseException {
        Calendar cad = Calendar.getInstance();
        cad.setTime(date);
        cad.add(Calendar.DATE, -1);
        return DateUtils.dtSimpleFormat(cad.getTime());
    }

    /**
     * 获取当前日期的日期时间差
     *
     * @param diff
     * @param hours
     *
     * @return
     */
    public static final String getDiffDateTime(int diff, int hours) {
        Calendar c = Calendar.getInstance();

        c.setTime(new Date());
        c.add(Calendar.DATE, diff);
        c.add(Calendar.HOUR, hours);
        return dtSimpleFormat(c.getTime());
    }

    /**
     * 把日期类型的日期换成数字类型 YYYYMMDD类型
     *
     * @param date
     *
     * @return
     */
    public static final Long dateToNumber(Date date) {
        if (date == null) {
            return null;
        }

        Calendar c = Calendar.getInstance();

        c.setTime(date);

        String month;
        String day;

        if ((c.get(Calendar.MONTH) + 1) >= 10) {
            month = "" + (c.get(Calendar.MONTH) + 1);
        } else {
            month = "0" + (c.get(Calendar.MONTH) + 1);
        }

        if (c.get(Calendar.DATE) >= 10) {
            day = "" + c.get(Calendar.DATE);
        } else {
            day = "0" + c.get(Calendar.DATE);
        }

        String number = c.get(Calendar.YEAR) + "" + month + day;

        return new Long(number);
    }

    /**
     * 获取每月的某天到月末的区间
     *
     * @param StringDate
     *
     * @return
     */
    @SuppressWarnings({ "unchecked", "rawtypes" })
    public static Map getLastWeek(String StringDate, int interval)
            throws ParseException {
        Map lastWeek = new HashMap();
        Date tempDate = DateUtils.shortstring2Date(StringDate);
        Calendar cad = Calendar.getInstance();

        cad.setTime(tempDate);

        int dayOfMonth = cad.getActualMaximum(Calendar.DAY_OF_MONTH);

        cad.add(Calendar.DATE, (dayOfMonth - 1));
        lastWeek.put("endDate", DateUtils.shortDate(cad.getTime()));
        cad.add(Calendar.DATE, interval);
        lastWeek.put("startDate", DateUtils.shortDate(cad.getTime()));

        return lastWeek;
    }

    /**
     * 获取下月
     *
     * @param StringDate
     *
     * @return
     */
    public static String getNextMon(String StringDate) throws ParseException {
        Date tempDate = DateUtils.shortstring2Date(StringDate);
        Calendar cad = Calendar.getInstance();

        cad.setTime(tempDate);
        cad.add(Calendar.MONTH, 1);
        return DateUtils.shortDate(cad.getTime());
    }

    /**
     * 获取下一天
     *
     * @param StringDate
     *
     * @return
     *
     * @throws ParseException
     */
    public static String getNextDay(String StringDate) throws ParseException {
        Date tempDate = DateUtils.string2Date(StringDate);
        Calendar cad = Calendar.getInstance();

        cad.setTime(tempDate);
        cad.add(Calendar.DATE, 1);
        return DateUtils.dtSimpleFormat(cad.getTime());
    }

    /**
     * 获取下一天 返回 dtSimple 格式字符
     *
     * @param date
     *
     * @return
     *
     * @throws ParseException
     */
    public static String getNextDay(Date date) throws ParseException {
        Calendar cad = Calendar.getInstance();
        cad.setTime(date);
        cad.add(Calendar.DATE, 1);
        return DateUtils.dtSimpleFormat(cad.getTime());
    }

    /**
     * 获取下一天 返回 dtshort 格式字符
     *
     * @param StringDate
     *            "20061106"
     *
     * @return String "2006-11-07"
     *
     * @throws ParseException
     */
    public static Date getNextDayDtShort(String StringDate)
            throws ParseException {
        Date tempDate = DateUtils.shortstring2Date(StringDate);
        Calendar cad = Calendar.getInstance();

        cad.setTime(tempDate);
        cad.add(Calendar.DATE, 1);
        return cad.getTime();
    }

    /**
     * 取得相差的天数
     *
     * @param startDate
     * @param endDate
     *
     * @return
     */
    public static long countDays(String startDate, String endDate) {
        Date tempDate1 = null;
        Date tempDate2 = null;
        long days = 0;

        try {
            tempDate1 = DateUtils.string2Date(startDate);

            tempDate2 = DateUtils.string2Date(endDate);
            days = (tempDate2.getTime() - tempDate1.getTime())
                    / (1000 * 60 * 60 * 24);
        } catch (ParseException e) {
            logger.error("DateUtils ParseException:",e);
        }
        return days;
    }

    /**
     * 取得相差的天数
     *
     * @param date
     * @param month
     *
     * @return
     */
    @SuppressWarnings("deprecation")
    public static Date addMonth(Date date, int month) {
        Date retDate = (Date) date.clone();
        retDate.setMonth(retDate.getMonth() + month);
        return retDate;

    }

    /**
     * 返回日期相差天数，向下取整数
     *
     * @param dateStart
     *            一般前者小于后者dateEnd
     * @param dateEnd
     *
     * @return
     */
    public static int countDays(Date dateStart, Date dateEnd) {
        if ((dateStart == null) || (dateEnd == null)) {
            return -1;
        }

        return (int) ((dateEnd.getTime() - dateStart.getTime()) / (1000 * 60 * 60 * 24));
    }

    /**
     * 校验start与end相差的天数，是否满足end-start lessEqual than days
     *
     * @param start
     * @param end
     * @param days
     *
     * @return
     */
    public static boolean checkDays(Date start, Date end, int days) {
        int g = countDays(start, end);

        return g <= days;
    }

    public static Date now() {
        return new Date();
    }

    /**
     * 获取传入时间相差的日期
     *
     * @param dt
     *            传入日期，可以为空
     * @param diff
     *            需要获取相隔diff天的日期 如果为正则取以后的日期，否则时间往前推
     *
     * @return
     */
    public static String getDiffStringDate(Date dt, int diff) {
        Calendar ca = Calendar.getInstance();

        if (dt == null) {
            ca.setTime(new Date());
        } else {
            ca.setTime(dt);
        }

        ca.add(Calendar.DATE, diff);
        return dtSimpleFormat(ca.getTime());
    }

    /**
     * 校验输入的时间格式是否合法，但不需要校验时间一定要是8位的
     *
     * @param statTime
     *
     * @return alahan
     */
    public static boolean checkTime(String statTime) {
        if (statTime.length() > 8) {
            return false;
        }

        String[] timeArray = statTime.split(":");

        if (timeArray.length != 3) {
            return false;
        }

        for (int i = 0; i < timeArray.length; i++) {
            String tmpStr = timeArray[i];

            try {
                Integer tmpInt = new Integer(tmpStr);

                if (i == 0) {
                    if ((tmpInt.intValue() > 23) || (tmpInt.intValue() < 0)) {
                        return false;
                    } else {
                        continue;
                    }
                }

                if ((tmpInt.intValue() > 59) || (tmpInt.intValue() < 0)) {
                    return false;
                }
            } catch (Exception e) {
                return false;
            }
        }

        return true;
    }

    /**
     * 将字符串按format格式转换为date类型
     *
     * @param str
     * @param format
     *
     * @return
     */
    public static Date string2Date(String str, String format) {
        SimpleDateFormat sdf = new SimpleDateFormat(format);

        try {
            return sdf.parse(str);
        } catch (ParseException e) {
            return null;
        }
    }

    /**
     * 加减天数
     *
     * @param aDate
     * @return Date
     *
     */
    public static final Date increaseDate(Date aDate, int days) {
        Calendar cal = Calendar.getInstance();

        cal.setTime(aDate);
        cal.add(Calendar.DAY_OF_MONTH, days);
        return cal.getTime();
    }

    /**
     * 是否闰年
     *
     * @param year
     * @return
     */
    public static final boolean isLeapYear(int year) {
        return (year % 4 == 0 && year % 100 != 0) || (year % 400 == 0);

    }

    /**
     * 判断是否是默认工作日，一般默认工作日是星期一都星期五， 所以，这个函数本质是判断是否是星期一到星期五
     *
     * @param date
     * @return
     */
    public static final boolean isDefaultWorkingDay(Date date) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        int week = calendar.get(Calendar.DAY_OF_WEEK);
        return !(week == 7 || week == 1);
    }

    /**
     * 根据年份获取所有的Date
     *
     * @param year
     * @return
     */
    @SuppressWarnings("deprecation")
    public static final List<Date> getAllDates(int year) {
        Date firstDay = new Date(year - 1900, 0, 1);
        int dayCount = 365;
        if (DateUtils.isLeapYear(year)) {
            dayCount = 366;
        }
        List<Date> dateList = new ArrayList<Date>();
        for (int i = 1; i <= dayCount; i++) {
            dateList.add(DateUtils.increaseDate(firstDay, i - 1));
        }

        return dateList;
    }

    /**
     * 根据年份月份获取所有的Date
     *
     * @param year
     * @param month
     * @return
     */
    @SuppressWarnings("deprecation")
    public static final List<Date> getAllDates(int year, int month) {
        Date firstDay = new Date(year - 1900, month, 1);
        int dayCount = 30;
        if (month == 1 || month == 3 || month == 5 || month == 7 || month == 8
                || month == 10 || month == 12) {
            dayCount = 31;
        } else if (month == 2) {
            if (DateUtils.isLeapYear(year)) {
                dayCount = 29;
            } else {
                dayCount = 28;
            }
        }

        List<Date> dateList = new ArrayList<Date>();
        for (int i = 1; i <= dayCount; i++) {
            dateList.add(DateUtils.increaseDate(firstDay, i - 1));
        }
        return dateList;
    }

    /**
     * 构造Date
     *
     * @param year
     * @param month
     * @param day
     * @return
     */
    @SuppressWarnings("deprecation")
    public static final Date newDate(int year, int month, int day) {
        return new Date(year - 1900, month - 1, day);
    }

    /**
     * 获取星期名，如“星期一”、“星期二”
     *
     * @param date
     * @return
     */
    public static final String getWeekDay(Date date) {
        return getFormat(week).format(date);
    }

    @SuppressWarnings("deprecation")
    public static final Date getCurrentYearMonthDay() {
        Date date = new Date();
        return new Date(date.getYear(), date.getMonth(), date.getDate());
    }

    /**
     * 获取当前时间的字符串格式，以半个小时为单位<br>
     * 当前时间2007-02-02 22:23 则返回 2007-02-02 22:00 当前时间2007-02-02 22:33 则返回
     * 2007-02-02 22:30
     *
     * @return
     */
    public static final String getNowDateForPageSelectAhead() {

        Calendar cal = Calendar.getInstance();
        if (cal.get(Calendar.MINUTE) < 30) {
            cal.set(Calendar.MINUTE, 0);
        } else {
            cal.set(Calendar.MINUTE, 30);
        }
        return simpleDate(cal.getTime());
    }

    /**
     * 获取当前时间的字符串格式，以半个小时为单位<br>
     * 当前时间2007-02-02 22:23
     *
     * @return
     */
    public static final String getCurrentDateTime() {
        Calendar cal = Calendar.getInstance();

        return simpleDate(cal.getTime());
    }

    public static final Date getCurrentfdateTime() {
        Calendar cal = Calendar.getInstance();

        return cal.getTime();
    }

    /**
     * 获取当前时间的字符串格式，以半个小时为单位<br>
     * 当前时间2007-02-02 22:23 则返回 2007-02-02 22:30 当前时间2007-02-02 22:33 则返回
     * 2007-02-02 23:00
     *
     * @return
     */
    public static final String getNowDateForPageSelectBehind() {
        Calendar cal = Calendar.getInstance();
        if (cal.get(Calendar.MINUTE) < 30) {
            cal.set(Calendar.MINUTE, 30);
        } else {
            cal.set(Calendar.HOUR_OF_DAY, cal.get(Calendar.HOUR_OF_DAY) + 1);
            cal.set(Calendar.MINUTE, 0);
        }
        return simpleDate(cal.getTime());
    }

    /**
     * 获取当前时间的Date，以半个小时为单位<br>
     * 当前时间2007-02-02 22:23 则返回 2007-02-02 22:30 当前时间2007-02-02 22:33 则返回
     * 2007-02-02 23:00
     *
     * @return
     */
    public static final Date getNowDateForPageSelectBehind_Date() {
        Calendar cal = Calendar.getInstance();
        if (cal.get(Calendar.MINUTE) < 30) {
            cal.set(Calendar.MINUTE, 30);
        } else {
            cal.set(Calendar.HOUR_OF_DAY, cal.get(Calendar.HOUR_OF_DAY) + 1);
            cal.set(Calendar.MINUTE, 0);
        }
        return cal.getTime();
    }

    /**
     * 获取当前时间的Date，以半个小时为单位<br>
     * 当前时间2007-02-02 22:23 则返回 2007-02-02 22:00 当前时间2007-02-02 22:33 则返回
     * 2007-02-02 22:30
     *
     * @return
     */
    public static final Date getNowDateForPageSelectAhead_Date() {

        Calendar cal = Calendar.getInstance();
        if (cal.get(Calendar.MINUTE) < 30) {
            cal.set(Calendar.MINUTE, 0);
        } else {
            cal.set(Calendar.MINUTE, 30);
        }
        return cal.getTime();
    }

    /*
     * (non-Javadoc)
     *
     * @see com.himalayas.biz.core.system.DailyFacade#getWeekStartEnd()
     */
    @SuppressWarnings("deprecation")
    public static Date[] getWeekStartEnd(Date date) {

        if (date == null) {
            date = DateUtils.now();
        }
        Calendar cal = Calendar.getInstance();
        cal.setTime(date);
        int weekDay = cal.get(Calendar.DAY_OF_WEEK);

        Date startDate = DateUtils.increaseDate(date, -weekDay + 2);
        Date endDate = DateUtils.increaseDate(date, 8 - weekDay);
        return new Date[] {
                new Date(startDate.getYear(), startDate.getMonth(),
                        startDate.getDate()),
                new Date(endDate.getYear(), endDate.getMonth(),
                        endDate.getDate(), 23, 59) };

    }

    @SuppressWarnings("deprecation")
    public static void initDateStart(Date date) {
        if (date == null) {
            return;
        }
        date.setHours(0);
        date.setMinutes(0);
        date.setSeconds(0);
    }

    @SuppressWarnings("deprecation")
    public static void initDateEnd(Date date) {
        if (date == null) {
            return;
        }
        date.setHours(23);
        date.setMinutes(59);
        date.setSeconds(59);
    }

    /**
     * 获得日期是周几
     *
     * @param date
     * @return dayOfWeek
     */
    public static int getDayOfWeek(Date date) {
        Calendar cal = Calendar.getInstance();
        cal.setTime(date);
        return cal.get(Calendar.DAY_OF_WEEK);
    }

    /**
     *
     * 取得当月首日，今天如果是2008-07-14，那这个函数返回的应该是2008-07-01<br>
     * 仅支持YYYY-MM-DD日期格式
     *
     * @return
     */
    public static String getCurMonthFirstDay() {
        Calendar cal = Calendar.getInstance();
        Date d = new Date();
        cal.setTime(d);
        cal.set(Calendar.DATE, 1);
        return dtSimpleFormat(cal.getTime());
    }

    /**
     *
     * 取得某个日期所在月份的第一天，今天如果是2009-05-13，那这个函数返回的应该是2009-05-01<br>
     * 仅支持YYYY-MM-DD日期格式
     *
     * @return
     */
    public static String getCurMonthFirstDay(String date) {
        Date d = strToDtSimpleFormat(date);
        Calendar cal = Calendar.getInstance();
        cal.setTime(d);
        cal.set(Calendar.DATE, 1);
        return dtSimpleFormat(cal.getTime());
    }

    /**
     * <pre>
     * 取得本季度的首日
     * 比如今天是2009-05-09，那这个函数返回的应该是2009-04-01
     * </pre>
     *
     * @return
     */
    public static String getCurSeasonFirstDay() {
        Calendar cal = Calendar.getInstance();
        cal.setTime(new Date());
        int season = cal.get(Calendar.MONTH);
        if (season >= 0 && season <= 2) {
            cal.set(Calendar.MONTH, 0);
        }
        if (season >= 3 && season <= 5) {
            cal.set(Calendar.MONTH, 3);
        }
        if (season >= 6 && season <= 8) {
            cal.set(Calendar.MONTH, 6);
        }
        if (season >= 9 && season <= 11) {
            cal.set(Calendar.MONTH, 9);
        }
        cal.set(Calendar.DATE, 1);
        return dtSimpleFormat(cal.getTime());
    }

    /**
     * <pre>
     * 取得某个日期所在季度的第一天，比如今天是2009-05-13，那这个函数返回的应该是2009-04-01
     * 仅支持YYYY-MM-DD日期格式
     * </pre>
     *
     * @return
     */
    public static String getCurSeasonFirstDay(String date) {
        Date d = strToDtSimpleFormat(date);
        Calendar cal = Calendar.getInstance();
        cal.setTime(d);
        int season = cal.get(Calendar.MONTH);
        if (season >= 0 && season <= 2) {
            cal.set(Calendar.MONTH, 0);
        }
        if (season >= 3 && season <= 5) {
            cal.set(Calendar.MONTH, 3);
        }
        if (season >= 6 && season <= 8) {
            cal.set(Calendar.MONTH, 6);
        }
        if (season >= 9 && season <= 11) {
            cal.set(Calendar.MONTH, 9);
        }
        cal.set(Calendar.DATE, 1);
        return DateUtils.dtSimpleFormat(cal.getTime());
    }

    /**
     *
     * 根据给定的日期，得到下个月首日的日期,今天如果是2008-07-14，那返回的应该是2008-08-01<br>
     * 仅支持YYYY-MM-DD日期格式
     *
     * @param date
     * @return
     */
    public static String getNextMonthFirstDay(String date) {
        Calendar cal = Calendar.getInstance();
        Date dt = strToDtSimpleFormat(date);
        cal.setTime(dt);
        cal.add(Calendar.MONTH, 1);
        cal.set(Calendar.DATE, 1);
        return dtSimpleFormat(cal.getTime());
    }

    /**
     *
     * 根据给定的日期，得到该周一的日期<br>
     * 仅支持YYYY-MM-DD日期格式
     *
     * @param date
     * @return
     */
    public static String getMonday(String date) {
        Date d = strToDtSimpleFormat(date);
        Calendar cal = Calendar.getInstance();
        cal.setTime(d);
        cal.set(Calendar.DAY_OF_WEEK, Calendar.MONDAY);
        return dtSimpleFormat(cal.getTime());
    }

    /**
     *
     * <br>
     * 以星期一作为一周的起点<br>
     * 假如2009-05-10（星期天），那么返回2009-05-04（星期一）<br>
     * 根据给定的日期，得到该周一的日期<br>
     * 仅支持YYYY-MM-DD日期格式
     *
     * @param date
     * @return
     */
    public static String getMondayEx(String date) {
        Date d = strToDtSimpleFormat(date);
        Calendar cal = Calendar.getInstance();
        cal.setFirstDayOfWeek(Calendar.MONDAY);
        cal.setTime(d);
        cal.set(Calendar.DAY_OF_WEEK, Calendar.MONDAY);
        return dtSimpleFormat(cal.getTime());
    }

    /**
     * <pre>
     *  判断是否是周末
     *  仅支持YYYY-MM-DD日期格式
     * </pre>
     *
     * @param date
     * @return
     */
    public static boolean isWeekend(String date) {
        Calendar cal = Calendar.getInstance();
        Date d = strToDtSimpleFormat(date);
        cal.setTime(d);
        int day = cal.get(Calendar.DAY_OF_WEEK);
        if (day == Calendar.SUNDAY || day == Calendar.SATURDAY) {
            return true;
        }

        return false;
    }

    /**
     *
     * 根据给定的日期，得到该周日的日期<br>
     * 仅支持YYYY-MM-DD日期格式
     *
     * @param date
     * @return
     */
    public static String getSunday(String date) {
        Date d = strToDtSimpleFormat(date);
        Calendar cal = Calendar.getInstance();
        cal.setTime(d);
        cal.add(Calendar.WEEK_OF_YEAR, 1);
        cal.set(Calendar.DAY_OF_WEEK, Calendar.SUNDAY);
        return dtSimpleFormat(cal.getTime());
    }

    /**
     *
     * 根据给定的日期，得到该周四的日期<br>
     * 仅支持YYYY-MM-DD日期格式
     *
     * @param date
     * @return
     */
    public static String getThursday(String date) {
        Date d = strToDtSimpleFormat(date);
        Calendar cal = Calendar.getInstance();
        cal.setTime(d);
        cal.set(Calendar.DAY_OF_WEEK, Calendar.THURSDAY);
        return dtSimpleFormat(cal.getTime());
    }

    /**
     *
     * 根据给定的日期，得到该上周五的日期<br>
     * 仅支持YYYY-MM-DD日期格式
     *
     * @param date
     * @return
     */
    public static String getPreviousFriday(String date) {
        Date d = strToDtSimpleFormat(date);
        Calendar cal = Calendar.getInstance();
        cal.setTime(d);
        cal.add(Calendar.WEEK_OF_YEAR, -1);
        cal.set(Calendar.DAY_OF_WEEK, Calendar.FRIDAY);
        return dtSimpleFormat(cal.getTime());
    }

    /**
     *
     * @Description 获取上个月的1号
     * @return yyyy-mm-dd hh:mm:ss
     *
     */
    public static Date getPreviousMonthFirstDay() {
        // 获取上个月一号的日期
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(new Date());

        int month = calendar.get(Calendar.MONTH);
        int year = calendar.get(Calendar.YEAR);
        // 获取上一个月，如果是1月份，则获取上一年度的12月份
        if (month == 0) {
            year = year - 1;
            month = 11;
        } else {
            month = month - 1;
        }
        calendar.set(Calendar.YEAR, year);
        calendar.set(Calendar.MONTH, month);
        calendar.set(Calendar.DAY_OF_MONTH, 1);

        return calendar.getTime();
    }

    /**
     * 返回上个月的最后一天
     *
     * @return
     */
    public static Date getPreviousMonthLastDay() {
        Calendar calendar = Calendar.getInstance();
        calendar.add(Calendar.MONTH, -1);// 减一个月
        calendar.set(Calendar.DATE, 1);// 把日期设置为当月第一天
        calendar.roll(Calendar.DATE, -1);// 日期回滚一天，也就是本月最后一天
        return calendar.getTime();
    }

    /**
     *
     * @description 获取当天在本月的天数
     * */
    public static int getDayNum() {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(new Date());

        return calendar.get(Calendar.DAY_OF_MONTH);
    }

    /**
     *
     * @description 根据输入的在每月中的天数,得到需要的日期
     * */
    public static Date getDateFromDayNum(int dayNum) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(new Date());
        calendar.set(Calendar.DAY_OF_MONTH, dayNum);
        return calendar.getTime();
    }

    /**
     * 返回当前年份
     *
     * @return
     */
    public static String getYearString() {
        return getFormat(yearFormat).format(new Date());
    }

    /**
     * 返回一天的凌晨
     *
     * @return
     */
    public static Date getWeeHoursOfDay(Date date) {
        String dateStr = dtSimpleFormat(date);
        return strToDtSimpleFormat(dateStr);
    }

    /**
     * 返回从给定日期相差天数的date
     *
     * @param givenDate
     *            给定的日期
     * @param numOfDay
     *            与给定日期相差的天数，可以是负数
     * @return 日期,只精确到天
     */
    public static Date getDateByGivenTime(Date givenDate, int numOfDay) {
        long givenDateStamp = givenDate.getTime();
        long resultDateStamp = givenDateStamp + numOfDay * 86400000L;
        String resultDateStr = dtSimpleFormat(new Date(resultDateStamp));
        return strToDtSimpleFormat(resultDateStr);
    }

    /**
     * 返回从给定日期相差小时的date
     *
     * @param givenDate
     *            给定的日期
     * @param numOfHour
     *            与给定日期相差的小时数，可以是负数
     * @return 日期
     */
    public static Date getDateByGivenHour(Date givenDate, int numOfHour) {
        long givenDateStamp = givenDate.getTime();
        long resultDateStamp = givenDateStamp + numOfHour * 3600000;
        return new Date(resultDateStamp);
    }

    //  此工具类后续需要抽出时间整理

    /**
     * 设置格式
     *
     * @param format
     *            format 给定的格式 eg:2011-11-11
     * @return DateFormat
     */
    public static final DateFormat setFormat(String format) {
        return new SimpleDateFormat(format);
    }

    /**
     * 将给定的日期转为dtSimple格式（yyyy-MM-dd） 的字符串
     *
     * @param theDate
     *            theDate 给定的日期
     * @return String dtSimple格式的字符串
     */
    public static final String dtSimpleFormat(Date theDate) {
        if (theDate == null) {
            return "";
        }
        return getFormat(dtSimple).format(theDate);
    }

    /**
     * 将给定的日期转为MM-dd模式 的字符串
     *
     * @param theDate
     * @return
     */
    public static final String mdFormat(Date theDate) {
        if (theDate == null) {
            return "";
        }
        String dateStr = getFormat(dtSimple).format(theDate);
        dateStr = dateStr.substring(dateStr.indexOf("-") + 1, dateStr.length());

        return dateStr;
    }

    /**
     * 将给定的日期字符串转为dtSimpel格式（yyyy-MM-dd）的日期类型
     *
     * @param strDate
     *            strDate 给定的日期字符串
     * @return dtSimple格式的日期
     */
    public static final Date strToDtSimpleFormat(String strDate) {
        if (strDate == null) {
            return null;
        }

        try {
            return getFormat(dtSimple).parse(strDate);
        } catch (Exception e) {
        }

        return null;
    }

    /**
     * 返回从给定日期获得当月的第一天
     *
     * @param theDate 时间
     *            theDate 给定的日期
     * @return Date returnDate 当月的第一天的日期(YYYY-MM-dd)
     */
    public static Date getFirstDateOfMonth(Date theDate) {

        Calendar calendar = Calendar.getInstance();
        calendar.setTime(new Date());
        calendar.set(Calendar.DAY_OF_MONTH, 1);

        String dateStr = dtSimpleFormat(calendar.getTime());

        return strToDtSimpleFormat(dateStr);
    }

    /**
     * 返回从给定的日期获得当月的最后一天
     *
     * @param theDate
     *            theDate 给定的日期
     * @return Date returnDate 当月的最后一天的日期(YYYY-MM-dd)
     */
    public static Date getLastDateOfMonth(Date theDate) {

        Calendar calendar = Calendar.getInstance();
        calendar.set(Calendar.DATE, 1);
        calendar.roll(Calendar.DATE, -1);

        String dateStr = dtSimpleFormat(calendar.getTime());

        return strToDtSimpleFormat(dateStr);
    }

    /**
     * 得到某一天的凌晨
     *
     * @param theDate
     * @return
     */
    public static Date getZeroClock(Date theDate) {

        String dateStr = dtSimpleFormat(theDate);

        return strToDtSimpleFormat(dateStr);
    }

    public static int getSeconds(Date date) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        return calendar.get(Calendar.SECOND);
    }
    /**
     * Date转String
     *
     * @param date
     * @param format
     * @return
     */
    public static String date2FormatString(Date date,String format){
        SimpleDateFormat simpleDateFormat=new SimpleDateFormat(format);
        return simpleDateFormat.format(date);
    }

    /**
     * 将给定的秒数转为 xx天xx小时xx分钟 模式 的字符串
     *
     * @param secondsTotal
     * @return
     */
    public static String secondsFormatString(Integer secondsTotal){
        String resultDate ="";
        int days = 0;int hours = 0;int minutes = 0;
//        int seconds = 0;
        if(secondsTotal/3600/24>0){
            days = secondsTotal/3600/24;
            resultDate += days+"天";
            hours = (secondsTotal-3600*24*days)/3600;
            resultDate += hours+"小时";
            minutes = (secondsTotal-3600*24*days-3600*hours)/60;
            resultDate += minutes+"分钟";
//            seconds = secondsTotal-3600*24*days-3600*hours-60*minutes;
//            resultDate += seconds+"秒";
        }else{
            if(secondsTotal/3600>0){
                hours = secondsTotal/3600;
                resultDate += hours+"小时";
                minutes = (secondsTotal-3600*hours)/60;
                resultDate += minutes+"分钟";
//                seconds = secondsTotal-3600*hours-60*minutes;
//                resultDate += seconds+"秒";
            }else{
                minutes = secondsTotal/60;
                resultDate += minutes+"分钟";
//                seconds = secondsTotal-60*minutes;
//                resultDate += seconds+"秒";
            }
        }
        return resultDate;
    }

    /**
     * 给两个时间，返回时间差秒数
     * @param startDate
     * @param endDate
     * @return 相差秒数
     */
    public static Integer intervalSeconds(Date startDate,Date endDate){
        long start = startDate.getTime();
        long end = endDate.getTime();
        int seconds = (int)((end - start) / 1000);
        return seconds;
    }

    /**
     * @Title: DateUtils
     * @Description: yyyyMMddHHmmss 字符串 转 yyyy-MM-dd HH:mm:ss date类型
     * @param longDateString
     * @return date类型
     */
    public static Date longDateStringToDate(String longDateString){
        String simpleDate = longDateString.substring(0, 4)+"-"+longDateString.substring(4, 6)+"-"+
                longDateString.substring(6, 8)+" "+longDateString.substring(8, 10)+":"+
                longDateString.substring(10, 12)+":"+longDateString.substring(12, 14);
        return strToDate(simpleDate);
    }

    /**
     * @Title: isValidFormatDate
     * @Description: 验证时间格式是否有效，时间字符串 dateString    、验证的格式 format
     * @param dateString
     * @param format
     * @param @return
     * @return boolean
     */
    public static boolean isValidFormatDate(String dateString, String format){
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat(format);
        simpleDateFormat.setLenient(false);
        try {
            simpleDateFormat.parse(dateString);
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    /**
     * 参数 2015-09-17T06:55:00Z，返回date
     * @param strDate
     * @return Date
     * @throws ParseException
     */
    public static Date parseISO8601Simple(String strDate) throws ParseException {
        if(null != strDate && !"".equals(strDate)) {
            SimpleDateFormat df = new SimpleDateFormat(simpleISO8601);
            df.setTimeZone(new SimpleTimeZone(0, "GMT"));
            return df.parse(strDate);
        } else {
            return null;
        }
    }

    /**
     * 参数 2015-09-17T06:55Z，返回date
     * @param strDate
     * @return Date
     * @throws ParseException
     */
    public static Date parseISO8601SimpleFormat(String strDate) throws ParseException {
        if(null != strDate && !"".equals(strDate)) {
            SimpleDateFormat df = new SimpleDateFormat(simpleFormatISO8601);
            df.setTimeZone(new SimpleTimeZone(0, "GMT"));
            return df.parse(strDate);
        } else {
            return null;
        }
    }
}
