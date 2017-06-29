package cn.com.parkguard.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.InputType;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;

import com.alibaba.sdk.android.push.CloudPushService;
import com.alibaba.sdk.android.push.CommonCallback;
import com.alibaba.sdk.android.push.noonesdk.PushServiceFactory;
import com.linked.erfli.library.base.BaseActivity;
import com.linked.erfli.library.base.MyTitle;
import com.linked.erfli.library.utils.SharedUtil;
import com.linked.erfli.library.utils.ToastUtil;

import cn.com.parkguard.R;
import cn.com.parkguard.Utils.MyRequest;
import cn.com.parkguard.bean.MyLoginBean;
import cn.com.parkguard.interfaces.LoginInterface;



/**
 * 文件名：LoginActivity
 * 描    述：登录类
 * 作    者：stt
 * 时    间：2017.01.04
 * 版    本：V1.0.0
 */

public class LoginActivity extends BaseActivity implements View.OnClickListener, LoginInterface {
    private Context context;
    private EditText inputUsername, inputPassword;//用户名，密码
    private String username, password;
    private Button loginBtn;
    private TextView forgetPassword;
    private ImageButton pwdVisible,pwdDelete;
    private Boolean pwdIsVisible=false;
    @Override
    protected void setView() {
        setContentView(R.layout.activity_login);
        context = this;
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
        pwdVisible=(ImageButton)findViewById(R.id.password_visible);
        pwdDelete=(ImageButton)findViewById(R.id.password_delete);
        pwdVisible.setOnClickListener(this);
        pwdDelete.setOnClickListener(this);
        pwdVisible.setVisibility(View.GONE);
        pwdDelete.setVisibility(View.GONE);
        inputPassword.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                pwdVisible.setVisibility(View.VISIBLE);
                pwdDelete.setVisibility(View.VISIBLE);
                if(s.length()==0)
                {
                    pwdVisible.setVisibility(View.GONE);
                    pwdDelete.setVisibility(View.GONE);
                    hidePassword();
                }
            }
        });
    }

    @Override
    public void onNetChange(int netMobile) {
        super.onNetChange(netMobile);
        MyTitle.getInstance().setNetText(this, netMobile);
    }

    @Override
    public void onClick(View v) {

        switch (v.getId()) {
            case R.id.login_button:
                username = inputUsername.getText().toString().trim();
                password = inputPassword.getText().toString().trim();
                if (isEmpt()) {
                    MyRequest.loginRequest(this, username, password);
                }
                break;
            case R.id.login_forget_password:
                Intent intent = new Intent(this, ForgetPasswordActivity.class);
                startActivity(intent);
                break;
            case R.id.password_visible:
                if(pwdIsVisible)
                {
                    hidePassword();
                }
                else
                {
                    showPassword();
                }
                break;
            case R.id.password_delete:
                inputPassword.setText("");
                pwdVisible.setVisibility(View.GONE);
                pwdDelete.setVisibility(View.GONE);
                if(pwdIsVisible)
                {
                    hidePassword();
                }
                break;
        }
    }

    private boolean isEmpt() {
        boolean flag = false;
        if (TextUtils.isEmpty(username)) {
            ToastUtil.show(context, "请输入用户名");
            flag = false;
        } else if (TextUtils.isEmpty(password)) {
            ToastUtil.show(context, "请输入密码");
            flag = false;
        } else {
            flag = true;
        }
        return flag;
    }
    public void hidePassword() {
        inputPassword.setInputType(InputType.TYPE_CLASS_TEXT|InputType.TYPE_TEXT_VARIATION_PASSWORD);
        pwdVisible.setBackgroundResource(R.drawable.open);
        inputPassword.setSelection(inputPassword.getText().toString().trim().length());
        pwdIsVisible=false;
    }
    public void showPassword(){
        inputPassword.setInputType(InputType.TYPE_TEXT_VARIATION_VISIBLE_PASSWORD);
        pwdVisible.setBackgroundResource(R.drawable.close);
        inputPassword.setSelection(inputPassword.getText().toString().trim().length());
        pwdIsVisible=true;
    }
    @Override
    public void login(final MyLoginBean userBean) {
        if (userBean.getPersonId() == 0) {
            ToastUtil.show(this, "用户名或者密码不对，请重新输入");
            return;
        }

        SharedUtil.setString(this, "PersonID", userBean.getPersonId() + "");
        SharedUtil.setBoolean(this, "isSuccess", true);
//        CloudPushService pushService = PushServiceFactory.getCloudPushService();
//        pushService.bindAccount(userBean.getPersonId() + "", new CommonCallback() {
//            @Override
//            public void onSuccess(String s) {
//                Log.i("loginInitUserName", "bind account success");
//            }
//
//            @Override
//            public void onFailed(String errorCode, String errorMessage) {
//                Log.i("loginInitUserNameError", "bind account fail" + "err:" + errorCode + " - message:" + errorMessage);
//                initPersonIdAli();
//            }
//        });

        if (0 == userBean.getUserType()) {
            Intent intent = new Intent(this, EditorUserActivity.class);
            intent.putExtra("username", username);
            startActivity(intent);
            this.finish();
        }
        if (1 == userBean.getUserType()) {
            SharedUtil.setBoolean(this, "isLogin", true);
            Intent intent = new Intent(this, HomeActivity.class);
            startActivity(intent);
            this.finish();
        }
    }

    private void initPersonIdAli() {
        while (SharedUtil.getBoolean(this, "isSuccess", false)) {
            CloudPushService pushService = PushServiceFactory.getCloudPushService();
            pushService.bindAccount(SharedUtil.getString(this, "personId"), new CommonCallback() {
                @Override
                public void onSuccess(String s) {
                    SharedUtil.setBoolean(LoginActivity.this, "isSuccess", false);
                    Log.i("InitPersonId", "bind account success");
                }

                @Override
                public void onFailed(String errorCode, String errorMessage) {
                    Log.i("InitPersonIdError", "bind account fail" + "err:" + errorCode + " - message:" + errorMessage);
                }
            });
        }
    }

}
