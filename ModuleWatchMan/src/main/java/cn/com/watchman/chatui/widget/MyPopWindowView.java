package cn.com.watchman.chatui.widget;

import android.app.Activity;
import android.content.Context;
import android.graphics.drawable.BitmapDrawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.WindowManager;
import android.widget.PopupWindow;
import android.widget.TextView;

import cn.com.watchman.R;


/**
 * Created by 志强 on 2017.6.7.
 */

public class MyPopWindowView extends PopupWindow {
    //根试图
    private View mRootView;
    LayoutInflater mInflater;
    private Activity mActivity;

    public MyPopWindowView(Activity context, int width, int height, String title) {
        super(context);
        this.mActivity = context;
        mInflater = (LayoutInflater) context
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        mRootView = mInflater.inflate(R.layout.pop_layout, null);
        TextView tv = (TextView) mRootView.findViewById(R.id.tv_pop_title_TextView);
        tv.setText(title);
        setContentView(mRootView);
        this.setWidth(width);
        this.setHeight(height);
        // 设置PopUpWindow弹出的相关属性
        setTouchable(true);
        setOutsideTouchable(true);
        setFocusable(true);
        setBackgroundDrawable(new BitmapDrawable(context.getResources()));
        backgroundAlpha(1f);
        update();
        getContentView().setFocusableInTouchMode(true);
        getContentView().setFocusable(true);
        setAnimationStyle(R.style.AppTheme);
    }

    public void backgroundAlpha(float bgAlpha) {
        WindowManager.LayoutParams lp = mActivity.getWindow().getAttributes();
        lp.alpha = bgAlpha; //0.0-1.0
        mActivity.getWindow().setAttributes(lp);
    }
}
