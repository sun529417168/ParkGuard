package com.linked.erfli.library;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.linked.erfli.library.base.BaseActivity;
import com.linked.erfli.library.utils.StatusBarUtils;

import java.io.BufferedInputStream;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;


/**
 * Created by 志强 on 2017.5.2.
 */

public class ShowPhotoActivity extends BaseActivity implements ViewPager.OnPageChangeListener {
    /**
     * ViewPager
     */
    private ViewPager viewPager;

    /**
     * 装点点的ImageView数组
     */
    private ImageView[] tips;

    /**
     * 装ImageView数组
     */
    private ImageView[] mImageViews;
    private ImageView mImageViewObj;


    private ArrayList<Bitmap> list = new ArrayList<Bitmap>();
    private ArrayList listPath = new ArrayList();
    private int imageType = -1;
    private Handler mHandler = new Handler() {
        public void handleMessage(Message msg) {
            switch (msg.what) {
                // 正在下载
                case 1:
                    ImageView imageView = (ImageView) msg.obj;
                    Bitmap bitmap = msg.getData().getParcelable("bitmap");
                    imageView.setImageBitmap(bitmap);
                    break;
                default:
                    break;
            }
        }

        ;
    };


    @Override
    protected void setView() {
        setContentView(R.layout.showphoto);
        StatusBarUtils.ff(ShowPhotoActivity.this, R.color.black);
    }

    @Override
    protected void setDate(Bundle savedInstanceState) {
        imageType = getIntent().getIntExtra("type", 0);
        if (imageType == 1) {
            listPath = getIntent().getStringArrayListExtra("listPath");
        } else {
            listPath = getIntent().getStringArrayListExtra("listPath");
            for (int i = 0; i < listPath.size(); i++) {
                BitmapFactory.Options option = new BitmapFactory.Options();
                option.inSampleSize = 2;
                Bitmap bitmap = BitmapFactory.decodeFile((String) listPath.get(i), option);
                list.add(bitmap);
            }
        }
    }

    public void getBitmap(final String url, ImageView imageView) {
        Bitmap bm = null;
        try {
            URL iconUrl = new URL(url);
            URLConnection conn = iconUrl.openConnection();
            HttpURLConnection http = (HttpURLConnection) conn;
            int length = http.getContentLength();
            conn.connect();
            // 获得图像的字符流
            InputStream is = conn.getInputStream();
            BufferedInputStream bis = new BufferedInputStream(is, length);
            bm = BitmapFactory.decodeStream(bis);
            bis.close();
            is.close();// 关闭流
            Message msg = mHandler.obtainMessage();
            msg.what = 1;
            msg.obj = imageView;
            Bundle data = new Bundle();
            data.putParcelable("bitmap", bm);
            msg.setData(data);
            mHandler.sendMessage(msg);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    protected void init() {
        ViewGroup group = (ViewGroup) findViewById(R.id.activity_detailImage_viewGroup);
        viewPager = (ViewPager) findViewById(R.id.activity_detailImage_viewPager);
        // 将点点加入到ViewGroup中
        tips = new ImageView[listPath.size()];
        for (int i = 0; i < tips.length; i++) {
            ImageView imageView = new ImageView(this);
            imageView.setLayoutParams(new ViewGroup.LayoutParams(10, 10));
            tips[i] = imageView;
            if (i == 0) {
                tips[i].setBackgroundResource(R.mipmap.item_yuan_yes);
            } else {
                tips[i].setBackgroundResource(R.mipmap.item_yuan_no);
            }

            LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(
                    new ViewGroup.LayoutParams(ViewPager.LayoutParams.WRAP_CONTENT,
                            ViewPager.LayoutParams.WRAP_CONTENT));
            layoutParams.leftMargin = 5;
            layoutParams.rightMargin = 5;
            group.addView(imageView, layoutParams);
        }

        if (imageType == 1) {
            mImageViews = new ImageView[listPath.size()];
            for (int i = 0; i < listPath.size(); i++) {
                final ImageView imageView = new ImageView(this);
                mImageViews[i] = imageView;
                final int finalI = i;
                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        getBitmap((String) listPath.get(finalI), imageView);
                    }
                }).start();
            }
        } else {
            // 将图片装载到数组中
            mImageViews = new ImageView[list.size()];
            for (int i = 0; i < mImageViews.length; i++) {
                ImageView imageView = new ImageView(this);
                mImageViews[i] = imageView;
                imageView.setImageBitmap(list.get(i));
            }
        }

        // 设置Adapter
        viewPager.setAdapter(new MyAdapter());
        // 设置监听，主要是设置点点的背景
        viewPager.addOnPageChangeListener(this);
        // 设置ViewPager的默认项, 设置为长度的100倍，这样子开始就能往左滑动
//        viewPager.setCurrentItem((mImageViews.length) * 100);
    }

    /**
     * 文件名：MyAdapter
     * 描    述：图片滑动的适配器
     * 作    者：stt
     * 时    间：2016.12.06
     * 版    本：V1.0.0
     */
    public class MyAdapter extends PagerAdapter {

        @Override
        public int getCount() {
            return mImageViews.length;
        }

        @Override
        public boolean isViewFromObject(View arg0, Object arg1) {
            return arg0 == arg1;
        }

        @Override
        public void destroyItem(ViewGroup container, int position, Object object) {
            container.removeView(mImageViews[position]);
        }

        /**
         * 载入图片进去，用当前的position 除以 图片数组长度取余数是关键
         */
        @Override
        public Object instantiateItem(ViewGroup container, int position) {
            container.addView(mImageViews[position], 0);
            return mImageViews[position];
        }

    }

    @Override
    public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

    }

    @Override
    public void onPageSelected(int position) {
        setImageBackground(position % mImageViews.length);
    }

    @Override
    public void onPageScrollStateChanged(int state) {

    }

    /**
     * 设置选中的tip的背景
     *
     * @param selectItems
     */
    private void setImageBackground(int selectItems) {
        for (int i = 0; i < tips.length; i++) {
            if (i == selectItems) {
                tips[i].setBackgroundResource(R.mipmap.item_yuan_yes);
            } else {
                tips[i].setBackgroundResource(R.mipmap.item_yuan_no);
            }
        }
    }
}
