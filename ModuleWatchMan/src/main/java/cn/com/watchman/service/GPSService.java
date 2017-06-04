package cn.com.watchman.service;

import android.app.Service;
import android.content.Intent;
import android.os.Handler;
import android.os.IBinder;
import android.os.Message;
import android.util.Log;

import com.baidu.location.BDLocation;
import com.baidu.location.BDLocationListener;
import com.linked.erfli.library.application.LibApplication;
import com.linked.erfli.library.service.LocationService;
import com.linked.erfli.library.utils.SharedUtil;

import cn.com.watchman.bean.GPSBean;
import cn.com.watchman.utils.Distance;
import cn.com.watchman.utils.MyRequest;

/**
 * 文件名：LocationService
 * 描    述：获取点位信息的服务
 * 作    者：stt
 * 时    间：2017.5.9
 * 版    本：V1.0.0
 */
public class GPSService extends RecordService{
    private LocationService locationService;
    private int type;
    private String describe = "";
    private boolean isThread = true;
    private GPSBean gpsBean;
    private int currentCount = 0,totalCount;
    private Intent intent;

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
//        SharedUtil.setBoolean(this, "serviceFlag", false);
        intent=new Intent("cn.com.watchman.count");
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
                    currentCount++;
                    totalCount=SharedUtil.getInteger(getApplicationContext(),"totalCount",0)+1;
                    SharedUtil.setInteger(getApplicationContext(),"totalCount",totalCount);
                    intent.putExtra("currentCount",currentCount);
                    intent.putExtra("totalCount",totalCount);
                    sendBroadcast(intent);
                }
            }
        }

        public void onConnectHotSpotMessage(String s, int i) {
        }
    };
    Handler mHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            if (msg.what == 0) {
                MyRequest.gpsRequest(GPSService.this, gpsBean);
                currentCount++;
                totalCount=SharedUtil.getInteger(getApplicationContext(),"totalCount",0)+1;
                SharedUtil.setInteger(getApplicationContext(),"totalCount",totalCount);
                Log.i("上传次数", currentCount + "");
                intent.putExtra("currentCount",currentCount);
                intent.putExtra("totalCount",totalCount);
                sendBroadcast(intent);
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
                    Thread.sleep(1000*30 );
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }
    }


    @Override
    public void onDestroy() {
        super.onDestroy();
        currentCount=0;
        SharedUtil.setInteger(this,"totalCount",totalCount);
        isThread=false;
//        SharedUtil.setBoolean(this, "serviceFlag", false);
    }
}
