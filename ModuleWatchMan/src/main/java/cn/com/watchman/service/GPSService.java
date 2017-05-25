package cn.com.watchman.service;

import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.location.GpsSatellite;
import android.location.GpsStatus;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.os.Handler;
import android.os.IBinder;
import android.os.Message;
import android.util.Log;

import com.baidu.location.BDLocation;
import com.baidu.location.BDLocationListener;
import com.baidu.location.Poi;
import com.linked.erfli.library.application.LibApplication;
import com.linked.erfli.library.service.LocationService;
import com.linked.erfli.library.utils.SharedUtil;
import com.linked.erfli.library.utils.ToastUtil;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import cn.com.watchman.activity.WatchMainActivity;
import cn.com.watchman.application.WMApplication;
import cn.com.watchman.bean.GPSBean;
import cn.com.watchman.interfaces.GPSInfoInterface;
import cn.com.watchman.utils.Distance;
import cn.com.watchman.utils.MyRequest;

/**
 * 文件名：LocationService
 * 描    述：获取点位信息的服务
 * 作    者：stt
 * 时    间：2017.5.9
 * 版    本：V1.0.0
 */
public class GPSService extends Service {
    private LocationService locationService;
    private int type;
    private String describe = "";
    private boolean isThread = true;
    private GPSBean gpsBean;
    private int count = 0;

    @Override
    public void onCreate() {
        super.onCreate();
        locationService = ((LibApplication) getApplication()).locationService;
        //获取locationservice实例，建议应用中只初始化1个location实例，然后使用，可以参考其他示例的activity，都是通过此种方式获取locationservice实例的
        locationService.registerListener(mListener);
        //注册监听
        if (type == 0) {
            locationService.setLocationOption(locationService.getDefaultLocationClientOption());
        } else if (type == 1) {
            locationService.setLocationOption(locationService.getOption());
        }
        locationService.start();// 定位SDK
        SharedUtil.setBoolean(this, "serviceFlag", false);
        new MyThread().start();
    }

    @Override
    public IBinder onBind(Intent intent) {
        type = intent.getIntExtra("from", 0);
        return null;
    }

    /*****
     *
     * 定位结果回调，重写onReceiveLocation方法，可以直接拷贝如下代码到自己工程中修改
     *
     */
    private BDLocationListener mListener = new BDLocationListener() {

        @Override
        public void onReceiveLocation(BDLocation location) {
            // TODO Auto-generated method stub
            if (null != location && location.getLocType() != BDLocation.TypeServerError) {
                if (location.getLocType() == BDLocation.TypeGpsLocation) {// GPS定位结果
                    describe = "gps定位成功";
                } else if (location.getLocType() == BDLocation.TypeNetWorkLocation) {// 网络定位结果
                    describe = "网络定位成功";
                }
                gpsBean = new GPSBean(location.getLongitude(), location.getLatitude(), location.getLocationDescribe(), location.getRadius(), location.getAltitude(), describe, location.getSatelliteNumber(), location.getGpsAccuracyStatus() + "", location.getSpeed());
                if (location.getSpeed() > 10) {
                    //实时上传
                    MyRequest.gpsRequest(GPSService.this, gpsBean);
                }
                Log.i("serviceGPS", gpsBean.toString());
            }
        }

        public void onConnectHotSpotMessage(String s, int i) {
        }
    };
    Handler mHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            if (msg.what == 0) {
                count++;
                Log.i("上传次数", count + "");
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
                    if (Distance.isCompare(GPSService.this, gpsBean)) {
                        MyRequest.gpsRequest(GPSService.this, gpsBean);
                        Message msg = mHandler.obtainMessage();
                        msg.what = 0;
                        mHandler.sendMessage(msg);
                    } else {
                        Message msg = mHandler.obtainMessage();
                        msg.what = 1;
                        mHandler.sendMessage(msg);
                    }
                    SharedUtil.setString(GPSService.this, "longitude", String.valueOf(gpsBean.getLongitude()));
                    SharedUtil.setString(GPSService.this, "latitude", String.valueOf(gpsBean.getLatitude()));
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
    public void onDestroy() {
        super.onDestroy();
        SharedUtil.setBoolean(this, "serviceFlag", true);
    }
}
