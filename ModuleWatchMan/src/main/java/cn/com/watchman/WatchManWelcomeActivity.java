package cn.com.watchman;

import android.content.Intent;
import android.os.Bundle;
import android.view.animation.Animation;
import android.view.animation.ScaleAnimation;
import android.widget.ImageView;

import com.linked.erfli.library.base.BaseActivity;
import com.linked.erfli.library.utils.SharedUtil;

/**
 * Created by 志强 on 2017.5.3.
 */

public class WatchManWelcomeActivity extends BaseActivity {

    private WatchManWelcomeActivity mContext;
    private ImageView watchman_welcome_image;
    private String userName;

    @Override
    protected void setView() {
        setContentView(R.layout.watchman_welcome_layout);
        mContext = this;
    }

    @Override
    protected void setDate(Bundle savedInstanceState) {
        userName = SharedUtil.getString(mContext, "userName");//获取用户名

    }

    @Override
    protected void init() {
        watchman_welcome_image = (ImageView) findViewById(R.id.watchman_welcome_image);
        initView();
    }

    /**
     * 数据操作
     */
    private void initView() {
        ScaleAnimation scaleAnimation = new ScaleAnimation(1.0f, 1.1f, 1.0f, 1.1f, Animation.RELATIVE_TO_SELF, 0.5f, Animation.RELATIVE_TO_SELF, 0.5f);
        scaleAnimation.setFillAfter(true);
        scaleAnimation.setDuration(5000);
        watchman_welcome_image.startAnimation(scaleAnimation);

        //缩放动画监听
        scaleAnimation.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {

            }

            @Override
            public void onAnimationEnd(Animation animation) {
                if (userName == null || "".equals(userName)) {
                    //userName为null 跳转到登录页面
                    startActivity(new Intent(mContext, WatchManLoginActivity.class));
                } else {
                    //跳转到主页面
                    startActivity(new Intent(mContext, WatchManActivity.class));
                }
                finish();
            }

            @Override
            public void onAnimationRepeat(Animation animation) {

            }
        });
    }
}
