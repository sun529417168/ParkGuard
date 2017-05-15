package cn.com.watchman.utils;

import android.app.Activity;
import android.renderscript.Double2;
import android.text.TextUtils;
import android.util.Log;

import com.linked.erfli.library.utils.SharedUtil;

import cn.com.watchman.bean.GPSBean;

/**
 * 文件名：Distance
 * 描    述：计算两点距离的工具类
 * 作    者：stt
 * 时    间：2017.05.12
 * 版    本：V1.0.0
 */

public class Distance {
    private static final double EARTH_RADIUS = 6378137.0;

    /**
     * 方法名：getDistance
     * 功    能：计算两点的米数
     * 参    数：double longitude1, double latitude1, double longitude2, double latitude2
     * 返回值：double
     */
    public static double getDistance(double longitude1, double latitude1, double longitude2, double latitude2) {
        double Lat1 = rad(latitude1);
        double Lat2 = rad(latitude2);
        double a = Lat1 - Lat2;
        double b = rad(longitude1) - rad(longitude2);
        double s = 2 * Math.asin(Math.sqrt(Math.pow(Math.sin(a / 2), 2) + Math.cos(Lat1) * Math.cos(Lat2) * Math.pow(Math.sin(b / 2), 2)));
        s = s * EARTH_RADIUS;
        s = Math.round(s * 10000) / 10000;
        return s;
    }

    private static double rad(double d) {
        return d * Math.PI / 180.0;
    }

    /**
     * 方法名：isCompare
     * 功    能：判断是否大于10米
     * 参    数：Activity activity, GPSBean gpsBean
     * 返回值：boolean
     */
    public static boolean isCompare(Activity activity, GPSBean gpsBean) {
        double compare1 = getDistance(gpsBean.getLongitude(), gpsBean.getLatitude(), TextUtils.isEmpty(SharedUtil.getString(activity, "longitude")) ? gpsBean.getLongitude() : Double.parseDouble(SharedUtil.getString(activity, "longitude")), TextUtils.isEmpty(SharedUtil.getString(activity, "latitude")) ? gpsBean.getLatitude() : Double.parseDouble(SharedUtil.getString(activity, "latitude")));
        double compare2 = 10;
        Double double1 = new Double(String.valueOf(compare1));
        Double double2 = new Double(String.valueOf(compare2));
        Log.i("实际距离", "距离1==" + compare1 + "距离2==" + compare2 + "///" + double1 + "---------" + double2);
        Log.i("实际距离2", gpsBean.toString());
        Log.i("实际距离3", SharedUtil.getString(activity, "longitude") + "-----" + SharedUtil.getString(activity, "latitude"));
        int retval = double1.compareTo(double2);
        if (retval > 0) {
            Log.i("看看距离", "大于10米");
            return true;
        } else if (retval < 0) {
            Log.i("看看距离", "小于10米");
            return false;
        } else {
            Log.i("看看距离", "等于10米");
            return true;
        }
    }
}
