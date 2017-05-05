package cn.com.task;

import android.content.ActivityNotFoundException;
import android.content.Context;
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
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.RelativeLayout;
import android.widget.ScrollView;
import android.widget.TextView;

import com.linked.erfli.library.ShowZoomPhotoActivity;
import com.linked.erfli.library.utils.MyUtils;
import com.linked.erfli.library.utils.SharedUtil;
import com.linked.erfli.library.utils.StatusBarUtils;
import com.linked.erfli.library.utils.ToastUtil;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import cn.com.task.adapter.TaskDetalDescribePhotoAdapter;
import cn.com.task.adapter.TaskDetalFeedbackPhotoAdapter;
import cn.com.task.adapter.TaskDetalPhotoAdapter;
import cn.com.task.bean.TaskDetailBean;
import cn.com.task.interfaces.SearchTypePopInterface;
import cn.com.task.interfaces.TaskAssignedInterface;
import cn.com.task.networkrequest.TaskReuest;
import cn.com.task.photobase.CompressConfig;
import cn.com.task.photobase.TakePhotoActivity;
import cn.com.task.utils.CalendarUtils;
import cn.com.task.utils.DialogUtils;
import cn.com.task.utils.DownloadUtil;

/**
 * 文件名：TaskDetailActivity
 * 描    述：任务详情类
 * 作    者：zzq
 * 时    间：2017年4月27日15:35:00
 * 版    本：V1.0.0
 */
public class TaskDetailActivity extends TakePhotoActivity implements View.OnClickListener,
        AdapterView.OnItemClickListener, SearchTypePopInterface, TaskAssignedInterface {

    private Context context;
    private TextView titleName;
    private LinearLayout back;
    private TaskDetailBean taskBean = new TaskDetailBean();
    /**
     * 编号，名称，类型，状态，所在位置，创建人，任务优先级，下发时间，反馈时间
     */
    private TextView numberText, nameText, typeText, stateTaskText, stateReplyText, addressText, founderText, priorityText, sendTimeText, feedbackTimeText;
    private RelativeLayout nameLayout;
    private TextView fileName, filePath;
    private EditText infoEdit;
    private String info;
    private ImageView takePhoto;
    private ArrayList<String> listPath = new ArrayList<String>();
    private ArrayList<Bitmap> list = new ArrayList<Bitmap>();
    private GridView gridView, gridViewDescribe, gridViewFeedback;
    private TaskDetalPhotoAdapter taskDetalPhotoAdapter;
    private TaskDetalDescribePhotoAdapter taskDetalDescribePhotoAdapter;
    private TaskDetalFeedbackPhotoAdapter taskDetalFeedbackPhotoAdapter;
    private Button submitBtn;
    private ArrayList<String> describeList = new ArrayList<>();
    private ArrayList<String> describeListFanKui = new ArrayList<>();
    private PopupWindow showReplyPop;
    private int feedbackState = -1;
    private List<File> listFile = new ArrayList<>();
    private Map<String, File> fileMap = new HashMap<>();
    private File mCameraFile;
    private String path;
    private TextView taskInfo;
    private CheckBox over, noOver;
    private String taskId = "";
    private ImageView reverse;

    //new add 2017年4月28日15:35:41 zzq
    private FrameLayout task_detail_button_layout;
    private ScrollView task_detail_scrollow;

    @Override
    protected void setView() {
        setContentView(R.layout.activity_task_detail);
        context = this;
        StatusBarUtils.ff(context, R.color.blue);
    }

    @Override
    protected void setDate(Bundle savedInstanceState) {

        taskId = getIntent().getStringExtra("taskId");
        TaskReuest.taskDetailTaskAssignedRequest(this, taskId);
        if (savedInstanceState != null) {
            list = savedInstanceState.getParcelableArrayList("listBitmap");
        }

    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putParcelableArrayList("listBitmap", list);
    }

    @Override
    protected void init() {
        //new add zzq 2017年4月28日15:36:51
        task_detail_button_layout = (FrameLayout) findViewById(R.id.task_detail_button_layout);
        task_detail_scrollow = (ScrollView) findViewById(R.id.task_detail_scrollow);


        titleName = (TextView) findViewById(R.id.title_name);
        titleName.setText("任务详情");
        back = (LinearLayout) findViewById(R.id.title_back);
        back.setVisibility(View.VISIBLE);
        back.setOnClickListener(this);
        takePhoto = (ImageView) findViewById(R.id.task_detail_takePhoto);
        takePhoto.setOnClickListener(this);
        numberText = (TextView) findViewById(R.id.task_detail_number);//编号
        nameText = (TextView) findViewById(R.id.task_detail_name);//名称
        nameLayout = (RelativeLayout) findViewById(R.id.task_detail_nameLayout);
        nameLayout.setOnClickListener(this);
        typeText = (TextView) findViewById(R.id.task_detail_type);//类型
        stateTaskText = (TextView) findViewById(R.id.task_detail_state);//状态
        addressText = (TextView) findViewById(R.id.task_detail_address);//所在位置
        founderText = (TextView) findViewById(R.id.task_detail_founder);//创建人
        priorityText = (TextView) findViewById(R.id.task_detail_priority);//任务优先级
        sendTimeText = (TextView) findViewById(R.id.task_detail_sendTime);//下发时间
        fileName = (TextView) findViewById(R.id.task_detail_state_fileName);//文件名称
        filePath = (TextView) findViewById(R.id.task_detail_state_file);//文件路径
        filePath.setOnClickListener(this);
        taskInfo = (TextView) findViewById(R.id.task_detail_describe);
        stateReplyText = (TextView) findViewById(R.id.task_detail_state_reply);//反馈状态
        feedbackTimeText = (TextView) findViewById(R.id.task_detail_feedbackTime);//反馈时间
        submitBtn = (Button) findViewById(R.id.task_detail_button);//提交按钮
        submitBtn.setOnClickListener(this);
        reverse = (ImageView) findViewById(R.id.task_detail_button_reverse);
        infoEdit = (EditText) findViewById(R.id.task_detail_info);//任务描述
        gridView = (GridView) findViewById(R.id.task_detail_gridView);
        gridView.setOnItemClickListener(this);
        taskDetalPhotoAdapter = new TaskDetalPhotoAdapter(this, list);
        gridView.setAdapter(taskDetalPhotoAdapter);
        taskDetalPhotoAdapter.setList(list);
        gridViewDescribe = (GridView) findViewById(R.id.task_detail_gridView_describe);//描述查看的图片

        over = (CheckBox) findViewById(R.id.task_detail_state_over);
        noOver = (CheckBox) findViewById(R.id.task_detail_state_noOver);
        over.setOnCheckedChangeListener(checkedChangeListener);
        noOver.setOnCheckedChangeListener(checkedChangeListener);
    }

    private CompoundButton.OnCheckedChangeListener checkedChangeListener = new CompoundButton.OnCheckedChangeListener() {
        @Override
        public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
            int i = buttonView.getId();
            if (i == R.id.task_detail_state_over) {
                if (isChecked) {
                    noOver.setChecked(false);
                    feedbackState = 3;
                } else {
                    feedbackState = -1;
                }

            } else if (i == R.id.task_detail_state_noOver) {
                if (isChecked) {
                    over.setChecked(false);
                    feedbackState = 4;
                } else {
                    feedbackState = -1;
                }

            }

        }
    };

    @Override
    public void onClick(View v) {
        int i = v.getId();
        if (i == R.id.title_back) {
            PGApp.finishTop();

        } else if (i == R.id.task_detail_takePhoto) {
            File file = new File(Environment.getExternalStorageDirectory(), "/XiBei/" + System.currentTimeMillis() + ".jpg");
            if (!file.getParentFile().exists()) file.getParentFile().mkdirs();
            Uri imageUri = Uri.fromFile(file);
            CompressConfig compressConfig = new CompressConfig.Builder().setMaxSize(50 * 1024).setMaxPixel(800).create();//压缩方法实例化就是压缩图片，根据配置参数压缩
            getTakePhoto().onEnableCompress(compressConfig, true).onPickFromCapture(imageUri);//从相机拍取照片不裁剪

        } else if (i == R.id.task_detail_button) {
            info = infoEdit.getText().toString().trim();
            if (feedbackState == -1) {
                ToastUtil.show(context, "请选择反馈状态");
                return;
            }
            if (feedbackState == 3 && listFile.size() == 0) {
                ToastUtil.show(context, "请您拍照");
                return;
            }
            if (TextUtils.isEmpty(info)) {
                ToastUtil.show(context, "请输入反馈内容");
                return;
            }
            TaskReuest.filesRequest(this, reverse, fileMap, info, taskBean.getTask().getTaskAssignedID(), feedbackState);

        } else if (i == R.id.task_detail_state_file) {
            if (isEmpty()) {
                if (isAttachment()) {
                    for (TaskDetailBean.TaskBean.ImageListBean imageBean : taskBean.getTask().getImageList()) {
                        if (imageBean.getAttachmentType() == 2) {
                            if (MyUtils.getVideoFileName(path).size() > 0) {
                                for (String fileUrl : MyUtils.getVideoFileName(path)) {
                                    if (fileUrl.equals(imageBean.getFileName())) {
                                        File files = new File(path + fileUrl);// 这里更改为你的名称
                                        Log.i("fileName", fileUrl + "=======" + files.getPath());
                                        Uri path = Uri.fromFile(files);
                                        Intent intent = new Intent(Intent.ACTION_VIEW);
                                        intent.setDataAndType(path, "application/msword");
                                        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                                        try {
                                            startActivity(intent);
                                        } catch (ActivityNotFoundException e) {
                                            ToastUtil.show(context, "出现异常，请稍候再试");
                                        }
                                        break;
                                    }
                                }
                            }
                        }
                    }
                } else {
                    for (TaskDetailBean.TaskBean.ImageListBean imageBean : taskBean.getTask().getImageList()) {
                        if (imageBean.getAttachmentType() == 2) {
                            DownloadUtil down = new DownloadUtil(TaskDetailActivity.this, imageBean.getFileName(), imageBean.getFileUrl(), filePath);
                            down.showDownloadDialog();
                        }
                    }
                }
            }

        } else if (i == R.id.task_detail_nameLayout) {
            DialogUtils.showTaskNameDialog(this, taskBean.getTask().getTaskName());

        }
    }

    private boolean isAttachment() {
        boolean isFlag = false;
        path = Environment.getExternalStorageDirectory() + "/XiBeiDownload/";
        for (TaskDetailBean.TaskBean.ImageListBean imageBean : taskBean.getTask().getImageList()) {
            if (imageBean.getAttachmentType() == 2) {
                if (MyUtils.getVideoFileName(path).size() > 0) {
                    for (String fileUrl : MyUtils.getVideoFileName(path)) {
                        if (fileUrl.equals(imageBean.getFileName())) {
                            isFlag = true;
                        }
                    }
                }
            }
        }
        return isFlag;
    }

    private boolean isEmpty() {
        if (taskBean.getTask().getImageList().size() == 0) {
            ToastUtil.show(context, "没有可下载的文件");
            return false;
        }
        if (Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED)) {
            path = Environment.getExternalStorageDirectory() + "/XiBeiDownload";
            return true;
        } else {
            ToastUtil.show(context, "没有SD卡");
            return false;
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

    @Override
    public void takeSuccess(String imagePath) {
        super.takeSuccess(imagePath);
        Log.i("imagePath", imagePath);
        listPath.add(imagePath);
        mCameraFile = new File(imagePath);
        fileMap.put(imagePath, mCameraFile);
        listFile.add(mCameraFile);
        showImg(imagePath);
    }

    private void showImg(String imagePath) {
        BitmapFactory.Options option = new BitmapFactory.Options();
        option.inSampleSize = 2;
        Bitmap bitmap = BitmapFactory.decodeFile(imagePath, option);
        list.add(bitmap);
        taskDetalPhotoAdapter = new TaskDetalPhotoAdapter(this, list);
        gridView.setAdapter(taskDetalPhotoAdapter);
        taskDetalPhotoAdapter.notifyDataSetChanged();
        if (list.size() == 5) {
            takePhoto.setVisibility(View.INVISIBLE);
        }
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        Intent in = new Intent(this, ShowZoomPhotoActivity.class);
        in.putStringArrayListExtra("listPath", listPath);
        startActivity(in);
    }

    @Override
    public void searchType(String typeName) {
        feedbackState = "完成".equals(typeName) ? 3 : 4;
        stateReplyText.setText(typeName);
        if (showReplyPop.isShowing()) {
            showReplyPop.dismiss();
        }
    }

    public void getTaskAssignedInfo(TaskDetailBean.TaskAssignedBean taskAssignedBean) {
        if (taskAssignedBean.getFeedbackState() == 3) {
            stateReplyText.setVisibility(View.VISIBLE);
            over.setVisibility(View.GONE);
            noOver.setVisibility(View.GONE);
            stateReplyText.setText("已完成");
            submitBtn.setVisibility(View.INVISIBLE);
            takePhoto.setVisibility(View.INVISIBLE);
            task_detail_button_layout.setVisibility(View.GONE);

            infoEdit.setFocusable(false);

        }
        if (taskAssignedBean.getFeedbackState() == 4) {
            stateReplyText.setVisibility(View.VISIBLE);
            over.setVisibility(View.GONE);
            noOver.setVisibility(View.GONE);
            stateReplyText.setText("未完成");
            submitBtn.setVisibility(View.INVISIBLE);
            task_detail_button_layout.setVisibility(View.GONE);
            takePhoto.setVisibility(View.INVISIBLE);
            infoEdit.setFocusable(false);
        }

        infoEdit.setText(taskAssignedBean.getFeedBackContent());
        for (TaskDetailBean.TaskAssignedBean.ImageListBeanX imageBean : taskAssignedBean.getImageList()) {
            if (imageBean.getAttachmentType() == 1) {
                describeListFanKui.add(imageBean.getFileUrl());
            }
        }
        taskDetalFeedbackPhotoAdapter = new TaskDetalFeedbackPhotoAdapter(this, describeListFanKui);
        gridView.setAdapter(taskDetalFeedbackPhotoAdapter);
    }

    @Override
    public void getTaskDetail(TaskDetailBean taskDetailBean) {
        task_detail_button_layout.setVisibility(View.VISIBLE);
        task_detail_scrollow.setVisibility(View.VISIBLE);
        taskBean = taskDetailBean;
        if (taskBean.getTaskAssigned().getFeedbackState() == 3 || taskBean.getTaskAssigned().getFeedbackState() == 4) {
            CalendarUtils.deleteCalendarInfo(context, taskId);
        } else {
            if (TextUtils.isEmpty(SharedUtil.getString(context, taskId)))
                CalendarUtils.addCalendarsAccount(context, taskId);
            if (TextUtils.isEmpty(SharedUtil.getString(context, taskDetailBean.getTask().getTaskSno())))
                CalendarUtils.insertCalendarInfo(context, taskDetailBean, taskId);
        }
        for (TaskDetailBean.TaskBean.ImageListBean imageBean : taskBean.getTask().getImageList()) {
            if (imageBean.getAttachmentType() == 1) {
                describeList.add(imageBean.getFileUrl());
            }
        }
        taskDetalDescribePhotoAdapter = new TaskDetalDescribePhotoAdapter(this, describeList);
        gridViewDescribe.setAdapter(taskDetalDescribePhotoAdapter);


        numberText.setText(taskBean.getTask().getTaskSno());
        nameText.setText(taskBean.getTask().getTaskName());
        typeText.setText(taskBean.getTask().getTaskTypeName());
        addressText.setText(taskBean.getTask().getTaskAddr());
        founderText.setText(taskBean.getTask().getPersonName());
        priorityText.setText(taskBean.getTask().getTaskPriorityName());
        sendTimeText.setText(taskBean.getTask().getStartDateApi());

        filePath.setText(isAttachment() ? "打开" : "下载");
        for (TaskDetailBean.TaskBean.ImageListBean imageBean : taskBean.getTask().getImageList()) {
            if (imageBean.getAttachmentType() == 2) {
                fileName.setText("文件(" + imageBean.getFileName() + ")");
            }
        }
        if (TextUtils.isEmpty(fileName.getText().toString().trim())) {
            findViewById(R.id.task_detail_state_fileLayout).setVisibility(View.GONE);
        }

        feedbackTimeText.setText(taskBean.getTaskAssigned().getFeedBackDateApi());
        stateTaskText.setText(taskBean.getTask().getTaskStateName());
        taskInfo.setText(taskBean.getTask().getTaskDes());
        getTaskAssignedInfo(taskBean.getTaskAssigned());
    }
}
