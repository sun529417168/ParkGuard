package cn.com.parkguard.activity;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.KeyEvent;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.linked.erfli.library.base.BaseActivity;
import com.linked.erfli.library.utils.SharedUtil;
import com.linked.erfli.library.utils.ToastUtil;

import cn.com.parkguard.R;
import cn.com.parkguard.Utils.DialogUtils;
import cn.com.parkguard.Utils.MyRequest;
import cn.com.parkguard.bean.PersonBean;
import cn.com.parkguard.interfaces.PersonInfoInterface;
import cn.com.parkguard.interfaces.SexChooseInterface;


/**
 * 文件名：EditorUserActivity
 * 描    述：完善自己资料的类
 * 作    者：stt
 * 时    间：2017.01.04
 * 版    本：V1.0.0
 */

public class EditorUserActivity extends BaseActivity implements View.OnClickListener, SexChooseInterface, PersonInfoInterface {
    private Context context;
    private TextView titleName;
    /**
     * 手机号，旧密码，新密码，姓名，身份证号
     */
    private EditText inputPhone, inputOldPassword, inputNewPassword, inputNewPasswordAgain, inputName, inputUserID;
    private String phone, oldPasword, newPasswrod, newPasswrodAgain, name, userID, sex;
    /**
     * 用户名，工号
     */
    private TextView usernameText, workNumberText;
    private Button btnSure;
    private RelativeLayout showSexLayout;
    private Dialog showSexDialog;
    private TextView sexText;
    private String username;

    @Override
    protected void setView() {
        setContentView(R.layout.activity_editor_user);
    }

    @Override
    protected void setDate(Bundle savedInstanceState) {
        username = getIntent().getStringExtra("username");
        context = this;
        MyRequest.personInfoRequest(this, SharedUtil.getString(this, "PersonID"));
    }

    @Override
    protected void init() {
        titleName = (TextView) findViewById(R.id.title_name);
        titleName.setText("完善资料");
        inputPhone = (EditText) findViewById(R.id.editor_phone);
        inputOldPassword = (EditText) findViewById(R.id.editor_oldPassword);
        inputNewPassword = (EditText) findViewById(R.id.editor_newPassword);
        inputNewPasswordAgain = (EditText) findViewById(R.id.editor_again_newPassword);
        inputName = (EditText) findViewById(R.id.editor_name);
        inputUserID = (EditText) findViewById(R.id.editor_userID);
        btnSure = (Button) findViewById(R.id.sure_button);
        btnSure.setOnClickListener(this);
        showSexLayout = (RelativeLayout) findViewById(R.id.editor_user_sex_layout);
        showSexLayout.setOnClickListener(this);
        sexText = (TextView) findViewById(R.id.editor_sex);
        usernameText = (TextView) findViewById(R.id.editor_username);
        usernameText.setText(username);
        workNumberText = (TextView) findViewById(R.id.editor_workNumber);
        inputOldPassword.addTextChangedListener(watcher);
        inputOldPassword.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (!hasFocus) {
                    MyRequest.isCheckPassWordRequest(EditorUserActivity.this, oldPasword);
                }
            }
        });
    }

    private TextWatcher watcher = new TextWatcher() {

        @Override
        public void onTextChanged(CharSequence s, int start, int before, int count) {
            // TODO Auto-generated method stub
            oldPasword = s.toString();
        }

        @Override
        public void beforeTextChanged(CharSequence s, int start, int count,
                                      int after) {
            // TODO Auto-generated method stub
        }

        @Override
        public void afterTextChanged(Editable s) {
            // TODO Auto-generated method stub
        }
    };

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.sure_button:
                name = inputName.getText().toString().trim();
                sex = sexText.getText().toString().trim();
                userID = inputUserID.getText().toString().trim();
                phone = inputPhone.getText().toString().trim();
                oldPasword = inputOldPassword.getText().toString().trim();
                newPasswrod = inputNewPassword.getText().toString().trim();
                newPasswrodAgain = inputNewPasswordAgain.getText().toString().trim();
                if (isEmpty(name, sex, phone, oldPasword, newPasswrod, newPasswrodAgain)) {
                    MyRequest.uodatePersonInfoRequest(this, name, sex, userID, phone, oldPasword, newPasswrod);
                }
                break;
            case R.id.editor_user_sex_layout:
                showSexDialog = DialogUtils.showSexDialog(this);
                break;
        }
    }

    @Override
    public void getSex(int index) {
        switch (index) {
            case 0:
                sexText.setText("男");
                if (showSexDialog.isShowing()) {
                    showSexDialog.dismiss();
                }
                break;
            case 1:
                sexText.setText("女");
                if (showSexDialog.isShowing()) {
                    showSexDialog.dismiss();
                }
                break;
            case 2:
                if (showSexDialog.isShowing()) {
                    showSexDialog.dismiss();
                }
                break;
        }
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            DialogUtils.closeActivity(this);
            return false;
        }
        return super.onKeyDown(keyCode, event);
    }

    private boolean isEmpty(String... strings) {
        if (TextUtils.isEmpty(strings[0])) {
            ToastUtil.show(context, "请输入姓名");
            return false;
        } else if (TextUtils.isEmpty(strings[1])) {
            ToastUtil.show(context, "请选择性别");
            return false;
        } else if (TextUtils.isEmpty(strings[2])) {
            ToastUtil.show(context, "请输入手机号");
            return false;
        } else if (TextUtils.isEmpty(strings[3])) {
            ToastUtil.show(context, "请输入旧密码");
            return false;
        } else if (TextUtils.isEmpty(strings[4])) {
            ToastUtil.show(context, "请输入新密码");
            return false;
        } else if (TextUtils.isEmpty(strings[5])) {
            ToastUtil.show(context, "请再次输入新密码");
            return false;
        } else if (!strings[4].equals(strings[5])) {
            ToastUtil.show(context, "两次输入的密码不一致");
            return false;
        } else {
            return true;
        }
    }


    @Override
    public void getPersonInfo(PersonBean personBean) {
        workNumberText.setText(personBean.getWorkNO());
    }
}
