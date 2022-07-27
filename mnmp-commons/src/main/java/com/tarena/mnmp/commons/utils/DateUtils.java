package com.tarena.mnmp.commons.utils;

import com.tarena.mnmp.enums.CycleLevel;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

public class DateUtils {



    public static Date generateNextTriggerTime(int cl, int clNum, Date nextTriggerTime) {
        if (cl == 0) {
            return null;
        }
        final GregorianCalendar cal = new GregorianCalendar();
        cal.setTime(nextTriggerTime);
        CycleLevel cycleLevel = CycleLevel.getInstance(cl);
        switch (cycleLevel) {
            case MINUTE:
                cal.add(Calendar.MINUTE, clNum);
                break;
            case HOUR:
                cal.add(Calendar.HOUR, clNum);
                break;
            case DAY:
                cal.add(Calendar.DAY_OF_MONTH, clNum);
                break;
            case WEEK:
                cal.add(Calendar.WEEK_OF_MONTH, clNum);
                break;
            case MONTH:
                cal.add(Calendar.MONTH, clNum);
                break;
            case YEAR:
                cal.add(Calendar.YEAR, clNum);
                break;
        }
        return cal.getTime();
    }

    public static String dateStr(Date date, String format) {
        SimpleDateFormat sdf = new SimpleDateFormat(format);
        return sdf.format(date);
    }

    public static void main(String[] args) {
        System.out.println(dateStr(new Date(), "yyyy/MM/dd/HH"));
    }

}
