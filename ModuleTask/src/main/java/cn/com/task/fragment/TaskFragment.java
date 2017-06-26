package cn.com.task.fragment;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.linked.erfli.library.base.BaseFragment;
import com.linked.erfli.library.refresh.PullToRefreshBase;
import com.linked.erfli.library.refresh.PullToRefreshListView;
import com.linked.erfli.library.utils.SharedUtil;
import com.linked.erfli.library.utils.ToastUtil;

import java.util.ArrayList;
import java.util.List;

import cn.com.task.AddTaskActivity;
import cn.com.task.R;
import cn.com.task.TaskSearchActivity;
import cn.com.task.adapter.TaskAdapter;
import cn.com.task.bean.TaskBean;
import cn.com.task.interfaces.TaskListInterface;
import cn.com.task.networkrequest.TaskReuest;

/**
 * Created by 志强 on 2017.4.27.
 */

public class TaskFragment extends BaseFragment implements TaskListInterface, View.OnClickListener {
    private Context context;
    private View view;
    private TextView title_name;
    private LinearLayout searchLayout;
    private TabLayout tabLayout;
    //刷新控件
    private PullToRefreshListView mPullRefreshListView;
    private RelativeLayout nothing;
    private TaskAdapter taskAdapter;
    //private HorizontalListView horizontalListView;
    //private TaskStateAdapter taskStateAdapter;
    private int state = 0;//状态
    private int pageindex = 1;//页码数
    private List<TaskBean.RowsBean> rowsBeanList = new ArrayList();
    private LinearLayout addTask;
    private LinearLayout title_back;

    @Override
    protected View setView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        context = inflater.getContext();
        view = inflater.inflate(R.layout.fragment_task, null);
        return view;
    }

    @Override
    protected void setDate() {
        requestData(pageindex, state);
    }

    @Override
    protected void init(View rootView) {
        if (SharedUtil.getBoolean(context, "isTask", false)) {
            title_back = (LinearLayout) rootView.findViewById(R.id.title_back);
            title_back.setVisibility(View.VISIBLE);
            title_back.setOnClickListener(this);
        }
        title_name = (TextView) rootView.findViewById(R.id.title_name);
        title_name.setText("任务");
        searchLayout = (LinearLayout) rootView.findViewById(R.id.title_search);
        searchLayout.setVisibility(View.INVISIBLE);
        searchLayout.setOnClickListener(this);
        addTask = (LinearLayout) rootView.findViewById(R.id.problem_addInfo);
        addTask.setVisibility(View.VISIBLE);
        addTask.setOnClickListener(this);
        mPullRefreshListView = (PullToRefreshListView) rootView.findViewById(R.id.task_refresh_list);
        mPullRefreshListView.setMode(PullToRefreshBase.Mode.BOTH);
        //horizontalListView = (HorizontalListView) rootView.findViewById(R.id.task_horizontalListView);
        nothing = (RelativeLayout) rootView.findViewById(R.id.task_nothing);
        //taskStateAdapter = new TaskStateAdapter(context, getTypeData());
        // horizontalListView.setAdapter(taskStateAdapter);
        //taskStateAdapter.setSelectIndex(0);
        //taskStateAdapter.notifyDataSetChanged();
        /*horizontalListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                state = position;
                pageindex = 1;
                requestData(pageindex, position);
                taskStateAdapter.setSelectIndex(position);
                taskStateAdapter.notifyDataSetChanged();
            }
        });*/
        tabLayout=(TabLayout)rootView.findViewById(R.id.tabLayout);
        initTablayout(tabLayout,getTypeData());
        mPullRefreshListView.setOnRefreshListener(new PullToRefreshBase.OnRefreshListener2<ListView>() {
            @Override
            public void onPullDownToRefresh(
                    PullToRefreshBase<ListView> refreshView) {
                Log.e("TAG", "onPullDownToRefresh");
                // 这里写下拉刷新的任务
                try {
                    pageindex = 1;
                    requestData(pageindex, state);
                    taskAdapter.notifyDataSetChanged();
                    mPullRefreshListView.onRefreshComplete();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onPullUpToRefresh(
                    PullToRefreshBase<ListView> refreshView) {
                Log.e("TAG", "onPullUpToRefresh");
                // 这里写上拉加载更多的任务
                pageindex++;
                requestData(pageindex, state);
                taskAdapter.notifyDataSetChanged();
                mPullRefreshListView.onRefreshComplete();
            }
        });
    }

    private void requestData(int pageindex, int state) {
        TaskReuest.tasIssuedListRequest(context, this, pageindex, "5", state);
    }

    @Override
    public void showTaskList(TaskBean taskBean) {
        if (pageindex == 1 && taskBean.getRows().size() == 0) {
            mPullRefreshListView.setVisibility(View.GONE);
            nothing.setVisibility(View.VISIBLE);
        } else {
            mPullRefreshListView.setVisibility(View.VISIBLE);
            nothing.setVisibility(View.GONE);
            if (pageindex == 1) {
                rowsBeanList = taskBean.getRows();
                taskAdapter = new TaskAdapter(context, rowsBeanList);
                mPullRefreshListView.setAdapter(taskAdapter);
            } else if (pageindex > 1 && taskBean.getRows().size() != 0) {
                rowsBeanList.addAll(taskBean.getRows());
                taskAdapter = new TaskAdapter(context, rowsBeanList);
                mPullRefreshListView.setAdapter(taskAdapter);
            } else if (pageindex > 1 && taskBean.getRows().size() == 0) {
                ToastUtil.show(context, "没有更多数据了");
            }
        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 200 || requestCode == getActivity().RESULT_OK) {
//            ToastUtil.show(context, data.getStringExtra("username"));
        }
    }

    @Override
    public void onClick(View v) {
        Intent intent;
        int i = v.getId();
        if (i == R.id.title_search) {
            intent = new Intent(getActivity(), TaskSearchActivity.class);
            startActivityForResult(intent, 200);

        } else if (i == R.id.problem_addInfo) {
            intent = new Intent(context, AddTaskActivity.class);
            startActivity(intent);
        } else if (i == R.id.title_back) {
            getActivity().finish();
        }
    }
    private void initTablayout(TabLayout tabLayout,List<String> typeList){
        for(String data:typeList)
        {
            tabLayout.addTab(tabLayout.newTab().setText(data));
        }
        tabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                state = tab.getPosition();
                pageindex = 1;
                requestData(pageindex, tab.getPosition());
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });
    }

    private List<String> getTypeData() {
        List<String> data = new ArrayList<String>();
        data.add("全部");
        data.add("未查阅");
        data.add("处理中");
        data.add("已完成");
        data.add("未完成");
        return data;
    }

    @Override
    public void onResume() {
        super.onResume();
        requestData(pageindex, state);
    }
}
