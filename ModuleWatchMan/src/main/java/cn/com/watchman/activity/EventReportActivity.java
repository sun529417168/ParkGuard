package cn.com.watchman.activity;

import android.Manifest;
import android.app.Activity;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.GridView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.baidu.location.BDLocationListener;
import com.linked.erfli.library.application.LibApplication;
import com.linked.erfli.library.base.MyTitle;
import com.linked.erfli.library.function.takephoto.app.TakePhotoActivity;
import com.linked.erfli.library.function.takephoto.compress.CompressConfig;
import com.linked.erfli.library.function.takephoto.model.CropOptions;
import com.linked.erfli.library.service.LocationService;
import com.linked.erfli.library.utils.DeviceUuidFactory;
import com.linked.erfli.library.utils.SharedUtil;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import cn.com.watchman.R;
import cn.com.watchman.adapter.PhotoGridViewAdapter;
import cn.com.watchman.bean.GPSBean;
import cn.com.watchman.interfaces.EventReportDataInterface;
import cn.com.watchman.interfaces.GPSInfoInterface;
import cn.com.watchman.interfaces.GridViewDelPhotoInterface;
import cn.com.watchman.networkrequest.WatchManRequest;
import cn.com.watchman.service.MsgReceiver;
import cn.com.watchman.utils.MyLocationListener;

import static java.lang.System.currentTimeMillis;


/**
 * 文件名：EventReportActivity
 * 描    述：巡更问题上报
 * 作    者：stt
 * 时    间：2017.5.11
 * 版    本：V1.0.0
 */

public class EventReportActivity extends TakePhotoActivity implements OnItemClickListener, View.OnClickListener, GPSInfoInterface, GridViewDelPhotoInterface, EventReportDataInterface {
    private GridView picGridview;
    private boolean withOwnCrop;
    private TextView tv_content_Position, tv_wd, tv_con_wd, tv_con_altitude;//地址  纬度  经度 海拔
    private Button btn_EventReportButton;//提交按钮
    private EditText edt_describe_editext;//详情描述文本框
    //伪造的imei
    String imei = android.os.Build.SERIAL;
    private LocationService locationService;
    private BDLocationListener mListener;
    private MsgReceiver msgReceiver;
    private PhotoGridViewAdapter photoGridViewAdapter;
    private Activity mActivity;
    private Map<String, File> fileMap = new HashMap<>();//存放图片file集合
    public List<Bitmap> bmp = new ArrayList<Bitmap>();
    ArrayList<File> fileArray = new ArrayList<File>();
    private Spinner spinner;
    private int alarmtype = 3;//告警类型

    @Override
    protected void setView() {
        setContentView(R.layout.activity_eventreport);
    }

    @Override
    protected void setDate(Bundle savedInstanceState) {
        mActivity = this;
        MyTitle.getInstance().setTitle(this, "事件上报", PGApp, true);
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
        Intent intnet = getIntent();
    }

    @Override
    protected void init() {
        //提交按钮
        btn_EventReportButton = (Button) findViewById(R.id.btn_EventReportButton);
        btn_EventReportButton.setOnClickListener(this);
        //详情描述
        edt_describe_editext = (EditText) findViewById(R.id.edt_describe_editext);
        tv_content_Position = (TextView) findViewById(R.id.tv_content_Position);
        tv_wd = (TextView) findViewById(R.id.tv_wd);
        tv_con_wd = (TextView) findViewById(R.id.tv_con_wd);
        tv_con_altitude = (TextView) findViewById(R.id.tv_con_altitude);
        picGridview = (GridView) findViewById(R.id.picGridview);
        picGridview.setSelector(new ColorDrawable(Color.TRANSPARENT));
        tv_content_Position.setText(TextUtils.isEmpty(getIntent().getStringExtra("address")) ? "" : getIntent().getStringExtra("address"));
        tv_wd.setText(TextUtils.isEmpty(getIntent().getStringExtra("longitude")) ? "" : getIntent().getStringExtra("longitude"));
        tv_con_wd.setText(TextUtils.isEmpty(getIntent().getStringExtra("latitude")) ? "" : getIntent().getStringExtra("latitude"));
        tv_con_altitude.setText(TextUtils.isEmpty(getIntent().getStringExtra("accuracy")) ? "" : getIntent().getStringExtra("accuracy"));
        //下拉列表选项
        spinner = (Spinner) findViewById(R.id.spinner);
        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                String[] languages = getResources().getStringArray(R.array.eventType);
                alarmtype = alarmtype + position;
//                Toast.makeText(mActivity, "" + alarmtype, Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
        gridviewInit();
    }


    @Override
    public void onClick(View v) {
        int i1 = v.getId();
        //信息提交方法
        if (i1 == R.id.btn_EventReportButton) {//true表示为null false表示不为null
            boolean bool = edt_describe_editext.getText().toString().trim().isEmpty();
            //判断文本框是否为空
//            if (bool) {
//                Toast.makeText(this, "请输入详细的信息描述!", Toast.LENGTH_SHORT).show();
//                return;
//            }
            //判断是否选择上传的图片
            if (list.size() <= 0) {
                Toast.makeText(this, "请选择要上传的图片", Toast.LENGTH_SHORT).show();
                return;
            } else {
                for (int i = 0; i < list.size(); i++) {
                    fileArray.add(new File(list.get(i)));
                }
                //上传数据和图片
                sendEventReportData();
            }
        }
    }


    /**
     * 上传数据
     */
    private void sendEventReportData() {
        /**
         * 上传数据
         *
         * @param mActivity
         * @param dataType:手机预警       1
         * @param userId:用户id
         * @param alarmtext:告警描述信息
         * @param alarmtime:告警时间
         * @param alarmtype:告警类型  1：长时间未移动
        2：指定时间未巡更（手机可不提供）
        3：人工上报告警信息
        4：人工上报普通信息
        5: 巡更点位异常（如：二维码损坏）
         * @param deviceguid:巡更设备唯一编号
         * @param Longitude:经度
         */
        int userId = Integer.parseInt(SharedUtil.getString(mActivity, "PersonID"));
        String alarmtext = edt_describe_editext.getText().toString().trim();
        String deviceguid = new DeviceUuidFactory(mActivity).getDeviceUuid().toString();
        String Latitude = tv_con_wd.getText().toString();//纬度
        String Longitude = tv_wd.getText().toString();//经度
        if ("4.9E-324".equals(Longitude) || "".equals(Longitude)) {
            Longitude = "-1";
        }
        if ("4.9E-324".equals(Latitude) || "".equals(Latitude)) {
            Latitude = "-1";
        }
        String mark = "patrolphone";//驱动标识
        WatchManRequest.dataRequest(EventReportActivity.this, 10, 1, mark, userId, alarmtext, getTime(), alarmtype, deviceguid, Latitude, Longitude);
    }

    /**
     * 获取十位数时间戳
     *
     * @return 返回时间戳 pass
     */
    public String getTime() {
        long time = System.currentTimeMillis() / 1000;//获取系统时间的10位的时间戳
        String str = String.valueOf(time);
        return str;
    }

    @Override
    public void onItemClick(AdapterView<?> arg0, View arg1, int arg2, long arg3) {
        ((InputMethodManager) getSystemService(INPUT_METHOD_SERVICE))
                .hideSoftInputFromWindow(EventReportActivity.this
                                .getCurrentFocus().getWindowToken(),
                        InputMethodManager.HIDE_NOT_ALWAYS);
        if (arg2 == bmp.size()) {
            String sdcardState = Environment.getExternalStorageState();
            if (Environment.MEDIA_MOUNTED.equals(sdcardState)) {
                if (ContextCompat.checkSelfPermission(EventReportActivity.this, Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
                    //申请WRITE_EXTERNAL_STORAGE权限
                    ActivityCompat.requestPermissions(EventReportActivity.this, new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE}, 1);
                }
                File file = new File(Environment.getExternalStorageDirectory(), "/mytemp/" + currentTimeMillis() + ".png");
                if (!file.getParentFile().exists()) file.getParentFile().mkdirs();
                Uri imageUri = Uri.fromFile(file);
                CompressConfig compressConfig = new CompressConfig.Builder().setMaxSize(50 * 1024).setMaxPixel(800).create();
                CropOptions cropOptions = new CropOptions.Builder().setAspectX(1).setAspectY(1).setWithOwnCrop(withOwnCrop).create();
                getTakePhoto().onEnableCompress(compressConfig, true).onPickFromCapture(imageUri);

            } else {
                Toast.makeText(getApplicationContext(), "sdcard已拔出，不能选择照片",
                        Toast.LENGTH_SHORT).show();
            }
        } else {

        }
    }

    @Override
    public void takeCancel() {
        super.takeCancel();
    }

    @Override
    public void takeFail(String msg) {
        super.takeFail(msg);
    }

    Bitmap bim;
    ArrayList<String> list = new ArrayList<>();
    private File imageFile;

    /**
     * 拍照返回结果
     *
     * @param imagePath:保存图片sd卡路径
     */
    @Override
    public void takeSuccess(String imagePath) {
        super.takeSuccess(imagePath);
        Log.i("", "takeSuccess图片返回路径:" + imagePath);
        list.add(imagePath);
        bim = showImg(imagePath);
        bmp.add(bim);
        imageFile = new File(imagePath);
        fileMap.put(imagePath, imageFile);
        gridviewInit();
    }

    /**
     * 本地图片转换bitmap
     *
     * @param imagePath:图片路径
     * @return
     */
    private Bitmap showImg(String imagePath) {
        BitmapFactory.Options option = new BitmapFactory.Options();
        option.inSampleSize = 2;
        return BitmapFactory.decodeFile(imagePath, option);
    }


    @Override
    public void getGPSInfo(GPSBean gpsBean) {
        try {
            if (!gpsBean.getAddress().isEmpty()) {
                tv_content_Position.setText(gpsBean.getAddress());
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        tv_wd.setText(String.valueOf(gpsBean.getLongitude()));
        tv_con_wd.setText(String.valueOf(gpsBean.getLatitude()));
        tv_con_altitude.setText(String.valueOf(gpsBean.getAccuracy()));
    }

    @Override
    protected void onDestroy() {
        unregisterReceiver(msgReceiver);//注销广播
        locationService.unregisterListener(mListener); //注销掉监听
        locationService.stop(); //停止定位服务
        super.onDestroy();
    }

    @Override
    public void deleteItemPhoto(int position) {
        File fileS = new File(list.get(position));
        if (fileS.exists()) {
            fileS.delete();
        } else {
            Toast.makeText(EventReportActivity.this, "没有找到文件", Toast.LENGTH_SHORT).show();
        }
        fileMap.remove(list.get(position));//删除map里存放的file文件
        bmp.remove(position);//删除bmp集合里存放的bitmao
        list.remove(position);//删除list结合存放的图片路径
        photoGridViewAdapter.notifyDataSetInvalidated();
//        Toast.makeText(EventReportActivity.this, "" + list.size(), Toast.LENGTH_SHORT).show();
    }

    /**
     * 实例化GridView适配器
     */
    public void gridviewInit() {
        photoGridViewAdapter = new PhotoGridViewAdapter(EventReportActivity.this, bmp, this);
        photoGridViewAdapter.setSelectedPosition(0);
        picGridview.setAdapter(photoGridViewAdapter);
        picGridview.setOnItemClickListener(this);
    }

    /**
     * 数据上传成功后回调方法
     *
     * @param result
     */
    @Override
    public void getERDinterface(int result) {
//        int alarmid = 1;//告警关联id
        for (int i = 0; i < fileMap.size(); i++) {
            WatchManRequest.sendImageRequest(mActivity, 6, fileMap.get(list.get(i)), fileMap.get(list.get(i)).getName(), result, fileMap.size(), i + 1);
        }
    }
}
