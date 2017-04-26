package cn.com.watchman;

import android.app.Activity;
import android.os.Bundle;

import com.github.mzule.activityrouter.annotation.Router;
import com.linked.erfli.library.base.BaseActivity;
import com.linked.erfli.library.base.BaseEventActivity;


/**
 * 文件名：WatchManActivity
 * 描    述：巡更类
 * 作    者：stt
 * 时    间：2017.4.25
 * 版    本：V1.0.0
 */
@Router("watchman")
public class WatchManActivity extends BaseActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_watchman);
    }

    @Override
    protected void setView() {

    }

    @Override
    protected void setDate(Bundle savedInstanceState) {

    }

    @Override
    protected void init() {

    }
}
