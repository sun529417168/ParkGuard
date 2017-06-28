package cn.com.watchman.activity;

import android.content.BroadcastReceiver;
import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.text.TextUtils;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.amap.api.location.AMapLocationClient;
import com.amap.api.location.AMapLocationClientOption;
import com.amap.api.maps.model.PolylineOptions;
import com.amap.api.trace.TraceLocation;
import com.amap.api.trace.TraceOverlay;
import com.baidu.location.BDLocationListener;
import com.github.mzule.activityrouter.annotation.Router;
import com.linked.erfli.library.application.LibApplication;
import com.linked.erfli.library.base.BaseActivity;
import com.linked.erfli.library.base.MyTitle;
import com.linked.erfli.library.myserviceutils.MyConstant;
import com.linked.erfli.library.myserviceutils.MyServiceUtils;
import com.linked.erfli.library.service.LocationService;
import com.linked.erfli.library.utils.DeviceUuidFactory;
import com.linked.erfli.library.utils.SharedUtil;
import com.linked.erfli.library.utils.ToastUtil;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import cn.com.watchman.R;
import cn.com.watchman.bean.GPSBean;
import cn.com.watchman.bean.PathRecord;
import cn.com.watchman.chatui.ChatMainActivity;
import cn.com.watchman.chatui.service.MyScoketService;
import cn.com.watchman.database.DbAdapter;
import cn.com.watchman.interfaces.GPSInfoInterface;
import cn.com.watchman.interfaces.UploadCountInterface;
import cn.com.watchman.service.GPSService;
import cn.com.watchman.service.MsgReceiver;
import cn.com.watchman.utils.DialogUtils;
import cn.com.watchman.utils.MyLocationListener;
import cn.com.watchman.utils.MyRequest;
import cn.com.watchman.utils.NotifyUtils;
import cn.com.watchman.utils.WMyUtils;
import cn.com.watchman.weight.RadarView;

import static cn.com.watchman.R.id.watchMan_address;


/**
 * 文件名：WatchManActivity
 * 描    述：巡更类
 * 作    者：stt
 * 时    间：2017.4.25
 * 版    本：V1.0.0
 */
@Router("watchman")
public class WatchMainActivity extends BaseActivity implements View.OnClickListener, GPSInfoInterface, UploadCountInterface {

    /**
     * 经度,纬度,海拔,精度,地址
     */
    private TextView tv_longitude, tv_latitude, tv_altitude, tv_accuracy, tv_address;

    /**
     * 发现卫星数量,上传次数,定位描述
     */
    private TextView tv_findsatelliteNum, tv_sendCount, tv_describe;
    private TextView personName;
    /**
     * 事件上报,地理位置,巡更统计,二维码巡更
     */
    private LinearLayout eventLayout, mapLayout, statisticsLayout, codeLayout;
    private ImageButton suspendBtn;
    boolean isStart;
    private LocationService locationService;
    private BDLocationListener mListener;
    private AMapLocationClient mLocationClient;
    private AMapLocationClientOption mLocationOption;
    private MsgReceiver msgReceiver;
    private CountReceiver countReceiver;
    private RadarView scan_radar;
    private TextView scan_text;
    private GPSBean gpsBean = new GPSBean();
    private int count = 0;
    private TextView deviceID, copyText;
    private NotifyUtils notifyUtils;
    private PathRecord record;
    private long mStartTime;
    private long mEndTime;
    private List<TraceOverlay> mOverlayList = new ArrayList<>();
    private List<TraceLocation> mTracelocationlist = new ArrayList<>();
    private DbAdapter DbHepler;
    private PolylineOptions mPolyoptions, tracePolytion;
    /*
    当前上传次数，总上传次数
     */
    private int currentCount = 0, totalCount = 0;
    private Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            if (msg.what == 1) {
                tv_sendCount.setText("" + msg.arg1);
                notifyUtils.showButtonNotify(msg.arg1);
            }
        }
    };
    private LinearLayout title_share;//分享按钮父布局
    private ImageView iv_Share_ImageView;//分享按钮

    @Override
    protected void setView() {
        setContentView(R.layout.activity_watchman);
    }

    @Override
    protected void setDate(Bundle savedInstanceState) {
        MyTitle.getInstance().setTitle(this, "巡更指挥系统", PGApp, false);
        isStart = MyServiceUtils.isServiceRunning(MyConstant.GPSSERVICE_CLASSNAME, this);
        SharedUtil.setLong(this, "runTime", System.currentTimeMillis() / 1000);
        msgReceiver = new MsgReceiver();
        countReceiver = new CountReceiver();
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
        notifyUtils = new NotifyUtils(this);
    }

    /**
     * 网络状态监听回调接口
     *
     * @param netMobile
     */
    @Override
    public void onNetChange(int netMobile) {
        super.onNetChange(netMobile);
        MyTitle.getInstance().setNetText(this, netMobile);
    }

    @Override
    protected void init() {
        //new add  分享按钮父布局 2017年5月25日10:53:21
        title_share = (LinearLayout) findViewById(R.id.title_share);
        iv_Share_ImageView = (ImageView) findViewById(R.id.iv_Share_ImageView);

        tv_longitude = (TextView) findViewById(R.id.watchMan_Longitude);
        tv_latitude = (TextView) findViewById(R.id.watchMan_Latitude);
        tv_altitude = (TextView) findViewById(R.id.watchMan_altitude);
        tv_accuracy = (TextView) findViewById(R.id.watchMan_accuracy);
        tv_address = (TextView) findViewById(watchMan_address);
        tv_findsatelliteNum = (TextView) findViewById(R.id.watchMan_find_satelliteNum);
        tv_sendCount = (TextView) findViewById(R.id.watchMan_sendCount);
        tv_describe = (TextView) findViewById(R.id.tv_content_GPS);
        personName = (TextView) findViewById(R.id.name);
        personName.setText(SharedUtil.getString(this, "personName"));
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
        title_share.setVisibility(View.VISIBLE);
        title_share.setOnClickListener(this);
        scan_radar = (RadarView) findViewById(R.id.watchMan_scan_radar);
        scan_text = (TextView) findViewById(R.id.watchMan_scan_text);
        deviceID = (TextView) findViewById(R.id.watchMan_deviceId);
        deviceID.setText("设备号:" + new DeviceUuidFactory(this).getDeviceUuid().toString().substring(0, 4) + "*****" + new DeviceUuidFactory(this).getDeviceUuid().toString().substring(new DeviceUuidFactory(this).getDeviceUuid().toString().length() - 4));
        copyText = (TextView) findViewById(R.id.watchMan_copy);
        copyText.setOnClickListener(this);
        if (!isStart) {
            scan_radar.setVisibility(View.VISIBLE);
            scan_text.setVisibility(View.GONE);
            MyRequest.typeRequest(this, -1);
            suspendBtn.setBackgroundResource(R.drawable.activity_main_start);
            locationService.stop();
            scan_radar.setSearching(false);//停止扫描
        } else {
            MyRequest.typeRequest(this, 1);
            suspendBtn.setBackgroundResource(R.drawable.activity_main_stop);
            scan_radar.setSearching(true);//开始扫描
            locationService.start();
            tv_sendCount.setText("" + SharedUtil.getInteger(this, "currentCount", 0));
            IntentFilter intentFilter = new IntentFilter();
            intentFilter.addAction(MyLocationListener.GPSTYPE);
            registerReceiver(msgReceiver, intentFilter);
            IntentFilter intentFilter1 = new IntentFilter();
            intentFilter.addAction("cn.com.watchman.count");
            registerReceiver(countReceiver, intentFilter1);
        }
        Calendar calendar = Calendar.getInstance();
        String currentTime = "" + calendar.get(Calendar.YEAR) + (calendar.get(Calendar.MONTH) + 1) + calendar.get(Calendar.DAY_OF_MONTH);
        String lastTime = SharedUtil.getString(this, "lastTime", "");
        if (!TextUtils.isEmpty(lastTime)) {
            if (Integer.parseInt(currentTime) > Integer.parseInt(lastTime)) {
                SharedUtil.setInteger(this, "totalCount", 0);
                SharedUtil.setInteger(LibApplication.getContent(), "WMSize", 0);
                SharedUtil.setString(LibApplication.getContent(), "traffic", "");
            }
        }
        if (String.valueOf(calendar.get(Calendar.DAY_OF_MONTH)).equals("1")) {
            SharedUtil.setInteger(LibApplication.getContent(), "monthWMSize", 0);
            SharedUtil.setString(LibApplication.getContent(), "monthTraffic", "");
        }
        SharedUtil.setString(this, "lastTime", currentTime);

    }

    @Override
    public void onClick(View v) {
        int i = v.getId();
        if (i == R.id.watchMan_center) {
            if (!WMyUtils.isOpen(this)) {
                DialogUtils.showGPSDialog(this);
                return;
            }
            if (!isStart) {
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN_MR1) {
                    notifyUtils.showButtonNotify(0);
                }
                watchActivityStartService();
                MyRequest.typeRequest(this, 1);
                suspendBtn.setBackgroundResource(R.drawable.activity_main_stop);
                isStart = true;
                scan_radar.setSearching(true);//开始扫描
                locationService.start();
                tv_sendCount.setText("0");
                IntentFilter intentFilter = new IntentFilter();
                intentFilter.addAction(MyLocationListener.GPSTYPE);
                registerReceiver(msgReceiver, intentFilter);
                IntentFilter intentFilter1 = new IntentFilter();
                intentFilter1.addAction("cn.com.watchman.count");
                registerReceiver(countReceiver, intentFilter1);
            } else {
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN_MR1) {
                    notifyUtils.clearAllNotify();
                }
                watchActivityStopService();
                isStart = false;
            }

        } else if (i == R.id.watchMan_EventReport) {
            if (!WMyUtils.isOpen(this)) {
                DialogUtils.showGPSDialog(this);
                return;
            }
            intent = new Intent(this, ChatMainActivity.class);
            startActivity(intent);
        } else if (i == R.id.watchMan_map) {
            if (!WMyUtils.isOpen(this)) {
                DialogUtils.showGPSDialog(this);
                return;
            }
            startActivity(new Intent(this, MarkerActivity.class));
        } else if (i == R.id.watchMan_statistics) {
            intent = new Intent(this, WatchManStatisticsActivity.class);
            intent.putExtra("currentCount", currentCount);
            startActivity(intent);
        } else if (i == R.id.watchMan_code) {
            intent = new Intent(this, WatchManQRcodeActivity.class);
            Bundle bundle = new Bundle();
            bundle.putSerializable("gpsBean", gpsBean);
            intent.putExtras(bundle);
            startActivity(intent);
        } else if (i == R.id.watchMan_copy) {
            ClipboardManager cmb = (ClipboardManager) getSystemService(Context.CLIPBOARD_SERVICE);
            cmb.setPrimaryClip(ClipData.newPlainText(null, "设备号:" + new DeviceUuidFactory(this).getDeviceUuid().toString())); //将内容放入粘贴管理器,在别的地方长按选择"粘贴"即可
            ToastUtil.show(this, "复制成功,去粘贴吧");
        } else if (i == R.id.title_share) {
            //分享方法
            myShareMethod();
        }
    }


    private void myShareMethod() {
        Intent shareIntent = new Intent();
        shareIntent.setAction(Intent.ACTION_SEND);
        String text = "http://api.map.baidu.com/marker?location=" + gpsBean.getLatitude() + "," + gpsBean.getLongitude() + "&output=html";
        shareIntent.putExtra(Intent.EXTRA_TEXT, text);
        shareIntent.setType("text/plain");
        //设置分享列表的标题，并且每次都显示分享列表
        startActivity(Intent.createChooser(shareIntent, "分享巡更信息到"));
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

    //获取当前上传次数
    @Override
    public void getCurrentCount(final int count) {

        currentCount = count;
        new Thread(new Runnable() {
            @Override
            public void run() {
                Message message = new Message();
                message.what = 1;
                message.arg1 = count;
                handler.sendMessage(message);
            }
        }).start();
    }

    //获取总上传次数
    @Override
    public void getTotalCount(int count) {
        totalCount = count;
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
        super.onDestroy();
        if (countReceiver.isOrderedBroadcast()) {
            unregisterReceiver(countReceiver);
        }
        if (msgReceiver.isOrderedBroadcast()) {
            unregisterReceiver(msgReceiver);//注销广播
        }
        locationService.unregisterListener(mListener); //注销掉监听
        locationService.stop(); //停止定位服务
        MyRequest.typeRequest(this, -1);
        SharedUtil.isValue(this, "longitude");
        SharedUtil.isValue(this, "latitude");
//        SharedUtil.setBoolean(this, "serviceFlag", true);
        SharedUtil.setInteger(this, "currentCount", currentCount);

    }

    public class CountReceiver extends BroadcastReceiver {

        @Override
        public void onReceive(Context context, Intent intent) {

            Log.i("test", "" + intent.getIntExtra("currentCount", 0));
            UploadCountInterface uploadCount = (UploadCountInterface) WatchMainActivity.this;
            uploadCount.getCurrentCount(intent.getIntExtra("currentCount", 0));
            uploadCount.getTotalCount(intent.getIntExtra("totalCount", 0));

        }
    }

    @Override
    protected void onPause() {
        super.onPause();
        if (countReceiver.isOrderedBroadcast()) {
            unregisterReceiver(countReceiver);
        }
        if (msgReceiver.isOrderedBroadcast()) {
            unregisterReceiver(msgReceiver);//注销广播
        }
    }

    Intent intent;

    private void watchActivityStartService() {
        startService(new Intent(this, GPSService.class));
        MyRequest.typeRequest(this, 1);
        suspendBtn.setBackgroundResource(R.drawable.activity_main_stop);
        isStart = false;
        scan_radar.setSearching(true);//开始扫描
        locationService.start();
        IntentFilter intentFilter = new IntentFilter();
        intentFilter.addAction(MyLocationListener.GPSTYPE);
        msgReceiver = new MsgReceiver();
        registerReceiver(msgReceiver, intentFilter);
    }

    private void watchActivityStopService() {
        stopService(new Intent(this, GPSService.class));
        scan_radar.setVisibility(View.VISIBLE);
        scan_text.setVisibility(View.GONE);
        MyRequest.typeRequest(this, -1);
        suspendBtn.setBackgroundResource(R.drawable.activity_main_start);
        isStart = true;
        locationService.stop();
        scan_radar.setSearching(false);//停止扫描
    }

    @Override
    protected void onStart() {
        super.onStart();
        if (!MyServiceUtils.isServiceRunning(MyConstant.SOCKETSERVICE_CLASSNAME, WatchMainActivity.this)) {
            Intent startIntent = new Intent(this, MyScoketService.class);
            startService(startIntent);
        }
    }
    
    
}
