package com.linked.erfli.library.base;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;

import com.linked.erfli.library.utils.ActivityManagerUtils;

/**
 * Created by 志强 on 2017.5.4.
 */

public abstract class PhotoAppActivity extends PhotoBaseActivity {
    //获取第一个fragment
    protected abstract PhotoBaseFragment getFirstFragment();

    //获取Intent
    protected void handleIntent(Intent intent) {

    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(getContentViewId());

        if (null != getIntent()) {
            handleIntent(getIntent());
        }
        //避免重复添加Fragment
        if (null == getSupportFragmentManager().getFragments()) {
            PhotoBaseFragment firstFragment = getFirstFragment();
            //SplashFragment
            if (null != firstFragment) {
                addFragment(firstFragment);
            }
        }

        ActivityManagerUtils.getInstance().addActivity(this);
    }

    @Override
    protected int getContentViewId() {
        return 0;
    }

    @Override
    protected int getFragmentContentId() {
        return 0;
    }

    @Override
    public void onClick(View v) {

    }
}
