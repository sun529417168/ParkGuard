package cn.com.problem.adapter;

import android.content.Context;
import android.support.v4.content.ContextCompat;
import android.view.View;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import cn.com.problem.R;
import cn.com.problem.base.MyBaseAdapter;
import cn.com.problem.bean.ProblemTypeLeft;


/**
 * 文件名：PopProblemTypeLeftAdapter
 * 描    述：问题列表类型左边的适配器
 * 作    者：stt
 * 时    间：2017.02.28
 * 版    本：V1.0.0
 */

public class PopProblemTypeLeftAdapter extends MyBaseAdapter {
    private ArrayList<ProblemTypeLeft> list = new ArrayList<>();
    private int index = -1;
    private Context context;

    public PopProblemTypeLeftAdapter(Context context, List list) {
        super(context, list);
        this.list = (ArrayList<ProblemTypeLeft>) list;
        this.context = context;
    }

    @Override
    public int getContentView() {
        return R.layout.item_textview;
    }

    @Override
    public void onInitView(View view, int position) {
        TextView info = get(view, R.id.item_textView);
        info.setText(list.get(position).getName());
        if (index == position) {
            view.setBackgroundColor(ContextCompat.getColor(context, R.color.gray_4));
        }else{
            view.setBackgroundColor(ContextCompat.getColor(context, R.color.white));
        }
    }

    public void setPosition(int index) {
        this.index = index;
    }
}
