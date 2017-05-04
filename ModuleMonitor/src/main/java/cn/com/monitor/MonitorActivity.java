package cn.com.monitor;

import android.os.Bundle;
import android.view.KeyEvent;

import com.github.mzule.activityrouter.annotation.Router;
import com.linked.erfli.library.base.BaseActivity;
import com.linked.erfli.library.base.BaseEventActivity;
import com.linked.erfli.library.utils.SharedUtil;
import com.linked.erfli.library.utils.ToastUtil;


/**
 * 文件名：MonitorActivity
 * 描    述：视频监控类
 * 作    者：stt
 * 时    间：2017.4.25
 * 版    本：V1.0.0
 */
@Router("monitor")
public class MonitorActivity extends BaseActivity {
    private long exitTime;//上一次按退出键时间
    private static final long TIME = 2000;//双击回退键间隔时间

    @Override
    protected void setView() {
        setContentView(R.layout.activity_monitor);
    }

    @Override
    protected void setDate(Bundle savedInstanceState) {

    }

    @Override
    protected void init() {

    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            if (SharedUtil.getBoolean(this, "isMonitor", false)) {
                PGApp.finishTop();
                return true;
            } else {
                if ((System.currentTimeMillis() - exitTime) > TIME) {
                    ToastUtil.show(this, "再按一次返回键退出");
                    exitTime = System.currentTimeMillis();
                    return true;
                } else {
                    PGApp.exit();
                }
            }
            return false;
        }
        return super.onKeyDown(keyCode, event);
    }
}
