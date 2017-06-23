package cn.com.watchman.utils;

/**
 * Created by 志强 on 2017.6.23.
 */

public class GetTimeStamp {
    public static String getTime() {
        long time = System.currentTimeMillis() / 1000;//获取系统时间的10位的时间戳
        return String.valueOf(time);
    }
}
