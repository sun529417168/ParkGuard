package cn.com.watchman.activity;

import android.app.Activity;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.text.TextUtils;
import android.util.Log;

import com.baidu.location.BDLocationListener;
import com.linked.erfli.library.application.LibApplication;
import com.linked.erfli.library.function.scan.CaptureActivity;
import com.linked.erfli.library.service.LocationService;
import com.linked.erfli.library.utils.ToastUtil;

import cn.com.watchman.bean.GPSBean;
import cn.com.watchman.interfaces.GPSInfoInterface;
import cn.com.watchman.service.MsgReceiver;
import cn.com.watchman.utils.Distance;
import cn.com.watchman.utils.MyLocationListener;
import cn.com.watchman.utils.MyRequest;

/**
 * Created by 志强 on 2017.5.15.
 */

public class WatchManQRcodeActivity extends CaptureActivity implements GPSInfoInterface {
    protected Activity mActivity = this;
    private GPSBean gpsBean = new GPSBean();
    private LocationService locationService;
    private BDLocationListener mListener;
    private MsgReceiver msgReceiver;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        mActivity = this;
        super.onCreate(savedInstanceState);
        gpsBean = (GPSBean) getIntent().getSerializableExtra("gpsBean");
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
        locationService.start();
        msgReceiver = new MsgReceiver();
        IntentFilter intentFilter = new IntentFilter();
        intentFilter.addAction(MyLocationListener.GPSTYPE);
        registerReceiver(msgReceiver, intentFilter);
    }

    @Override
    protected void handleResult(final String resultString) {
        if (TextUtils.isEmpty(resultString)) {
            restartPreview();
        } else {
            /**
             * 指定的二维码
             */
//            if (Distance.isCompare(this, gpsBean, resultString)) {
//                MyRequest.codeRequest(this, gpsBean, resultString);
//            } else {
//                ToastUtil.show(this, "您没有在有效范围内");
//            }
//                        restartPreview();//不重启
            /**
             * 目前的版本，只是显示
             */
            Intent in = new Intent(this, QRcodeShowActivity.class);
            in.putExtra("value", resultString);
            startActivity(in);
            finish();

        }
    }

    @Override
    public void getGPSInfo(GPSBean gpsBean) {
        this.gpsBean = gpsBean;
    }

    @Override
    protected void onDestroy() {
        unregisterReceiver(msgReceiver);//注销广播
        locationService.unregisterListener(mListener); //注销掉监听
        locationService.stop(); //停止定位服务
        super.onDestroy();
    }
}

