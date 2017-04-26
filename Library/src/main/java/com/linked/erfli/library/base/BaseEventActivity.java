package com.linked.erfli.library.base;

import android.app.Activity;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;


import com.linked.erfli.library.utils.EventPool;

import org.greenrobot.eventbus.EventBus;

/**
 * Created by erfli on 11/7/16.
 */

public abstract class BaseEventActivity extends Activity {
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EventBus.getDefault().post(new EventPool.ActivityNotify(this.getPackageName() + "/" + this.getClass().getSimpleName()));
    }
}
