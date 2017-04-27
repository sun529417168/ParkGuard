package cn.com.parkguard.adapter;

import android.content.Context;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.linked.erfli.library.base.MyBaseAdapter;

import java.util.ArrayList;
import java.util.List;

import cn.com.parkguard.R;
import cn.com.parkguard.bean.HomeBean;


/**
 * 文件名：HomeAdapter
 * 描    述：首页功能item适配器
 * 作    者：stt
 * 时    间：2017.4.6
 * 版    本：V1.1.2
 */

public class HomeAdapter extends MyBaseAdapter {
    private ArrayList<HomeBean> homeList = new ArrayList<>();


    public HomeAdapter(Context context, List list) {
        super(context, list);
        homeList = (ArrayList<HomeBean>) list;
    }

    @Override
    public int getContentView() {
        return R.layout.item_home;
    }

    @Override
    public void onInitView(View view, int position) {
        HomeBean homeBean = homeList.get(position);
        ImageView imageView = get(view, R.id.item_home_image);
        TextView name = get(view, R.id.item_home_name);

        imageView.setImageResource(homeBean.getImageView());
        name.setText(homeBean.getName());
    }
}
