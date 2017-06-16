package cn.com.notice.adapter;

import android.content.Context;
import android.content.Intent;
import android.support.v4.content.ContextCompat;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.linked.erfli.library.base.MyBaseAdapter;
import com.linked.erfli.library.config.UrlConfig;
import com.linked.erfli.library.okhttps.OkHttpUtils;
import com.linked.erfli.library.okhttps.callback.GenericsCallback;
import com.linked.erfli.library.okhttps.utils.JsonGenericsSerializator;
import com.linked.erfli.library.utils.GetWeek;
import com.linked.erfli.library.utils.MyUtils;
import com.nostra13.universalimageloader.core.ImageLoader;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import cn.com.notice.R;
import cn.com.notice.activity.NoticeDetailActivity;
import cn.com.notice.bean.NoticeBean;
import okhttp3.Call;

/**
 * 文件名：NoticeAdapter
 * 描    述：通知列表的适配器
 * 作    者：stt
 * 时    间：2017.1.3
 * 版    本：V1.0.0
 */

public class NoticeAdapter extends MyBaseAdapter {
    private ArrayList<NoticeBean.RowsBean> list = new ArrayList<>();

    public NoticeAdapter(Context context) {
        super(context);
    }

    public NoticeAdapter(Context context, List list) {
        super(context, list);
        this.list = (ArrayList<NoticeBean.RowsBean>) list;
    }

    @Override
    public int getContentView() {
        return R.layout.item_notice;
    }

    @Override
    public void onInitView(View view, int position) {
        TextView number = get(view, R.id.item_notice_number);  // 编号
        TextView date = get(view, R.id.item_notice_date);  // 日期
        TextView name = get(view, R.id.item_notice_name);  // 名称
        ImageView imageView = get(view, R.id.item_notice_imageView);//图片
        RelativeLayout noticeCalendarLayout=get(view,R.id.item_notice_calendar);//日历布局
        LinearLayout noticePictureLayout=get(view,R.id.item_notice_picture);//图片布局
        TextView notice_calendar_year_month=get(view,R.id.notice_year_month);//获取年月
        TextView notice_calendar_week=get(view,R.id.notice_week);//获取星期
        TextView notice_calendar_date=get(view,R.id.notice_day);//获取日期
        TextView notice_calendar_lunar=get(view,R.id.notice_lunar);//获取农历日期
        final TextView info = get(view, R.id.item_notice_info);  // 具体内容
        final TextView[] views = {number, date, name, info};
        final NoticeBean.RowsBean bean = list.get(position);
        /**
        *删除日期中的小时
        **/
         String firstDate=bean.getApiCreateTime().split(" ")[0];
        //获取年和月
        String notice_text_year_month=firstDate.split("/")[0]+"年"+firstDate.split("/")[1]+"月";
        //获取日期
        String notice_text_date=firstDate.split("/")[2];
        //获得星期
        String notice_week = GetWeek.getWeek(firstDate);
        //获得农历日期
        String notice_lunar= MyUtils.getLunar(firstDate);
        /**
         * 赋值
         */
        number.setText("编号:"+bean.getInformSno());
        date.setText(firstDate+" "+notice_week);
        notice_calendar_year_month.setText(notice_text_year_month);
        notice_calendar_week.setText(notice_week);
        notice_calendar_date.setText(notice_text_date);
        notice_calendar_lunar.setText(notice_lunar);
        name.setText(bean.getName());
        info.setText(bean.getContentInfo());
        for (NoticeBean.RowsBean.FileListBean fileBean : bean.getFileList()) {
            if (fileBean.getAttachmentType() == 1) {
                if (fileBean.getFileUrl() != null) {
                    noticeCalendarLayout.setVisibility(View.GONE);
                    noticePictureLayout.setVisibility(View.VISIBLE);
                    ImageLoader.getInstance().displayImage(fileBean.getFileUrl(), imageView);
                } else {
                    noticePictureLayout.setVisibility(View.GONE);
                    noticeCalendarLayout.setVisibility(View.VISIBLE);
                }
            }
        }
        if (bean.getFileList().size() == 0) {
            noticePictureLayout.setVisibility(View.GONE);
            noticeCalendarLayout.setVisibility(View.VISIBLE);
        }
        if (bean.isIsCheck()) {
            setTextColor(views);
        }

        view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (false == bean.isIsCheck()) {
                    setTextColor(views);
                    notifyDataSetChanged();
                }
                isCheckRequest(bean.getAcceptID());
                Intent intent = new Intent(context, NoticeDetailActivity.class);
                intent.putExtra("noticeId", bean.getID());
                context.startActivity(intent);
            }
        });

    }

    private void setTextColor(TextView[] views) {
        for (TextView view : views) {
            view.setTextColor(ContextCompat.getColor(context, R.color.gray));
        }
    }

    /**
     * 方法名：isCheckRequest
     * 功    能：点击修改查阅状态
     * 参    数：int id
     * 返回值：无
     */
    public static void isCheckRequest(int id) {
        Map<String, Object> params = new HashMap<>();
        try {
            params.put("ID", id);
        } catch (Exception e) {
            e.printStackTrace();
        }
        OkHttpUtils.post().url(UrlConfig.URL_NOTICEISCHECK).params(params).build().execute(new GenericsCallback<String>(new JsonGenericsSerializator()) {
            @Override
            public void onResponse(String response, int id) {
                Log.i("noticeIsCheck", response.toString());

            }

            @Override
            public void onError(Call call, Exception e, int id) {
                Log.i("noticeIsCheckError", e.getMessage().toString());

            }
        });
    }
}
