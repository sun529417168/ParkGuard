package cn.com.watchman.activity;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.os.Environment;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.ImageButton;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.linked.erfli.library.base.BaseActivity;
import com.linked.erfli.library.base.MyTitle;
import com.linked.erfli.library.utils.DataCleanManager;
import com.linked.erfli.library.utils.DialogUtils;
import com.linked.erfli.library.utils.SharedUtil;

import java.io.File;
import java.util.ArrayList;

import cn.com.watchman.R;
import cn.com.watchman.utils.FileSizeUtils;
import cn.com.watchman.utils.WMyUtils;


/**
 * 文件名：WatchManStatisticsActivity
 * 描    述：巡更统计页面
 * 作    者：stt
 * 时    间：2017.4.25
 * 版    本：V1.0.0
 */

public class WatchManStatisticsActivity extends BaseActivity implements View.OnClickListener {
    private ImageButton img_Title_GoBack_ImageButton;
    private TextView tv_Title_TextView;
    private TextView tv_statisicsCurrNum, tv_statisicsTotalNum,tv_day_flow,tv_month_flow;//上传次数 运行时间 流量统计
    private TextView tv_FunctionTime;//运行时间
    private RelativeLayout rl_cache_layout;//缓存文本父布局
    private TextView tv_Cache;//缓存大小
    private TextView tv_flow;
    private String url;
    private ArrayList<String> imgListPath = new ArrayList<>();

    @Override
    protected void setView() {
        setContentView(R.layout.activity_statistics);
    }

    @Override
    protected void setDate(Bundle savedInstanceState) {
        MyTitle.getInstance().setTitle(this, "巡更统计", PGApp, true);
        url = Environment.getExternalStorageDirectory() + "/mytemp";
        getFileImagePath();
    }

    @Override
    public void onNetChange(int netMobile) {
        super.onNetChange(netMobile);
        MyTitle.getInstance().setNetText(this, netMobile);
    }

    @Override
    protected void init() {
        tv_statisicsCurrNum = (TextView) findViewById(R.id.tv_statisicsCurrNum);
        tv_statisicsTotalNum=(TextView)findViewById(R.id.tv_statisicsTotalNum);
        tv_day_flow=(TextView)findViewById(R.id.tv_day_flow);
        tv_month_flow=(TextView)findViewById(R.id.tv_month_flow);
        tv_FunctionTime = (TextView) findViewById(R.id.tv_FunctionTime);
        tv_FunctionTime.setText(WMyUtils.runTime(this));
        rl_cache_layout = (RelativeLayout) findViewById(R.id.rl_cache_layout);
        rl_cache_layout.setOnClickListener(this);
        tv_Cache = (TextView) findViewById(R.id.tv_Cache);
        tv_statisicsCurrNum.setText(String.valueOf(getIntent().getIntExtra("currentCount",0))+"次");
        tv_statisicsTotalNum.setText(SharedUtil.getInteger(getApplicationContext(),"totalCount",0)+"次");
        try {
            tv_Cache.setText(DataCleanManager.getTotalCacheSize(this));
        } catch (Exception e) {
            e.printStackTrace();
        }
        tv_day_flow.setText(TextUtils.isEmpty(SharedUtil.getString(this, "traffic")) ? "" : SharedUtil.getString(this, "traffic"));
        tv_month_flow.setText(TextUtils.isEmpty(SharedUtil.getString(this, "monthTraffic")) ? "" : SharedUtil.getString(this, "monthTraffic"));
    }


    private void getFileImagePath() {
        File scanner5Directory = new File(url);
        if (scanner5Directory.isDirectory()) {
            for (File file : scanner5Directory.listFiles()) {
                String imgPath = file.getAbsolutePath();
                if (imgPath.endsWith(".jpg") || imgPath.endsWith(".JPEG")
                        || imgPath.endsWith(".png")) {
                    Log.i("cspath", imgPath);
                    imgListPath.add(imgPath);
                }
            }
        }
    }

    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.rl_cache_layout) {
            DialogUtils.clearData(this, tv_Cache);
        }
    }

    /**
     * 弹出提示框
     */
    private void showRemoveCacheDialog() {
        new AlertDialog.Builder(this).setTitle("提示")
                .setIcon(R.drawable.my_question_mark)
                .setMessage("确定清除缓存?")
                .setPositiveButton("确认", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        //缓存清除操作
                        for (int i = 0; i < imgListPath.size(); i++) {
                            File fileS = new File(imgListPath.get(i));
                            if (fileS.exists()) {
                                fileS.delete();
                            }
                        }
                        String fileSize = FileSizeUtils.getAutoFileOrFilesSize(url);
                        tv_Cache.setText(fileSize);
                    }
                })
                .setNegativeButton("取消", null)
                .create().show();
    }
}
