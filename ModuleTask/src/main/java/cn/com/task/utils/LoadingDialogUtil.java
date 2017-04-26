package cn.com.task.utils;

import android.app.ProgressDialog;
import android.content.Context;

import cn.com.task.R;

/**
 * 类  名:LoadingDialogUtil
 * 描  述:任务加载提示框
 * 作  者:zzq
 * 时  间:2017年4月26日
 * 版  本:V1.0,0
 */

public class LoadingDialogUtil {
    private static ProgressDialog dialog;

    /**
     * @param context 当前页面
     * @return 返回加载框
     */
    public static ProgressDialog show(Context context) {
        if (dialog != null && dialog.isShowing()) {
            return dialog;
        }
        dialog = new ProgressDialog(context);
        dialog.setMessage("加载中...");
        // dialog.setCancelable(false);
        dialog.setCanceledOnTouchOutside(false);
        dialog.setProgressStyle(R.color.colorPrimary);
        dialog.show();
        return dialog;
    }

    /**
     * @param context 当前页面
     * @param str     提示内容
     * @return 返回加载框
     */
    public static ProgressDialog show(Context context, String str) {
        if (dialog != null && dialog.isShowing()) {
            return dialog;
        }
        dialog = new ProgressDialog(context);
        dialog.setMessage(str + "...");
        // dialog.setCancelable(false);
        dialog.setCanceledOnTouchOutside(false);
        dialog.show();
        return dialog;
    }

    public static void dismiss() {
        if (dialog != null && dialog.isShowing()) {
            dialog.dismiss();
        }
    }
}
