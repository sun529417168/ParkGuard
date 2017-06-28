package cn.com.watchman.service;

import android.app.Service;
import android.content.Intent;
import android.os.Handler;
import android.os.IBinder;
import android.os.Message;
import android.util.Log;

import com.amap.api.location.AMapLocation;
import com.amap.api.location.AMapLocationClient;
import com.amap.api.location.AMapLocationClientOption;
import com.amap.api.location.AMapLocationListener;
import com.linked.erfli.library.utils.SharedUtil;
import com.linked.erfli.library.utils.ToastUtil;

import java.util.ArrayList;
import java.util.List;

import cn.com.watchman.bean.DinatesBean;
import cn.com.watchman.bean.DinatesDaoImpl;
import cn.com.watchman.bean.GPSBean;
import cn.com.watchman.utils.Distance;
import cn.com.watchman.utils.MyRequest;
import cn.com.watchman.utils.WMyUtils;

/**
 * 文件名：LocationService
 * 描    述：获取点位信息的服务
 * 作    者：stt
 * 时    间：2017.5.9
 * 版    本：V1.0.0
 */
public class GPSService extends Service {
    /**
     * 阿里的
     */
    private AMapLocationClient locationClientContinue = null;
    private long sleepTime = 15 * 1000;
    private boolean isThread = true;
    private GPSBean gpsBean;
    private DinatesDaoImpl dinatesDao;
    private Intent intent;
    private int currentCount = 0, totalCount;
    protected List<DinatesBean> dinatesList = new ArrayList<>();


    @Override
    public void onCreate() {
        super.onCreate();
        dinatesDao = new DinatesDaoImpl(this);
        startContinueLocation();
        intent = new Intent("cn.com.watchman.count");
//        new MyThread().start();
        //获取比今天早的点位数据，并都删掉
        dinatesList = dinatesDao.rawQuery("select * from t_gps where time < ?", new String[]{WMyUtils.getTimesmorning()});
        if (dinatesList.size() > 0) {
            for (DinatesBean bean : dinatesList) {
                dinatesDao.delete(bean.getId());
            }
        }
    }

    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    /**
     * 启动连续客户端定位
     */
    void startContinueLocation() {
        if (null == locationClientContinue) {
            locationClientContinue = new AMapLocationClient(this.getApplicationContext());
        }

        //使用连续的定位方式  默认连续
        AMapLocationClientOption locationClientOption = new AMapLocationClientOption();
        // 地址信息
        locationClientOption.setNeedAddress(true);
        // 每10秒定位一次
        locationClientOption.setInterval(10 * 1000);
        locationClientContinue.setLocationOption(locationClientOption);
        locationClientContinue.setLocationListener(locationContinueListener);
        locationClientContinue.startLocation();
    }

    /**
     * 连续客户端的定位监听
     */
    AMapLocationListener locationContinueListener = new AMapLocationListener() {
        @Override
        public void onLocationChanged(AMapLocation location) {
            gpsBean = new GPSBean(location.getLongitude(), location.getLatitude());
            if ((int) location.getLongitude() != 0 || (int) location.getLatitude() != 0) {
                if (location != null && Double.parseDouble(String.valueOf(location.getAccuracy())) > 0 && Double.parseDouble(String.valueOf(location.getAccuracy())) < 25) {
                    if (Distance.isCompareGD(GPSService.this, gpsBean)) {
                        MyRequest.gpsRequest(GPSService.this, gpsBean);
                        currentCount++;
                        totalCount = SharedUtil.getInteger(getApplicationContext(), "totalCount", 0) + 1;
                        SharedUtil.setInteger(getApplicationContext(), "totalCount", totalCount);
                        intent.putExtra("currentCount", currentCount);
                        intent.putExtra("totalCount", totalCount);
                        sendBroadcast(intent);
                        dinatesDao.insert(new DinatesBean(location.getLongitude(), location.getLatitude(), System.currentTimeMillis() / 1000));
                        SharedUtil.setString(GPSService.this, "longitude", String.valueOf(location.getLongitude()));
                        SharedUtil.setString(GPSService.this, "latitude", String.valueOf(location.getLatitude()));
                    }
                }
            }

            /**
             * 一下是各种判断目前先不考虑
             */
//            gpsBean = new GPSBean(location.getLongitude(), location.getLatitude(), location.getAddress(), location.getAccuracy(), location.getAltitude(), location.getProvider(), location.getSatellites(), location.getGpsAccuracyStatus() + "", location.getSpeed());
//            if (Double.parseDouble(String.valueOf(location.getSpeed())) > 0 && Double.parseDouble(String.valueOf(location.getSpeed())) <= 1) {//行走状态
//                sleepTime = 15 * 1000;
//            } else if (Double.parseDouble(String.valueOf(location.getSpeed())) > 1 && Double.parseDouble(String.valueOf(location.getSpeed())) <= 3) {
//                sleepTime = 10 * 1000;
//            } else if (Double.parseDouble(String.valueOf(location.getSpeed())) > 3 && Double.parseDouble(String.valueOf(location.getSpeed())) <= 10) {
//                sleepTime = 4 * 1000;
//            } else if (Double.parseDouble(String.valueOf(location.getSpeed())) > 10 && Double.parseDouble(String.valueOf(location.getSpeed())) <= 40) {
//                sleepTime = 4 * 1000;
//            } else if (Double.parseDouble(String.valueOf(location.getSpeed())) > 40) {
//                sleepTime = 3 * 1000;
//            } else {
//                sleepTime = 10 * 1000;
//            }
        }
    };


    Handler mHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            if (msg.what == 0) {
                /**
                 * 开始收集信息
                 */
                Log.i("aMapLocation", gpsBean.getLongitude() + "===" + gpsBean.getLatitude());
                MyRequest.gpsRequest(GPSService.this, gpsBean);
                currentCount++;
                totalCount = SharedUtil.getInteger(getApplicationContext(), "totalCount", 0) + 1;
                SharedUtil.setInteger(getApplicationContext(), "totalCount", totalCount);
                intent.putExtra("currentCount", currentCount);
                intent.putExtra("totalCount", totalCount);
                sendBroadcast(intent);
                dinatesDao.insert(new DinatesBean(gpsBean.getLongitude(), gpsBean.getLatitude(), System.currentTimeMillis() / 1000));
            }
            if (msg.what == 1) {
                Log.i("aMapLocation", "小于10米，没有进入");
            }
        }
    };

    public class MyThread extends Thread {
        public void run() {
            while (isThread) {
                MyRequest.typeRequest(GPSService.this, 1);
                if (gpsBean != null) {
                    Log.i("gpsInfo", gpsBean.toString());
                    if (gpsBean != null && Double.parseDouble(String.valueOf(gpsBean.getAccuracy())) < 20) {
                        if (Distance.isCompare(GPSService.this, gpsBean)) {
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
                        Thread.sleep(sleepTime);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }
        }
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        return super.onStartCommand(intent, flags, Service.START_NOT_STICKY);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        currentCount = 0;
        SharedUtil.setInteger(GPSService.this, "totalCount", totalCount);
        isThread = false;
        if (null != locationClientContinue) {
            locationClientContinue.stopLocation();
            locationClientContinue.onDestroy();
            locationClientContinue = null;
        }
    }
}
