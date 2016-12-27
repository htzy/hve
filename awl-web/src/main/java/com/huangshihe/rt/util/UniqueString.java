package com.huangshihe.rt.util;

import java.util.Calendar;

/**
 * Created by 黄诗鹤 on 2015/4/3.
 * create unique string
 */
public class UniqueString {
    /**
     * get unique string
     * @param prefix uniqueStr's prefix
     * @return
     */
    public static String getUniqueStr(String prefix){
        return prefix + "_" + getUniqueStr();
    }

    /**
     * get unique string without prefix
     * @return
     */
    public static String getUniqueStr(){
        Calendar cal = Calendar.getInstance();
        int year = cal.get(Calendar.YEAR);
        int month = cal.get(Calendar.MONTH) + 1;
        int days = cal.get(Calendar.DAY_OF_MONTH);
        int hour = cal.get(Calendar.HOUR_OF_DAY);
        int minutes = cal.get(Calendar.MINUTE);
        int seconds = cal.get(Calendar.SECOND);
        int MI = cal.get(Calendar.MILLISECOND);
        return "" + year + month + days + hour + minutes + seconds + MI;
    }
}
