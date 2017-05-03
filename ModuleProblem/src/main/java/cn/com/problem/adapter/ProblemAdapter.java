package cn.com.problem.adapter;

import android.content.Context;
import android.content.Intent;
import android.support.v4.content.ContextCompat;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.linked.erfli.library.utils.GetWeek;
import com.linked.erfli.library.utils.MyUtils;
import com.nostra13.universalimageloader.core.ImageLoader;

import java.util.ArrayList;
import java.util.List;

import cn.com.problem.ProblemDetailActivity;
import cn.com.problem.R;
import cn.com.problem.base.MyBaseAdapter;
import cn.com.problem.bean.ProblemBean;


/**
 * 文件名：ProblemAdapter
 * 描    述：问题列表的适配器
 * 作    者：stt
 * 时    间：2017.1.3
 * 版    本：V1.0.0
 */


public class ProblemAdapter extends MyBaseAdapter {
    private ArrayList<ProblemBean.RowsBean> list = new ArrayList<>();

    public ProblemAdapter(Context context, List list) {
        super(context, list);
        this.list = (ArrayList<ProblemBean.RowsBean>) list;
    }

    @Override
    public int getContentView() {
        return R.layout.item_problem;
    }

    @Override
    public void onInitView(View view, final int position) {
        TextView number = get(view, R.id.item_problem_number);  // 编号
        TextView state = get(view, R.id.item_problem_state);  // 状态
        TextView date = get(view, R.id.item_problem_date);  // 日期
        ImageView imageView = get(view, R.id.item_problem_image);  // 图片
        TextView name = get(view, R.id.item_problem_name);  // 名称
        TextView sender = get(view, R.id.item_problem_sender);  // 发送人
        TextView info = get(view, R.id.item_problem_info);  // 具体内容
        TextView executor = get(view, R.id.item_problem_executor);  // 处理人
        RelativeLayout problemCalendarLayout = get(view, R.id.item_problem_calendar);//日历布局
        LinearLayout problemPictureLayout = get(view, R.id.item_problem_picture);//图片布局
        TextView problem_calendar_year_month = get(view, R.id.problem_year_month);//获取年月
        TextView problem_calendar_week = get(view, R.id.problem_week);//获取星期
        TextView problem_calendar_date = get(view, R.id.problem_day);//获取日期
        TextView problem_calendar_lunar = get(view, R.id.problem_lunar);//获取农历日期
        TextView[] views = {number, date, name, sender, info, executor};
        final ProblemBean.RowsBean rowsBean = list.get(position);
        /**
         *删除日期中的小时
         **/
        String firstDate = rowsBean.getFindDateApi().split(" ")[0];
        //获取年和月
        String problem_text_year_month = firstDate.split("/")[0] + "年" + firstDate.split("/")[1] + "月";
        //获取日期
        String problem_text_date = firstDate.split("/")[2];
        //获得星期
        String problem_week = GetWeek.getWeek(firstDate);
        //获得农历日期
        String problem_lunar = MyUtils.getLunar(firstDate);
        /**
         * 赋值
         */
        problem_calendar_year_month.setText(problem_text_year_month);
        problem_calendar_week.setText(problem_week);
        problem_calendar_date.setText(problem_text_date);
        problem_calendar_lunar.setText(problem_lunar);
        number.setText("P" + rowsBean.getProblemSno().substring(9));
        if ("已上报".equals(rowsBean.getStateName())) {
            state.setText("已上报");
            state.setTextColor(ContextCompat.getColor(context, R.color.white));
            state.setBackgroundResource(R.color.blue);
        }
        if ("已回复".equals(rowsBean.getStateName())) {
            state.setText("已回复");
            state.setTextColor(ContextCompat.getColor(context, R.color.white));
            state.setBackgroundResource(R.color.green);
        }
        for (ProblemBean.RowsBean.ReportAttachmentListBean imageBean : rowsBean.getReportAttachmentList()) {
            if (imageBean.getAttachmentType() == 1) {
                if (imageBean.getFileUrl() != null) {
                    problemCalendarLayout.setVisibility(View.GONE);
                    problemPictureLayout.setVisibility(View.VISIBLE);

                    ImageLoader.getInstance().displayImage(imageBean.getFileUrl(), imageView);
                } else {
                    problemPictureLayout.setVisibility(View.GONE);
                    problemCalendarLayout.setVisibility(View.VISIBLE);
                }
            }
        }
        if (rowsBean.getReportAttachmentList().size() == 0) {
            problemPictureLayout.setVisibility(View.GONE);
            problemCalendarLayout.setVisibility(View.VISIBLE);
        }
        date.setText(firstDate + " " + problem_week);
        name.setText("类型：" + rowsBean.getProblemTypeName());
        sender.setText("上报人：" + rowsBean.getReportPersonName());
        info.setText("问题描述：" + rowsBean.getProblemDes());
        view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, ProblemDetailActivity.class);
                intent.putExtra("problemId", rowsBean.getID() + "");
                context.startActivity(intent);
            }
        });


    }

    private void setTextColor(TextView[] views) {
        for (TextView view : views) {
            view.setTextColor(ContextCompat.getColor(context, R.color.gray));
        }
    }
}
