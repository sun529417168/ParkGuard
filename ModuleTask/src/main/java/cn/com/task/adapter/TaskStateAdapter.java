package cn.com.task.adapter;

import android.content.Context;
import android.support.v4.content.ContextCompat;
import android.view.View;
import android.widget.TextView;

import com.linked.erfli.library.base.MyBaseAdapter;

import java.util.ArrayList;
import java.util.List;

import cn.com.task.R;


/**
 * 文件名：TaskStateAdapter
 * 描    述：横向的listView适配器
 * 作    者：stt
 * 时    间：2017.1.9
 * 版    本：V1.0.0
 */

public class TaskStateAdapter extends MyBaseAdapter {
    private ArrayList<String> list = new ArrayList();
    private int selectIndex = -1;

    public TaskStateAdapter(Context context, List list) {
        super(context, list);
        this.list = (ArrayList) list;
    }

    @Override
    public int getContentView() {
        return R.layout.item_task_textview;
    }

    @Override
    public void onInitView(View view, int position) {
        TextView textView = get(view, R.id.item_task_textView_state);
        textView.setText(list.get(position) + "");
        if (position == selectIndex) {
            view.setSelected(true);
            textView.setTextColor(ContextCompat.getColor(context, R.color.white));
        } else {
            view.setSelected(false);
            textView.setTextColor(ContextCompat.getColor(context, R.color.blue));
        }
    }

    public void setSelectIndex(int i) {
        selectIndex = i;
    }
}
