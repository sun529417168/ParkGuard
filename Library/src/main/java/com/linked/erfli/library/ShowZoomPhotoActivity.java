package com.linked.erfli.library;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;

import com.linked.erfli.library.base.PhotoAppActivity;
import com.linked.erfli.library.base.PhotoBaseFragment;
import com.linked.erfli.library.fragment.ShowPhotoFragment;
import com.linked.erfli.library.interfaces.ShowPhotoInterfaces;
import com.linked.erfli.library.utils.ActivityManagerUtils;
import com.linked.erfli.library.utils.StatusBarUtil;

/**
 * Created by 志强 on 2017.5.4.
 */

public class ShowZoomPhotoActivity extends PhotoAppActivity implements ShowPhotoInterfaces {

    private View mViewNeedOffset;
    private ShowPhotoFragment showPhotoFragment;
    private ImageButton img_photo_back, img_photo_download, img_photo_rotate;
    private TextView tv_photo_title;

    @Override
    protected int getContentViewId() {
        return R.layout.showphoto_activity;
    }

    @Override
    protected int getFragmentContentId() {
        return R.id.girl_fragment;
    }

    @Override
    protected PhotoBaseFragment getFirstFragment() {
        showPhotoFragment = ShowPhotoFragment.newInstance(getIntent().getIntExtra("type", 0), getIntent().getStringArrayListExtra("listPath"), getIntent().getIntExtra("current", 0));
        return showPhotoFragment;
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        init();
    }

    private void init() {
        mViewNeedOffset = findViewById(R.id.view_need_offset);
        StatusBarUtil.setTranslucentForImageView(this, 0, mViewNeedOffset);
        img_photo_back = (ImageButton) findViewById(R.id.img_photo_back);
        img_photo_back.setOnClickListener(this);
        tv_photo_title = (TextView) findViewById(R.id.tv_photo_title);
        img_photo_rotate = (ImageButton) findViewById(R.id.img_photo_rotate);
        img_photo_rotate.setOnClickListener(this);
        img_photo_download = (ImageButton) findViewById(R.id.img_photo_download);
        img_photo_download.setOnClickListener(this);
        //隐藏下载按钮
        if (getIntent().getIntExtra("type", 0) != 1) {
            img_photo_download.setVisibility(View.GONE);
        }

    }

    int number = 0;

    @Override
    public void onClick(View v) {
        super.onClick(v);
        int i = v.getId();
        if (i == R.id.img_photo_back) {
            //关闭当前activity
            ActivityManagerUtils.getInstance().finishActivity();
        } else if (i == R.id.img_photo_rotate) {
            number++;
            showPhotoFragment.photoRotate(number);
        } else if (i == R.id.img_photo_download) {
            showPhotoFragment.saveGirl();
        }
    }

    @Override
    public void setTitleValue(int c, int con) {
        tv_photo_title.setText(c + 1 + "/" + con);
    }

    @Override
    public void setNumber() {
        number = 0;
    }

}
