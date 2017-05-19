package cn.com.problem.utils;

import android.app.ActionBar;
import android.app.Activity;
import android.content.Context;
import android.os.Build;
import android.util.DisplayMetrics;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.PopupWindow;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import cn.com.problem.R;
import cn.com.problem.adapter.PopProblemAdapter;
import cn.com.problem.adapter.PopProblemTypeLeftAdapter;
import cn.com.problem.bean.ProblemTypeLeft;
import cn.com.problem.interfaces.ProblemTypeInterface;
import cn.com.problem.interfaces.ProblemTypeRightInterface;
import cn.com.problem.interfaces.SearchTypePopInterface;
import cn.com.problem.networkrequest.ProblemRequest;


/**
 * 文件名：PopWindowUtils
 * 描    述：popWindow工具类
 * 作    者：stt
 * 时    间：2017.01.19
 * 版    本：V1.0.0
 */

public class PopWindowUtils {

    /**
     * 方法名：showSearchTypePop
     * 功    能：弹出任务类型的pop
     * 参    数：Activity activity,View btnPopup
     * 返回值：无
     */
    public static PopupWindow showSearchTypePop(final Activity activity, View btnPopup) {
        final SearchTypePopInterface searchType = (SearchTypePopInterface) activity;
        // TODO: 2016/5/17 构建一个popupwindow的布局
        final View popupView = activity.getLayoutInflater().inflate(R.layout.pop_task_search, null);

        final TextView name = (TextView) popupView.findViewById(R.id.pop_task_search_name);
        final TextView sender = (TextView) popupView.findViewById(R.id.pop_task_search_sender);
        final TextView all = (TextView) popupView.findViewById(R.id.pop_task_search_all);
        all.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                searchType.searchType(all.getText().toString());
            }
        });
        name.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                searchType.searchType(name.getText().toString());
            }
        });
        sender.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                searchType.searchType(sender.getText().toString());
            }
        });
        // TODO: 2016/5/17 创建PopupWindow对象，指定宽度和高度
        PopupWindow window = new PopupWindow(popupView, 300, 400);
        // TODO: 2016/5/17 设置背景颜色
//        window.setBackgroundDrawable(new ColorDrawable(Color.parseColor("#00000000")));
        // TODO: 2016/5/17 设置可以获取焦点
        window.setFocusable(true);
        // TODO: 2016/5/17 设置可以触摸弹出框以外的区域
        window.setOutsideTouchable(true);
        // TODO：更新popupwindow的状态
        window.update();
        // TODO: 2016/5/17 以下拉的方式显示，并且可以设置显示的位置
        window.showAsDropDown(btnPopup, 0, 20);
        return window;
    }

    /**
     * 方法名：showTaskReplyPop
     * 功    能：弹出任务回复状态的pop
     * 参    数：Activity activity,View btnPopup
     * 返回值：无
     */
    public static PopupWindow showTaskReplyPop(final Activity activity, View btnPopup) {
        final SearchTypePopInterface searchType = (SearchTypePopInterface) activity;
        // TODO: 2016/5/17 构建一个popupwindow的布局
        final View popupView = activity.getLayoutInflater().inflate(R.layout.pop_task_reply, null);

        final TextView finish = (TextView) popupView.findViewById(R.id.pop_task_reply_finish);
        final TextView unFinish = (TextView) popupView.findViewById(R.id.pop_task_reply_unFinish);
        finish.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                searchType.searchType(finish.getText().toString());
            }
        });
        unFinish.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                searchType.searchType(unFinish.getText().toString());
            }
        });
        // TODO: 2016/5/17 创建PopupWindow对象，指定宽度和高度
        PopupWindow window = new PopupWindow(popupView, ViewGroup.LayoutParams.MATCH_PARENT, 300);
        // TODO: 2016/5/17 设置背景颜色
//        window.setBackgroundDrawable(new ColorDrawable(Color.parseColor("#00000000")));
        // TODO: 2016/5/17 设置可以获取焦点
        window.setFocusable(true);
        // TODO: 2016/5/17 设置可以触摸弹出框以外的区域
        window.setOutsideTouchable(true);
        // TODO：更新popupwindow的状态
        window.update();
        // TODO: 2016/5/17 以下拉的方式显示，并且可以设置显示的位置
        window.showAsDropDown(btnPopup);
        return window;
    }

    /**
     * 方法名：showProblemPop
     * 功    能：弹出问题上报时间，状态的pop
     * 参    数：Activity activity, View btnPopup, List groups
     * 返回值：popupWindow
     */
    public static PopupWindow showProblemPop(Context activity, ProblemTypeInterface problemInterface, View btnPopup, final List groups, final int type, int layoutHight) {
        final ProblemTypeInterface problemTypeInterface = problemInterface;
        LayoutInflater layoutInflater = (LayoutInflater) activity.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View view = layoutInflater.inflate(R.layout.pop_problem, null);
        ListView listView = (ListView) view.findViewById(R.id.pop_problem_list);
        // 加载数据
        PopProblemAdapter popProblemAdapter = new PopProblemAdapter(activity, groups);
        listView.setAdapter(popProblemAdapter);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                problemTypeInterface.getProblemType(type, (String) groups.get(position));
            }
        });
        // 创建一个PopuWidow对象
        WindowManager windowManager = (WindowManager) activity.getSystemService(Context.WINDOW_SERVICE);
        DisplayMetrics displayMetrics = new DisplayMetrics();
        windowManager.getDefaultDisplay().getMetrics(displayMetrics);
        final PopupWindow popupWindow = new PopupWindow(view, ActionBar.LayoutParams.MATCH_PARENT, ActionBar.LayoutParams.MATCH_PARENT, true);
        // TODO: 2016/5/17 设置可以获取焦点
        popupWindow.setFocusable(true);
        // TODO: 2016/5/17 设置可以触摸弹出框以外的区域
        popupWindow.setOutsideTouchable(true);
        if (Build.VERSION.SDK_INT != 24) {
            // TODO: 2016/5/17 以下拉的方式显示，并且可以设置显示的位置
            popupWindow.showAsDropDown(btnPopup);
        } else {
            popupWindow.showAtLocation(btnPopup, Gravity.TOP, 0, layoutHight);
        }
        // TODO：更新popupwindow的状态
        popupWindow.update();
        view.findViewById(R.id.pop_problem_view).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                problemTypeInterface.clearColor();
            }
        });
        return popupWindow;
    }

    /**
     * 方法名：showAddProblemTypePop
     * 功    能：弹出添加问题，问题类型的pop
     * 参    数：Activity activity,View btnPopup
     * 返回值：无
     */
    public static PopupWindow showAddProblemTypePop(final Activity activity, View btnPopup) {
        final SearchTypePopInterface searchType = (SearchTypePopInterface) activity;
        // TODO: 2016/5/17 构建一个popupwindow的布局
        final View popupView = activity.getLayoutInflater().inflate(R.layout.pop_addproblem_type, null);

        final TextView event = (TextView) popupView.findViewById(R.id.pop_addProblem_event);
        final TextView parts = (TextView) popupView.findViewById(R.id.pop_addProblem_parts);
        event.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                searchType.searchType(event.getText().toString());
            }
        });
        parts.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                searchType.searchType(parts.getText().toString());
            }
        });
        // TODO: 2016/5/17 创建PopupWindow对象，指定宽度和高度
        PopupWindow window = new PopupWindow(popupView, ViewGroup.LayoutParams.MATCH_PARENT, 300);
        // TODO: 2016/5/17 设置背景颜色
//        window.setBackgroundDrawable(new ColorDrawable(Color.parseColor("#00000000")));
        // TODO: 2016/5/17 设置可以获取焦点
        window.setFocusable(true);
        // TODO: 2016/5/17 设置可以触摸弹出框以外的区域
        window.setOutsideTouchable(true);
        // TODO：更新popupwindow的状态
        window.update();
        // TODO: 2016/5/17 以下拉的方式显示，并且可以设置显示的位置
        window.showAsDropDown(btnPopup, 0, 20);
        return window;
    }

    /**
     * 方法名：showProblemTypePop
     * 功    能：弹出问题上报类型的pop
     * 参    数：Activity activity, View btnPopup, List groups
     * 返回值：popupWindow
     */
    public static PopupWindow showProblemTypePop(final Context activity, ProblemTypeInterface problemInterface, ProblemTypeRightInterface problemTypeRightInterfaces, View btnPopup, int layoutHight, final ArrayList<ProblemTypeLeft> leftList) {
        final ProblemTypeInterface problemTypeInterface = problemInterface;
        final ProblemTypeRightInterface problemTypeRightInterface = problemTypeRightInterfaces;
        LayoutInflater layoutInflater = (LayoutInflater) activity.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View view = layoutInflater.inflate(R.layout.pop_problem_type, null);
        ListView listViewLeft = (ListView) view.findViewById(R.id.pop_problem_type_leftList);
        final ListView listViewRight = (ListView) view.findViewById(R.id.pop_problem_type_rightList);
        //添加头部
        View headView = layoutInflater.inflate(R.layout.item_textview, null);
        TextView textView = (TextView) headView.findViewById(R.id.item_textView);
        textView.setText("全部");
        listViewLeft.addHeaderView(headView);
        // 加载数据
        final PopProblemTypeLeftAdapter popProblemTypeLeftAdapter = new PopProblemTypeLeftAdapter(activity, leftList);
        listViewLeft.setAdapter(popProblemTypeLeftAdapter);
        listViewLeft.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                if (position == 0) {
                    problemTypeInterface.getProblemType(0, "类型(全部)");
                } else {
                    popProblemTypeLeftAdapter.setPosition(position - 1);
                    popProblemTypeLeftAdapter.notifyDataSetChanged();
                    ProblemRequest.getTypeListByCodeRequest(activity, leftList.get(position - 1).getCode(), listViewRight, problemTypeRightInterface);
                }
            }
        });
        // 创建一个PopuWidow对象
        WindowManager windowManager = (WindowManager) activity.getSystemService(Context.WINDOW_SERVICE);
        DisplayMetrics displayMetrics = new DisplayMetrics();
        windowManager.getDefaultDisplay().getMetrics(displayMetrics);
        final PopupWindow popupWindow = new PopupWindow(view, ActionBar.LayoutParams.MATCH_PARENT, ActionBar.LayoutParams.MATCH_PARENT, true);
        // TODO: 2016/5/17 设置可以获取焦点
        popupWindow.setFocusable(true);
        // TODO: 2016/5/17 设置可以触摸弹出框以外的区域
        popupWindow.setOutsideTouchable(true);
        if (Build.VERSION.SDK_INT != 24) {
            // TODO: 2016/5/17 以下拉的方式显示，并且可以设置显示的位置
            popupWindow.showAsDropDown(btnPopup);
        } else {
            popupWindow.showAtLocation(btnPopup, Gravity.TOP, 0, layoutHight);
        }
        // TODO：更新popupwindow的状态
        popupWindow.update();
        view.findViewById(R.id.pop_problem_type_view).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                problemTypeInterface.clearColor();
            }
        });
        return popupWindow;
    }
}
