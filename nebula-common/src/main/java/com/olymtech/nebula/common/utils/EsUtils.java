/**
 * Olymtech.com Inc.
 * Copyright (c) 2002-2015 All Rights Reserved.
 */
package com.olymtech.nebula.common.utils;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

/**
 * Created by Gavin on 2016-01-28 16:48.
 */
public class EsUtils {
    private static SimpleDateFormat sdf =   new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.000'Z'" );
    private static SimpleDateFormat s =   new SimpleDateFormat("yyyy-MM-dd" );
    private static Calendar calendar = new GregorianCalendar();

    public static String nowDate(){
        String nowDate = sdf.format(new Date());
        return nowDate;
    }

    public static String specificDateToString(Date date){
        String specificDate = sdf.format(date);
        return specificDate;
    }

    public static final Date stringToSpecificDate(String strDate) {
        if (strDate == null) {
            return null;
        }
        try {
            return sdf.parse(strDate);

        } catch (Exception e) {
        }
        return null;
    }

    public static String oneDayAgoFrom(){
        calendar.setTime(new Date());
        calendar.add(calendar.DATE,-1);
        Date date=calendar.getTime();
        String da = sdf.format(date);
        return da;
    }

    public static String twoDayAgoFrom(){
        calendar.setTime(new Date());
        calendar.add(calendar.DATE,-2);
        Date date=calendar.getTime();
        String da = sdf.format(date);
        return da;
    }

    public static String twoDayAgoF(){
        calendar.setTime(new Date());
        calendar.add(calendar.DATE,-2);
        Date date=calendar.getTime();
        String da = s.format(date);
        return da;
    }

    public static String twoDayAgoTo(){
        calendar.setTime(new Date());
        calendar.add(calendar.DATE,-1);
        Date date=calendar.getTime();
        String da = sdf.format(date);
        return da;
    }

    public static String eightDayAgoFrom(){
        calendar.setTime(new Date());
        calendar.add(calendar.DATE,-8);
        Date date=calendar.getTime();
        String da = sdf.format(date);
        return da;
    }

    public static String eightDayAgoTo(){
        calendar.setTime(new Date());
        calendar.add(calendar.DATE,-7);
        Date date=calendar.getTime();
        String da = sdf.format(date);
        return da;
    }

    public static String thirtyOneDayAgoFrom(){
        calendar.setTime(new Date());
        calendar.add(calendar.DATE,-31);
        Date date=calendar.getTime();
        String da = sdf.format(date);
        return da;
    }

    public static String thirtyOneDayAgoTo(){
        calendar.setTime(new Date());
        calendar.add(calendar.DATE,-30);
        Date date=calendar.getTime();
        String da = sdf.format(date);
        return da;
    }

}
