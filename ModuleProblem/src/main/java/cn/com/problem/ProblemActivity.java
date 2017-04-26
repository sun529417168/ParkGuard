package cn.com.problem;

import android.os.Bundle;

import com.github.mzule.activityrouter.annotation.Router;
import com.linked.erfli.library.base.BaseActivity;
import com.linked.erfli.library.base.BaseEventActivity;


/**
 * 文件名：ProblemActivity
 * 描    述：问题管理类
 * 作    者：stt
 * 时    间：2017.4.25
 * 版    本：V1.0.0
 */
@Router("problem_list")
public class ProblemActivity extends BaseActivity {

    @Override
    protected void setView() {
        setContentView(R.layout.activity_problem);
    }

    @Override
    protected void setDate(Bundle savedInstanceState) {

    }

    @Override
    protected void init() {

    }
}
