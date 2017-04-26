package com.linked.erfli.library.service;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;

import com.linked.erfli.library.base.BaseActivity;
import com.linked.erfli.library.utils.NetWorkUtils;

/**
 * 文件名：NetBroadcastReceiver
 * 描    述：广播监听网络变化
 * 作    者：stt
 * 时    间：2016.12.30
 * 版    本：V1.0.0
 */
public class NetBroadcastReceiver extends BroadcastReceiver {
    public NetEvevt evevt = BaseActivity.evevt;

    @Override
    public void onReceive(Context context, Intent intent) {
        if (intent.getAction().equals(ConnectivityManager.CONNECTIVITY_ACTION)) {
            int netWorkState = NetWorkUtils.getNetWorkState(context);
            // 接口回调传过去状态的类型
            evevt.onNetChange(netWorkState);
        }
    }


    // 自定义接口
    public interface NetEvevt {
        void onNetChange(int netMobile);
    }
}
