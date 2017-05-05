package cn.com.task.adapter;

import android.content.Context;
import android.content.Intent;
import android.view.View;
import android.widget.ImageView;

import com.linked.erfli.library.ShowZoomPhotoActivity;
import com.linked.erfli.library.base.MyBaseAdapter;
import com.nostra13.universalimageloader.core.ImageLoader;

import java.util.ArrayList;
import java.util.List;

import cn.com.task.R;


/**
 * 文件名：TaskDetalPhotoAdapter
 * 描    述：任务详情图片的适配器
 * 作    者：stt
 * 时    间：2017.1.9
 * 版    本：V1.0.0
 */

public class TaskDetalDescribePhotoAdapter extends MyBaseAdapter {
    private ArrayList list = new ArrayList<>();

    public TaskDetalDescribePhotoAdapter(Context context, List list) {
        super(context, list);
        this.list = (ArrayList) list;
    }

    @Override
    public int getContentView() {
        return R.layout.task_item_gridview_photo;
    }

    @Override
    public void onInitView(View view, final int position) {
        ImageView imageView = get(view, R.id.item_grid_image);
        ImageLoader.getInstance().displayImage((String) list.get(position), imageView);
        imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent in = new Intent(context, ShowZoomPhotoActivity.class);
                in.putExtra("type", 1);
                in.putStringArrayListExtra("listPath", list);
                in.putExtra("current", position);
                context.startActivity(in);
            }
        });
    }

}
