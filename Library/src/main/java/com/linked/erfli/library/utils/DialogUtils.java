package com.linked.erfli.library.utils;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.provider.Settings;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.TextView;

import com.linked.erfli.library.R;

import static com.alibaba.sdk.android.ams.common.global.AmsGlobalHolder.getPackageName;

/**
 * 文件名：DialogUtils
 * 描    述：dialog工具类
 * 作    者：stt
 * 时    间：2017.01.10
 * 版    本：V1.0.0
 */

public class DialogUtils {


    /**
     * 方法名：closeActivity
     * 功    能：弹出是否关闭当前activity的dialog
     * 参    数：Context context
     * 返回值：无
     */
    public static void closeActivity(final Activity activity) {
        AlertDialog.Builder dialog = new AlertDialog.Builder(activity);
        dialog.setTitle("提示信息");
        dialog.setMessage("确定要退出么？");
        dialog.setPositiveButton("确定", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface arg0, int arg1) {
                activity.finish();
            }
        });
        dialog.setNeutralButton("取消",
                new DialogInterface.OnClickListener() {

                    @Override
                    public void onClick(DialogInterface arg0, int arg1) {
                        arg0.dismiss();
                    }
                });
        dialog.show();
    }

    /**
     * 点击设置系统信任
     *
     */
    public static void intentPermission(final Activity activity) {
        AlertDialog.Builder dialog = new AlertDialog.Builder(activity);
        dialog.setTitle("提示信息");
        dialog.setMessage("确定->权限管理->后台管理->允许后台运行");
        dialog.setPositiveButton("确定", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface arg0, int arg1) {
                getAppDetailSettingIntent(activity);
            }
        });
        dialog.setNeutralButton("取消",
                new DialogInterface.OnClickListener() {

                    @Override
                    public void onClick(DialogInterface arg0, int arg1) {
                        arg0.dismiss();
                    }
                });
        dialog.show();
    }

    /**
     * 跳转到当前app权限设置页面
     */
    public static void getAppDetailSettingIntent(Activity activity) {
        Intent localIntent = new Intent();
        localIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        if (Build.VERSION.SDK_INT >= 9) {
            localIntent.setAction("android.settings.APPLICATION_DETAILS_SETTINGS");
            localIntent.setData(Uri.fromParts("package", getPackageName(), null));
        } else if (Build.VERSION.SDK_INT <= 8) {
            localIntent.setAction(Intent.ACTION_VIEW);
            localIntent.setClassName("com.android.settings", "com.android.settings.InstalledAppDetails");
            localIntent.putExtra("com.android.settings.ApplicationPkgName", getPackageName());
        }
        activity.startActivity(localIntent);
    }


    /**
     * 方法名：showWaitDialog
     * 功    能：加载数据时的等待对话框
     * 参    数：Context context
     * 返回值：Dialog
     */
    public static Dialog showWaitDialog(final Context context) {
        Dialog dialog = new Dialog(context, R.style.DialogStyle);
        View view = LayoutInflater.from(context).inflate(R.layout.loading_dialog, null);
        Window window = dialog.getWindow();
        WindowManager.LayoutParams params = window.getAttributes();
        params.dimAmount = 1.0f; // 透明显示效果
        window.setAttributes(params);
        window.setLayout(WindowManager.LayoutParams.MATCH_PARENT, WindowManager.LayoutParams.MATCH_PARENT);
        dialog.show();
        dialog.setContentView(view);
        dialog.setCancelable(false);
        return dialog;
    }

    /**
     * 方法名：initGPS
     * 功    能：是否打开的GPS
     * 参    数：(final Activity activity)
     * 返回值：无
     */
    public static void initGPS(final Activity activity) {
        AlertDialog.Builder dialog = new AlertDialog.Builder(activity);
        dialog.setTitle("提示信息");
        dialog.setMessage("请开启手机GPS功能开关，点击[确定]按钮切换到GPS设置界面");
        dialog.setPositiveButton("确定", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface arg0, int arg1) {
                // 转到手机设置界面，用户设置GPS
                Intent intent = new Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS);
                activity.startActivity(intent); // 设置完成后返回到原来的界面
            }
        });
        dialog.setNeutralButton("取消",
                new DialogInterface.OnClickListener() {

                    @Override
                    public void onClick(DialogInterface arg0, int arg1) {
                        arg0.dismiss();
                    }
                });
        dialog.show();
    }

    /**
     * 方法名：clearData
     * 功    能：清楚缓存
     * 参    数：Context context, final TextView textView
     * 返回值：无
     */
    public static void clearData(final Context context, final TextView textView) {
        AlertDialog.Builder dialog = new AlertDialog.Builder(context);
        dialog.setTitle("提示信息");
        dialog.setMessage("确定要清楚缓存么？");
        dialog.setPositiveButton("确定", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface arg0, int arg1) {
                DataCleanManager.clearAllCache(context);
                textView.setText("0KB");
            }
        });
        dialog.setNeutralButton("取消",
                new DialogInterface.OnClickListener() {

                    @Override
                    public void onClick(DialogInterface arg0, int arg1) {
                        arg0.dismiss();
                    }
                });
        dialog.show();
    }


    /**
     * 方法名：showTaskNameDialog
     * 功    能：弹出任务名称的dialog
     * 参    数：Activity activity, String taskName
     * 返回值：
     */
    public static void showTaskNameDialog(Activity activity, String taskName) {
        final Dialog mDialog = new Dialog(activity, R.style.DialogStyle);
        View root = LayoutInflater.from(activity).inflate(R.layout.dialog_detail_task_name, null);
        TextView name = (TextView) root.findViewById(R.id.dialog_detail_taskName);
        Button close = (Button) root.findViewById(R.id.dialog_detail_task_close);
        name.setText(taskName);
        close.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mDialog.dismiss();
            }
        });
        Window window = mDialog.getWindow();
        WindowManager.LayoutParams params = window.getAttributes();
        params.dimAmount = 1.0f; // 透明显示效果
        window.setAttributes(params);
        window.setLayout(WindowManager.LayoutParams.MATCH_PARENT, WindowManager.LayoutParams.MATCH_PARENT);
        mDialog.show();
        mDialog.setContentView(root);
        mDialog.setCancelable(true);
    }

}
