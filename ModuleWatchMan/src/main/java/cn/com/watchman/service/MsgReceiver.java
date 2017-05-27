package cn.com.watchman.service;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

import cn.com.watchman.bean.GPSBean;
import cn.com.watchman.interfaces.GPSInfoInterface;

/**
 * 文件名：MsgReceiver
 * 描    述：GPS的广播
 * 作    者：stt
 * 时    间：2017.5.11
 * 版    本：V1.0.0
 */

public class MsgReceiver extends BroadcastReceiver implements GPSInfoInterface {
    private GPSInfoInterface gpsInfoInterface;
    @Override
    public void onReceive(Context context, Intent intent) {
        GPSBean gpsBean = (GPSBean) intent.getSerializableExtra("gpsBean");
        gpsInfoInterface = (GPSInfoInterface) context;
        gpsInfoInterface.getGPSInfo(gpsBean);

    }

    @Override
    public void getGPSInfo(GPSBean gpsBean) {

    }
}
