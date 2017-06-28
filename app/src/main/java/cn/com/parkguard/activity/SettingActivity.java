package cn.com.parkguard.activity;

import com.linked.erfli.library.base.BaseActivity;
import com.linked.erfli.library.base.MyTitle;
import com.linked.erfli.library.utils.DataCleanManager;
import com.linked.erfli.library.utils.MyUtils;
import com.linked.erfli.library.utils.SharedUtil;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.RelativeLayout;
import android.widget.TextView;

import cn.com.parkguard.R;
import cn.com.parkguard.Utils.DialogUtils;
import cn.com.parkguard.Utils.MyRequest;
import cn.com.parkguard.bean.PersonBean;
import cn.com.parkguard.interfaces.PersonInfoInterface;

/**
 * 文件名：SettingActivity
 * 描    述：设置界面
 * 作    者：stt
 * 时    间：2017.06.26
 * 版    本：V1.0.0
 */
public class SettingActivity extends BaseActivity implements View.OnClickListener, PersonInfoInterface {
    private RelativeLayout updatePassword;
    /**
     * 姓名，手机号，身份证号
     */
    private TextView nameText, phoneText, userIDText;
    private TextView userNameText, workNoText;
    private RelativeLayout clearCacheLayout;
    private TextView cacheSize, version;
    private Button exitBtn;

    @Override
    protected void setView() {
        setContentView(R.layout.activity_setting);
    }

    @Override
    protected void setDate(Bundle savedInstanceState) {
        MyRequest.personInfoRequest(this, SharedUtil.getString(this, "PersonID"));
    }

    @Override
    protected void init() {
        MyTitle.getInstance().setTitle(this, "设置", PGApp, true);
        userNameText = (TextView) findViewById(R.id.mine_titleUsername);
        workNoText = (TextView) findViewById(R.id.mine_titleWorkNo);

        nameText = (TextView) findViewById(R.id.mine_name);
        phoneText = (TextView) findViewById(R.id.mine_phone);
        userIDText = (TextView) findViewById(R.id.mine_userID);

        updatePassword = (RelativeLayout) findViewById(R.id.mine_updatePassword);
        updatePassword.setOnClickListener(this);
        clearCacheLayout = (RelativeLayout) findViewById(R.id.mine_cache_Layout);
        clearCacheLayout.setOnClickListener(this);
        cacheSize = (TextView) findViewById(R.id.mine_cacheSize);
        try {
            cacheSize.setText(DataCleanManager.getTotalCacheSize(this));
        } catch (Exception e) {
            e.printStackTrace();
        }
        exitBtn = (Button) findViewById(R.id.exit_button);
        exitBtn.setOnClickListener(this);
        version = (TextView) findViewById(R.id.mine_version);
        version.setText(MyUtils.getAppVersionName(this));
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.mine_updatePassword:
                Intent intent = new Intent(this, UpdatePasswordActivity.class);
                startActivity(intent);
                break;
            case R.id.mine_cache_Layout:
                DialogUtils.clearData(this, cacheSize);
                break;
            case R.id.exit_button:
                DialogUtils.exit(PGApp, this);
                break;
        }
    }

    @Override
    public void getPersonInfo(PersonBean personBean) {
        userNameText.setText("用户名:" + SharedUtil.getString(this, "userName"));
        workNoText.setText("工号:" + personBean.getWorkNO());
        nameText.setText(personBean.getName());
        phoneText.setText(personBean.getPhone());
        userIDText.setText(personBean.getIDCard());
    }
}
