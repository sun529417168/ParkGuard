package cn.com.watchman.utils;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.provider.Settings;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.linked.erfli.library.utils.DataCleanManager;

import cn.com.watchman.R;
import cn.com.watchman.interfaces.SexChooseInterface;


/**
 * 文件名：DialogUtils
 * 描    述：dialog工具类
 * 作    者：stt
 * 时    间：2017.01.10
 * 版    本：V1.0.0
 */

public class DialogUtils {

    /**
     * 方法名：showSexDialog
     * 功    能：弹出性别的dialog
     * 参    数：Context context
     * 返回值：Dialog
     */
    public static Dialog showSexDialog(final Activity context) {
        final SexChooseInterface sexChoose = (SexChooseInterface) context;
        Dialog mCameraDialog = new Dialog(context, R.style.sex_dialog);
        LinearLayout root = (LinearLayout) LayoutInflater.from(context).inflate(
                R.layout.dialog_sex, null);
        TextView men = (TextView) root.findViewById(R.id.dialog_sex_men);
        TextView woman = (TextView) root.findViewById(R.id.dialog_sex_woman);
        TextView cancle = (TextView) root.findViewById(R.id.dialog_sex_cancel);
        men.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                sexChoose.getSex(0);
            }
        });
        woman.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                sexChoose.getSex(1);
            }
        });
        cancle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                sexChoose.getSex(2);
            }
        });
        mCameraDialog.setContentView(root);
        Window dialogWindow = mCameraDialog.getWindow();
        dialogWindow.setGravity(Gravity.BOTTOM);
        dialogWindow.setWindowAnimations(R.style.sex_dialogStyle); // 添加动画
        WindowManager.LayoutParams lp = dialogWindow.getAttributes(); // 获取对话框当前的参数值
        lp.x = 0; // 新位置X坐标
        lp.y = -20; // 新位置Y坐标
        lp.width = context.getResources().getDisplayMetrics().widthPixels; // 宽度
//      lp.height = WindowManager.LayoutParams.WRAP_CONTENT; // 高度
//      lp.alpha = 9f; // 透明度
        root.measure(0, 0);
        lp.height = root.getMeasuredHeight();
        lp.alpha = 9f; // 透明度
        dialogWindow.setAttributes(lp);
        mCameraDialog.show();

        return mCameraDialog;
    }

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
     * 方法名：showGPSDialog
     * 功    能：打开GPS的dialog
     * 参    数：Context context, final TextView textView
     * 返回值：无
     */
    public static void showGPSDialog(final Context context) {
        AlertDialog.Builder normalDialog = new AlertDialog.Builder(context);
        normalDialog.setTitle("提示！");
        normalDialog.setMessage("是否打开GPS？");
        normalDialog.setPositiveButton("确定", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                Intent intent = new Intent();
                intent.setAction(android.provider.Settings.ACTION_LOCATION_SOURCE_SETTINGS);
                context.startActivity(intent);
            }
        });
        normalDialog.setNegativeButton("取消", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                //...To-do
            }
        });
        // 显示
        normalDialog.show();
    }
}
