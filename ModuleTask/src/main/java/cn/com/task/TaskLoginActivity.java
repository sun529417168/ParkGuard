package cn.com.task;

import android.os.Bundle;

import com.linked.erfli.library.base.BaseActivity;

/**
 * 类  名:TaskLoginActivity
 * 描  述:任务登录页面
 * 作  者:zzq
 * 时  间:2017年4月26日
 * 版  本:V1.0,0
 */

public class TaskLoginActivity extends BaseActivity {

    private TaskLoginActivity mContext;

    @Override
    protected void setView() {
        setContentView(R.layout.task_activity_login);
        mContext = this;
    }

    /**
     * 接收上级，填充数据 第二步
     *
     * @param savedInstanceState
     */
    @Override
    protected void setDate(Bundle savedInstanceState) {

    }

    /**
     * 初始化组件
     */
    @Override
    protected void init() {

    }
}
