package cn.com.problem;

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
import com.linked.erfli.library.utils.DialogUtils;
import com.linked.erfli.library.utils.MyUtils;
import com.linked.erfli.library.utils.SharedUtil;
import com.linked.erfli.library.utils.ToastUtil;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import cn.com.problem.bean.ProblemTypeLeft;
import cn.com.problem.interfaces.ProblemTypeLeftInterface;
import cn.com.problem.networkrequest.ProblemRequest;
import cn.com.problem.weight.AddProblemTypePopwindow;


/**
 * Created by 志强 on 2017.5.2.
 */

public class AddProblemActivity extends TakePhotoActivity implements View.OnClickListener, AdapterView.OnItemClickListener, GetGPSInterface, ProblemTypeLeftInterface {

    private Context context;
    private TextView titleName;
    private LinearLayout back;
    private ImageView takePhoto;
    private ArrayList<String> listPath = new ArrayList<String>();
    private ArrayList<Bitmap> list = new ArrayList<Bitmap>();
    private List<File> listFile = new ArrayList<>();
    private Map<String, File> fileMap = new HashMap<>();
    private File mCameraFile;
    private GridView gridView;
    private TaskDetalPhotoAdapter taskDetalPhotoAdapter;
    private EditText nameEdit, addressEdit, inputInfoEdit;
    private RelativeLayout typeLayout, findTimeLayout, positionLayout;
    private TextView typeText, senderText, findTimeText, sendTimeText, positionText;
    private Button sendInfoBtn;
    private String problemType = "";
    private String gps;
    private String problemTitle, address, findDate, problemDes;

    @Override
    protected void setView() {
        setContentView(R.layout.problem_activity_add);
    }

    @Override
    protected void setDate(Bundle savedInstanceState) {
        if (!MyUtils.gpsIsOPen(this)) {
            DialogUtils.initGPS(this);
        }
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
        titleName.setText("问题上报");
        back = (LinearLayout) findViewById(R.id.title_back);
        back.setVisibility(View.VISIBLE);
        back.setOnClickListener(this);
        // nameEdit = (EditText) findViewById(R.id.add_problem_detail_name);
        typeLayout = (RelativeLayout) findViewById(R.id.add_problem_typeLayout);
        positionLayout = (RelativeLayout) findViewById(R.id.add_problem_positionLayout);
        positionText = (TextView) findViewById(R.id.add_problem_address);
        typeLayout.setOnClickListener(this);
        positionLayout.setOnClickListener(this);
        typeText = (TextView) findViewById(R.id.add_problem_type);
        //addressEdit = (EditText) findViewById(R.id.add_problem_address);
        senderText = (TextView) findViewById(R.id.add_problem_sender);
        senderText.setText(SharedUtil.getString(this, "personName"));
        findTimeLayout = (RelativeLayout) findViewById(R.id.add_problem_findTimeLayout);
        findTimeLayout.setOnClickListener(this);
        findTimeText = (TextView) findViewById(R.id.add_problem_findTime);
        sendTimeText = (TextView) findViewById(R.id.add_problem_sendTime);
        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm");//设置日期格式
        findTimeText.setText(df.format(new Date()));
        inputInfoEdit = (EditText) findViewById(R.id.add_problem_inputInfo);
        sendInfoBtn = (Button) findViewById(R.id.add_problem_detail_button);
        sendInfoBtn.setOnClickListener(this);


        takePhoto = (ImageView) findViewById(R.id.add_problem_detail_takePhoto);
        takePhoto.setOnClickListener(this);
        gridView = (GridView) findViewById(R.id.add_problem_detail_gridView);
        gridView.setOnItemClickListener(this);
        taskDetalPhotoAdapter = new TaskDetalPhotoAdapter(this, list);
        gridView.setAdapter(taskDetalPhotoAdapter);
        taskDetalPhotoAdapter.setList(list);
    }

    @Override
    public void onClick(View v) {
        int i = v.getId();
        if (i == R.id.title_back) {
            PGApp.finishTop();

        } else if (i == R.id.add_problem_detail_takePhoto) {
            File file = new File(Environment.getExternalStorageDirectory(), "/XiBeiProblem/" + System.currentTimeMillis() + ".jpg");
            if (!file.getParentFile().exists()) file.getParentFile().mkdirs();
            Uri imageUri = Uri.fromFile(file);
            CompressConfig compressConfig = new CompressConfig.Builder().setMaxSize(50 * 1024).setMaxPixel(700).create();//压缩方法实例化就是压缩图片，根据配置参数压缩
            getTakePhoto().onEnableCompress(compressConfig, true).onPickFromCapture(imageUri);//从相机拍取照片不裁剪

        } else if (i == R.id.add_problem_typeLayout) {
            ProblemRequest.getProblemTypeLeft(this, AddProblemActivity.this);

        } else if (i == R.id.add_problem_positionLayout) {
            Intent intent = new Intent(AddProblemActivity.this, InputTextActivity.class);
            address = positionText.getText().toString().trim();
            if (!TextUtils.isEmpty(address)) {
                intent.putExtra("value", address);
            }
            startActivityForResult(intent, 1);

        } else if (i == R.id.add_problem_findTimeLayout) {
            DateTimePickDialogUtil dateTimePicKDialog = new DateTimePickDialogUtil(AddProblemActivity.this, "");
            dateTimePicKDialog.dateTimePicKDialog(findTimeText);

        } else if (i == R.id.add_problem_detail_button) {
            if (!MyUtils.gpsIsOPen(this)) {
                DialogUtils.initGPS(this);
                return;
            }
            MyUtils.getLoc(AddProblemActivity.this);
            //problemTitle = nameEdit.getText().toString().trim();
            findDate = findTimeText.getText().toString().trim();
            problemDes = inputInfoEdit.getText().toString().trim();
            if (isEmpty()) {
                ProblemRequest.addProblemRequestsb(this, fileMap, problemType, address, gps, findDate, problemDes);//不管有没有图片
            }


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
        } else if (TextUtils.isEmpty(findDate)) {
            ToastUtil.show(this, "请选择发现时间");
            return false;
        } else if (TextUtils.isEmpty(problemDes)) {
            ToastUtil.show(this, "请输入问题描述");
            return false;
        } else {
            return true;
        }
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        Intent in = new Intent(this, ShowZoomPhotoActivity.class);
        in.putStringArrayListExtra("listPath", listPath);
        in.putExtra("current", position);
        startActivity(in);
    }

    @Override
    public void getGPS(String longitude, String latitude) {
        gps = longitude + "," + latitude;
        Log.i("gps", gps);
    }

    @Override
    public void getTypeLeft(List<ProblemTypeLeft> problemTypeLeftList) {
        AddProblemTypePopwindow mAddProblemTypePopwindow = new AddProblemTypePopwindow(AddProblemActivity.this, (ArrayList<ProblemTypeLeft>) problemTypeLeftList);
        mAddProblemTypePopwindow.showAsDropDown(typeLayout, 0, 5);
        mAddProblemTypePopwindow.setAddresskListener(new AddProblemTypePopwindow.OnAddressCListener() {
            @Override
            public void onClick(String left, String right, String code) {
                typeText.setText(right);
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
            takePhoto.setVisibility(View.INVISIBLE);
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        switch (requestCode) {
            case 1:
                if (resultCode == RESULT_OK) {
                    try {
                        address = data.getStringExtra("return_value");
                        positionText.setText(address);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
                if (resultCode == RESULT_CANCELED) {
                    try {
                        address = data.getStringExtra("return_value");
                        positionText.setText(address);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
                break;

            default:
                break;
        }
    }
}
