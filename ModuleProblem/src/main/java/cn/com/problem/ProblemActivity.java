package cn.com.problem;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.ViewTreeObserver;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.PopupWindow;
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
import java.util.List;

import cn.com.problem.adapter.ProblemAdapter;
import cn.com.problem.bean.ProblemBean;
import cn.com.problem.bean.ProblemTypeLeft;
import cn.com.problem.bean.ProblemTypeRight;
import cn.com.problem.interfaces.ProblemListInterface;
import cn.com.problem.interfaces.ProblemTypeInterface;
import cn.com.problem.interfaces.ProblemTypeLeftInterface;
import cn.com.problem.interfaces.ProblemTypeRightInterface;
import cn.com.problem.networkrequest.ProblemRequest;
import cn.com.problem.utils.PopWindowUtils;


/**
 * 文件名：ProblemActivity
 * 描    述：问题管理类
 * 作    者：stt
 * 时    间：2017.4.25
 * 版    本：V1.0.0
 */
@Router("problem_list")
public class ProblemActivity extends BaseActivity implements View.OnClickListener,
        ProblemTypeInterface,
        ProblemListInterface,
        ProblemTypeLeftInterface,
        ProblemTypeRightInterface {

    //刷新控件
    private PullToRefreshListView mPullRefreshListView;
    private ProblemBean problemBean = new ProblemBean();
    private ProblemAdapter problemAdapter;
    private LinearLayout addProblem;
    /**
     * pop的类型，时间，状态
     */
    private LinearLayout typeLayout, timeLayout, stateLayout;
    private TextView typeText, timeText, stateText;
    private ImageView typeImage, timeImage, stateImage;
    private PopupWindow popupWindow;
    private int state = 0;//状态
    private int pageindex = 1;//页码数
    private int searchState = 0;//状态
    private String searchProblemType = "";//类型
    private int searchDate = 0;//时间
    private List<ProblemBean.RowsBean> rowsBeanList = new ArrayList();
    private TextView[] textViewsTit;
    private ImageView[] imageViewTit;
    private RelativeLayout nothing;

    private ProblemActivity mContext;
    private int ViewHight = 0;
    private long exitTime;//上一次按退出键时间
    private static final long TIME = 2000;//双击回退键间隔时间

    @Override
    protected void setView() {
        setContentView(R.layout.activity_problem);
        mContext = this;
    }

    @Override
    protected void setDate(Bundle savedInstanceState) {
        MyTitle.getInstance().setTitle(this, "问题上报", PGApp, true);
    }

    private void requestData(int pageindex, int searchState, String searchProblemType, int searchDate) {
        ProblemRequest.problemListRequest(mContext, this, pageindex, searchState, searchProblemType, searchDate);
    }

    @Override
    protected void init() {
        mPullRefreshListView = (PullToRefreshListView) findViewById(R.id.problem_refresh_list);
        mPullRefreshListView.setMode(PullToRefreshBase.Mode.BOTH);
        addProblem = (LinearLayout) findViewById(R.id.problem_addInfo);
        addProblem.setVisibility(View.VISIBLE);
        addProblem.setOnClickListener(this);

        typeLayout = (LinearLayout) findViewById(R.id.problem_layout_type);
        timeLayout = (LinearLayout) findViewById(R.id.problem_layout_time);
        stateLayout = (LinearLayout) findViewById(R.id.problem_layout_state);
        typeText = (TextView) findViewById(R.id.problem_layout_type_text);
        timeText = (TextView) findViewById(R.id.problem_layout_time_text);
        stateText = (TextView) findViewById(R.id.problem_layout_state_text);
        typeLayout.setOnClickListener(this);
        timeLayout.setOnClickListener(this);
        stateLayout.setOnClickListener(this);
        textViewsTit = new TextView[]{typeText, timeText, stateText};
        typeImage = (ImageView) findViewById(R.id.problem_layout_type_image);
        timeImage = (ImageView) findViewById(R.id.problem_layout_time_image);
        stateImage = (ImageView) findViewById(R.id.problem_layout_state_image);
        nothing = (RelativeLayout) findViewById(R.id.problem_nothing);
        imageViewTit = new ImageView[]{typeImage, timeImage, stateImage};
        mPullRefreshListView.setOnRefreshListener(new PullToRefreshBase.OnRefreshListener2<ListView>() {
            @Override
            public void onPullDownToRefresh(
                    PullToRefreshBase<ListView> refreshView) {
                Log.e("TAG", "onPullDownToRefresh");
                // 这里写下拉刷新的任务
                pageindex = 1;
                requestData(pageindex, searchState, searchProblemType, searchDate);
                mPullRefreshListView.onRefreshComplete();
            }

            @Override
            public void onPullUpToRefresh(
                    PullToRefreshBase<ListView> refreshView) {
                Log.e("TAG", "onPullUpToRefresh");
                // 这里写上拉加载更多的任务
                pageindex++;
                requestData(pageindex, searchState, searchProblemType, searchDate);
                mPullRefreshListView.onRefreshComplete();
            }
        });
        final RelativeLayout titleLayout = (RelativeLayout) findViewById(R.id.title_layout);
        final LinearLayout problemTitleLayout = (LinearLayout) findViewById(R.id.problem_title_layout);
        ViewTreeObserver titleLayoutVTO = titleLayout.getViewTreeObserver();
        titleLayoutVTO.addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
            @Override
            public void onGlobalLayout() {
                ViewHight = titleLayout.getHeight();
                Log.i("ViewHight", ViewHight + "");
            }
        });
        ViewTreeObserver problemTitleLayoutVTO = problemTitleLayout.getViewTreeObserver();
        problemTitleLayoutVTO.addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
            @Override
            public void onGlobalLayout() {
                ViewHight = problemTitleLayout.getHeight() * 2 + ViewHight;
            }
        });
    }

    @Override
    public void showTaskList(ProblemBean problemBean) {
        if (pageindex == 1 && problemBean.getRows().size() == 0) {
            mPullRefreshListView.setVisibility(View.GONE);
            nothing.setVisibility(View.VISIBLE);
        } else {
            mPullRefreshListView.setVisibility(View.VISIBLE);
            nothing.setVisibility(View.GONE);
            if (pageindex == 1) {
                rowsBeanList = problemBean.getRows();
                problemAdapter = new ProblemAdapter(mContext, rowsBeanList);
                mPullRefreshListView.setAdapter(problemAdapter);
                problemAdapter.notifyDataSetChanged();
            } else if (pageindex > 1 && problemBean.getRows().size() != 0) {
                rowsBeanList.addAll(problemBean.getRows());
                problemAdapter = new ProblemAdapter(mContext, rowsBeanList);
                mPullRefreshListView.setAdapter(problemAdapter);
                problemAdapter.notifyDataSetChanged();
            } else if (pageindex > 1 && problemBean.getRows().size() == 0) {
                ToastUtil.show(mContext, "没有更多数据了");
            }
        }
    }

    @Override
    public void onClick(View v) {
        List list;
        int i = v.getId();
        if (i == R.id.problem_addInfo) {
            Intent intent = new Intent(mContext, AddProblemActivity.class);
            startActivity(intent);

        } else if (i == R.id.problem_layout_type) {//                list = new ArrayList();
            ProblemRequest.getProblemTypeLeft(mContext, this);
            setTextViewColor(typeText);

        } else if (i == R.id.problem_layout_time) {
            list = new ArrayList();
            list.add("全部");
            list.add("三天");
            list.add("一周");
            list.add("一个月");
            popupWindow = PopWindowUtils.showProblemPop(mContext, this, v, list, 1, ViewHight);
            setTextViewColor(timeText);

        } else if (i == R.id.problem_layout_state) {
            list = new ArrayList();
            list.add("全部");
            list.add("已上报");
            list.add("已回复");
            popupWindow = PopWindowUtils.showProblemPop(mContext, this, v, list, 2, ViewHight);
            setTextViewColor(stateText);
        }
    }

    private void setTextViewColor(TextView textView) {
        if (textView != null) {
            for (int i = 0; i < textViewsTit.length; i++) {
                if (textView.getId() == textViewsTit[i].getId()) {
//                    imageViewTit[i].setImageResource(R.mipmap.search_top);
                    textViewsTit[i].setTextColor(ContextCompat.getColor(mContext, R.color.blue));
                } else {
//                    imageViewTit[i].setImageResource(R.mipmap.search_bottom);
                    textViewsTit[i].setTextColor(ContextCompat.getColor(mContext, R.color.black));
                }
            }
        } else {
            for (int i = 0; i < textViewsTit.length; i++) {
//                imageViewTit[i].setImageResource(R.mipmap.search_bottom);
                textViewsTit[i].setTextColor(ContextCompat.getColor(mContext, R.color.black));
            }
        }
    }

    @Override
    public void getTypeRight(ProblemTypeRight problemTypeRight) {
        searchProblemType = problemTypeRight.getCode();
        typeText.setText("类型(" + problemTypeRight.getName().substring(0, problemTypeRight.getName().length() - 2) + ")");
        setTextViewColor(null);
        pageindex = 1;
        requestData(pageindex, searchState, searchProblemType, searchDate);
        if (popupWindow.isShowing()) {
            popupWindow.dismiss();
        }
    }

    @Override
    public void getTypeLeft(List<ProblemTypeLeft> problemTypeLeftList) {
        popupWindow = PopWindowUtils.showProblemTypePop(mContext, this, this, typeLayout, ViewHight, (ArrayList<ProblemTypeLeft>) problemTypeLeftList);
    }

    @Override
    public void getProblemType(int type, String typeName) {
        switch (type) {
            case 0:
                searchProblemType = "";
                typeText.setText(typeName);
                setTextViewColor(null);
                break;
            case 1:
                searchDate = "全部".equals(typeName) ? 0 : "三天".equals(typeName) ? 1 : "一周".equals(typeName) ? 2 : "一个月".equals(typeName) ? 3 : 0;
                timeText.setText("时间(" + typeName + ")");
                setTextViewColor(null);
                break;
            case 2:
                searchState = "全部".equals(typeName) ? 0 : "已上报".equals(typeName) ? 1 : "已回复".equals(typeName) ? 2 : 0;
                stateText.setText("状态(" + typeName + ")");
                setTextViewColor(null);
                break;
        }
        pageindex = 1;
        requestData(pageindex, searchState, searchProblemType, searchDate);
        if (popupWindow.isShowing()) {
            popupWindow.dismiss();
        }
    }

    @Override
    public void clearColor() {
        if (popupWindow.isShowing()) {
            popupWindow.dismiss();
        }
        setTextViewColor(null);
    }

    @Override
    public void onResume() {
        super.onResume();
        requestData(pageindex, searchState, searchProblemType, searchDate);
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            if (SharedUtil.getBoolean(this, "isProblem", false)) {
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
