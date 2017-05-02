package cn.com.task.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.ExpandableListView;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

import cn.com.task.R;
import cn.com.task.TaskChoosePersonActivity;
import cn.com.task.bean.TaskChoosePersonBean;


/**
 * 文件名：TaskChoosePersonAdapter
 * 描    述：选择下发人员的适配器
 * 作    者：stt
 * 时    间：2017.3.21
 * 版    本：V1.0.7
 */
public class TaskChoosePersonAdapter extends BaseExpandableListAdapter {
    private List<List<TaskChoosePersonBean>> childrenData;
    private List<TaskChoosePersonBean> groupData;
    private Context context;
    private ExpandableListView listView;
    private LayoutInflater inflater;
    private List<List<Boolean>> childCheckBox;
    private List<Boolean> parentCheckBox;

    public TaskChoosePersonAdapter(List<List<TaskChoosePersonBean>> childrenData, List<TaskChoosePersonBean> groupData,
                                   List<List<Boolean>> childCheckBox, List<Boolean> parentCheckBox, Context context, ExpandableListView listView) {
        this.groupData = groupData;
        this.childrenData = childrenData;
        this.context = context;
        this.listView = listView;
        this.childCheckBox = childCheckBox;
        this.parentCheckBox = parentCheckBox;
        inflater = LayoutInflater.from(this.context);
    }

    @Override
    public Object getChild(int groupPosition, int childPosition) {
        return childrenData.get(groupPosition).get(childPosition);
    }

    @Override
    public long getChildId(int groupPosition, int childPosition) {
        return childPosition;
    }

    @Override
    public View getChildView(int groupPosition, int childPosition, boolean isLastChild, View convertView, ViewGroup parent) {
        View view = null;
        if (convertView != null) {
            view = convertView;
        } else {
            view = createChildrenView();
        }
        TextView text = (TextView) view.findViewById(R.id.choose_person_childText);
        text.setText(childrenData.get(groupPosition).get(childPosition).getName());
        final int group = groupPosition;
        final int child = childPosition;
        CheckBox childBox = (CheckBox) view.findViewById(R.id.choose_person_childCheckBox);
        childBox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                childCheckBox.get(group).set(child, isChecked);
                for (Boolean i : childCheckBox.get(group)) {
                    if (!i) {
                        parentCheckBox.set(group, false);
                    }
                }
                TaskChoosePersonActivity.adapter.notifyDataSetChanged();

            }
        });
        childBox.setChecked(childCheckBox.get(groupPosition).get(childPosition));
        return view;
    }

    @Override
    public int getChildrenCount(int groupPosition) {
        return childrenData.get(groupPosition).size();
    }

    @Override
    public Object getGroup(int groupPosition) {
        return groupData.get(groupPosition);
    }

    @Override
    public int getGroupCount() {
        return groupData.size();
    }

    @Override
    public long getGroupId(int groupPosition) {
        return groupPosition;
    }

    @Override
    public View getGroupView(int groupPosition, boolean isExpanded,
                             View convertView, ViewGroup parent) {
        View view = null;
        if (convertView != null) {
            view = convertView;
        } else {
            view = createGroupView();
        }

        ImageView iv = (ImageView) view.findViewById(R.id.choose_person_groupImage);

        if (isExpanded) {
            iv.setImageResource(R.mipmap.ex_down);
        } else {
            iv.setImageResource(R.mipmap.ex_right);
        }

        TextView text = (TextView) view.findViewById(R.id.choose_person_groupText);
        TextView num = (TextView) view.findViewById(R.id.choose_person_groupNum);
        text.setText(groupData.get(groupPosition).getName());
        int count = 0;
        for (Boolean i : childCheckBox.get(groupPosition)) {
            if (i) {
                count++;
            }
        }
        num.setText(count + "/" + childrenData.get(groupPosition).size());
        final int group = groupPosition;
        final CheckBox parentBox = (CheckBox) view.findViewById(R.id.choose_person_groupCheckBox);
        parentBox.setChecked(parentCheckBox.get(groupPosition));
        parentBox.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (parentBox.isChecked()) {
                    parentCheckBox.set(group, true);
                    for (int j = 0; j < childCheckBox.get(group).size(); j++) {
                        childCheckBox.get(group).set(j, true);
                    }
                } else {
                    parentCheckBox.set(group, false);
                    for (int j = 0; j < childCheckBox.get(group).size(); j++) {
                        childCheckBox.get(group).set(j, false);
                    }
                }
                TaskChoosePersonActivity.adapter.notifyDataSetChanged();
            }
        });
        return view;
    }

    @Override
    public boolean hasStableIds() {
        return true;
    }

    @Override
    public boolean isChildSelectable(int groupPosition, int childPosition) {
        return true;
    }

    private View createChildrenView() {
        return inflater.inflate(R.layout.item_choose_person_child, null);
    }

    private View createGroupView() {
        return inflater.inflate(R.layout.item_choose_person_group, null);
    }


}
