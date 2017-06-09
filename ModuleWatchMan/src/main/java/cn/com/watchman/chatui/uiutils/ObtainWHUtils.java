package cn.com.watchman.chatui.uiutils;

import android.content.Context;
import android.util.DisplayMetrics;

/**
 * Created by 志强 on 2017.6.9.
 */

public class ObtainWHUtils {

    /**
     * 初始化当前设备屏幕宽高
     */
    public static int initScreenWidth(Context context) {
        DisplayMetrics curMetrics = context.getApplicationContext().getResources().getDisplayMetrics();
        return curMetrics.widthPixels;
    }

    public static int initScreenHeight(Context context) {
        DisplayMetrics curMetrics = context.getApplicationContext().getResources().getDisplayMetrics();
        return curMetrics.heightPixels;
    }

    public static float initScreenDensity(Context context) {
        DisplayMetrics curMetrics = context.getApplicationContext().getResources().getDisplayMetrics();
        return curMetrics.density;
    }
}
