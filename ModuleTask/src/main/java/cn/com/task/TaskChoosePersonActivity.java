package cn.com.task;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ExpandableListView;
import android.widget.TextView;

import com.linked.erfli.library.base.BaseActivity;
import com.linked.erfli.library.utils.StatusBarUtils;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.TreeMap;

import cn.com.task.adapter.TaskChoosePersonAdapter;
import cn.com.task.bean.TaskChoosePersonBean;
import cn.com.task.interfaces.ChoosePersonInterface;
import cn.com.task.networkrequest.TaskReuest;

/**
 * Created by 志强 on 2017.4.27.
 */

public class TaskChoosePersonActivity extends BaseActivity implements View.OnClickListener, ChoosePersonInterface {
    private TextView titleName;
    private ExpandableListView explistview;
    private List<List<TaskChoosePersonBean>> childrenData = new ArrayList<>();
    private List<TaskChoosePersonBean> groupData = new ArrayList<>();
    public static TaskChoosePersonAdapter adapter;
    private TreeMap<String, TaskChoosePersonBean> parentMap = new TreeMap<>();
    private TreeMap<String, TaskChoosePersonBean> childMap = new TreeMap<>();
    private List<List<Boolean>> childCheckBox = new ArrayList<>();
    private List<Boolean> parentCheckBox = new ArrayList<>();
    private List<TaskChoosePersonBean> resultData = new ArrayList<>();
    private Button submit;
    private int parentCount = 0;
    private int childCount = 0;

    @Override
    protected void setView() {
        setContentView(R.layout.task_activity_chooseperon);
        StatusBarUtils.ff(TaskChoosePersonActivity.this, R.color.blue);
    }

    @Override
    protected void setDate(Bundle savedInstanceState) {
        TaskReuest.GetPersonInfoByDepartmentRequest(this);
    }

    @Override
    protected void init() {
        titleName = (TextView) findViewById(R.id.title_name);
        titleName.setText("人员选择");
        explistview = (ExpandableListView) findViewById(R.id.task_choose_person_expandableListView);
        submit = (Button) findViewById(R.id.choose_person_submit);
        submit.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        for (int i = 0; i < childCheckBox.size(); i++) {
            for (int j = 0; j < childCheckBox.get(i).size(); j++) {
                if (childCheckBox.get(i).get(j)) {
                    resultData.add(childrenData.get(i).get(j));
                }
            }
        }
        Intent intent = new Intent();
        intent.putExtra("data", (Serializable) resultData);
        setResult(RESULT_OK, intent);
        finish();
    }

    @Override
    public void getPerson(ArrayList<TaskChoosePersonBean> choosePersonList) {
        parentMap = new TreeMap<>();
        childMap = new TreeMap<>();
        for (TaskChoosePersonBean bean : choosePersonList) {
            if (bean.isIsParent() == true) {
                parentMap.put(bean.getName(), bean);
            } else {
                childMap.put(bean.getName(), bean);
            }
        }
        Iterator parentit = parentMap.keySet().iterator();
        Iterator childit = childMap.keySet().iterator();
        while (parentit.hasNext()) {
            Object parentflag = parentit.next();
            groupData.add(parentCount, (TaskChoosePersonBean) parentMap.get(parentflag));
            parentCheckBox.add(false);
            List<TaskChoosePersonBean> flagList = new ArrayList<>();
            List<Boolean> checkList = new ArrayList<Boolean>();
            while (childit.hasNext()) {
                Object childflag = childit.next();
                if (parentMap.get(parentflag).getId().equals(childMap.get(childflag).getPid())) {
                    flagList.add(childCount, (TaskChoosePersonBean) childMap.get(childflag));
                    checkList.add(false);
                    childCount++;
                }
            }
            childrenData.add(flagList);
            childCheckBox.add(checkList);
            childit = childMap.keySet().iterator();
            childCount = 0;
            parentCount++;
        }
        adapter = new TaskChoosePersonAdapter(childrenData, groupData, childCheckBox, parentCheckBox, getApplicationContext(), explistview);
        explistview.setAdapter(adapter);
    }
}
