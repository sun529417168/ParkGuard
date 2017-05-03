package cn.com.problem;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.animation.Animation;
import android.view.animation.ScaleAnimation;
import android.widget.ImageView;

import com.linked.erfli.library.utils.SharedUtil;

/**
 * zzq on 2017.5.2.
 */

public class ProblemWelcomeActivity extends AppCompatActivity {

    private ImageView img_Show_WelcomeImage;
    private String userName;
    private Context mContext;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.problem_activity_welcome);
        mContext = this;
        setData();
        initControl();
        initView();
    }

    private void setData() {
        userName = SharedUtil.getString(mContext, "userName");//获取用户名
    }

    private void initControl() {
        img_Show_WelcomeImage = (ImageView) findViewById(R.id.img_Show_WelcomeImage);
    }

    private void initView() {
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
                if (userName == null || "".equals(userName)) {
                    //userName为null 跳转到登录页面
                    startActivity(new Intent(mContext, ProblemLoginActivity.class));
                } else {
                    //跳转到主页面
                    startActivity(new Intent(mContext, ProblemActivity.class));
                }
                finish();
            }

            @Override
            public void onAnimationRepeat(Animation animation) {

            }
        });
    }
}
