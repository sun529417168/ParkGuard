package com.linked.erfli.library.utils;

import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.location.Criteria;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;

import com.linked.erfli.library.interfaces.GetGPSInterface;

import java.io.BufferedReader;
import java.io.File;
import java.io.InputStreamReader;
import java.math.BigDecimal;
import java.text.ParseException;
import java.text.ParsePosition;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.TimeZone;
import java.util.Vector;

import static android.content.Context.LOCATION_SERVICE;


/**
 * 文件名：MyUtils
 * 描    述：工具类
 * 作    者：stt
 * 时    间：2017.01.04
 * 版    本：V1.0.0
 */

public class MyUtils {
    /**
     * 方法名：getFromAssets
     * 功    能：获取assets的数据
     * 参    数：Context context,String fileName
     * 返回值：String
     */
    public static String getFromAssets(Context context, String fileName) {
        try {
            InputStreamReader inputReader = new InputStreamReader(context.getResources().getAssets().open(fileName));
            BufferedReader bufReader = new BufferedReader(inputReader);
            String line = "";
            String Result = "";
            while ((line = bufReader.readLine()) != null)
                Result += line;
            return Result;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return "";
    }

    /**
     * 方法名：GetVideoFileName
     * 功    能：获取SD卡目录下的子目录，循环取出
     * 参    数：Context context,String fileName
     * 返回值：String
     */
    public static Vector<String> getVideoFileName(String fileAbsolutePath) {
        Vector<String> vecFile = new Vector<String>();
        File file = new File(fileAbsolutePath);
        // 判断文件目录是否存在
        if (!file.exists()) {
            file.mkdir();
        }
        File[] subFile = file.listFiles();
        for (int iFileLength = 0; iFileLength < subFile.length; iFileLength++) {
            // 判断是否为文件夹
            if (!subFile[iFileLength].isDirectory()) {
                String filename = subFile[iFileLength].getName();
                // 判断是否为xlsx结尾
//                if (filename.trim().toLowerCase().endsWith(".xlsx")) {
                vecFile.add(filename);
//                }
            }
        }
        return vecFile;
    }

    /**
     * 方法名：gpsIsOPen
     * 功    能：判断GPS是否打开
     * 参    数：Context context
     * 返回值：boolean
     * 作    者：stt
     * 时    间：2017.2.21
     */
    public static boolean gpsIsOPen(Context context) {
        LocationManager locationManager = (LocationManager) context.getSystemService(LOCATION_SERVICE);
        // 通过GPS卫星定位，定位级别可以精确到街（通过24颗卫星定位，在室外和空旷的地方定位准确、速度快）
        boolean gps = locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER);
        // 通过WLAN或移动网络(3G/2G)确定的位置（也称作AGPS，辅助GPS定位。主要用于在室内或遮盖物（建筑群或茂密的深林等）密集的地方定位）
        boolean network = locationManager.isProviderEnabled(LocationManager.NETWORK_PROVIDER);
        if (gps) {
            return true;
        }
        return false;
    }

    /**
     * 方法名：toggleGPS
     * 功    能：自动打开GPS
     * 参    数：Context context
     * 返回值：
     * 作    者：stt
     * 时    间：2017.2.21
     */
    public static void toggleGPS(Context context) {
        Intent gpsIntent = new Intent();
        gpsIntent.setClassName("com.android.settings", "com.android.settings.widget.SettingsAppWidgetProvider");
        gpsIntent.addCategory("android.intent.category.ALTERNATIVE");
        gpsIntent.setData(Uri.parse("custom:3"));
        try {
            PendingIntent.getBroadcast(context, 0, gpsIntent, 0).send();
        } catch (PendingIntent.CanceledException e) {
            e.printStackTrace();
        }
    }

    /**
     * 方法名：getLoc
     * 功    能：返回GPS的参数，经纬度
     * 参    数：Context context
     * 返回值：
     * 作    者：stt
     * 时    间：2017.2.21
     */
    public static void getLoc(Context context) {
        final GetGPSInterface getGPSInterface = (GetGPSInterface) context;
        // 位置
        LocationManager locationManager;
        LocationListener locationListener;
        Location location;
        String contextService = context.LOCATION_SERVICE;
        String provider;
        double lat;
        double lon;
        locationManager = (LocationManager) context.getSystemService(contextService);
        Criteria criteria = new Criteria();
        criteria.setAccuracy(Criteria.ACCURACY_FINE);// 高精度
        criteria.setAltitudeRequired(false);// 不要求海拔
        criteria.setBearingRequired(false);// 不要求方位
        criteria.setCostAllowed(true);// 允许有花费
        criteria.setPowerRequirement(Criteria.POWER_LOW);// 低功耗
        // 从可用的位置提供器中，匹配以上标准的最佳提供器
        provider = locationManager.getBestProvider(criteria, true);
        // 获得最后一次变化的位置
        location = locationManager.getLastKnownLocation(provider);
        locationListener = new LocationListener() {
            public void onStatusChanged(String provider, int status,
                                        Bundle extras) {
                // TODO Auto-generated method stub
            }

            public void onProviderEnabled(String provider) {
                // TODO Auto-generated method stub
            }

            public void onProviderDisabled(String provider) {
                // TODO Auto-generated method stub
            }

            public void onLocationChanged(Location location) {
                getGPSInterface.getGPS(location.getLongitude() + "", location.getLatitude() + "");
            }
        };
        // 监听位置变化，2秒一次，距离10米以上
        locationManager.requestLocationUpdates(provider, 2000, 10,
                locationListener);
    }

    /**
     * 方法名：getWeekOfString
     * 功    能：根据日期时间返回星期几
     * 参    数：String s
     * 返回值：String
     * 作    者：stt
     * 时    间：2017.3.28
     */
    public static String getWeekOfString(String s) {
        final String dayNames[] = {"星期日", "星期一", "星期二", "星期三", "星期四", "星期五", "星期六"};
        SimpleDateFormat sdfInput = new SimpleDateFormat("yyyy/MM/dd");
        Calendar calendar = Calendar.getInstance();
        Date date = new Date();
        try {
            date = sdfInput.parse(s);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        calendar.setTime(date);
        int dayOfWeek = calendar.get(Calendar.DAY_OF_WEEK) - 1;
        if (dayOfWeek < 0)
            dayOfWeek = 0;
        return dayNames[dayOfWeek];
    }

    //阴历日期转换为阳历日期
    public static String getLunar(String time) {
        SimpleDateFormat format = new SimpleDateFormat("yyyy/MM/dd");
        ParsePosition pos = new ParsePosition(0);
        Date date = format.parse(time, pos);
        Calendar cal = Calendar.getInstance();
        cal.setTime(date);
        cal.setTimeZone(TimeZone.getDefault());
        Lunar lunar = new Lunar(cal);
        return lunar.toString();
    }

    /**
     * 方法名：dateToStamp
     * 功 能：将时间戳转换为时间
     * 参 数：String s
     * 返回值：String
     * 作 者：stt
     * 时间：2017.3.28
     */
    public static String stampToDate(String s) {
        String res;
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        long lt = new Long(s);
        Date date = new Date(lt);
        res = simpleDateFormat.format(date);
        return res;
    }

    /**
     * 十位数时间戳转换成时间
     *
     * @param time 时间戳
     * @return
     */
    public static String stampToDates(String time) {
        SimpleDateFormat sdr = new SimpleDateFormat("yyyy年MM月dd日HH时mm分");
        @SuppressWarnings("unused")
        long lcc = Long.valueOf(time);
        int i = Integer.parseInt(time);
        String times = sdr.format(new Date(i * 1000L));
        return times;
    }

    /**
     * 十位数时间戳转换成时间
     *
     * @param time 时间戳
     * @return
     */
    public static String stampToTime(String time) {
        SimpleDateFormat sdr = new SimpleDateFormat("HH:mm");
        @SuppressWarnings("unused")
        long lcc = Long.valueOf(time);
        int i = Integer.parseInt(time);
        String times = sdr.format(new Date(i * 1000L));
        return times;
    }

    /**
     * 方法名：dateToStamp
     * 功 能：将时间转换为时间戳
     * 参 数：String s
     * 返回值：String
     * 作 者：stt
     * 时间：2017.3.28
     */
    public static String dateToStamp(String s) {
        String res = null;
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
        Date date;
        try {
            date = simpleDateFormat.parse(s);
            long ts = date.getTime();
            res = String.valueOf(ts);
        } catch (ParseException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return res;
    }

    /**
     * 方法名：getFormatSize
     * 功    能：格式化单位
     * 参    数：(double size)
     * 返回值：String
     */
    public static String getFormatSize(double size) {
        double kiloByte = size / 1024;
        if (kiloByte < 1) {
            return size + "B";
        }

        double megaByte = kiloByte / 1024;
        if (megaByte < 1) {
            BigDecimal result1 = new BigDecimal(Double.toString(kiloByte));
            return result1.setScale(2, BigDecimal.ROUND_HALF_UP)
                    .toPlainString() + "KB";
        }

        double gigaByte = megaByte / 1024;
        if (gigaByte < 1) {
            BigDecimal result2 = new BigDecimal(Double.toString(megaByte));
            return result2.setScale(2, BigDecimal.ROUND_HALF_UP)
                    .toPlainString() + "MB";
        }

        double teraBytes = gigaByte / 1024;
        if (teraBytes < 1) {
            BigDecimal result3 = new BigDecimal(Double.toString(gigaByte));
            return result3.setScale(2, BigDecimal.ROUND_HALF_UP)
                    .toPlainString() + "GB";
        }
        BigDecimal result4 = new BigDecimal(teraBytes);
        return result4.setScale(2, BigDecimal.ROUND_HALF_UP).toPlainString()
                + "TB";
    }

    /**
     * 方法名：getAppVersionName
     * 功    能：获取当前版本号
     * 参    数：Context context
     * 返回值：String
     */
    public static String getAppVersionName(Context context) {
        String versionName = "";
        try {
            // ---get the package info---  
            PackageManager pm = context.getPackageManager();
            PackageInfo pi = pm.getPackageInfo(context.getPackageName(), 0);
            versionName = pi.versionName;
            if (versionName == null || versionName.length() <= 0) {
                return "";
            }
        } catch (Exception e) {
            Log.e("VersionInfo", "Exception", e);
        }
        return versionName;
    }
}
