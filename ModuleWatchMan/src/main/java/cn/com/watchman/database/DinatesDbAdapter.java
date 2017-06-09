package cn.com.watchman.database;

import android.content.Context;

import cn.com.watchman.bean.DinatesBean;
import cn.com.watchman.database.dbutil.MyDBHelper;


public class DinatesDbAdapter extends MyDBHelper {
    private static final String DBNAME = "MyGps.db";// 数据库名
    private static final int DBVERSION = 1;
    private static final Class<?>[] clazz = {DinatesBean.class};// 要初始化的表

    public DinatesDbAdapter(Context context) {
        super(context, DBNAME, null, DBVERSION, clazz);
    }

}
