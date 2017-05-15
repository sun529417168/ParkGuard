package cn.com.watchman.utils;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.text.TextUtils;
import android.util.Log;

import com.alibaba.fastjson.JSON;
import com.linked.erfli.library.config.UrlConfig;
import com.linked.erfli.library.okhttps.OkHttpUtils;
import com.linked.erfli.library.okhttps.callback.GenericsCallback;
import com.linked.erfli.library.okhttps.utils.JsonGenericsSerializator;
import com.linked.erfli.library.utils.DeviceUuidFactory;
import com.linked.erfli.library.utils.DialogUtils;
import com.linked.erfli.library.utils.SharedUtil;
import com.linked.erfli.library.utils.ToastUtil;

import java.util.HashMap;
import java.util.Map;

import cn.com.watchman.activity.WatchMainActivity;
import cn.com.watchman.bean.GPSBean;
import cn.com.watchman.bean.PersonBean;
import cn.com.watchman.config.WMUrlConfig;
import cn.com.watchman.interfaces.PersonInfoInterface;
import okhttp3.Call;

/**
 * 文件名：MyRequest
 * 描    述：请求工具类
 * 作    者：stt
 * 时    间：2017.01.11
 * 版    本：V1.0.0
 */

public class MyRequest {
    /**
     * 方法名：personInfoRequest
     * 功    能：返回人员基本信息
     * 参    数：Activity activity final String username, final String password
     * 返回值：无
     */
    public static void personInfoRequest(final Context activity, String personID) {
        final Dialog progDialog = DialogUtils.showWaitDialog(activity);
        final PersonInfoInterface personInfoInterface = (PersonInfoInterface) activity;
        Map<String, Object> params = new HashMap<>();
        try {
            params.put("PersonID", personID);
        } catch (Exception e) {
            e.printStackTrace();
        }
        OkHttpUtils.post().url(UrlConfig.URL_GETPERSONINFO).params(params).build().execute(new GenericsCallback<String>(new JsonGenericsSerializator()) {
            @Override
            public void onResponse(String response, int id) {
                if (!TextUtils.isEmpty(response)) {
                    PersonBean personBean = JSON.parseObject(response, PersonBean.class);
                    personInfoInterface.getPersonInfo(personBean);
                }
                if (progDialog.isShowing()) {
                    progDialog.dismiss();
                }
            }

            @Override
            public void onError(Call call, Exception e, int id) {
                ToastUtil.show(activity, "服务器有错误，请稍候再试");
                if (progDialog.isShowing()) {
                    progDialog.dismiss();
                }
            }
        });
    }


    /**
     * 方法名：isCheckPassWordRequest
     * 功    能：判断密码是否正确
     * 参    数：Activity activity String strings
     * 返回值：无
     */
    public static void isCheckPassWordRequest(final Activity activity, String strings) {
        Map<String, Object> params = new HashMap<>();
        try {
            params.put("PassWord", strings);
            params.put("PersonID", SharedUtil.getString(activity, "PersonID"));
        } catch (Exception e) {
            e.printStackTrace();
        }
        OkHttpUtils.post().url(UrlConfig.URL_ISCHECKPASSWORD).params(params).build().execute(new GenericsCallback<String>(new JsonGenericsSerializator()) {
            @Override
            public void onResponse(String response, int id) {
                if ("false".equals(response)) {
                    ToastUtil.show(activity, "密码有误，请重新输入");
                }
            }

            @Override
            public void onError(Call call, Exception e, int id) {
            }
        });
    }

    /**
     * 方法名：uodatePersonInfoRequest
     * 功    能：完善个人信息
     * 参    数：Activity activity String... strings
     * 返回值：无
     */
    public static void uodatePersonInfoRequest(final Activity activity, final String... strings) {
        final Dialog progDialog = DialogUtils.showWaitDialog(activity);
        Map<String, Object> params = new HashMap<>();
        try {
            params.put("Name", strings[0]);
            params.put("Gender", strings[1].equals("男") ? 1 : 2);
            params.put("IDCard", strings[2]);
            params.put("Phone", strings[3]);
            params.put("OldPassWord", strings[4]);
            params.put("NewPassWord", strings[5]);
            params.put("PersonID", SharedUtil.getString(activity, "PersonID"));
        } catch (Exception e) {
            e.printStackTrace();
        }
        OkHttpUtils.post().url(UrlConfig.URL_EDITTEXTUSERINFO).params(params).build().execute(new GenericsCallback<String>(new JsonGenericsSerializator()) {
            @Override
            public void onResponse(String response, int id) {
                if ("true".equals(response)) {
                    SharedUtil.setBoolean(activity, "isLogin", true);
                    Intent intent = new Intent(activity, WatchMainActivity.class);
                    activity.startActivity(intent);
                    activity.finish();
                } else if ("false".equals(response)) {
                    ToastUtil.show(activity, "修改信息失败");
                }
                if (progDialog.isShowing()) {
                    progDialog.dismiss();
                }
            }

            @Override
            public void onError(Call call, Exception e, int id) {
                ToastUtil.show(activity, "连接超时，请稍候再试");
                if (progDialog.isShowing()) {
                    progDialog.dismiss();
                }
            }
        });
    }


    /**
     * 方法名：gpsRequest
     * 功    能：上传gps点位
     * 参    数：Activity activity String... strings
     * 返回值：无
     */
    public static void gpsRequest(final Activity activity, final GPSBean gpsBean) {
        Map<String, Object> params = new HashMap<>();
        Map<String, Object> map = new HashMap<>();
        map.put("DeviceGUID", new DeviceUuidFactory(activity).getDeviceUuid().toString());
        map.put("Longitude", gpsBean.getLongitude());//经度
        map.put("Latitude", gpsBean.getLatitude());//纬度
        map.put("Speed", "");//速度
        map.put("RecvGpsTime", System.currentTimeMillis());//GPS接收时间
        map.put("HappenTime", System.currentTimeMillis());//发生时间
        map.put("ActiveFlag", -1);//
        map.put("Direction", 0);//方向
        map.put("Description", "");//备注
        map.put("GPSType", 0);//GPS类型
        map.put("patrol_type", 1);//
        map.put("point_code", -1);//巡更点编号
        map.put("user_id", SharedUtil.getString(activity, "PersonID"));
        map.put("recordid", 0);//原数据库编号记录编号，同步数据库数据时用
        map.put("satellitenum", gpsBean.getSatellite());//卫星数
        map.put("accuracy", gpsBean.getAccuracy());//精准度
        try {
            params.put("subSysType", 10);
            params.put("dataType", 2);
            params.put("mark", "patrolphone");
            params.put("data", map);
        } catch (Exception e) {
            e.printStackTrace();
        }
        OkHttpUtils.post().url(WMUrlConfig.URL).params(params).build().execute(new GenericsCallback<String>(new JsonGenericsSerializator()) {
            @Override
            public void onResponse(String response, int id) {
//                ToastUtil.show(activity, "success" + response);
            }

            @Override
            public void onError(Call call, Exception e, int id) {
                Log.i("gpsError", e.getMessage().toString());
//                ToastUtil.show(activity, "error" + e.getMessage().toString());
            }
        });
    }


    /**
     * 方法名：typeRequest
     * 功    能：上传手机状态
     * 参    数：Activity activity String... strings
     * 返回值：无
     */
    public static void typeRequest(final Activity activity, final int status) {
        Map<String, Object> params = new HashMap<>();
        Map<String, Object> map = new HashMap<>();
        map.put("DeviceGUID", new DeviceUuidFactory(activity).getDeviceUuid().toString());
        map.put("status", status);//状态
        try {
            params.put("subSysType", 10);
            params.put("dataType", 4);
            params.put("mark", "patrolphone");
            params.put("data", map);
        } catch (Exception e) {
            e.printStackTrace();
        }
        OkHttpUtils.post().url(WMUrlConfig.URL).params(params).build().execute(new GenericsCallback<String>(new JsonGenericsSerializator()) {
            @Override
            public void onResponse(String response, int id) {
//                ToastUtil.show(activity, "success" + response);
            }

            @Override
            public void onError(Call call, Exception e, int id) {
                Log.i("gpsError", e.getMessage().toString());
//                ToastUtil.show(activity, "error" + e.getMessage().toString());
            }
        });
    }


}
