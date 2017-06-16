package cn.com.watchman.chatui;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.linked.erfli.library.base.BaseActivity;

import cn.com.watchman.R;

/**
 * zzq
 * 2017年6月15日13:41:35
 * 巡更事件上报详情页面
 */

public class WarningDetailsActivity extends BaseActivity implements View.OnClickListener {
    private LinearLayout title_back;
    private TextView title_name;
    private String warningId;


    //=========
    private TextView chat_war_details_tv_number_value;//编号文本

    //===========

    @Override
    protected void setView() {
        setContentView(R.layout.activity_chat_warning_details);
    }

    @Override
    protected void setDate(Bundle savedInstanceState) {
        Intent intent = getIntent();
        warningId = intent.getStringExtra("id");
    }

    @Override
    protected void init() {
        title_back = (LinearLayout) findViewById(R.id.title_back);
        title_name = (TextView) findViewById(R.id.title_name);
        title_back.setVisibility(View.VISIBLE);
        title_name.setText("问题详情");
        title_back.setOnClickListener(this);
        //======================
        chat_war_details_tv_number_value = (TextView) findViewById(R.id.chat_war_details_tv_number_value);
        chat_war_details_tv_number_value.setText(warningId);
        //===========
    }

    @Override
    public void onClick(View v) {
        int i = v.getId();
        if (i == R.id.title_back) {
            PGApp.finishTop();
        }
    }
}
