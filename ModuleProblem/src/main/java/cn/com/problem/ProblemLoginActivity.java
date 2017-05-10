package cn.com.problem;

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

import cn.com.problem.bean.UserBean;
import cn.com.problem.interfaces.ProblemLoginInterface;
import cn.com.problem.networkrequest.ProblemRequest;

/**
 * Created by 志强 on 2017.5.2.
 */

public class ProblemLoginActivity extends BaseActivity implements View.OnClickListener, ProblemLoginInterface {
    private ProblemLoginActivity mContext;
    private Button problem_login_button;//登陆按钮
    private TextView problem_login_forget_password;//忘记密码
    private EditText problem_login_username;//用户名
    private EditText problem_login_password;//密码
    private String userName, passWord;

    @Override
    protected void setView() {
        setContentView(R.layout.problem_activity_login);
        mContext = this;
    }

    @Override
    protected void setDate(Bundle savedInstanceState) {
        MyTitle.getInstance().setTitle(this, "登陆", PGApp, false);
    }

    @Override
    protected void init() {
        problem_login_button = (Button) findViewById(R.id.problem_login_button);
        problem_login_forget_password = (TextView) findViewById(R.id.problem_login_forget_password);
        problem_login_username = (EditText) findViewById(R.id.problem_login_username);
        problem_login_password = (EditText) findViewById(R.id.problem_login_password);
        problem_login_button.setOnClickListener(this);
        problem_login_forget_password.setOnClickListener(this);
    }

    @Override
    public void onNetChange(int netMobile) {
        super.onNetChange(netMobile);
        MyTitle.getInstance().setNetText(this, netMobile);
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

    @Override
    public void onClick(View v) {
        int i = v.getId();
        if (i == R.id.problem_login_button) {
            userName = problem_login_username.getText().toString().trim();
            passWord = problem_login_password.getText().toString().trim();
            if (isEmpt()) {
                ProblemRequest.loginRequest(mContext, userName, passWord);
            }

        } else if (i == R.id.problem_login_forget_password) {
        }
    }

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
            Intent intent = new Intent(this, ProblemActivity.class);
            startActivity(intent);
            this.finish();
        }
    }
}
