package cn.com.watchman.activity;

import android.Manifest;
import android.annotation.SuppressLint;
import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.pm.PackageManager;
import android.location.LocationManager;
import android.os.Build;
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
import android.widget.Toast;

import com.amap.api.location.AMapLocation;
import com.amap.api.maps.AMapUtils;
import com.amap.api.maps.model.LatLng;
import com.amap.api.trace.LBSTraceClient;
import com.amap.api.trace.TraceListener;
import com.amap.api.trace.TraceOverlay;
import com.baidu.location.BDLocationListener;
import com.github.mzule.activityrouter.annotation.Router;
import com.linked.erfli.library.application.LibApplication;
import com.linked.erfli.library.base.BaseActivity;
import com.linked.erfli.library.base.MyTitle;
import com.linked.erfli.library.utils.DeviceUuidFactory;
import com.linked.erfli.library.utils.SharedUtil;
import com.linked.erfli.library.utils.ToastUtil;

import cn.com.watchman.R;
import cn.com.watchman.application.WMApplication;
import cn.com.watchman.bean.GPSBean;
import cn.com.watchman.bean.PathRecord;
import cn.com.watchman.database.DbAdapter;
import cn.com.watchman.interfaces.GPSInfoInterface;

import com.linked.erfli.library.service.LocationService;

import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import cn.com.watchman.service.GPSService;
import cn.com.watchman.service.MsgReceiver;
import cn.com.watchman.utils.DialogUtils;
import cn.com.watchman.utils.Distance;
import cn.com.watchman.utils.MyLocationListener;
import cn.com.watchman.utils.MyRequest;
import cn.com.watchman.utils.NotifyUtils;
import cn.com.watchman.utils.Util;
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
    boolean isStart;
    private LocationService locationService;
    private BDLocationListener mListener;
    private MsgReceiver msgReceiver;
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
    private TraceOverlay mTraceoverlay;
    private DbAdapter DbHepler;

    @Override
    protected void setView() {
        setContentView(R.layout.activity_watchman);
    }

    @Override
    protected void setDate(Bundle savedInstanceState) {
        MyTitle.getInstance().setTitle(this, "实时巡更", PGApp, false);
        isStart = SharedUtil.getBoolean(this, "serviceFlag", true);
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
        notifyUtils = new NotifyUtils(this);

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
        deviceID = (TextView) findViewById(R.id.watchMan_deviceId);
        deviceID.setText("设备号:" + new DeviceUuidFactory(this).getDeviceUuid().toString().substring(0, 4) + "*****" + new DeviceUuidFactory(this).getDeviceUuid().toString().substring(new DeviceUuidFactory(this).getDeviceUuid().toString().length() - 4));
        copyText = (TextView) findViewById(R.id.watchMan_copy);
        copyText.setOnClickListener(this);
        if (isStart) {
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
            IntentFilter intentFilter = new IntentFilter();
            intentFilter.addAction(MyLocationListener.GPSTYPE);
            registerReceiver(msgReceiver, intentFilter);
        }
    }

    @Override
    public void onClick(View v) {
        Intent intent;
        int i = v.getId();
        if (i == R.id.watchMan_center) {
            if (!WMyUtils.isOpen(this)) {
                DialogUtils.showGPSDialog(this);
                return;
            }
            if (isStart) {
                if (record != null) {
                    record = null;
                }
                record = new PathRecord();
                mStartTime = System.currentTimeMillis();
                record.setDate(getcueDate(mStartTime));

                notifyUtils.showButtonNotify();
                intent = new Intent(this, GPSService.class);
                startService(intent);
                MyRequest.typeRequest(this, 1);
                suspendBtn.setBackgroundResource(R.drawable.activity_main_stop);
                isStart = false;
                scan_radar.setSearching(true);//开始扫描
                locationService.start();
                IntentFilter intentFilter = new IntentFilter();
                intentFilter.addAction(MyLocationListener.GPSTYPE);
                registerReceiver(msgReceiver, intentFilter);
            } else {
                notifyUtils.clearAllNotify();
                intent = new Intent(this, GPSService.class);
                stopService(intent);
                scan_radar.setVisibility(View.VISIBLE);
                scan_text.setVisibility(View.GONE);
                MyRequest.typeRequest(this, -1);
                suspendBtn.setBackgroundResource(R.drawable.activity_main_start);
                isStart = true;
                locationService.stop();
                scan_radar.setSearching(false);//停止扫描
                //高德
                mEndTime = System.currentTimeMillis();
                DecimalFormat decimalFormat = new DecimalFormat("0.0");
                LBSTraceClient mTraceClient = new LBSTraceClient(getApplicationContext());
                mTraceClient.queryProcessedTrace(2, Util.parseTraceLocationList(record.getPathline()), LBSTraceClient.TYPE_AMAP, (TraceListener) WatchMainActivity.this);
                saveRecord(record.getPathline(), record.getDate());
            }

        } else if (i == R.id.watchMan_EventReport) {
            intent = new Intent(this, EventReportActivity.class);
            intent.putExtra("longitude", String.valueOf(gpsBean.getLongitude()));
            intent.putExtra("latitude", String.valueOf(gpsBean.getLatitude()));
            intent.putExtra("accuracy", String.valueOf(gpsBean.getAccuracy()));
            intent.putExtra("address", gpsBean.getAddress());
            startActivity(intent);

        } else if (i == R.id.watchMan_map) {
            startActivity(new Intent(this, WatchManStatisticsActivity.class));
        } else if (i == R.id.watchMan_statistics) {
            intent = new Intent(this, WatchManStatisticsActivity.class);
            intent.putExtra("count", count);
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
        }
    }

    @SuppressLint("SimpleDateFormat")
    private String getcueDate(long time) {
        SimpleDateFormat formatter = new SimpleDateFormat(
                "yyyy-MM-dd  HH:mm:ss ");
        Date curDate = new Date(time);
        String date = formatter.format(curDate);
        return date;
    }

    protected void saveRecord(List<AMapLocation> list, String time) {
        if (list != null && list.size() > 0) {
            DbHepler = new DbAdapter(this);
            DbHepler.open();
            String duration = getDuration();
            float distance = getDistance(list);
            String average = getAverage(distance);
            String pathlineSring = getPathLineString(list);
            AMapLocation firstLocaiton = list.get(0);
            AMapLocation lastLocaiton = list.get(list.size() - 1);
            String stratpoint = amapLocationToString(firstLocaiton);
            String endpoint = amapLocationToString(lastLocaiton);
            DbHepler.createrecord(String.valueOf(distance), duration, average,
                    pathlineSring, stratpoint, endpoint, time);
            DbHepler.close();
        } else {
            Toast.makeText(WatchMainActivity.this, "没有记录到路径", Toast.LENGTH_SHORT)
                    .show();
        }
    }

    private String getDuration() {
        return String.valueOf((mEndTime - mStartTime) / 1000f);
    }

    private String getAverage(float distance) {
        return String.valueOf(distance / (float) (mEndTime - mStartTime));
    }

    private float getDistance(List<AMapLocation> list) {
        float distance = 0;
        if (list == null || list.size() == 0) {
            return distance;
        }
        for (int i = 0; i < list.size() - 1; i++) {
            AMapLocation firstpoint = list.get(i);
            AMapLocation secondpoint = list.get(i + 1);
            LatLng firstLatLng = new LatLng(firstpoint.getLatitude(),
                    firstpoint.getLongitude());
            LatLng secondLatLng = new LatLng(secondpoint.getLatitude(),
                    secondpoint.getLongitude());
            double betweenDis = AMapUtils.calculateLineDistance(firstLatLng,
                    secondLatLng);
            distance = (float) (distance + betweenDis);
        }
        return distance;
    }

    private String getPathLineString(List<AMapLocation> list) {
        if (list == null || list.size() == 0) {
            return "";
        }
        StringBuffer pathline = new StringBuffer();
        for (int i = 0; i < list.size(); i++) {
            AMapLocation location = list.get(i);
            String locString = amapLocationToString(location);
            pathline.append(locString).append(";");
        }
        String pathLineString = pathline.toString();
        pathLineString = pathLineString.substring(0,
                pathLineString.length() - 1);
        return pathLineString;
    }

    private String amapLocationToString(AMapLocation location) {
        StringBuffer locString = new StringBuffer();
        locString.append(location.getLatitude()).append(",");
        locString.append(location.getLongitude()).append(",");
        locString.append(location.getProvider()).append(",");
        locString.append(location.getTime()).append(",");
        locString.append(location.getSpeed()).append(",");
        locString.append(location.getBearing());
        return locString.toString();
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
        super.onDestroy();
        if (msgReceiver.isOrderedBroadcast())
            unregisterReceiver(msgReceiver);//注销广播
        locationService.unregisterListener(mListener); //注销掉监听
        locationService.stop(); //停止定位服务
        MyRequest.typeRequest(this, -1);
        SharedUtil.isValue(this, "longitude");
        SharedUtil.isValue(this, "latitude");
        SharedUtil.setBoolean(this, "serviceFlag", true);
    }

    @Override
    protected void onStart() {
        super.onStart();
        // 适配android M，检查权限
        List<String> permissions = new ArrayList<>();
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M && isNeedRequestPermissions(permissions)) {
            requestPermissions(permissions.toArray(new String[permissions.size()]), 0);
        }
    }

    private boolean isNeedRequestPermissions(List<String> permissions) {
        // 定位精确位置
        addPermission(permissions, Manifest.permission.ACCESS_FINE_LOCATION);
        // 存储权限
        addPermission(permissions, Manifest.permission.WRITE_EXTERNAL_STORAGE);
        // 读取手机状态
        addPermission(permissions, Manifest.permission.READ_PHONE_STATE);
        return permissions.size() > 0;
    }

    private void addPermission(List<String> permissionsList, String permission) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M
                && checkSelfPermission(permission) != PackageManager.PERMISSION_GRANTED) {
            permissionsList.add(permission);
        }
    }
}
