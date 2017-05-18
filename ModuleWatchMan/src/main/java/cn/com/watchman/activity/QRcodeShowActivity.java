package cn.com.watchman.activity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;

import com.linked.erfli.library.base.BaseActivity;

import cn.com.watchman.R;

/**
 * 文件名：QRcodeShowActivity
 * 描    述：二维码扫描结果展示
 * 作    者：zzq
 * 时    间：2017.5.15
 * 版    本：V1.0.0
 */
public class QRcodeShowActivity extends BaseActivity {
    private TextView tv_value;
    private String value;

    @Override
    protected void setView() {
        setContentView(R.layout.activity_qrcode);
    }

    @Override
    protected void setDate(Bundle savedInstanceState) {
        Intent intent = getIntent();
        value = intent.getStringExtra("value");
    }

    @Override
    protected void init() {
        tv_value = (TextView) findViewById(R.id.tv_value);
        tv_value.setText(value);
    }
}
