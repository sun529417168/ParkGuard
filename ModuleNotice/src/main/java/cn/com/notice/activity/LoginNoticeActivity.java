package cn.com.notice.activity;

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
import com.linked.erfli.library.utils.ToastUtil;

import cn.com.notice.R;
import cn.com.notice.Utils.MyRequest;
import cn.com.notice.bean.UserBean;
import cn.com.notice.interfaces.LoginInterface;


/**
 * 文件名：LoginActivity
 * 描    述：登录类
 * 作    者：stt
 * 时    间：2017.01.04
 * 版    本：V1.0.0
 */

public class LoginNoticeActivity extends BaseActivity implements View.OnClickListener, LoginInterface {
    private EditText inputUsername, inputPassword;//用户名，密码
    private String username, password;
    private Button loginBtn;
    private TextView forgetPassword;

    @Override
    protected void setView() {
        setContentView(R.layout.activity_notice_login);
    }

    @Override
    protected void setDate(Bundle savedInstanceState) {
        MyTitle.getInstance().setTitle(this, "登陆", PGApp, false);
    }

    @Override
    protected void init() {
        inputUsername = (EditText) findViewById(R.id.login_username);
        inputPassword = (EditText) findViewById(R.id.login_password);
        inputUsername.setText(SharedUtil.getString(this, "userName"));
        inputPassword.setText(SharedUtil.getString(this, "passWord"));
        loginBtn = (Button) findViewById(R.id.login_button);
        loginBtn.setOnClickListener(this);
        forgetPassword = (TextView) findViewById(R.id.login_forget_password);
        forgetPassword.setOnClickListener(this);

    }

    @Override
    public void onNetChange(int netMobile) {
        super.onNetChange(netMobile);
        MyTitle.getInstance().setNetText(this, netMobile);
    }

    @Override
    public void onClick(View v) {

        int i = v.getId();
        if (i == R.id.login_button) {
            username = inputUsername.getText().toString().trim();
            password = inputPassword.getText().toString().trim();
            if (isEmpt()) {
                MyRequest.loginRequest(this, username, password);
            }

        } else if (i == R.id.login_forget_password) {
            Intent intent = new Intent(this, ForgetPasswordActivity.class);
            startActivity(intent);

        }
    }

    private boolean isEmpt() {
        boolean flag = false;
        if (TextUtils.isEmpty(username)) {
            ToastUtil.show(this, "请输入用户名");
            flag = false;
        } else if (TextUtils.isEmpty(password)) {
            ToastUtil.show(this, "请输入密码");
            flag = false;
        } else {
            flag = true;
        }
        return flag;
    }

    @Override
    public void login(final UserBean userBean) {
        SharedUtil.setString(this, "PersonID", userBean.getPersonId() + "");
        SharedUtil.setBoolean(this, "isSuccess", true);
        if (userBean.getPersonId() == 0) {
            ToastUtil.show(this, "用户名或者密码不对，请重新输入");
            return;
        }
        if (0 == userBean.getUserType()) {
            Intent intent = new Intent(this, EditorUserActivity.class);
            intent.putExtra("username", username);
            startActivity(intent);
            this.finish();
        }
        if (1 == userBean.getUserType()) {
            SharedUtil.setBoolean(this, "isLogin", true);
            Intent intent = new Intent(this, NoticeActivity.class);
            startActivity(intent);
            this.finish();
        }
    }


}
