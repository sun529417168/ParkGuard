package cn.com.watchman.activity;

import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.location.LocationManager;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.text.TextUtils;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.baidu.location.BDLocationListener;
import com.github.mzule.activityrouter.annotation.Router;
import com.linked.erfli.library.application.LibApplication;
import com.linked.erfli.library.base.BaseActivity;
import com.linked.erfli.library.base.MyTitle;
import com.linked.erfli.library.utils.SharedUtil;
import com.linked.erfli.library.utils.ToastUtil;

import cn.com.watchman.R;
import cn.com.watchman.application.WMApplication;
import cn.com.watchman.bean.GPSBean;
import cn.com.watchman.interfaces.GPSInfoInterface;

import com.linked.erfli.library.service.LocationService;

import cn.com.watchman.service.MsgReceiver;
import cn.com.watchman.utils.DialogUtils;
import cn.com.watchman.utils.Distance;
import cn.com.watchman.utils.MyLocationListener;
import cn.com.watchman.utils.MyRequest;
import cn.com.watchman.utils.WMyUtils;
import cn.com.watchman.weight.RadarView;


/**
 * 文件名：WatchManActivity
 * 描    述：巡更类
 * 作    者：stt
 * 时    间：2017.4.25
 * 版    本：V1.0.0
 */
@Router("watchman")
public class WatchMainActivity extends BaseActivity implements View.OnClickListener, GPSInfoInterface {

    /**
     * 经度,纬度,海拔,精度,地址
     */
    private TextView tv_longitude, tv_latitude, tv_altitude, tv_accuracy, tv_address;

    /**
     * 发现卫星数量,上传次数,定位描述
     */
    private TextView tv_findsatelliteNum, tv_sendCount, tv_describe;

    /**
     * 事件上报,地理位置,巡更统计,二维码巡更
     */
    private LinearLayout eventLayout, mapLayout, statisticsLayout, codeLayout;
    private ImageButton suspendBtn;
    boolean isStart = true;
    private LocationService locationService;
    private BDLocationListener mListener;
    private MsgReceiver msgReceiver;
    private RadarView scan_radar;
    private TextView scan_text;
    private GPSBean gpsBean = new GPSBean();
    private boolean isThread = true;
    private int count = 0;

    @Override
    protected void setView() {
        setContentView(R.layout.activity_watchman);
    }

    @Override
    protected void setDate(Bundle savedInstanceState) {
        MyTitle.getInstance().setTitle(this, "主页", PGApp, false);
        SharedUtil.setLong(this, "runTime", System.currentTimeMillis() / 1000);
        msgReceiver = new MsgReceiver();
        mListener = new MyLocationListener(this);
        locationService = ((LibApplication) getApplication()).locationService;
        //获取locationservice实例，建议应用中只初始化1个location实例，然后使用，可以参考其他示例的activity，都是通过此种方式获取locationservice实例的
        locationService.registerListener(mListener);
        //注册监听
        int type = getIntent().getIntExtra("from", 0);
        if (type == 0) {
            locationService.setLocationOption(locationService.getDefaultLocationClientOption());
        } else if (type == 1) {
            locationService.setLocationOption(locationService.getOption());
        }
    }

    @Override
    public void onNetChange(int netMobile) {
        super.onNetChange(netMobile);
        MyTitle.getInstance().setNetText(this, netMobile);
    }

    @Override
    protected void init() {
        tv_longitude = (TextView) findViewById(R.id.watchMan_Longitude);
        tv_latitude = (TextView) findViewById(R.id.watchMan_Latitude);
        tv_altitude = (TextView) findViewById(R.id.watchMan_altitude);
        tv_accuracy = (TextView) findViewById(R.id.watchMan_accuracy);
        tv_address = (TextView) findViewById(R.id.watchMan_address);
        tv_findsatelliteNum = (TextView) findViewById(R.id.watchMan_find_satelliteNum);
        tv_sendCount = (TextView) findViewById(R.id.watchMan_sendCount);
        tv_describe = (TextView) findViewById(R.id.tv_content_GPS);
        eventLayout = (LinearLayout) findViewById(R.id.watchMan_EventReport);
        mapLayout = (LinearLayout) findViewById(R.id.watchMan_map);
        statisticsLayout = (LinearLayout) findViewById(R.id.watchMan_statistics);
        codeLayout = (LinearLayout) findViewById(R.id.watchMan_code);
        suspendBtn = (ImageButton) findViewById(R.id.watchMan_center);
        eventLayout.setOnClickListener(this);
        mapLayout.setOnClickListener(this);
        statisticsLayout.setOnClickListener(this);
        codeLayout.setOnClickListener(this);
        suspendBtn.setOnClickListener(this);
        scan_radar = (RadarView) findViewById(R.id.watchMan_scan_radar);
        scan_text = (TextView) findViewById(R.id.watchMan_scan_text);

    }

    @Override
    public void onClick(View v) {
        int i = v.getId();
        if (i == R.id.watchMan_center) {
            if (!WMyUtils.isOpen(this)) {
                DialogUtils.showGPSDialog(this);
                return;
            }
            if (isStart) {
                MyRequest.typeRequest(this, 1);
                suspendBtn.setBackgroundResource(R.drawable.activity_main_stop);
                isStart = false;
                scan_radar.setSearching(true);//开始扫描
                locationService.start();
                IntentFilter intentFilter = new IntentFilter();
                intentFilter.addAction(MyLocationListener.GPSTYPE);
                registerReceiver(msgReceiver, intentFilter);
                new MyThread().start();
            } else {
                scan_radar.setVisibility(View.VISIBLE);
                scan_text.setVisibility(View.GONE);
                MyRequest.typeRequest(this, -1);
                suspendBtn.setBackgroundResource(R.drawable.activity_main_start);
                isStart = true;
                locationService.stop();
                scan_radar.setSearching(false);//停止扫描
                isThread = false;
            }

        } else if (i == R.id.watchMan_EventReport) {
            Intent in = new Intent(this, EventReportActivity.class);
            in.putExtra("longitude", String.valueOf(gpsBean.getLongitude()));
            in.putExtra("latitude", String.valueOf(gpsBean.getLatitude()));
            in.putExtra("accuracy", String.valueOf(gpsBean.getAccuracy()));
            in.putExtra("address", gpsBean.getAddress());
            startActivity(in);

        } else if (i == R.id.watchMan_map) {
            startActivity(new Intent(this, WatchManLocationActivity.class));
        } else if (i == R.id.watchMan_statistics) {
            Intent intent = new Intent(this, WatchManStatisticsActivity.class);
            intent.putExtra("count", count);
            startActivity(intent);
        } else if (i == R.id.watchMan_code) {
            startActivity(new Intent(this, WatchManQRcodeActivity.class));
        }
    }

    Handler mHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            if (msg.what == 0) {
                count++;
                tv_sendCount.setText(Integer.toString(count));
            }
            if (msg.what == 1) {
                Log.i("发送的距离", "小于10米");
            }
        }
    };

    public class MyThread extends Thread {
        public void run() {
            while (isThread) {
                if (gpsBean != null) {
                    Log.i("gpsInfo", gpsBean.toString());
                    if (Distance.isCompare(WatchMainActivity.this, gpsBean)) {
                        MyRequest.gpsRequest(WatchMainActivity.this, gpsBean);
                        Message msg = mHandler.obtainMessage();
                        msg.what = 0;
                        mHandler.sendMessage(msg);
                    } else {
                        Message msg = mHandler.obtainMessage();
                        msg.what = 1;
                        mHandler.sendMessage(msg);
                    }
                    SharedUtil.setString(WatchMainActivity.this, "longitude", String.valueOf(gpsBean.getLongitude()));
                    SharedUtil.setString(WatchMainActivity.this, "latitude", String.valueOf(gpsBean.getLatitude()));
                }
                try {
                    Thread.sleep(1000 * 60);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    @Override
    public void getGPSInfo(final GPSBean gpsBean) {
        this.gpsBean = gpsBean;
        Log.i("gpsRequest", gpsBean.toString());
        if ("网络定位成功".equals(gpsBean.getDescribe())) {
            scan_radar.setSearching(false);//停止扫描
            scan_radar.setVisibility(View.GONE);
            scan_text.setVisibility(View.VISIBLE);
            tv_findsatelliteNum.setVisibility(View.INVISIBLE);
            findViewById(R.id.tv_Title_StatelliteNum).setVisibility(View.INVISIBLE);
        } else if ("gps定位成功".equals(gpsBean.getDescribe())) {
            scan_radar.setSearching(false);//停止扫描
            scan_radar.setVisibility(View.GONE);
            scan_text.setVisibility(View.VISIBLE);
            tv_findsatelliteNum.setVisibility(View.VISIBLE);
            findViewById(R.id.tv_Title_StatelliteNum).setVisibility(View.VISIBLE);
            tv_findsatelliteNum.setText(String.valueOf(gpsBean.getSatellite()));
        } else {
            scan_radar.setVisibility(View.VISIBLE);
            scan_text.setVisibility(View.GONE);
            scan_radar.setSearching(true);//开始扫描
        }
        tv_longitude.setText(String.valueOf(gpsBean.getLongitude()));
        tv_latitude.setText(String.valueOf(gpsBean.getLatitude()));
        tv_altitude.setText(String.valueOf(gpsBean.getAltitude()) + "米");
        tv_accuracy.setText(String.valueOf(gpsBean.getAccuracy()));
        tv_address.setText(TextUtils.isEmpty(gpsBean.getAddress()) ? "" : gpsBean.getAddress());
        tv_describe.setText(gpsBean.getDescribe());
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

    @Override
    protected void onDestroy() {
        if (msgReceiver.isOrderedBroadcast())
            unregisterReceiver(msgReceiver);//注销广播
        locationService.unregisterListener(mListener); //注销掉监听
        locationService.stop(); //停止定位服务
        isThread = false;
        MyRequest.typeRequest(this, -1);
        SharedUtil.isValue(this, "longitude");
        SharedUtil.isValue(this, "latitude");
        super.onDestroy();
    }
}
