package com.linked.erfli.library.adapter;

import android.content.Context;
import android.graphics.Bitmap;
import android.view.View;
import android.widget.ImageView;

import com.linked.erfli.library.R;
import com.linked.erfli.library.base.MyBaseAdapter;

import java.util.ArrayList;
import java.util.List;


/**
 * 文件名：TaskDetalPhotoAdapter
 * 描    述：任务详情图片的适配器
 * 作    者：stt
 * 时    间：2017.1.9
 * 版    本：V1.0.0
 */

public class TaskDetalPhotoAdapter extends MyBaseAdapter {
    private ArrayList<Bitmap> list = new ArrayList<Bitmap>();

    public TaskDetalPhotoAdapter(Context context, List list) {
        super(context, list);
        this.list = (ArrayList<Bitmap>) list;
    }

    @Override
    public int getContentView() {
        return R.layout.item_gridview_photo;
    }

    @Override
    public void onInitView(View view, int position) {
        ImageView imageView = get(view, R.id.item_grid_image);
        imageView.setImageBitmap(list.get(position));
    }

    /**
     * 获取列表数据
     *
     * @param list
     */
    public void setList(ArrayList<Bitmap> list) {
        this.list = list;
        this.notifyDataSetChanged();
    }
}
