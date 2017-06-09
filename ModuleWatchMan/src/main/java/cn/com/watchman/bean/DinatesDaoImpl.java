package cn.com.watchman.bean;

import android.content.Context;

import cn.com.watchman.database.DinatesDbAdapter;
import cn.com.watchman.database.dao.BaseDaoImpl;

/**
 * Created by Sultan on 2017/6/8.
 */

public class DinatesDaoImpl extends BaseDaoImpl<DinatesBean> {
    public DinatesDaoImpl(Context context) {
        super(new DinatesDbAdapter(context));
    }
}
