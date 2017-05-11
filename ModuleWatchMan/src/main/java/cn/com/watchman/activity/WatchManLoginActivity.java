package cn.com.watchman.activity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.linked.erfli.library.base.BaseActivity;
import com.linked.erfli.library.base.MyTitle;
import com.linked.erfli.library.utils.SharedUtil;
import com.linked.erfli.library.utils.StatusBarUtils;
import com.linked.erfli.library.utils.ToastUtil;

import cn.com.watchman.R;
import cn.com.watchman.bean.UserBean;
import cn.com.watchman.interfaces.WatchManLoginInterface;
import cn.com.watchman.networkrequest.WatchManRequest;

/**
 * Created by 志强 on 2017.5.3.
 */

public class WatchManLoginActivity extends BaseActivity implements View.OnClickListener, WatchManLoginInterface {
    private WatchManLoginActivity mContext;
    private EditText login_username;//name
    private EditText login_password;//password
    private TextView login_forget_password;//忘记密码
    private Button login_button;//登录按钮
    private TextView title_name;//title name

    @Override
    protected void setView() {
        setContentView(R.layout.watchman_login_layout);
        mContext = this;
        StatusBarUtils.ff(mContext, R.color.blue);
    }

    @Override
    protected void setDate(Bundle savedInstanceState) {
        MyTitle.getInstance().setTitle(this, "登陆", PGApp, false);
    }

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

    @Override
    public void onNetChange(int netMobile) {
        super.onNetChange(netMobile);
        MyTitle.getInstance().setNetText(this, netMobile);
    }

    private String userName, passWord;

    @Override
    public void onClick(View v) {
        int i = v.getId();
        if (i == R.id.task_login_button) {
            userName = login_username.getText().toString().trim();
            passWord = login_password.getText().toString().trim();
            if (isEmpt()) {
                WatchManRequest.loginRequest(mContext, userName, passWord);
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
            Intent intent = new Intent(this, WatchManEditorUserActivity.class);
            intent.putExtra("username", userName);
            startActivity(intent);
            this.finish();
        }
        if (1 == userBean.getUserType()) {
            SharedUtil.setBoolean(this, "isLogin", true);
            Intent intent = new Intent(this, WatchMainActivity.class);
            startActivity(intent);
            this.finish();
        }
    }
}
