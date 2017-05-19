package com.linked.erfli.library.fragment;

import android.graphics.Bitmap;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.linked.erfli.library.R;
import com.linked.erfli.library.adapter.FragmentShowPhotoAdapter;
import com.linked.erfli.library.base.PhotoBaseFragment;
import com.linked.erfli.library.interfaces.ImageDownLoadCallBack;
import com.linked.erfli.library.interfaces.ShowPhotoInterfaces;
import com.linked.erfli.library.service.DownloadImageService;
import com.linked.erfli.library.utils.FileUtils;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

import uk.co.senab.photoview.PhotoView;


/**
 * Created by 志强 on 2017.5.4.
 */

public class ShowPhotoFragment extends PhotoBaseFragment implements ViewPager.OnPageChangeListener {
    private ViewPager view_pager;
    private FragmentShowPhotoAdapter mAdapter;
    private LinearLayout rootView;
    private ArrayList<String> datas;
    private int current, type;
    View mView;
    ShowPhotoInterfaces showPhotoInterfaces;

    public int numbers = 0;

    private Handler handler = new Handler() {
        public void handleMessage(android.os.Message msg) {
            switch (msg.what) {
                // case 等于1表示是正确
                case 1:
                    Bitmap bi = (Bitmap) msg.obj;
                    SimpleDateFormat formatter = new SimpleDateFormat("yyyy年MM月dd日   HH:mm:ss");
                    Date curDate = new Date(System.currentTimeMillis());
                    String str = formatter.format(curDate);
                    boolean b = FileUtils.saveBitmap(bi, str);
                    if (b) {
                        Toast.makeText(mActivity, "图片保存到" + FileUtils.SDPATH + "文件夹下", Toast.LENGTH_SHORT).show();
                    } else {
                        Toast.makeText(mActivity, "保存失败", Toast.LENGTH_SHORT).show();
                    }
                    break;
                case -1:
                    Toast.makeText(mActivity, "保存失败", Toast.LENGTH_SHORT).show();
                    break;
                default:
                    break;
            }
        }

        ;
    };

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        mView = super.onCreateView(inflater, container, savedInstanceState);
        showPhotoInterfaces = (ShowPhotoInterfaces) inflater.getContext();
        return mView;
    }

    @Override
    protected void initView(View view, Bundle savedInstanceState) {
        rootView = (LinearLayout) view.findViewById(R.id.rootView);
        view_pager = (ViewPager) view.findViewById(R.id.view_pager);
        Bundle bundle = getArguments();
        if (bundle != null) {
            type = bundle.getInt("type");
            datas = bundle.getStringArrayList("listPath");
            current = bundle.getInt("current");
        }
        mAdapter = new FragmentShowPhotoAdapter(mActivity, type, datas);
        view_pager.setAdapter(mAdapter);
        view_pager.setCurrentItem(current);
        view_pager.setOnPageChangeListener(this);

    }


    public static ShowPhotoFragment newInstance(int type, ArrayList<String> datas, int current) {
        Bundle bundle = new Bundle();
        ShowPhotoFragment fragment = new ShowPhotoFragment();
        bundle.putInt("type", type);
        bundle.putStringArrayList("listPath", datas);
        bundle.putInt("current", current);
        fragment.setArguments(bundle);
        return fragment;
    }

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_showphoto;
    }

    /**
     * 保存图片方法
     */

    public void saveGirl() {

        DownloadImageService service = new DownloadImageService(getContext(),
                datas.get(view_pager.getCurrentItem()),
                new ImageDownLoadCallBack() {
                    Message message = handler.obtainMessage();

                    @Override
                    public void onDownLoadSuccess(File file) {
                    }

                    @Override
                    public void onDownLoadSuccess(Bitmap bitmap) {
                        message.obj = bitmap;
                        message.what = 1;
                        handler.sendMessage(message);
                    }

                    @Override
                    public void onDownLoadFailed() {
                        // 图片保存失败
                        message.what = -1;
                        handler.sendMessage(message);
                    }
                });
        //启动图片下载线程
        new Thread(service).start();
    }

    View view;

    /**
     * 图片旋转方法
     */
    public void photoRotate(int number) {
        view = getCurrentImageView();
        numbers = number;
        //设置图片旋转
        view.animate().rotation(90 * number);

    }

    private PhotoView getCurrentImageView() {
        View currentItem = mAdapter.getPrimaryItem();
        if (currentItem == null) {
            return null;
        }
        PhotoView imageView = (PhotoView) currentItem.findViewById(R.id.img);
        if (imageView == null) {
            return null;
        }
        return imageView;
    }

    @Override
    public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
        showPhotoInterfaces.setTitleValue(position, datas.size());
        showPhotoInterfaces.setNumber();

    }

    @Override
    public void onPageSelected(int position) {

    }

    @Override
    public void onPageScrollStateChanged(int state) {
        //大于等于1表示 之前触发旋转操作,viewpage滑动 旋转复位操作
        if (numbers >= 1) {
            view.animate().rotation(0);
        }
    }
}
