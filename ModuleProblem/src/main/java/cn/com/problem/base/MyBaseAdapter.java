package cn.com.problem.base;

import android.content.Context;
import android.util.SparseArray;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

import java.util.ArrayList;
import java.util.List;


/**
 * 文件名：MyBaseAdapter
 * 描    述：适配器的基类
 * 作    者：stt
 * 时    间：2017.1.3
 * 版    本：V1.0.0
 */

public abstract class MyBaseAdapter extends BaseAdapter {
    protected List list;
    protected Context context;

    public MyBaseAdapter(Context context) {
        init(context, new ArrayList());
    }

    public MyBaseAdapter(Context context, List list) {
        init(context, list);
    }

    private void init(Context context, List list) {
        this.list = list;
        this.context = context;
    }

    public List getList() {
        return list;
    }

    public void setList(List list) {
        this.list = list;
    }

    public void clear() {
        this.list.clear();
        notifyDataSetChanged();
    }

    public void addAll(List list) {
        if (list != null) {
            this.list.addAll(list);
            notifyDataSetChanged();
        }
    }

    @Override
    public int getCount() {
        return list == null ? 0 : list.size();
    }

    @Override
    public Object getItem(int position) {
        return list.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if (null == convertView) {
            convertView = inflate(getContentView());
        }
        onInitView(convertView, position);
        return convertView;
    }

    /**
     * 加载布局
     */
    private View inflate(int layoutResID) {
        LayoutInflater layoutInflater = (LayoutInflater) context
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View view = layoutInflater.inflate(layoutResID, null);
        return view;
    }

    public abstract int getContentView();

    public abstract void onInitView(View view, int position);

    /**
     * @param view converView
     * @param id   控件的id
     * @return 返回<t extends="" view="">
     */
    @SuppressWarnings("unchecked")
    protected <T extends View> T get(View view, int id) {
        SparseArray<View> viewHolder = (SparseArray<View>) view.getTag();
        if (null == viewHolder) {
            viewHolder = new SparseArray<View>();
            view.setTag(viewHolder);
        }
        View childView = viewHolder.get(id);
        if (null == childView) {
            childView = view.findViewById(id);
            viewHolder.put(id, childView);
        }
        return (T) childView;
    }
}
