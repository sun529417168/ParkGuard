package cn.com.parkguard.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.animation.Animation;
import android.view.animation.ScaleAnimation;
import android.widget.ImageView;

import com.linked.erfli.library.base.BaseActivity;
import com.linked.erfli.library.utils.SharedUtil;
import com.linked.erfli.library.utils.StatusBarUtils;

import cn.com.parkguard.R;


/**
 * 文件名：NoticeWelcomeActivity
 * 描    述：通知公告的欢迎界面
 * 作    者：stt
 * 时    间：2017.5.3
 * 版    本：V1.0.0
 */

public class WelcomeActivity extends BaseActivity {

    private ImageView splash;//欢迎图片,这里固定设置,可动态设置
    private Context mContext;
    private String userName;
    private Boolean islogin = false;

    @Override
    protected void setView() {
        setContentView(R.layout.activity_welcome);
        mContext = this;
        StatusBarUtils.ff(mContext, R.color.transparent);
    }

    /**
     * 接收上级，填充数据 第二步
     */
    @Override
    protected void setDate(Bundle savedInstanceState) {
        userName = SharedUtil.getString(mContext, "userName");//获取用户名
        islogin = SharedUtil.getBoolean(mContext, "isSuccess", true);
        Log.i("", "userName=" + userName);

    }

    /**
     * 实例化控件 第三步
     */
    @Override
    protected void init() {
        splash = (ImageView) findViewById(R.id.splash);
        //欢迎图片动态设置操作
        initView();
    }

    /**
     * 数据操作
     */
    private void initView() {
        ScaleAnimation scaleAnimation = new ScaleAnimation(1.0f, 1.1f, 1.0f, 1.1f, Animation.RELATIVE_TO_SELF, 0.5f, Animation.RELATIVE_TO_SELF, 0.5f);
        scaleAnimation.setFillAfter(true);
        scaleAnimation.setDuration(5000);
        splash.startAnimation(scaleAnimation);

        //缩放动画监听
        scaleAnimation.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {

            }

            @Override
            public void onAnimationEnd(Animation animation) {
                if (userName == null || "".equals(userName) || islogin == false) {
                    //userName为null 跳转到登录页面
                    startActivity(new Intent(mContext, LoginActivity.class));
//                    startActivity(new Intent(mContext, HomeActivity.class));
                } else {
                    //跳转到主页面
                    startActivity(new Intent(mContext, HomeActivity.class));
                }
                finish();
            }

            @Override
            public void onAnimationRepeat(Animation animation) {

            }
        });
    }
}
