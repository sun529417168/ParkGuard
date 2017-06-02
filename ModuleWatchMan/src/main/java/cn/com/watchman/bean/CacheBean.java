package cn.com.watchman.bean;

/**
 * Created by Sultan on 2017/6/1.
 */

public class CacheBean {
    private static PathRecord pathRecord;

    public static PathRecord getPathRecord() {
        return pathRecord;
    }

    public static void setPathRecord(PathRecord pathRecord) {
        CacheBean.pathRecord = pathRecord;
    }
}
