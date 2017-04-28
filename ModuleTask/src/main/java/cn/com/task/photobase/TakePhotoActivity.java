package cn.com.task.photobase;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;

import com.linked.erfli.library.base.BaseActivity;


/**
 * 文件名：TakePhotoActivity
 * 描    述：继承这个类来让Activity获取拍照的能力<br>
 * 作    者：stt
 * 时    间：2016.11.17 14:48
 * 版    本：V1.0.0
 */
public class TakePhotoActivity extends BaseActivity implements TakePhoto.TakeResultListener {
    private TakePhoto takePhoto;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        getTakePhoto().onCreate(savedInstanceState);
        super.onCreate(savedInstanceState);
    }

    @Override
    protected void setView() {

    }

    @Override
    protected void setDate(Bundle savedInstanceState) {

    }

    @Override
    protected void init() {

    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        getTakePhoto().onSaveInstanceState(outState);
        super.onSaveInstanceState(outState);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        getTakePhoto().onActivityResult(requestCode, resultCode, data);
        super.onActivityResult(requestCode, resultCode, data);
    }

    /**
     * 获取TakePhoto实例
     *
     * @return
     */
    public TakePhoto getTakePhoto() {
        if (takePhoto == null) {
            takePhoto = new TakePhotoImpl(this, this);
        }
        return takePhoto;
    }

    @Override
    public void takeSuccess(String imagePath) {
        Log.i("info", "takeSuccess：" + imagePath);
    }

    @Override
    public void takeFail(String msg) {
        Log.w("info", "takeFail:" + msg);
    }

    @Override
    public void takeCancel() {
        Log.w("info", "用户取消");
    }
}
