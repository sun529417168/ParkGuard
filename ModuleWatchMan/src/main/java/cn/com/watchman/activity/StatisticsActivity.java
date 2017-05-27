package cn.com.watchman.activity;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.util.Log;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.linked.erfli.library.base.BaseActivity;
import com.linked.erfli.library.base.MyTitle;

import java.io.File;
import java.util.ArrayList;

import cn.com.watchman.R;


/**
 * Created by 志强 on 2017.4.10.
 */

public class StatisticsActivity extends BaseActivity {


    private TextView tv_statisicsCurrNum, tv_statisicsTotalNum,tv_day_flow,tv_month_flow,tv_FunctionTime, tv_flow;//上传次数 运行时间 流量统计
    private RelativeLayout rl_cache_layout;//缓存文本父布局
    private TextView tv_Cache;//缓存大小
    //    private SharePreferenceUtils sp;
    private String url;

    @Override
    protected void setView() {
        setContentView(R.layout.activity_statistics);
        MyTitle.getInstance().setTitle(this, "巡更统计", PGApp, true);
//        sp = new SharePreferenceUtils(StatisticsActivity.this);
    }

    @Override
    protected void setDate(Bundle savedInstanceState) {
        
    }

    @Override
    protected void init() {
        tv_statisicsCurrNum = (TextView) findViewById(R.id.tv_statisicsCurrNum);
        tv_statisicsTotalNum=(TextView)findViewById(R.id.tv_statisicsTotalNum);
        tv_day_flow=(TextView)findViewById(R.id.tv_day_flow);
        tv_month_flow=(TextView)findViewById(R.id.tv_month_flow);
        rl_cache_layout = (RelativeLayout) findViewById(R.id.rl_cache_layout);
        tv_Cache = (TextView) findViewById(R.id.tv_Cache);
    }


    /**
     * 弹出提示框
     */
    private void showRemoveCacheDialog() {
        new AlertDialog.Builder(StatisticsActivity.this).setTitle("提示")
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
                        Toast.makeText(StatisticsActivity.this, "clear cache success!", Toast.LENGTH_SHORT).show();
                    }
                })
                .setNegativeButton("取消", null)
                .create().show();
    }

    private ArrayList<String> imgListPath = new ArrayList<>();

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
}
