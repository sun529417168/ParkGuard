package cn.com.parkguard.activity;

import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import cn.com.parkguard.R;

import com.github.mzule.activityrouter.router.Routers;
import com.linked.erfli.library.base.BaseActivity;
import com.linked.erfli.library.utils.NetWorkUtils;
import com.linked.erfli.library.utils.SharedUtil;

public class MainActivity extends BaseActivity implements View.OnClickListener {
    private TextView mTv;
    private Button taskBtn, noticeBtn,problemBtn,watchmanBtn,monitorBtn;

    @Override
    protected void setView() {
        setContentView(R.layout.activity_main);
    }

    @Override
    protected void setDate(Bundle savedInstanceState) {
        SharedUtil.setString(this, "task", "task");
        SharedUtil.setString(this, "notice", "notice");
    }

    @Override
    protected void init() {
        mTv = (TextView) findViewById(R.id.tv);
        //启动时判断网络状态
        boolean netConnect = this.isNetConnect();
        if (netConnect) {
            mTv.setVisibility(View.GONE);
        } else {
            mTv.setVisibility(View.VISIBLE);
        }
        taskBtn = (Button) findViewById(R.id.task);
        taskBtn.setOnClickListener(this);
        noticeBtn = (Button) findViewById(R.id.notice);
        noticeBtn.setOnClickListener(this);
        problemBtn = (Button) findViewById(R.id.problem);
        problemBtn.setOnClickListener(this);
        monitorBtn = (Button) findViewById(R.id.monitor);
        monitorBtn.setOnClickListener(this);
        watchmanBtn = (Button) findViewById(R.id.watchman);
        watchmanBtn.setOnClickListener(this);
    }

    @Override
    public void onNetChange(int netMobile) {
        super.onNetChange(netMobile);
        //网络状态变化时的操作
        if (netMobile == NetWorkUtils.NETWORK_NONE) {
            findViewById(R.id.tv).setVisibility(View.VISIBLE);
        } else {
            findViewById(R.id.tv).setVisibility(View.GONE);
        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.task:
                Routers.open(MainActivity.this, Uri.parse("modularization://task_list"));
                break;
            case R.id.notice:
                Routers.open(MainActivity.this, Uri.parse("modularization://notice_list"));
                break;
            case R.id.problem:
                Routers.open(MainActivity.this, Uri.parse("modularization://problem_list"));
                break;
            case R.id.watchman:
                Routers.open(MainActivity.this, Uri.parse("modularization://watchman"));
                break;
            case R.id.monitor:
                Routers.open(MainActivity.this, Uri.parse("modularization://monitor"));
                break;
        }
    }
}
