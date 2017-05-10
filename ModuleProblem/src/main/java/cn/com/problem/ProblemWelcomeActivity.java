package cn.com.problem;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.animation.Animation;
import android.view.animation.ScaleAnimation;
import android.widget.ImageView;

import com.linked.erfli.library.base.BaseActivity;
import com.linked.erfli.library.utils.SharedUtil;
import com.linked.erfli.library.utils.StatusBarUtils;

/**
 * zzq on 2017.5.2.
 */

public class ProblemWelcomeActivity extends BaseActivity {

    private ImageView img_Show_WelcomeImage;
    private boolean isLogin;
    private Context mContext;


    @Override
    protected void setView() {
        setContentView(R.layout.problem_activity_welcome);
    }

    @Override
    protected void setDate(Bundle savedInstanceState) {
        mContext = this;
        isLogin = SharedUtil.getBoolean(this, "isLogin", false);
        StatusBarUtils.ff(mContext, R.color.transparent);
    }

    @Override
    protected void init() {
        img_Show_WelcomeImage = (ImageView) findViewById(R.id.img_Show_WelcomeImage);
        ScaleAnimation scaleAnimation = new ScaleAnimation(1.0f, 1.1f, 1.0f, 1.1f, Animation.RELATIVE_TO_SELF, 0.5f, Animation.RELATIVE_TO_SELF, 0.5f);
        scaleAnimation.setFillAfter(true);
        scaleAnimation.setDuration(5000);
        img_Show_WelcomeImage.startAnimation(scaleAnimation);

        //缩放动画监听
        scaleAnimation.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {

            }

            @Override
            public void onAnimationEnd(Animation animation) {
                if (isLogin) {
                    //跳转到主页面
                    startActivity(new Intent(mContext, ProblemActivity.class));
                } else {
                    //userName为null 跳转到登录页面
                    startActivity(new Intent(mContext, ProblemLoginActivity.class));
                }
                finish();
            }

            @Override
            public void onAnimationRepeat(Animation animation) {

            }
        });
    }

}
