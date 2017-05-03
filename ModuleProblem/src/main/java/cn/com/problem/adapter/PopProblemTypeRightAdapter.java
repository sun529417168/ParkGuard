package cn.com.problem.adapter;

import android.content.Context;
import android.view.View;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import cn.com.problem.R;
import cn.com.problem.base.MyBaseAdapter;
import cn.com.problem.bean.ProblemTypeRight;
import cn.com.problem.interfaces.ProblemTypeRightInterface;


/**
 * 文件名：PopProblemTypeRightAdapter
 * 描    述：问题列表类型左边的适配器
 * 作    者：stt
 * 时    间：2017.3.1
 * 版    本：V1.0.2
 */

public class PopProblemTypeRightAdapter extends MyBaseAdapter {
    private ArrayList<ProblemTypeRight> list = new ArrayList<>();
    private ProblemTypeRightInterface problemTypeRightInterface;

    public PopProblemTypeRightAdapter(Context context, List list, ProblemTypeRightInterface problemTypeRightInterfaces) {
        super(context, list);
        this.list = (ArrayList<ProblemTypeRight>) list;
        this.problemTypeRightInterface = problemTypeRightInterfaces;
    }

    @Override
    public int getContentView() {
        return R.layout.item_textview;
    }

    @Override
    public void onInitView(View view, final int position) {
        TextView info = get(view, R.id.item_textView);
        info.setText(list.get(position).getName());
        view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                problemTypeRightInterface.getTypeRight(list.get(position));
            }
        });
    }
}
