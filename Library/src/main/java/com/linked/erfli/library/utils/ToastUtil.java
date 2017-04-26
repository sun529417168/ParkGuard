package com.linked.erfli.library.utils;

import android.content.Context;
import android.widget.Toast;

/**
 * 文件名：ToastUtil
 * 描    述：Toast工具类
 * 作    者：stt
 * 时    间：2016.12.30
 * 版    本：V1.0.0
 */

public class ToastUtil {
    /**
     * 方法名：show
     * 功    能：吐司功能
     * 参    数：(Context context, String message)
     * 返回值：无
     */
    public static void show(Context context, String message) {
        Toast.makeText(context, message, Toast.LENGTH_SHORT).show();

    }
}
