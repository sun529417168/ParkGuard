package com.linked.erfli.library.base;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.view.KeyEvent;
import android.view.Window;
import android.view.WindowManager;

import com.linked.erfli.library.okhttps.OkHttpUtils;
import com.linked.erfli.library.service.NetBroadcastReceiver;
import com.linked.erfli.library.utils.NetWorkUtils;
import com.linked.erfli.library.utils.PGActivityUtil;

import java.util.List;

import static com.linked.erfli.library.utils.StatusBarUtils.mContext;


/**
 * 文件名：BaseFragmentActivity
 * 描    述：FragmentActivity的基类，一些共同的方法写在这
 * 作    者：stt
 * 时    间：2016.12.30
 * 版    本：V1.0.0
 */
public abstract class BaseFragmentActivity extends FragmentActivity implements NetBroadcastReceiver.NetEvevt {
    public PGActivityUtil PGApp;
    private static final String TAG = "BaseActivity";
    public static NetBroadcastReceiver.NetEvevt evevt;
    /**
     * 网络类型
     */
    private int netMobile;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        // TODO Auto-generated method stub
        super.onCreate(savedInstanceState);
        // 没有标题栏
//        StatusBarUtil.setColor(TaskActivity.this, ContextCompat.getColor(mContext, R.color.blue));
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_PAN);
        PGApp = PGActivityUtil.getInstance();
        PGApp.addActivity(this);
        evevt = this;
        inspectNet();
        setView();
        setData(savedInstanceState);
        init();
    }

    /**
     * 初始化时判断有没有网络
     */

    public boolean inspectNet() {
        this.netMobile = NetWorkUtils.getNetWorkState(BaseFragmentActivity.this);
        return isNetConnect();
    }

    /**
     * 方法名：getNetStart
     * 功    能：网络判断方法
     * 参    数：
     * 返回值：无
     */
    @Override
    public void onNetChange(int netMobile) {
        this.netMobile = netMobile;
        MyTitle.getInstance().setNetText((BaseActivity) mContext, netMobile);
        isNetConnect();
    }

    /**
     * 判断有无网络 。
     *
     * @return true 有网, false 没有网络.
     */
    public boolean isNetConnect() {
        if (netMobile == NetWorkUtils.NETWORK_WIFI) {
            return true;
        } else if (netMobile == NetWorkUtils.NETWORK_MOBILE) {
            return true;
        } else if (netMobile == NetWorkUtils.NETWORK_NONE) {
            return false;

        }
        return false;
    }

    /**
     * 设置布局
     */
    public abstract void setView();

    /**
     * 设置布局数据
     */
    public abstract void setData(Bundle savedInstanceState);

    /**
     * 控件实例化
     */
    public abstract void init();


    /**
     * 返回键监听
     */
    public boolean onKeyDown(int keyCode, KeyEvent event) {

        if (keyCode == KeyEvent.KEYCODE_BACK && event.getRepeatCount() == 0) {
            PGApp.finishTop();
            return true;
        }
        return false;
    }

    /**
     * 递归调用，对所有子Fragement生效
     *
     * @param frag
     * @param requestCode
     * @param resultCode
     * @param data
     */
    private void handleResult(Fragment frag, int requestCode, int resultCode,
                              Intent data) {
        frag.onActivityResult(requestCode & 0xffff, resultCode, data);
        List<Fragment> frags = frag.getChildFragmentManager().getFragments();
        if (frags != null) {
            for (Fragment f : frags) {
                if (f != null)
                    handleResult(f, requestCode, resultCode, data);
            }
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        evevt = this;
        inspectNet();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        OkHttpUtils.getInstance().cancelTag(this);
    }
}
