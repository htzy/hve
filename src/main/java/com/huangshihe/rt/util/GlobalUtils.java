package com.huangshihe.rt.util;

/**
 * Created by 黄诗鹤 on 2015/3/22.
 */
public class GlobalUtils {
    /**
     * 提示的时间格式：月份日期小时分钟
     */
    public static final String HINT_TIMEFORMAT = "MMddhhmm";

    /**
     * 提示的随机数的长度
     */
    public static final int HINT_NUMLENGTH = 8;

    /**
     * rule表中用来支持不定义密码规则用户，记录对应的id号（即普通方式的密码不借助提示信息）
     */
    public static final int NORULEID = 1;

    /**
     * 连续用户登录验证失败次数的上限
     */
    public static final int MAXERR0RTIMES = 3;

    /**
     * 合法用户名数据的正则表达式
     */
    public static final String USERNAMEREGEX = "[A-Za-z0-9_@.]{1,19}";

    /**
     * 合法密码数据的正则表达式
     */
    public static final String PASSWORDREGEX = "[A-Za-z0-9]{1,64}";

    /**
     * regex of legal url.
     */
    public static final String URLREGEX = "[A-Za-z0-9._/:?]{1,200}";

    /**
     * upload file location
     */
    public static final String UPLOAD_ORGFILE = "/res/photo";

    /**
     * resource location
     */
    public static final String RESOURCE_LOCATION = "http://oeerwig1e.bkt.clouddn.com/";

}
