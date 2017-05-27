package cn.com.task;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.KeyEvent;

import com.github.mzule.activityrouter.annotation.Router;
import com.linked.erfli.library.base.BaseFragmentActivity;
import com.linked.erfli.library.utils.SharedUtil;
import com.linked.erfli.library.utils.StatusBarUtils;
import com.linked.erfli.library.utils.ToastUtil;

import java.util.ArrayList;
import java.util.List;

import cn.com.task.fragment.TaskFragment;


/**
 * 文件名：TaskActivity
 * 描    述：任务管理类 主页面
 * 作    者：stt
 * 时    间：2017.4.25
 * 版    本：V1.0.0
 */
@Router("task_list")
public class TaskActivity extends BaseFragmentActivity {

    private Context mContext;
    private ViewPager activity_main_viewpager;
    private static List<Fragment> fragmentList = new ArrayList<Fragment>();
    private FragmentPagerAdapter fragmentPagerAdapter;
    private long exitTime;//上一次按退出键时间
    private static final long TIME = 2000;//双击回退键间隔时间

    @Override
    public void setView() {
        setContentView(R.layout.activity_task);
        mContext = this;
        StatusBarUtils.ff(mContext, R.color.task_blue);
    }

    @Override
    public void setData(Bundle savedInstanceState) {

    }

    @Override
    public void init() {
        activity_main_viewpager = (ViewPager) findViewById(R.id.activity_main_viewpager);
        fragmentList.add(new TaskFragment());

        fragmentPagerAdapter = new FragmentPagerAdapter(
                getSupportFragmentManager()) {

            @Override
            public int getCount() {
                // TODO Auto-generated method stub
                return fragmentList.size();
            }

            @Override
            public Fragment getItem(int position) {
                // TODO Auto-generated method stub
                return fragmentList.get(position);
            }
        };
        activity_main_viewpager.setAdapter(fragmentPagerAdapter);
        activity_main_viewpager.setCurrentItem(0);
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            if (SharedUtil.getBoolean(this, "isTask", false)) {
                PGApp.finishTop();
                return true;
            } else {
                if ((System.currentTimeMillis() - exitTime) > TIME) {
                    ToastUtil.show(this, "再按一次返回键退出");
                    exitTime = System.currentTimeMillis();
                    return true;
                } else {
                    PGApp.exit();
                }
            }
            return false;
        }
        return super.onKeyDown(keyCode, event);
    }

}
