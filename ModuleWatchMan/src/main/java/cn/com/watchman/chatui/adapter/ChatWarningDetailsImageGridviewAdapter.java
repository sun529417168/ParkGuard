package cn.com.watchman.chatui.adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.linked.erfli.library.ShowZoomPhotoActivity;

import java.util.ArrayList;

import cn.com.watchman.R;

/**
 * zzq
 */

public class ChatWarningDetailsImageGridviewAdapter extends BaseAdapter {

    private Context mContext;
    private ArrayList<String> list;
    LayoutInflater inflater;

    public ChatWarningDetailsImageGridviewAdapter(Context mContext, ArrayList<String> listFileBeen) {
        this.mContext = mContext;
        this.list = listFileBeen;
        this.inflater = LayoutInflater.from(mContext);
    }


    @Override
    public int getCount() {
        return list.size();
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
    public View getView(int position, View view, ViewGroup parent) {
        ViewHolder viewHolder;
        if (view == null) {
//            R.layout.item_gridview_photo
            view = LayoutInflater.from(mContext).inflate(R.layout.chat_warning_girdviewshowimage_item, null);
            viewHolder = new ViewHolder();
            viewHolder.imageView = (ImageView) view.findViewById(R.id.chat_warning_item_grid_image);
            view.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) view.getTag();
        }
        if (list.size() > 0) {
            Glide.with(mContext).load(list.get(position)).into(viewHolder.imageView);
//            ImageLoader.getInstance().displayImage(list.get(position), viewHolder.imageView);
        }
        viewHolder.imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent in = new Intent(mContext, ShowZoomPhotoActivity.class);
                in.putExtra("type", 1);
                in.putStringArrayListExtra("listPath", list);
                mContext.startActivity(in);
            }
        });
        return view;
    }

    class ViewHolder {
        ImageView imageView;
    }
}
