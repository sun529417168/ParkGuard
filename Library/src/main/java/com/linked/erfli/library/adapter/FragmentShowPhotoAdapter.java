package com.linked.erfli.library.adapter;

import android.content.Context;
import android.support.v4.view.PagerAdapter;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.bumptech.glide.Glide;
import com.linked.erfli.library.R;

import java.util.ArrayList;

import uk.co.senab.photoview.PhotoView;

/**
 * Created by 志强 on 2017.5.4.
 */

public class FragmentShowPhotoAdapter extends PagerAdapter {
    private Context mContext;
    private ArrayList<String> mDatas;
    private LayoutInflater layoutInflater;
    private View mCurrentView;
    private int types;

    public FragmentShowPhotoAdapter(Context context, int type, ArrayList<String> datas) {
        mContext = context;
        mDatas = datas;
        types = type;
        layoutInflater = LayoutInflater.from(this.mContext);
    }

    @Override
    public int getCount() {
        return mDatas.size();
    }


    @Override
    public void setPrimaryItem(ViewGroup container, int position, Object object) {
        super.setPrimaryItem(container, position, object);
        mCurrentView = (View) object;
    }

    public View getPrimaryItem() {
        return mCurrentView;
    }

    @Override
    public View instantiateItem(ViewGroup container, int position) {
        final String imageUrl;
        if (types == 1) {
            imageUrl = mDatas.get(position);
        } else {
            imageUrl = "file://" + mDatas.get(position);
        }

        View view = layoutInflater.inflate(R.layout.item_showphoto, container, false);
        PhotoView imageView = (PhotoView) view.findViewById(R.id.img);
        Glide.with(mContext)
                .load(imageUrl)
                .thumbnail(0.2f)
                .into(imageView);
        container.addView(view);
        return view;
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        container.removeView((View) object);
    }

    @Override
    public boolean isViewFromObject(View view, Object object) {
        return view == object;
    }
}
