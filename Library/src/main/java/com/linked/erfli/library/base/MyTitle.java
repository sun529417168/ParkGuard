package com.linked.erfli.library.base;


import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.provider.Settings;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.linked.erfli.library.R;
import com.linked.erfli.library.utils.NetWorkUtils;
import com.linked.erfli.library.utils.PGActivityUtil;

/**
 * 文件名：MyTitle
 * 描    述：title的公共使用
 * 作    者：stt
 * 时    间：2017.4.26
 * 版    本：V1.0.0
 */

public class MyTitle implements View.OnClickListener {
    private static MyTitle title;
    private LinearLayout back;
    private TextView titleName;
    private PGActivityUtil PGApp;
    private TextView netText;
    private Context context;

    /**
     * 单例模式中获取唯一的BYApplication实例
     *
     * @return
     */
    public static MyTitle getInstance() {
        if (null == title) {
            title = new MyTitle();
        }
        return title;
    }

    public void setTitle(BaseActivity view, String info, PGActivityUtil PGApp, boolean flag) {
        this.PGApp = PGApp;
        this.context = view;
        back = (LinearLayout) view.findViewById(R.id.title_back);
        if (flag) {
            back.setOnClickListener(this);
        } else {
            back.setVisibility(View.GONE);
        }
        titleName = (TextView) view.findViewById(R.id.title_name);
        titleName.setText(info);
        netText = (TextView) view.findViewById(R.id.title_net);
        boolean netConnect = view.isNetConnect();
        if (netConnect) {
            netText.setVisibility(View.GONE);
        } else {
            netText.setVisibility(View.VISIBLE);
        }
    }

    public void setNetText(BaseActivity view, int netMobile) {
        this.context = view;
        netText = (TextView) view.findViewById(R.id.title_net);
        netText.setOnClickListener(this);
        //网络状态变化时的操作
        if (netMobile == NetWorkUtils.NETWORK_NONE) {
            netText.setVisibility(View.VISIBLE);
        } else {
            netText.setVisibility(View.GONE);
        }
    }

    @Override
    public void onClick(View v) {
        int i = v.getId();
        if (i == R.id.title_back) {
            PGApp.finishTop();
        }
        if (i == R.id.title_net) {
            Intent intent = new Intent(Settings.ACTION_AIRPLANE_MODE_SETTINGS);
            context.startActivity(intent);
        }
    }
}
