package cn.com.notice.activity;

import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.github.mzule.activityrouter.annotation.Router;
import com.linked.erfli.library.base.BaseActivity;
import com.linked.erfli.library.base.MyTitle;
import com.linked.erfli.library.refresh.PullToRefreshBase;
import com.linked.erfli.library.refresh.PullToRefreshListView;
import com.linked.erfli.library.utils.SharedUtil;
import com.linked.erfli.library.utils.ToastUtil;

import java.util.ArrayList;

import cn.com.notice.R;
import cn.com.notice.Utils.MyRequest;
import cn.com.notice.adapter.NoticeAdapter;
import cn.com.notice.bean.NoticeBean;
import cn.com.notice.interfaces.NoticeListInterface;


/**
 * 文件名：NoticeActivity
 * 描    述：通知公告类
 * 作    者：stt
 * 时    间：2017.4.25
 * 版    本：V1.0.0
 */
@Router("notice_list")
public class NoticeActivity extends BaseActivity implements View.OnClickListener,NoticeListInterface {
    private TextView titleName;//标题名称
    /**
     * 全部，一天，一个星期，一个月
     */
    //private TextView noticeAll, noticeOneDay, noticeOneWeek, noticeOneMonth;
    //private TextView[] textviews;
    private TabLayout tabLayout;
    //刷新控件
    private PullToRefreshListView mPullRefreshListView;
    private ArrayList<NoticeBean.RowsBean> noticeList = new ArrayList<>();
    private NoticeAdapter noticeAdapter;
    private RelativeLayout nothing;
    private int timeNum = 0;
    private long exitTime;//上一次按退出键时间
    private static final long TIME = 2000;//双击回退键间隔时间
    private LinearLayout title_back;

    @Override
    protected void setView() {
        setContentView(R.layout.activity_notice);
    }

    @Override
    protected void setDate(Bundle savedInstanceState) {
        MyTitle.getInstance().setTitle(this, "通知", PGApp, false);
        request(timeNum);
    }

    private void request(int searchTime) {
        MyRequest.getNoticeListRequest(this, this, searchTime);
    }

    @Override
    protected void init() {
        if (SharedUtil.getBoolean(this, "isNotice", false)) {
            title_back = (LinearLayout) findViewById(R.id.title_back);
            title_back.setVisibility(View.VISIBLE);
            title_back.setOnClickListener(this);
        }
        titleName = (TextView) findViewById(R.id.title_name);
        titleName.setText("通知公告");
        mPullRefreshListView = (PullToRefreshListView) findViewById(R.id.notice_refresh_list);
        mPullRefreshListView.setMode(PullToRefreshBase.Mode.BOTH);
        tabLayout=(TabLayout)findViewById(R.id.notice_tab_layout);
        initTablayout(tabLayout);
        /*noticeAll = (TextView) findViewById(R.id.notice_all);
        noticeOneDay = (TextView) findViewById(R.id.notice_oneDay);
        noticeOneWeek = (TextView) findViewById(R.id.notice_oneWeek);
        noticeOneMonth = (TextView) findViewById(R.id.notice_oneMonth);
        textviews = new TextView[]{noticeAll, noticeOneDay, noticeOneWeek, noticeOneMonth};*/
        nothing = (RelativeLayout) findViewById(R.id.notice_nothing);
        /*setTextBack(noticeAll);
        noticeAll.setOnClickListener(this);
        noticeOneDay.setOnClickListener(this);
        noticeOneWeek.setOnClickListener(this);
        noticeOneMonth.setOnClickListener(this);*/
        mPullRefreshListView.setOnRefreshListener(new PullToRefreshBase.OnRefreshListener2<ListView>() {
            @Override
            public void onPullDownToRefresh(PullToRefreshBase<ListView> refreshView) {
                Log.e("TAG", "onPullDownToRefresh");
                // 这里写下拉刷新的任务
                request(timeNum);
                noticeAdapter.notifyDataSetChanged();
                mPullRefreshListView.onRefreshComplete();
            }

            @Override
            public void onPullUpToRefresh(PullToRefreshBase<ListView> refreshView) {
                Log.e("TAG", "onPullUpToRefresh");
                // 这里写上拉加载更多的任务
                request(timeNum);
                noticeAdapter.notifyDataSetChanged();
                mPullRefreshListView.onRefreshComplete();
            }
        });
    }

    @Override
    public void onNetChange(int netMobile) {
        super.onNetChange(netMobile);
        MyTitle.getInstance().setNetText(this, netMobile);
    }

    /*private void setTextBack(TextView view) {
        for (int i = 0; i < textviews.length; i++) {
            if (view.getId() == textviews[i].getId()) {
                textviews[i].setTextColor(ContextCompat.getColor(this, R.color.white));
                textviews[i].setBackgroundResource(R.color.blue2);
            } else {
                textviews[i].setTextColor(ContextCompat.getColor(this, R.color.blue));
                textviews[i].setBackgroundResource(R.color.white);
            }
        }
    }*/
    private void initTablayout(TabLayout tabLayout){
        tabLayout.addTab(tabLayout.newTab().setText("全部"));
        tabLayout.addTab(tabLayout.newTab().setText("三天"));
        tabLayout.addTab(tabLayout.newTab().setText("一周"));
        tabLayout.addTab(tabLayout.newTab().setText("一月"));
        tabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                int position=tab.getPosition();
                if (position == 0) {
                    timeNum = 0;
                    request(timeNum);

                } else if (position == 1) {
                    timeNum = 1;
                    request(timeNum);

                } else if (position == 2) {
                    timeNum = 2;
                    request(timeNum);

                } else if (position == 3) {
                    timeNum = 3;
                    request(timeNum);
                }
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });
    }
    @Override
    public void onClick(View v) {
        int i = v.getId();
        if (i == R.id.title_back) {
            PGApp.finishTop();
        }
    }

    @Override
    public void getNoticeList(NoticeBean noticeBean) {
        if (noticeBean.getRows().size() == 0) {
            mPullRefreshListView.setVisibility(View.GONE);
            nothing.setVisibility(View.VISIBLE);
        } else {
            mPullRefreshListView.setVisibility(View.VISIBLE);
            nothing.setVisibility(View.GONE);
            noticeList = (ArrayList<NoticeBean.RowsBean>) noticeBean.getRows();
            noticeAdapter = new NoticeAdapter(this, noticeList);
            mPullRefreshListView.setAdapter(noticeAdapter);
            noticeAdapter.notifyDataSetChanged();
        }
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            if (SharedUtil.getBoolean(this, "isNotice", false)) {
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
