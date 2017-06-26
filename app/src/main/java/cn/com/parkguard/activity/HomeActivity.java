package cn.com.parkguard.activity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.provider.Settings;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.alibaba.fastjson.JSON;
import com.github.mzule.activityrouter.router.Routers;
import com.linked.erfli.library.base.BaseActivity;
import com.linked.erfli.library.base.MyTitle;
import com.linked.erfli.library.myserviceutils.MyConstant;
import com.linked.erfli.library.myserviceutils.MyServiceUtils;
import com.linked.erfli.library.utils.MyUtils;
import com.linked.erfli.library.utils.NetWorkUtils;
import com.linked.erfli.library.utils.SharedUtil;
import com.linked.erfli.library.utils.StatusBarUtils;
import com.linked.erfli.library.utils.ToastUtil;

import java.util.ArrayList;

import cn.com.parkguard.R;
import cn.com.parkguard.adapter.HomeAdapter;
import cn.com.parkguard.bean.HomeBean;

/**
 * 文件名：HomeActivity
 * 描    述：主界面
 * 作    者：stt
 * 时    间：2017.01.04
 * 版    本：V1.0.0
 */
public class HomeActivity extends BaseActivity implements AdapterView.OnItemClickListener {
    private TextView netText;
    private GridView gridView;
    private LinearLayout setting;
    private int image[] = {R.mipmap.home_task, R.mipmap.home_notice, R.mipmap.home_problem, R.mipmap.home_statistical, R.mipmap.home_xungeng, R.mipmap.home_anwen, R.mipmap.ic_map, R.mipmap.home_monitor};
    private ArrayList<HomeBean> homeArrayList = new ArrayList<>();
    private HomeAdapter homeAdapter;
    private long exitTime;//上一次按退出键时间
    private static final long TIME = 2000;//双击回退键间隔时间

    @Override
    protected void setView() {
        setContentView(R.layout.activity_main);

    }

    @Override
    protected void setDate(Bundle savedInstanceState) {
        StatusBarUtils.ff(this,R.color.colorPrimary);
        MyTitle.getInstance().setTitle(this, "移动园区卫士", PGApp, false);
        setting=(LinearLayout) findViewById(R.id.home_setter);
        setting.setVisibility(View.VISIBLE);
        setting.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(HomeActivity.this,SettingActivity.class);
                startActivity(intent);
            }
        });
        ArrayList<HomeBean> homeList = (ArrayList<HomeBean>) JSON.parseArray(MyUtils.getFromAssets(this, "home.txt"), HomeBean.class);
        for (int i = 0; i < homeList.size(); i++) {
            if (homeList.get(i).isIsTrue()) {
                HomeBean homeBean = new HomeBean();
                homeBean.setId(homeList.get(i).getId());
                homeBean.setName(homeList.get(i).getName());
                homeBean.setImageView(image[i]);
                homeArrayList.add(homeBean);
            }
        }
    }

    @Override
    protected void init() {
        netText = (TextView) findViewById(R.id.main_title_net);
        //启动时判断网络状态
        boolean netConnect = this.isNetConnect();
        if (netConnect) {
            netText.setVisibility(View.GONE);
        } else {
            netText.setVisibility(View.VISIBLE);
        }
        gridView = (GridView) findViewById(R.id.home_gridView);
        homeAdapter = new HomeAdapter(this, homeArrayList);
        gridView.setAdapter(homeAdapter);
        gridView.setOnItemClickListener(this);
        netText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(Settings.ACTION_SETTINGS));
            }
        });
        if (!MyServiceUtils.isServiceRunning(MyConstant.GPSSERVICE_CLASSNAME, HomeActivity.this)) {
            //Intent startIntent = new Intent(this, MyScoketService.class);
            //startService(startIntent);
        }
    }

    @Override

    public void onNetChange(int netMobile) {
        super.onNetChange(netMobile);
        //网络状态变化时的操作
        if (netMobile == NetWorkUtils.NETWORK_NONE) {
            netText.setVisibility(View.VISIBLE);
        } else {
            netText.setVisibility(View.GONE);
        }
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        switch (homeArrayList.get(position).getId()) {
            case 101:
                SharedUtil.setBoolean(this, "isTask", true);
                Routers.open(HomeActivity.this, Uri.parse("modularization://task_list"));
                break;
            case 102:
                SharedUtil.setBoolean(this, "isNotice", true);
                Routers.open(HomeActivity.this, Uri.parse("modularization://notice_list"));
                break;
            case 103:
                SharedUtil.setBoolean(this, "isProblem", true);
                Routers.open(HomeActivity.this, Uri.parse("modularization://problem_list"));
                break;
            case 104:
                //统计
                break;
            case 105:
                SharedUtil.setBoolean(this, "isWatchMan", true);
                Routers.open(HomeActivity.this, Uri.parse("modularization://watchman"));
                break;
            case 106:

                break;
            case 107:

                break;
            case 108:
                ToastUtil.show(this, "正在开发中");
//                SharedUtil.setBoolean(this, "isMonitor", true);
//                Routers.open(HomeActivity.this, Uri.parse("modularization://monitor"));
                break;
        }
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            if ((System.currentTimeMillis() - exitTime) > TIME) {
                ToastUtil.show(this, "再按一次返回键退出");
                exitTime = System.currentTimeMillis();
                return true;
            } else {
                PGApp.exit();
            }
            return false;
        }
        return super.onKeyDown(keyCode, event);
    }

    @Override
    protected void onResume() {
        super.onResume();
        boolean serviceBool = MyServiceUtils.isServiceRunning(MyConstant.GPSSERVICE_CLASSNAME, HomeActivity.this);
        Log.i("", "gps上传数据服务:" + serviceBool);
        findViewById(R.id.home_sendMessage).setVisibility(serviceBool ? View.VISIBLE : View.INVISIBLE);
    }
}
