package cn.com.parkguard.activity;

import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;

import com.linked.erfli.library.base.BaseActivity;
import com.linked.erfli.library.base.MyTitle;
import com.linked.erfli.library.utils.ToastUtil;

import cn.com.parkguard.R;
import cn.com.parkguard.Utils.MyRequest;


/**
 * 文件名：UpdatePasswordActivity
 * 描    述：修改密码界面
 * 作    者：stt
 * 时    间：2017.1.18
 * 版    本：V1.0.0
 */

public class UpdatePasswordActivity extends BaseActivity implements View.OnClickListener {
    private LinearLayout back;
    private EditText oldPasswordEdit, newPasswordEdit, alginNewPasswordEdit;
    private String oldPassword, newPassword, alginNewPassword;
    private Button updatePassrodBtn;

    @Override
    protected void setView() {
        setContentView(R.layout.activity_update_password);
    }

    @Override
    protected void setDate(Bundle savedInstanceState) {

    }

    @Override
    protected void init() {
        MyTitle.getInstance().setTitle(this, "修改密码", PGApp, true);
        back = (LinearLayout) findViewById(R.id.title_back);
        back.setVisibility(View.VISIBLE);
        back.setOnClickListener(this);
        oldPasswordEdit = (EditText) findViewById(R.id.update_password_oldPassword);
        newPasswordEdit = (EditText) findViewById(R.id.update_password_newPassword);
        alginNewPasswordEdit = (EditText) findViewById(R.id.update_password_again_newPassword);
        updatePassrodBtn = (Button) findViewById(R.id.update_password_button);
        updatePassrodBtn.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.title_back:
                PGApp.finishTop();
                break;
            case R.id.update_password_button:
                oldPassword = oldPasswordEdit.getText().toString().trim();
                newPassword = newPasswordEdit.getText().toString().trim();
                alginNewPassword = alginNewPasswordEdit.getText().toString().trim();
                if (isEmpty()) {
                    MyRequest.updatePassWordRequest(this, oldPassword, newPassword);
                }
                break;
        }
    }

    private boolean isEmpty() {
        if (TextUtils.isEmpty(oldPassword)) {
            ToastUtil.show(this, "请输入旧密码");
            return false;
        } else if (TextUtils.isEmpty(newPassword)) {
            ToastUtil.show(this, "请输入新密码");
            return false;
        } else if (TextUtils.isEmpty(alginNewPassword)) {
            ToastUtil.show(this, "请再次输入新密码");
            return false;
        } else if (!alginNewPassword.equals(newPassword)) {
            ToastUtil.show(this, "两次输入的密码不一致");
            return false;
        } else {
            return true;
        }
    }

}
