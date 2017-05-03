package cn.com.problem.adapter;

import android.content.Context;
import android.view.View;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import cn.com.problem.R;
import cn.com.problem.base.MyBaseAdapter;


/**
 * 文件名：PopProblemAdapter
 * 描    述：问题列表筛选条件的pop
 * 作    者：stt
 * 时    间：2017.02.07
 * 版    本：V1.0.0
 */

public class PopProblemAdapter extends MyBaseAdapter {
    private ArrayList<String> list = new ArrayList<>();

    public PopProblemAdapter(Context context, List list) {
        super(context, list);
        this.list = (ArrayList<String>) list;
    }

    @Override
    public int getContentView() {
        return R.layout.item_textview;
    }

    @Override
    public void onInitView(View view, int position) {
        TextView info = get(view, R.id.item_textView);
        info.setText(list.get(position));
    }
}
