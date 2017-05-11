package cn.com.watchman.activity;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.baidu.location.BDLocationListener;
import com.github.mzule.activityrouter.annotation.Router;
import com.linked.erfli.library.base.BaseActivity;
import com.linked.erfli.library.base.MyTitle;
import com.linked.erfli.library.utils.SharedUtil;
import com.linked.erfli.library.utils.ToastUtil;

import cn.com.watchman.R;
import cn.com.watchman.application.MyApplication;
import cn.com.watchman.bean.GPSBean;
import cn.com.watchman.interfaces.GPSInfoInterface;
import cn.com.watchman.service.LocationService;
import cn.com.watchman.service.MsgReceiver;
import cn.com.watchman.utils.MyLocationListener;
import cn.com.watchman.weight.RadarView;


/**
 * 文件名：WatchManActivity
 * 描    述：巡更类
 * 作    者：stt
 * 时    间：2017.4.25
 * 版    本：V1.0.0
 */
@Router("watchman")
public class WatchMainActivity extends BaseActivity implements View.OnClickListener,GPSInfoInterface {

    /**
     * 经度,纬度,海拔,精度,地址
     */
    private TextView tv_longitude, tv_latitude, tv_altitude, tv_accuracy, tv_address;

    /**
     * 发现卫星数量,上传次数,校准数,定位描述
     */
    private TextView tv_findsatelliteNum, tv_sendCount, tv_satelliteNum, tv_describe;

    /**
     * 事件上报,地理位置,巡更统计,二维码巡更
     */
    private LinearLayout eventLayout, mapLayout, statisticsLayout, codeLayout;
    private ImageButton suspendBtn;
    boolean isStart = true;
    private LocationService locationService;
    private BDLocationListener mListener ;
    private MsgReceiver msgReceiver;
    private RadarView scan_radar;
    private TextView scan_text;

    @Override
    protected void setView() {
        setContentView(R.layout.activity_watchman);
    }

    @Override
    protected void setDate(Bundle savedInstanceState) {
        MyTitle.getInstance().setTitle(this, "主页", PGApp, false);
        mListener = new MyLocationListener(this);
        locationService = ((MyApplication) getApplication()).locationService;
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
        tv_satelliteNum = (TextView) findViewById(R.id.watchMan_calibration_satelliteNum);
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
        switch (v.getId()) {
            case R.id.watchMan_center://开始暂停
                if (isStart) {
                    suspendBtn.setBackgroundResource(R.drawable.activity_main_stop);
                    isStart = false;
                    scan_radar.setSearching(true);//开始扫描
                    locationService.start();
                    msgReceiver = new MsgReceiver();
                    IntentFilter intentFilter = new IntentFilter();
                    intentFilter.addAction(MyLocationListener.GPSTYPE);
                    registerReceiver(msgReceiver, intentFilter);
                } else {
                    suspendBtn.setBackgroundResource(R.drawable.activity_main_start);
                    isStart = true;
                    locationService.stop();
                    scan_radar.setSearching(false);//开始扫描
                }
                break;
            case R.id.watchMan_EventReport://事件上报
                startActivity(new Intent(this, EventReportActivity.class));
                break;
            case R.id.watchMan_map://地理位置

                break;
            case R.id.watchMan_statistics://巡更统计

                break;
            case R.id.watchMan_code://二维码巡更

                break;
        }
    }

    @Override
    public void getGPSInfo(GPSBean gpsBean) {
        if ("网络定位成功".equals(gpsBean.getDescribe())) {
            scan_radar.setSearching(false);//停止扫描
            scan_radar.setVisibility(View.GONE);
            scan_text.setVisibility(View.VISIBLE);
            tv_satelliteNum.setVisibility(View.INVISIBLE);
            tv_findsatelliteNum.setVisibility(View.INVISIBLE);
            findViewById(R.id.tv_Title_StatelliteNum).setVisibility(View.INVISIBLE);
            findViewById(R.id.tv_Title_calibration).setVisibility(View.INVISIBLE);
        } else if ("gps定位成功".equals(gpsBean.getDescribe())) {
            scan_radar.setSearching(false);//停止扫描
            scan_radar.setVisibility(View.GONE);
            scan_text.setVisibility(View.VISIBLE);
            tv_satelliteNum.setVisibility(View.INVISIBLE);
            tv_findsatelliteNum.setVisibility(View.INVISIBLE);
            findViewById(R.id.tv_Title_StatelliteNum).setVisibility(View.VISIBLE);
            findViewById(R.id.tv_Title_calibration).setVisibility(View.VISIBLE);
        } else {
            scan_radar.setVisibility(View.VISIBLE);
            scan_text.setVisibility(View.GONE);
            scan_radar.setSearching(true);//开始扫描
        }
        tv_longitude.setText(String.valueOf(gpsBean.getLongitude()));
        tv_latitude.setText(String.valueOf(gpsBean.getLatitude()));
        tv_altitude.setText(String.valueOf(gpsBean.getAltitude()));
        tv_accuracy.setText(String.valueOf(gpsBean.getAccuracy()));
        tv_address.setText(String.valueOf(gpsBean.getAddress()));
        tv_findsatelliteNum.setText(String.valueOf(gpsBean.getFindSatellite()));
        tv_satelliteNum.setText(String.valueOf(gpsBean.getSatellite()));
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
        unregisterReceiver(msgReceiver);//注销广播
        locationService.unregisterListener(mListener); //注销掉监听
        locationService.stop(); //停止定位服务
        super.onDestroy();
    }
}
