package com.maoxin.util;

import java.sql.Timestamp;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by matthewyao on 2015/9/19.
 */
public class SuperDate {

    private static String YYYY_MM_DD_HH_MM_SS = "yyyy-MM-dd HH:mm:ss";
    private static String YYYY_MM_DD = "yyyy-MM-dd";

    public static String formatTimestamp(Timestamp timestamp){
        SimpleDateFormat sdf = new SimpleDateFormat(YYYY_MM_DD_HH_MM_SS);
        return  sdf.format(timestamp);
    }

    public static String formatDate(Date date){
        SimpleDateFormat sdf = new SimpleDateFormat(YYYY_MM_DD);
        return sdf.format(date);
    }

    public static Date formatString(String dateStr){
        SimpleDateFormat sdf = new SimpleDateFormat(YYYY_MM_DD);
        Date date = new Date();
        try {
            date = sdf.parse(dateStr);
        } catch (ParseException e) {
        }
        return date;
    }
}
