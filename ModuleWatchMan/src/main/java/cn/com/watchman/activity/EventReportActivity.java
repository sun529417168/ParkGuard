package cn.com.watchman.activity;

import android.Manifest;
import android.content.Context;
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
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.GridView;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;


import com.baidu.location.BDLocationListener;
import com.linked.erfli.library.base.BaseActivity;
import com.linked.erfli.library.base.MyTitle;
import com.linked.erfli.library.function.takephoto.app.TakePhotoActivity;
import com.linked.erfli.library.function.takephoto.app.TakePhotoFragmentActivity;
import com.linked.erfli.library.function.takephoto.compress.CompressConfig;
import com.linked.erfli.library.function.takephoto.model.CropOptions;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import cn.com.watchman.R;
import cn.com.watchman.application.MyApplication;
import cn.com.watchman.bean.GPSBean;
import cn.com.watchman.interfaces.GPSInfoInterface;
import cn.com.watchman.service.LocationService;
import cn.com.watchman.service.MsgReceiver;
import cn.com.watchman.utils.MyLocationListener;


/**
 * 文件名：EventReportActivity
 * 描    述：巡更问题上报
 * 作    者：stt
 * 时    间：2017.5.11
 * 版    本：V1.0.0
 */

public class EventReportActivity extends TakePhotoActivity implements AdapterView.OnItemClickListener, View.OnClickListener, GPSInfoInterface {
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


    @Override
    protected void setView() {
        setContentView(R.layout.activity_eventreport);
    }

    @Override
    protected void setDate(Bundle savedInstanceState) {
        MyTitle.getInstance().setTitle(this, "事件上报", PGApp, true);
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
    }


    public List<Bitmap> bmp = new ArrayList<Bitmap>();


    ArrayList<File> fileArray = new ArrayList<File>();

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_EventReportButton:
                //true表示为null false表示不为null
                boolean bool = edt_describe_editext.getText().toString().trim().isEmpty();
                //判断文本框是否为空
                if (bool) {
                    Toast.makeText(this, "请输入详细的信息描述!", Toast.LENGTH_SHORT).show();
                    return;
                }
                //判断是否选择上传的图片
                if (list.size() <= 0) {
                    Toast.makeText(this, "请选择要上传的图片", Toast.LENGTH_SHORT).show();
                    return;
                } else {
                    for (int i = 0; i < list.size(); i++) {
                        fileArray.add(new File(list.get(i)));
                    }
                }
                break;
        }
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
                File file = new File(Environment.getExternalStorageDirectory(), "/mytemp/" + System.currentTimeMillis() + ".png");
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
    ArrayList<String> imgPathList = new ArrayList<>();

    @Override
    public void takeSuccess(String imagePath) {
        super.takeSuccess(imagePath);
        list.add(imagePath);
        bim = showImg(imagePath);
        bmp.add(bim);
    }

    private Bitmap showImg(String imagePath) {
        BitmapFactory.Options option = new BitmapFactory.Options();
        option.inSampleSize = 2;
        return BitmapFactory.decodeFile(imagePath, option);
    }


    @Override
    public void getGPSInfo(GPSBean gpsBean) {
        tv_content_Position.setText(gpsBean.getAddress());
        tv_wd.setText(String.valueOf(gpsBean.getLongitude()));
        tv_con_wd.setText(String.valueOf(gpsBean.getLatitude()));
        tv_con_altitude.setText(String.valueOf(gpsBean.getAltitude()));
    }

    @Override
    protected void onDestroy() {
        unregisterReceiver(msgReceiver);//注销广播
        locationService.unregisterListener(mListener); //注销掉监听
        locationService.stop(); //停止定位服务
        super.onDestroy();
    }
}
