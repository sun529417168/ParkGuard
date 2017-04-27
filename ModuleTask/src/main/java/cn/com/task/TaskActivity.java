package cn.com.task;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;

import com.github.mzule.activityrouter.annotation.Router;
import com.linked.erfli.library.base.BaseFragmentActivity;

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

    @Override
    public void setView() {
        setContentView(R.layout.activity_task);
        mContext = this;
//        StatusBarUtil.setColor(TaskActivity.this, ContextCompat.getColor(mContext, R.color.blue));
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
}
