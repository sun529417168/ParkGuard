package cn.com.notice.adapter;

import android.content.Context;
import android.content.Intent;
import android.view.View;
import android.widget.ImageView;

import com.linked.erfli.library.ShowZoomPhotoActivity;
import com.linked.erfli.library.base.MyBaseAdapter;
import com.nostra13.universalimageloader.core.ImageLoader;

import java.util.ArrayList;
import java.util.List;

import cn.com.notice.R;


/**
 * 文件名：NoticeDetalPhotoAdapter
 * 描    述：通知详情图片的适配器
 * 作    者：stt
 * 时    间：2017.3.15
 * 版    本：V1.0.6
 */

public class NoticeDetalPhotoAdapter extends MyBaseAdapter {
    private ArrayList<String> list = new ArrayList<>();

    public NoticeDetalPhotoAdapter(Context context, List list) {
        super(context, list);
        this.list = (ArrayList<String>) list;
    }

    @Override
    public int getContentView() {
        return R.layout.item_gridview_photo;
    }

    @Override
    public void onInitView(View view, int position) {
        ImageView imageView = get(view, R.id.item_grid_image);
        ImageLoader.getInstance().displayImage(list.get(position), imageView);
        imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent in = new Intent(context, ShowZoomPhotoActivity.class);
                in.putExtra("type", 1);
                in.putStringArrayListExtra("listPath", list);
                context.startActivity(in);
            }
        });
    }

}
