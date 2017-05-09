package cn.com.watchman.activity;

import android.app.Notification;
import android.app.NotificationManager;
import android.content.Context;
import android.os.Bundle;
import android.view.KeyEvent;
import android.widget.ImageButton;
import android.widget.TextView;

import com.github.mzule.activityrouter.annotation.Router;
import com.linked.erfli.library.base.BaseActivity;
import com.linked.erfli.library.utils.SharedUtil;
import com.linked.erfli.library.utils.ToastUtil;

import cn.com.watchman.R;
import cn.com.watchman.weight.RadarView;


/**
 * 文件名：WatchManActivity
 * 描    述：巡更类
 * 作    者：stt
 * 时    间：2017.4.25
 * 版    本：V1.0.0
 */
@Router("watchman")
public class WatchMainActivity extends BaseActivity {
    private long exitTime;//上一次按退出键时间
    private static final long TIME = 2000;//双击回退键间隔时间
    private TextView tv_Title_TextView;//标题文本
    private ImageButton imgBtn_EventReport, imgBtn_QECode;//事件上报,二维码
    private ImageButton imgBtn_Geography_Positioin, imgBtn_Statistics;//地理位置,巡更统计
    private static final int REQUEST_QR_CODE = 1;
    //                  经度                      纬度
    private TextView tv_content_Longitude, tv_content_Latitude;
    //                  海拔                      精度
    private TextView tv_content_Altitude, tv_content_Accuracy;
    private TextView tv_content_Address;//具体地址
    private TextView tv_content_GPS;//卫星连接状态
    private TextView tv_content_StatelliteNum;//发现卫星数量
    private TextView tv_content_calibration;//已校准卫星数量
    private ImageButton rl_startOrstop_image;//开始按钮布局,做点击事件处理

    private NotificationManager mNManager;
    private Notification notify1;
    private static final int NOTIFYID_1 = 1;
    private Context mContext;
    public String imei;
    private TextView scan_text;
    private RadarView scan_radar;
    private TextView sendCount;

    @Override
    protected void setView() {
        setContentView(R.layout.activity_watchman);
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
            if (SharedUtil.getBoolean(this, "isWatchMan", false)) {
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
