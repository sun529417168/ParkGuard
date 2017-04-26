package com.linked.erfli.library.base;


import android.app.Activity;
import android.content.Context;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.linked.erfli.library.R;
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

    public void setTitle(Activity view, String info, PGActivityUtil PGApp, boolean flag) {
        this.PGApp = PGApp;
        back = (LinearLayout) view.findViewById(R.id.title_back);
        if (flag) {
            back.setOnClickListener(this);
        } else {
            back.setVisibility(View.GONE);
        }
        titleName = (TextView) view.findViewById(R.id.title_name);
        titleName.setText(info);
    }

    @Override
    public void onClick(View v) {
        int i = v.getId();
        if (i == R.id.title_back) {
            PGApp.finishTop();
        }
    }
}
