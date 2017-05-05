package com.linked.erfli.library.interfaces;

import android.graphics.Bitmap;

import java.io.File;

/**
 * Created by 志强 on 2017.5.5.
 */

public interface ImageDownLoadCallBack {
    void onDownLoadSuccess(File file);

    void onDownLoadSuccess(Bitmap bitmap);

    void onDownLoadFailed();
}
