package cn.com.task;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.linked.erfli.library.base.BaseActivity;
import com.linked.erfli.library.utils.SharedUtil;
import com.linked.erfli.library.utils.ToastUtil;

import cn.com.task.bean.UserBean;
import cn.com.task.interfaces.TaskLoginInterface;
import cn.com.task.networkrequest.TaskReuest;

/**
 * 类  名:TaskLoginActivity
 * 描  述:任务登录页面
 * 作  者:zzq
 * 时  间:2017年4月26日
 * 版  本:V1.0,0
 */

public class TaskLoginActivity extends BaseActivity implements View.OnClickListener, TaskLoginInterface {

    private TaskLoginActivity mContext;
    private EditText login_username;//name
    private EditText login_password;//password
    private TextView login_forget_password;//忘记密码
    private Button login_button;//登录按钮
    private TextView title_name;//title name

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
        title_name = (TextView) findViewById(R.id.title_name);
        title_name.setText("登录");
        login_username = (EditText) findViewById(R.id.login_username);
        login_password = (EditText) findViewById(R.id.login_password);
        login_button = (Button) findViewById(R.id.task_login_button);
        login_button.setOnClickListener(this);
        login_forget_password = (TextView) findViewById(R.id.login_forget_password);
        login_forget_password.setOnClickListener(this);
    }

    private String userName, passWord;

    @Override
    public void onClick(View v) {
        int i = v.getId();
        if (i == R.id.task_login_button) {
            userName = login_username.getText().toString().trim();
            passWord = login_password.getText().toString().trim();
            if (isEmpt()) {
                TaskReuest.loginRequest(mContext, userName, passWord);
            }

        } else if (i == R.id.login_forget_password) {
        }
    }

    private boolean isEmpt() {
        boolean flag = false;
        if (TextUtils.isEmpty(userName)) {
            ToastUtil.show(mContext, "请输入用户名");
            flag = false;
        } else if (TextUtils.isEmpty(passWord)) {
            ToastUtil.show(mContext, "请输入密码");
            flag = false;
        } else {
            flag = true;
        }
        return flag;
    }

    /**
     * 请求方法回调接口
     *
     * @param userBean 返回的实体类
     */
    @Override
    public void getLoginResult(UserBean userBean) {
        SharedUtil.setString(this, "personId", userBean.getPersonId() + "");
        SharedUtil.setBoolean(this, "isSuccess", true);
        if (userBean.getPersonId() == 0) {
            ToastUtil.show(this, "用户名或者密码不对，请重新输入");
            return;
        }
        if (0 == userBean.getUserType()) {
            Intent intent = new Intent(this, TaskEditorUserActivity.class);
            intent.putExtra("username", userName);
            startActivity(intent);
            this.finish();
        }
        if (1 == userBean.getUserType()) {
            SharedUtil.setBoolean(this, "isLogin", true);
            Intent intent = new Intent(this, TaskActivity.class);
            startActivity(intent);
            this.finish();
        }
    }
}
