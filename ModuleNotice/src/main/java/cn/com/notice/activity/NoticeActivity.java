package cn.com.notice.activity;

import android.content.Context;
import android.os.Bundle;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.github.mzule.activityrouter.annotation.Router;
import com.linked.erfli.library.base.BaseActivity;
import com.linked.erfli.library.refresh.PullToRefreshListView;
import com.linked.erfli.library.utils.SharedUtil;

import java.util.ArrayList;

import cn.com.notice.R;
import cn.com.notice.bean.NoticeBean;


/**
 * 文件名：NoticeActivity
 * 描    述：通知公告类
 * 作    者：stt
 * 时    间：2017.4.25
 * 版    本：V1.0.0
 */
@Router("notice_list")
public class NoticeActivity extends BaseActivity {
    private Context context;
    private TextView titleName;//标题名称
    /**
     * 全部，一天，一个星期，一个月
     */
    private TextView noticeAll, noticeOneDay, noticeOneWeek, noticeOneMonth;
    private TextView[] textviews;
    //刷新控件
    private PullToRefreshListView mPullRefreshListView;
    private ArrayList<NoticeBean.RowsBean> noticeList = new ArrayList<>();
    private NoticeAdapter noticeAdapter;
    private RelativeLayout nothing;
    private int timeNum = 0;

    @Override
    protected void setView() {
        setContentView(R.layout.activity_notice);
    }

    @Override
    protected void setDate(Bundle savedInstanceState) {

    }

    @Override
    protected void init() {

    }
}
