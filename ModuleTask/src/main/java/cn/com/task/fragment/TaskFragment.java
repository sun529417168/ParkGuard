package cn.com.task.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.linked.erfli.library.base.BaseFragment;

import cn.com.task.R;

/**
 * Created by 志强 on 2017.4.27.
 */

public class TaskFragment extends BaseFragment {
    private View view;
    private TextView title_name;

    @Override
    protected View setView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_task, null);
        return view;
    }

    @Override
    protected void setDate() {

    }

    @Override
    protected void init(View rootView) {
        title_name = (TextView) rootView.findViewById(R.id.title_name);
        title_name.setText("任务");
    }
}
