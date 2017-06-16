package cn.com.watchman.chatui;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.linked.erfli.library.ShowZoomPhotoActivity;
import com.linked.erfli.library.adapter.TaskDetalPhotoAdapter;
import com.linked.erfli.library.function.takephoto.app.TakePhotoActivity;
import com.linked.erfli.library.function.takephoto.compress.CompressConfig;
import com.linked.erfli.library.interfaces.GetGPSInterface;
import com.linked.erfli.library.utils.DateTimePickDialogUtil;
import com.linked.erfli.library.utils.DeviceUuidFactory;
import com.linked.erfli.library.utils.DialogUtils;
import com.linked.erfli.library.utils.MyUtils;
import com.linked.erfli.library.utils.SharedUtil;
import com.linked.erfli.library.utils.StatusBarUtils;
import com.linked.erfli.library.utils.ToastUtil;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import cn.com.watchman.R;
import cn.com.watchman.chatui.enity.ChatProblemTypeLeftEntity;
import cn.com.watchman.chatui.interfaces.ChatProblemTypeLeftInterface;
import cn.com.watchman.chatui.interfaces.ChatSendPicTureInterface;
import cn.com.watchman.chatui.widget.ChatProblemTypePopwindow;
import cn.com.watchman.networkrequest.WatchManRequest;

/**
 * 描    述：巡更告警
 * 作    者：zzq
 * 时    间：2017年6月14日
 * 版    本：V1.0.0
 */
public class ChatWarningActivity extends TakePhotoActivity implements View.OnClickListener, ChatProblemTypeLeftInterface, AdapterView.OnItemClickListener, GetGPSInterface, ChatSendPicTureInterface {
    private ArrayList<Bitmap> list = new ArrayList<Bitmap>();
    //问题类型
    private RelativeLayout chat_add_problem;//父布局
    private TextView chat_add_problem_type;//问题类型显示文本
    //所在区域
    private RelativeLayout chat_add_problem_positionLayout;
    private TextView chat_add_problem_address;//所在区域显示文本
    //上报人
    private TextView chat_add_problem_sender;//上报人显示文本
    //发现时间
    private RelativeLayout chat_add_problem_findTimeLayout;//
    private TextView chat_add_problem_findTime;//发现时间显示文本
    //图片
    private ImageView chat_add_problem_detail_takePhoto;//拍照按钮
    //问题描述
    private EditText chat_add_problem_inputInfo;
    //上报
    private Button chat_add_problem_detail_button;
    //标题
    private TextView title_name;
    private LinearLayout title_back;
    //girdview 图片展示
    private GridView chat_add_problem_detail_gridView;

    //==================
    private String problemType = "";
    private String address;
    private ArrayList<String> listPath = new ArrayList<String>();
    private File mCameraFile;
    private Map<String, File> fileMap = new HashMap<>();
    private List<File> listFile = new ArrayList<>();//file集合
    private List<String> fileName = new ArrayList<>();
    private TaskDetalPhotoAdapter taskDetalPhotoAdapter;
    private String getLongitude = "-1";//经度
    private String getLatitude = "-1";//纬度
//    private String findDate;//时间
    private String problemDes;//描述

    @Override
    protected void setView() {
        super.setView();
        setContentView(R.layout.activity_chat_warning);
        StatusBarUtils.ff(ChatWarningActivity.this);
    }

    @Override
    protected void setDate(Bundle savedInstanceState) {
        super.setDate(savedInstanceState);
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putParcelableArrayList("listBitmap", list);
    }

    @Override
    protected void init() {
        super.init();
        //标题
        title_name = (TextView) findViewById(R.id.title_name);
        title_back = (LinearLayout) findViewById(R.id.title_back);
        title_back.setVisibility(View.VISIBLE);
        title_name.setText("巡更巡检事件上报");
        title_back.setOnClickListener(this);
        //问题类型
        chat_add_problem = (RelativeLayout) findViewById(R.id.chat_add_problem);
        chat_add_problem_type = (TextView) findViewById(R.id.chat_add_problem_type);
        chat_add_problem.setOnClickListener(this);
        //所在区域
        chat_add_problem_positionLayout = (RelativeLayout) findViewById(R.id.chat_add_problem_positionLayout);
        chat_add_problem_positionLayout.setOnClickListener(this);
        //所在区域显示文本
        chat_add_problem_address = (TextView) findViewById(R.id.chat_add_problem_address);
        //上报人显示文本
        chat_add_problem_sender = (TextView) findViewById(R.id.chat_add_problem_sender);
        chat_add_problem_sender.setText(SharedUtil.getString(this, "personName"));//显示
        //发现时间
        chat_add_problem_findTimeLayout = (RelativeLayout) findViewById(R.id.chat_add_problem_findTimeLayout);
        chat_add_problem_findTime = (TextView) findViewById(R.id.chat_add_problem_findTime);
        chat_add_problem_findTimeLayout.setOnClickListener(this);
        //图片
        chat_add_problem_detail_takePhoto = (ImageView) findViewById(R.id.chat_add_problem_detail_takePhoto);
        chat_add_problem_detail_takePhoto.setOnClickListener(this);
        //问题描述
        chat_add_problem_inputInfo = (EditText) findViewById(R.id.chat_add_problem_inputInfo);
        //上报事件按钮
        chat_add_problem_detail_button = (Button) findViewById(R.id.chat_add_problem_detail_button);
        chat_add_problem_detail_button.setOnClickListener(this);
        //图片展示
        chat_add_problem_detail_gridView = (GridView) findViewById(R.id.chat_add_problem_detail_gridView);
        taskDetalPhotoAdapter = new TaskDetalPhotoAdapter(this, list);
        chat_add_problem_detail_gridView.setAdapter(taskDetalPhotoAdapter);
        taskDetalPhotoAdapter.setList(list);
    }

    @Override
    public void onClick(View v) {
        int i = v.getId();
        if (i == R.id.title_back) {
            PGApp.finishTop();
        } else if (i == R.id.chat_add_problem_detail_takePhoto) {
            File file = new File(Environment.getExternalStorageDirectory(), "/XiBeiProblem/" + System.currentTimeMillis() + ".jpg");
            if (!file.getParentFile().exists()) file.getParentFile().mkdirs();
            Uri imageUri = Uri.fromFile(file);
            CompressConfig compressConfig = new CompressConfig.Builder().setMaxSize(50 * 1024).setMaxPixel(700).create();//压缩方法实例化就是压缩图片，根据配置参数压缩
            getTakePhoto().onEnableCompress(compressConfig, true).onPickFromCapture(imageUri);//从相机拍取照片不裁剪
        } else if (i == R.id.chat_add_problem_detail_button) {
            if (!MyUtils.gpsIsOPen(this)) {
                DialogUtils.initGPS(this);
                return;
            }
            MyUtils.getLoc(ChatWarningActivity.this);
//            findDate = chat_add_problem_findTime.getText().toString().trim();
            problemDes = chat_add_problem_inputInfo.getText().toString().trim();
            String deviceUuid = new DeviceUuidFactory(ChatWarningActivity.this).getDeviceUuid().toString();
            if (isEmpty()) {
                WatchManRequest.newAddChatProblemPricture(this, listFile, fileName, problemDes, -1, 11, 10, getLongitude, getLatitude, deviceUuid, getTime());
            }
//            Intent intent = new Intent();
//            intent.putExtra("warningId", "1");
//            intent.putExtra("warningName", "事件上报");
//            intent.putExtra("warningTime", "2017年6月15日16:23:55");
//            intent.putExtra("warningAddress", "克利夫兰");
//            String imgUrl = "http://images11.app.happyjuzi.com/content/201705/03/d5cd4691-6350-482c-8d40-7acf226e10e2.jpeg";
//            intent.putExtra("warningImgUrl", imgUrl);
//            setResult(RESULT_OK, intent);
//            finish();
        } else if (i == R.id.chat_add_problem) {
            WatchManRequest.getChatProblemTypeLeft(this, ChatWarningActivity.this);
        } else if (i == R.id.chat_add_problem_positionLayout) {
            Intent intent = new Intent(ChatWarningActivity.this, ChatInputAddressActivity.class);
            address = chat_add_problem_address.getText().toString().trim();
            if (!TextUtils.isEmpty(address)) {
                intent.putExtra("value", address);
            }
            startActivityForResult(intent, 1);
        } else if (i == R.id.chat_add_problem_findTimeLayout) {
            DateTimePickDialogUtil dateTimePicKDialog = new DateTimePickDialogUtil(ChatWarningActivity.this, "");
            dateTimePicKDialog.dateTimePicKDialog(chat_add_problem_findTime);
        }
    }

    public String getTime() {
        long time = System.currentTimeMillis() / 1000;//获取系统时间的10位的时间戳
        return String.valueOf(time);

    }

    /**
     * 图片发送回调
     *
     * @param o
     */
    @Override
    public void getChatSenPictureResponce(Object o) {

    }

    private boolean isEmpty() {
       /* if (TextUtils.isEmpty(problemTitle)) {
            ToastUtil.show(this, "请输入问题名称");
            return false;
        } else */
        if (TextUtils.isEmpty(problemType)) {
            ToastUtil.show(this, "请选择问题类型");
            return false;
        } else if (TextUtils.isEmpty(address)) {
            ToastUtil.show(this, "请输入所在区域");
            return false;
        } else if (TextUtils.isEmpty(problemDes)) {
            ToastUtil.show(this, "请输入问题描述");
            return false;
        } else {
            return true;
        }
    }

    /**
     * 点击选择问题类型回调方法
     *
     * @param chatProblemTypeLeftEntities
     */
    @Override
    public void getChatTypeLeft(List<ChatProblemTypeLeftEntity> chatProblemTypeLeftEntities) {
        ChatProblemTypePopwindow chatProblemTypePopwindow = new ChatProblemTypePopwindow(ChatWarningActivity.this, (ArrayList<ChatProblemTypeLeftEntity>) chatProblemTypeLeftEntities);
        chatProblemTypePopwindow.showAsDropDown(chat_add_problem, 0, 5);
        chatProblemTypePopwindow.setAddresskListener(new ChatProblemTypePopwindow.OnAddressCListener() {
            @Override
            public void onClick(String left, String right, String code) {
                chat_add_problem_type.setText(right);
                problemType = code;
            }
        });
    }

    @Override
    public void takeSuccess(String imagePath) {
        super.takeSuccess(imagePath);
        Log.i("imagePaths", imagePath);
        listPath.add(imagePath);
        mCameraFile = new File(imagePath);
        fileMap.put(imagePath, mCameraFile);
        fileName.add(mCameraFile.getName());
        listFile.add(mCameraFile);
        showImg(imagePath);
    }

    private void showImg(String imagePath) {
        BitmapFactory.Options option = new BitmapFactory.Options();
        option.inSampleSize = 2;
        Bitmap bitmap = BitmapFactory.decodeFile(imagePath, option);
        list.add(bitmap);
        taskDetalPhotoAdapter.setList(list);
        if (list.size() == 5) {
            chat_add_problem_detail_takePhoto.setVisibility(View.INVISIBLE);
        }
    }

    @Override
    public void getGPS(String longitude, String latitude) {
        getLongitude = longitude;
        getLatitude = latitude;
        String gps = longitude + "," + latitude;
        Log.i("gps", gps);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        Log.i("聊天页面onActivityResult方2", "requestCode:" + requestCode + ",resultCode:" + resultCode);
        switch (requestCode) {
            case 1:
                if (resultCode == RESULT_OK) {
                    try {
                        address = data.getStringExtra("return_value");
                        chat_add_problem_address.setText(address);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
//                if (resultCode == RESULT_CANCELED) {
//                    try {
//                        address = data.getStringExtra("return_value");
//                        chat_add_problem_address.setText(address);
//                    } catch (Exception e) {
//                        e.printStackTrace();
//                    }
//                }
                break;

            default:
                break;
        }
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        Intent in = new Intent(this, ShowZoomPhotoActivity.class);
        in.putStringArrayListExtra("listPath", listPath);
        in.putExtra("current", position);
        startActivity(in);
    }


}
