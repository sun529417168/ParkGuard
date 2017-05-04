package cn.com.problem;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.view.View;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.linked.erfli.library.function.takephoto.app.TakePhotoActivity;
import com.linked.erfli.library.function.takephoto.compress.CompressConfig;

import java.io.File;
import java.util.ArrayList;

import cn.com.problem.adapter.ProblemDetalPhotoAdapter;
import cn.com.problem.bean.ProblemDetailBean;
import cn.com.problem.interfaces.ProblemDetailInterface;
import cn.com.problem.networkrequest.ProblemRequest;

/**
 * Created by 志强 on 2017.5.2.
 */

public class ProblemDetailActivity extends TakePhotoActivity implements View.OnClickListener, ProblemDetailInterface {
    private Context context;
    private TextView titleName;
    private LinearLayout back;
    private ImageView takePhoto;
    private ArrayList<String> listPath = new ArrayList<String>();
    private ArrayList<String> describeList = new ArrayList<String>();
    private ArrayList<Bitmap> list = new ArrayList<Bitmap>();
    private GridView gridView;
    private ProblemDetalPhotoAdapter problemDetalPhotoAdapter;
    private ProblemDetailBean problemBean;

    /**
     * 编号，问题名称，状态，上报人，上报时间，处理人，处理时间
     */
    private TextView numberText, nameText, stateText, senderText, sendTimeText;
    private TextView problemTypeText, addressText;
    private TextView infoEdit, describeText, replyTimeText;

    @Override
    protected void setView() {
        setContentView(R.layout.problem_activity_detail);
    }

    @Override
    protected void setDate(Bundle savedInstanceState) {
        String id = getIntent().getStringExtra("problemId");
        ProblemRequest.problemDetailRequest(this, id);
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
        titleName = (TextView) findViewById(R.id.title_name);
        titleName.setText("问题详情");
        back = (LinearLayout) findViewById(R.id.title_back);
        back.setVisibility(View.VISIBLE);
        back.setOnClickListener(this);
        numberText = (TextView) findViewById(R.id.problem_detail_number);
        //  nameText = (TextView) findViewById(R.id.problem_detail_name);
        stateText = (TextView) findViewById(R.id.problem_detail_state);
        problemTypeText = (TextView) findViewById(R.id.problem_detail_type);
        addressText = (TextView) findViewById(R.id.problem_detail_area);
        senderText = (TextView) findViewById(R.id.problem_detail_sender);
        sendTimeText = (TextView) findViewById(R.id.problem_detail_sendTime);
        infoEdit = (TextView) findViewById(R.id.problem_detail_infoEdit);
        describeText = (TextView) findViewById(R.id.problem_detail_describe);
        replyTimeText = (TextView) findViewById(R.id.problem_detail_replyTime);
    }

    @Override
    public void onClick(View v) {
        int i = v.getId();
        if (i == R.id.title_back) {
            PGApp.finishTop();

        } else if (i == R.id.problem_detail_takePhoto) {
            File file = new File(Environment.getExternalStorageDirectory(), "/sultan/" + "reported" + "pic" + System.currentTimeMillis() + ".jpg");
            if (!file.getParentFile().exists()) file.getParentFile().mkdirs();
            Uri imageUri = Uri.fromFile(file);
            CompressConfig compressConfig = new CompressConfig.Builder().setMaxSize(50 * 1024).setMaxPixel(800).create();//压缩方法实例化就是压缩图片，根据配置参数压缩
            getTakePhoto().onEnableCompress(compressConfig, true).onPickFromCapture(imageUri);//从相机拍取照片不裁剪

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
        listPath.add(imagePath);
        showImg(imagePath);
    }

    private void showImg(String imagePath) {
        BitmapFactory.Options option = new BitmapFactory.Options();
        option.inSampleSize = 2;
        Bitmap bitmap = BitmapFactory.decodeFile(imagePath, option);
        list.add(bitmap);
        problemDetalPhotoAdapter.setList(list);
        if (list.size() == 4) {
            takePhoto.setVisibility(View.INVISIBLE);
        }
    }

    @Override
    public void getProblemDetail(ProblemDetailBean problemDetailBean) {
        problemBean = problemDetailBean;
        numberText.setText(problemBean.getProblemSno());
        //nameText.setText(problemBean.getProblemTitle());
        if (problemBean.getState() == 1) {
            stateText.setText("已上报");
        }
        if (problemBean.getState() == 2) {
            stateText.setText("已回复");
        }
        problemTypeText.setText(problemBean.getProblemTypeName());
        addressText.setText(problemBean.getPosition());
        senderText.setText(problemBean.getReportPersonName());
        sendTimeText.setText(problemBean.getFindDateApi());
        infoEdit.setText(problemBean.getDescribe());
        describeText.setText(problemBean.getProblemDes());
        replyTimeText.setText(problemBean.getProcessDateApi());
        takePhoto = (ImageView) findViewById(R.id.problem_detail_takePhoto);
        takePhoto.setOnClickListener(this);
        takePhoto.setVisibility(View.INVISIBLE);
        gridView = (GridView) findViewById(R.id.problem_detail_gridView);
        for (ProblemDetailBean.ReportAttachmentListBean imageBean : problemBean.getReportAttachmentList()) {
            if (imageBean.getAttachmentType() == 1) {
                describeList.add(imageBean.getFileUrl());
            }
        }
        problemDetalPhotoAdapter = new ProblemDetalPhotoAdapter(ProblemDetailActivity.this, describeList);
        gridView.setAdapter(problemDetalPhotoAdapter);
    }
}
