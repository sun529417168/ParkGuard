package cn.com.watchman.adapter;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ImageView;

import java.util.List;

import cn.com.watchman.R;
import cn.com.watchman.interfaces.GridViewDelPhotoInterface;

/**
 * Created by 志强 on 2017.5.12.
 */

public class PhotoGridViewAdapter extends BaseAdapter {
    private boolean shape;
    private LayoutInflater listContainer;
    private List<Bitmap> bmp;
    private int selectedPosition = -1;
    private Context mContext;
    private GridViewDelPhotoInterface gridViewDelPhotoInterface;

    public boolean isShape() {
        return shape;
    }

    public PhotoGridViewAdapter(Context context, List<Bitmap> list, GridViewDelPhotoInterface anInterface) {
        this.mContext = context;
        this.bmp = list;
        this.gridViewDelPhotoInterface = anInterface;
        listContainer = LayoutInflater.from(mContext);
    }

    public void setShape(boolean shape) {
        this.shape = shape;
    }

    @Override
    public int getCount() {
        if (bmp.size() < 5) {
            return bmp.size() + 1;
        } else {
            return bmp.size();
        }
    }

    @Override
    public Object getItem(int position) {
        return null;
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    public void setSelectedPosition(int position) {
        selectedPosition = position;
    }

    public void setBitmapSieze(List<Bitmap> list) {
        bmp = list;
    }

    public List<Bitmap> setBitmapSieze() {
        return bmp;
    }

    public int getSelectedPosition() {
        return selectedPosition;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        final int sign = position;
        // 自定义视图
        ViewHolder holder = null;
        if (convertView == null) {
            holder = new ViewHolder();
            // 获取list_item布局文件的视图
            convertView = listContainer.inflate(
                    R.layout.item_published_grida, null);
            // 获取控件对象
            holder.image = (ImageView) convertView
                    .findViewById(R.id.item_grida_image);
            holder.bt = (Button) convertView
                    .findViewById(R.id.item_grida_bt);
            // 设置控件集到convertView
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }
        Log.i("", "adapter,bitmap-size:" + bmp.size());
        if (position == bmp.size()) {
            holder.image.setImageBitmap(BitmapFactory.decodeResource(
                    mContext.getResources(), R.drawable.icon_addpic_unfocused));
            holder.bt.setVisibility(View.GONE);
            if (position == 5) {
                holder.image.setVisibility(View.GONE);
            }
        } else {
            holder.image.setImageBitmap(bmp.get(position));
            holder.bt.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    gridViewDelPhotoInterface.deleteItemPhoto(position);
                }
            });
        }
        return convertView;
    }

    public class ViewHolder {
        public ImageView image;
        public Button bt;
    }
}
