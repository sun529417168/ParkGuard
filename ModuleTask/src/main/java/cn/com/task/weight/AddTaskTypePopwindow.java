package cn.com.task.weight;

import android.app.Activity;
import android.content.Context;
import android.graphics.drawable.ColorDrawable;
import android.view.View;
import android.view.ViewGroup;
import android.widget.PopupWindow;
import android.widget.TextView;

import java.util.ArrayList;

import cn.com.task.R;
import cn.com.task.bean.TaskTypeBean;
import cn.com.task.weight.wheelview.OnWheelChangedListener;
import cn.com.task.weight.wheelview.OnWheelScrollListener;
import cn.com.task.weight.wheelview.adapter.AbstractWheelTextAdapter1;


/**
 * 文件名：AddTaskTypePopwindow
 * 描 述：添加任务类型的pop
 * 作 者：stt
 * 时 间：2017.3.17
 * 版 本：V1.0.7
 */
public class AddTaskTypePopwindow extends PopupWindow implements
        View.OnClickListener {

    private WheelView wvProvince;
    private View lyChangeAddressChild;
    private TextView btnSure;
    private TextView btnCancel;

    private Activity context;
    private AddressTextAdapter provinceAdapter;
    private AddressTextAdapter cityAdapter;

    private String taskTypeName = "";
    private String code = "";
    private OnAddressCListener onAddressCListener;

    private int maxsize = 14;
    private int minsize = 12;
    ArrayList<TaskTypeBean> taskTypeBean;

    public AddTaskTypePopwindow(final Activity context, ArrayList<TaskTypeBean> taskTypeBeans) {
        super(context);
        this.context = context;
        this.taskTypeBean = taskTypeBeans;
        View view = View.inflate(context, R.layout.pop_add_task_type,
                null);

        wvProvince = (WheelView) view.findViewById(R.id.pop_taskType_wheelView);
        lyChangeAddressChild = view
                .findViewById(R.id.ly_myinfo_changeaddress_child);
        btnSure = (TextView) view.findViewById(R.id.pop_taskType_sure);
        btnCancel = (TextView) view.findViewById(R.id.pop_taskType_cancel);

        // 设置SelectPicPopupWindow的View
        this.setContentView(view);
        // 设置SelectPicPopupWindow弹出窗体的宽
        this.setWidth(ViewGroup.LayoutParams.MATCH_PARENT);
        // 设置SelectPicPopupWindow弹出窗体的高
        this.setHeight(ViewGroup.LayoutParams.MATCH_PARENT);
        // 设置SelectPicPopupWindow弹出窗体可点击
        this.setFocusable(true);
        // 设置SelectPicPopupWindow弹出窗体动画效果
        // this.setAnimationStyle(R.style.AnimBottom);
        // 实例化一个ColorDrawable颜色为半透明
        ColorDrawable dw = new ColorDrawable(0xb0000000);
        // 设置SelectPicPopupWindow弹出窗体的背景
        this.setBackgroundDrawable(dw);

        lyChangeAddressChild.setOnClickListener(this);
        btnSure.setOnClickListener(this);
        btnCancel.setOnClickListener(this);

        provinceAdapter = new AddressTextAdapter(context, taskTypeBean, getProvinceItem(taskTypeBean.get(0).getName()), maxsize, minsize);
        wvProvince.setVisibleItems(5);
        wvProvince.setViewAdapter(provinceAdapter);
        wvProvince.setCurrentItem(getProvinceItem(taskTypeBean.get(0).getName()));

        wvProvince.addChangingListener(new OnWheelChangedListener() {

            @Override
            public void onChanged(WheelView wheel, int oldValue, int newValue) {
                // TODO Auto-generated method stub
                String currentText = (String) provinceAdapter.getItemText(wheel.getCurrentItem());
                taskTypeName = currentText;
                code = taskTypeBean.get(wheel.getCurrentItem()).getValue() + "";
                setTextviewSize(currentText, provinceAdapter);
            }
        });

        wvProvince.addScrollingListener(new OnWheelScrollListener() {

            @Override
            public void onScrollingStarted(WheelView wheel) {
                // TODO Auto-generated method stub

            }

            @Override
            public void onScrollingFinished(WheelView wheel) {
                // TODO Auto-generated method stub
                String currentText = (String) provinceAdapter.getItemText(wheel.getCurrentItem());
                code = taskTypeBean.get(wheel.getCurrentItem()).getValue() + "";
                setTextviewSize(currentText, provinceAdapter);
            }
        });

    }

    private class AddressTextAdapter extends AbstractWheelTextAdapter1 {
        ArrayList<TaskTypeBean> taskArrayList;

        protected AddressTextAdapter(Context context, ArrayList<TaskTypeBean> list, int currentItem, int maxsize, int minsize) {
            super(context, R.layout.item_wheelview, NO_RESOURCE, currentItem, maxsize, minsize);
            this.taskArrayList = list;
            setItemTextResource(R.id.item_wheelView);
        }

        @Override
        public View getItem(int index, View cachedView, ViewGroup parent) {
            View view = super.getItem(index, cachedView, parent);
            return view;
        }

        @Override
        public int getItemsCount() {
            return taskArrayList.size();
        }

        @Override
        protected CharSequence getItemText(int index) {
            return taskArrayList.get(index).getName();
        }
    }

    /**
     * 设置字体大小
     *
     * @param curriteItemText
     * @param adapter
     */
    public void setTextviewSize(String curriteItemText,
                                AddressTextAdapter adapter) {
        ArrayList<View> arrayList = adapter.getTestViews();
        int size = arrayList.size();
        String currentText;
        for (int i = 0; i < size; i++) {
            TextView textvew = (TextView) arrayList.get(i);
            currentText = textvew.getText().toString();
            if (curriteItemText.equals(currentText)) {
                textvew.setTextSize(14);
            } else {
                textvew.setTextSize(12);
            }
        }
    }

    public void setAddresskListener(OnAddressCListener onAddressCListener) {
        this.onAddressCListener = onAddressCListener;
    }

    @Override
    public void onClick(View v) {
        // TODO Auto-generated method stub
        if (v == btnSure) {
            if (onAddressCListener != null) {
                onAddressCListener.onClick(taskTypeName, code);
            }
        } else if (v == btnCancel) {

        } else if (v == lyChangeAddressChild) {
            return;
        } else {
            // dismiss();
        }
        dismiss();
    }

    /**
     * 回调接口
     *
     * @author Administrator
     */
    public interface OnAddressCListener {
        void onClick(String name, String code);
    }

    /**
     * 返回省会索引，没有就返回默认“部件上报”
     *
     * @param province
     * @return
     */
    public int getProvinceItem(String province) {
        int size = taskTypeBean.size();
        int provinceIndex = 0;
        boolean noprovince = true;
        for (int i = 0; i < size; i++) {
            if (province.equals(taskTypeBean.get(i))) {
                noprovince = false;
                return provinceIndex;
            } else {
                provinceIndex++;
            }
        }
        if (noprovince) {
            taskTypeName = taskTypeBean.get(0).getName();
            code = taskTypeBean.get(0).getValue() + "";
            return 0;
        }
        return provinceIndex;
    }


}