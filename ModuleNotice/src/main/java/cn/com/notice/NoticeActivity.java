package cn.com.notice;

import android.os.Bundle;
import android.widget.TextView;

import com.github.mzule.activityrouter.annotation.Router;
import com.linked.erfli.library.base.BaseActivity;
import com.linked.erfli.library.utils.SharedUtil;


/**
 * 文件名：NoticeActivity
 * 描    述：通知公告类
 * 作    者：stt
 * 时    间：2017.4.25
 * 版    本：V1.0.0
 */
@Router("notice_list")
public class NoticeActivity extends BaseActivity {

    @Override
    protected void setView() {
        setContentView(R.layout.activity_notice);
        ((TextView) findViewById(R.id.notice)).setText(SharedUtil.getString(this, "notice"));
    }

    @Override
    protected void setDate(Bundle savedInstanceState) {

    }

    @Override
    protected void init() {

    }
}
