package cn.com.parkguard.activity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.TextView;

import cn.com.parkguard.R;
import cn.com.parkguard.adapter.HomeAdapter;
import cn.com.parkguard.bean.HomeBean;

import com.alibaba.fastjson.JSON;
import com.github.mzule.activityrouter.router.Routers;
import com.linked.erfli.library.base.BaseActivity;
import com.linked.erfli.library.utils.MyUtils;
import com.linked.erfli.library.utils.NetWorkUtils;
import com.linked.erfli.library.utils.ToastUtil;

import java.util.ArrayList;

public class HomeActivity extends BaseActivity implements AdapterView.OnItemClickListener {
    private TextView netText;
    private GridView gridView;
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
                Routers.open(HomeActivity.this, Uri.parse("modularization://task_list"));
                break;
            case 102:
                Routers.open(HomeActivity.this, Uri.parse("modularization://notice_list"));
                break;
            case 103:
                Routers.open(HomeActivity.this, Uri.parse("modularization://problem_list"));
                break;
            case 104:
                //统计
                break;
            case 105:
                Routers.open(HomeActivity.this, Uri.parse("modularization://watchman"));
                break;
            case 106:

                break;
            case 107:

                break;
            case 108:
                Routers.open(HomeActivity.this, Uri.parse("modularization://monitor"));
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
}
