package cn.com.task;

import android.app.Activity;
import android.os.Bundle;
import android.widget.TextView;

import com.github.mzule.activityrouter.annotation.Router;
import com.linked.erfli.library.base.BaseActivity;
import com.linked.erfli.library.base.BaseEventActivity;
import com.linked.erfli.library.utils.SharedUtil;


/**
 * 文件名：TaskActivity
 * 描    述：任务管理类
 * 作    者：stt
 * 时    间：2017.4.25
 * 版    本：V1.0.0
 */
@Router("task_list")
public class TaskActivity extends BaseActivity {

    @Override
    protected void setView() {
        setContentView(R.layout.activity_task);
    }

    @Override
    protected void setDate(Bundle savedInstanceState) {

    }

    @Override
    protected void init() {

    }
}
