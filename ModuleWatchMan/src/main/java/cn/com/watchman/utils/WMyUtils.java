package cn.com.watchman.utils;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.location.LocationManager;

import com.linked.erfli.library.utils.SharedUtil;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

/**
 * 文件名：WMyUtils
 * 描    述：工具类
 * 作    者：stt
 * 时    间：2017.05.12
 * 版    本：V1.0.0
 */

public class WMyUtils {
    /**
     * 方法名：isOPen
     * 功    能：判断GPS是否开启，GPS或者AGPS开启一个就认为是开启的
     * 参    数： Context context
     * 返回值：boolean
     */
    public static final boolean isOpen(final Context context) {
        LocationManager locationManager = (LocationManager) context.getSystemService(Context.LOCATION_SERVICE);
        // 通过GPS卫星定位，定位级别可以精确到街（通过24颗卫星定位，在室外和空旷的地方定位准确、速度快）
        boolean gps = locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER);
        // 通过WLAN或移动网络(3G/2G)确定的位置（也称作AGPS，辅助GPS定位。主要用于在室内或遮盖物（建筑群或茂密的深林等）密集的地方定位）
        boolean network = locationManager.isProviderEnabled(LocationManager.NETWORK_PROVIDER);
        if (gps) {
            return true;
        }

        return false;
    }

    public static final String runTime(Context context) {
        long now = System.currentTimeMillis() / 1000;
        long yes = SharedUtil.getLong(context, "runTime", 0);
        long time = now - yes;
        int h = (int) ((time / 3600));
        int m = (int) ((time % 3600) / 60);
        int s = (int) ((time % 3600) % 60);
        return Integer.toString(h) + "h" + Integer.toString(m) + "m" + Integer.toString(s) + "s";
    }

    /**
     * stt
     * 2016.8.29
     * 方法说明：获取本地缓存
     *
     * @param objects
     * @return
     */
    public static Object arrayList(Object[] objects) {
        List<Object> list = new ArrayList<>();
        if (objects == null || objects.length == 0) {
            return list;
        }
        for (int i = 0; i < objects.length; i++) {
            list.add(objects[i]);
        }
        return list;
    }

    /**
     * stt
     * 2016.8.29
     * 方法说明：获取今天凌晨的时间戳
     *
     * @return
     */
    public static String getTimesmorning() {
        Calendar cal = Calendar.getInstance();
        cal.set(Calendar.HOUR_OF_DAY, 0);
        cal.set(Calendar.SECOND, 0);
        cal.set(Calendar.MINUTE, 0);
        cal.set(Calendar.MILLISECOND, 0);
        return Long.toString((cal.getTimeInMillis() / 1000));
    }

    /**
     * stt
     * 2016.8.29
     * 方法说明：压缩图片释放内存
     *
     * @return
     */
    public static Bitmap readBitMap(Context context, int resId) {

        InputStream is = context.getResources().openRawResource(resId);

        BitmapFactory.Options options = new BitmapFactory.Options();

        options.inJustDecodeBounds = false;

        options.inSampleSize = 1;   // width，hight设为原来的十分一

        Bitmap btp = BitmapFactory.decodeStream(is, null, options);
        return btp;
    }
}
