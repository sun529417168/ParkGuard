package cn.com.watchman.chatui;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.GridView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.linked.erfli.library.base.BaseActivity;
import com.linked.erfli.library.utils.DeviceUuidFactory;
import com.linked.erfli.library.utils.SharedUtil;

import java.util.ArrayList;

import cn.com.watchman.R;
import cn.com.watchman.chatui.adapter.ChatWarningDetailsImageGridviewAdapter;
import cn.com.watchman.chatui.enity.WarningDetailsInfo;
import cn.com.watchman.chatui.interfaces.ChatWarningDetailsInterface;
import cn.com.watchman.chatui.uiutils.MyGridViewHeightUtiils;
import cn.com.watchman.networkrequest.WatchManRequest;

/**
 * zzq
 * 2017年6月15日13:41:35
 * 巡更事件上报详情页面
 */

public class WarningDetailsActivity extends BaseActivity implements View.OnClickListener, ChatWarningDetailsInterface {
    private LinearLayout title_back;
    private TextView title_name;
    private int warningId;


    //=========
    private TextView chat_war_details_tv_number_value;//编号文本
    private TextView chat_war_details_tv_reprottime_value;//时间
    private TextView chat_war_details_tv_describe_value;//备注信息
    private GridView problem_detail_gridView;//图片
    //===========
    private ChatWarningDetailsImageGridviewAdapter adapter;

    @Override
    protected void setView() {
        setContentView(R.layout.activity_chat_warning_details);
    }

    @Override
    protected void setDate(Bundle savedInstanceState) {
        Intent intent = getIntent();
        warningId = Integer.parseInt(intent.getStringExtra("id"));
        Toast.makeText(this, "" + warningId, Toast.LENGTH_SHORT).show();
        String deviceUuid = new DeviceUuidFactory(WarningDetailsActivity.this).getDeviceUuid().toString();
        int userId = Integer.parseInt(SharedUtil.getString(WarningDetailsActivity.this, "PersonID"));
        WatchManRequest.getWarningDetails(WarningDetailsActivity.this, 10, 12, deviceUuid, userId, warningId);
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
        chat_war_details_tv_reprottime_value = (TextView) findViewById(R.id.chat_war_details_tv_reprottime_value);
        chat_war_details_tv_describe_value = (TextView) findViewById(R.id.chat_war_details_tv_describe_value);
        problem_detail_gridView = (GridView) findViewById(R.id.problem_detail_gridView);
        //===========
    }

    @Override
    public void onClick(View v) {
        int i = v.getId();
        if (i == R.id.title_back) {
            PGApp.finishTop();
        }
    }

    private ArrayList<String> list;

    @Override
    public void getWarningDetailsInterface(WarningDetailsInfo warningDetailsInfo) {
        list = new ArrayList();
        if (warningDetailsInfo.getFile().size() > 0) {
            chat_war_details_tv_number_value.setText(warningDetailsInfo.getAlarm().getDeviceguid());
            String time = warningDetailsInfo.getAlarm().getAlarm_time();
            time = time.replace("T", " ");
            chat_war_details_tv_reprottime_value.setText(time);
            chat_war_details_tv_describe_value.setText(warningDetailsInfo.getAlarm().getAlarm_text());
            int fileSize = warningDetailsInfo.getFile().size();
            for (int i = 0; i < fileSize; i++) {
                list.add(warningDetailsInfo.getFile().get(i).getFile_address());
            }
            adapter = new ChatWarningDetailsImageGridviewAdapter(WarningDetailsActivity.this, list);
            problem_detail_gridView.setAdapter(adapter);
            MyGridViewHeightUtiils.setListViewHeightBasedOnChildren(problem_detail_gridView);
            adapter.notifyDataSetChanged();
        } else {
            Toast.makeText(this, "数据查询有误~", Toast.LENGTH_SHORT).show();
        }

    }
}
