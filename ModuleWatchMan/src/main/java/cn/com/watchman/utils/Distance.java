package cn.com.watchman.utils;

import android.app.Activity;
import android.content.Context;
import android.renderscript.Double2;
import android.text.TextUtils;
import android.util.Log;

import com.alibaba.fastjson.JSONObject;
import com.amap.api.location.AMapLocation;
import com.amap.api.maps.AMapUtils;
import com.amap.api.maps.model.LatLng;
import com.linked.erfli.library.utils.SharedUtil;
import com.linked.erfli.library.utils.ToastUtil;

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
     * 功    能：判断是否大于20米
     * 参    数：Activity activity, GPSBean gpsBean
     * 返回值：boolean
     */
    public static boolean isCompare(Context activity, GPSBean gpsBean) {
        double compare1 = getDistance(gpsBean.getLongitude(), gpsBean.getLatitude(), TextUtils.isEmpty(SharedUtil.getString(activity, "longitude")) ? gpsBean.getLongitude() : Double.parseDouble(SharedUtil.getString(activity, "longitude")), TextUtils.isEmpty(SharedUtil.getString(activity, "latitude")) ? gpsBean.getLatitude() : Double.parseDouble(SharedUtil.getString(activity, "latitude")));
        double compare2 = 10;
        Double double1 = new Double(String.valueOf(compare1));
        Double double2 = new Double(String.valueOf(compare2));
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

    /**
     * 方法名：isCompareGD
     * 功    能：判断是否大于20米,高德的判断方法
     * 参    数：Activity activity, GPSBean gpsBean
     * 返回值：boolean
     */
    public static boolean isCompareGD(Context activity, GPSBean gpsBean) {
        LatLng start = new LatLng(gpsBean.getLatitude(), gpsBean.getLongitude());
        LatLng end = new LatLng(TextUtils.isEmpty(SharedUtil.getString(activity, "latitude")) ? 0 : Double.parseDouble(SharedUtil.getString(activity, "latitude")), TextUtils.isEmpty(SharedUtil.getString(activity, "longitude")) ? 0 : Double.parseDouble(SharedUtil.getString(activity, "longitude")));
        float retval = AMapUtils.calculateLineDistance(start, end);
        if (retval > 10) {
            Log.i("看看距离", "大于10米");
            return true;
        } else if (retval < 10) {
            Log.i("看看距离", "小于10米");
            return false;
        } else {
            Log.i("看看距离", "等于10米");
            return true;
        }
    }

    /**
     * 方法名：isCompare
     * 功    能：判断是否大于20米
     * 参    数：Activity activity, GPSBean gpsBean
     * 返回值：boolean
     */
    public static boolean isCompareTwo(Context activity, AMapLocation amapLocation) {
        double compare1 = getDistance(amapLocation.getLongitude(), amapLocation.getLatitude(), TextUtils.isEmpty(SharedUtil.getString(activity, "ReLongitude")) ? amapLocation.getLongitude() : Double.parseDouble(SharedUtil.getString(activity, "ReLongitude")), TextUtils.isEmpty(SharedUtil.getString(activity, "ReLatitude")) ? amapLocation.getLatitude() : Double.parseDouble(SharedUtil.getString(activity, "ReLatitude")));
        double compare2 = 20;
        Double double1 = new Double(String.valueOf(compare1));
        Double double2 = new Double(String.valueOf(compare2));
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

    /**
     * 方法名：isCompare
     * 功    能：判断是否大于20米
     * 参    数：Activity activity, GPSBean gpsBean
     * 返回值：boolean
     */
    public static boolean isCompareTwoLa(Context activity, GPSBean amapLocation) {
        double compare1 = getDistance(amapLocation.getLongitude(), amapLocation.getLatitude(), TextUtils.isEmpty(SharedUtil.getString(activity, "ReLongitude")) ? amapLocation.getLongitude() : Double.parseDouble(SharedUtil.getString(activity, "ReLongitude")), TextUtils.isEmpty(SharedUtil.getString(activity, "ReLatitude")) ? amapLocation.getLatitude() : Double.parseDouble(SharedUtil.getString(activity, "ReLatitude")));
        double compare2 = 20;
        Double double1 = new Double(String.valueOf(compare1));
        Double double2 = new Double(String.valueOf(compare2));
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


    /**
     * 方法名：isCompare
     * 功    能：判断是否大于80米
     * 参    数：Activity activity, GPSBean gpsBean
     * 返回值：boolean
     */
    public static boolean isCompare(Activity activity, GPSBean gpsBean, String resultString) {
        JSONObject jsonObject = JSONObject.parseObject(resultString);
        double longitude = jsonObject.getDouble("Longitude");
        double latitude = jsonObject.getDouble("Latitude");
        double compare1 = getDistance(gpsBean.getLongitude(), gpsBean.getLatitude(), longitude, latitude);
        double compare2 = 80;
        Double double1 = new Double(String.valueOf(compare1));
        Double double2 = new Double(String.valueOf(compare2));
        Log.i("实际距离", "距离1==" + compare1 + "距离2==" + compare2 + "///" + double1 + "---------" + double2);
        Log.i("实际距离2", gpsBean.toString());
        int retval = double1.compareTo(double2);
        if (retval < 0) {
            return true;
        } else {
            return false;
        }
    }
}
