package cn.com.watchman.networkrequest;

import android.app.Activity;
import android.util.Log;
import android.widget.Toast;

import com.alibaba.fastjson.JSON;
import com.linked.erfli.library.config.UrlConfig;
import com.linked.erfli.library.okhttps.OkHttpUtils;
import com.linked.erfli.library.okhttps.callback.GenericsCallback;
import com.linked.erfli.library.okhttps.utils.JsonGenericsSerializator;
import com.linked.erfli.library.utils.LoadingDialogUtil;
import com.linked.erfli.library.utils.SharedUtil;
import com.linked.erfli.library.utils.ToastUtil;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import cn.com.watchman.bean.UserBean;
import cn.com.watchman.interfaces.EventReportDataInterface;
import cn.com.watchman.interfaces.WatchManLoginInterface;
import cn.com.watchman.utils.FileToByteUtils;
import okhttp3.Call;
import okhttp3.MediaType;

/**
 * Created by 志强 on 2017.5.3.
 */

public class WatchManRequest {
    /**
     * 功能:登录接口
     *
     * @param mActivity 当前类
     * @param userName  用户名
     * @param password  密码
     */

    public static void loginRequest(final Activity mActivity, final String userName, final String password) {
        LoadingDialogUtil.show(mActivity);//显示加载
        final WatchManLoginInterface taskLoginInterface = (WatchManLoginInterface) mActivity;
        Map<String, Object> params = new HashMap<>();
        try {
            params.put("Name", userName);
            params.put("PassWord", password);
            params.put("Module", "XBGD");
            //
            params.put("DeviceID", "7dd45a6ce4ad4a71bc5ffab35a273e6d");
//            params.put("DeviceID", PushServiceFactory.getCloudPushService().getDeviceId());
        } catch (Exception e) {
            e.printStackTrace();
        }
        OkHttpUtils.post().url(UrlConfig.URL_LOGIN).params(params).build().execute(new GenericsCallback<String>(new JsonGenericsSerializator()) {
            @Override
            public void onError(Call call, Exception e, int id) {
                ToastUtil.show(mActivity, "服务器有错误，请稍候再试");
                if (LoadingDialogUtil.show(mActivity).isShowing()) {
                    LoadingDialogUtil.dismiss();
                }
            }

            @Override
            public void onResponse(String response, int id) {
                Log.i("loginInfo", response);
                if ("0".equals(response.replace("\"", ""))) {
                    ToastUtil.show(mActivity, "您输入的密码有误");
                } else if ("-1".equals(response.replace("\"", ""))) {
                    ToastUtil.show(mActivity, "帐号不存在");
                } else {
                    UserBean userBean = JSON.parseObject(response, UserBean.class);
                    SharedUtil.setString(mActivity, "userName", userName);
                    SharedUtil.setString(mActivity, "passWord", password);
                    SharedUtil.setString(mActivity, "PersonID", userBean.getPersonId() + "");
                    SharedUtil.setString(mActivity, "LoginName", userBean.getLoginName());
                    SharedUtil.setString(mActivity, "personName", userBean.getPermissions().get(0).getUserName());
                    taskLoginInterface.getLoginResult(userBean);
                }
                if (LoadingDialogUtil.show(mActivity).isShowing()) {
                    LoadingDialogUtil.dismiss();
                }
            }
        });
    }

    /**
     * 十位数时间戳转换成时间
     *
     * @param time 时间戳
     * @return
     */
    public static String times(String time) {
        SimpleDateFormat sdr = new SimpleDateFormat("yyyy年MM月dd日HH时mm分ss秒");
        @SuppressWarnings("unused")
        long lcc = Long.valueOf(time);
        int i = Integer.parseInt(time);
        String times = sdr.format(new Date(i * 1000L));
        return times;
    }

    /**
     *
     * @param mActivity
     * @param dataType:手机预警       1
     * @param userId:用户id
     * @param alarmtext:告警描述信息
     * @param alarmtime:告警时间
     * @param alarmtype:告警类型
     * @param deviceguid:巡更设备唯一编号
     * @param Longitude:经度
     */
    /**
     * 上传数据
     *
     * @param mActivity:
     * @param subSysType:系统标识
     * @param dataType:类型
     * @param mark:驱动标识
     * @param userId:
     * @param alarmtext:
     * @param alarmtime:
     * @param alarmtype:
     * @param deviceguid:
     * @param Longitude:
     */
    public static void dataRequest(final Activity mActivity, int subSysType, int dataType, String mark, int userId, String alarmtext, final String alarmtime, int alarmtype, String deviceguid, String Latitude, String Longitude) {
        LoadingDialogUtil.show(mActivity);//显示加载
        final EventReportDataInterface eventReportDataInterface = (EventReportDataInterface) mActivity;
        Map<String, Object> params = new HashMap<>();
        Map<String, Object> map = new HashMap<>();
        map.put("alarm_related_info", 0);//int 如果告警类型为长时间未移动 ，此字段为持续为移动时间 如果此字段为巡更点异常，1、为巡更点故障  2、恢复(可为null)
        map.put("alarmtext", alarmtext);//备注 描述信息
        map.put("alarmtime", alarmtime);//告警时间
        map.put("alarmtype", alarmtype);//int
        map.put("deviceguid", deviceguid);//当前设备唯一id
        map.put("specified_point", -1);//规定巡更点id（后期扩展用）
        map.put("point_code", "");//string 巡更点编号，可为无。
        map.put("routeid", -1);//路线id（后期扩展用）
        map.put("Longitude", Longitude);//纬度
        map.put("Latitude", Latitude);//经度
        map.put("userId", userId);
        try {
            params.put("subSysType", subSysType);
            params.put("dataType", dataType);
            params.put("mark", mark);
            params.put("data", map);

        } catch (Exception e) {
            e.printStackTrace();
        }
        String json = JSON.toJSONString(params);
        Log.i("", "数据请求返回json:" + json);
        OkHttpUtils.postString().url(UrlConfig.URL_WMReport).mediaType(MediaType.parse("application/json; charset=utf-8")).content(json).build().execute(new GenericsCallback<String>(new JsonGenericsSerializator()) {
            @Override
            public void onError(Call call, Exception e, int id) {
                ToastUtil.show(mActivity, "服务器有错误，请稍候再试" + e.toString());
                if (LoadingDialogUtil.show(mActivity).isShowing()) {
                    LoadingDialogUtil.dismiss();
                }
            }

            @Override
            public void onResponse(String response, int id) {
                //response：返回的json串
                //json转成实体对象
                //判断,返回数据是否正常
                try {
                    JSONObject jsonObject = new JSONObject(response);
                    int d = jsonObject.getInt("d");
                    if (d > 1) {
                        Log.i("事件上报数据上传返回结果:", "" + response);
//                            Toast.makeText(mActivity, "" + times(alarmtime), Toast.LENGTH_SHORT).show();
                        eventReportDataInterface.getERDinterface(response);
                    } else {
                        Toast.makeText(mActivity, "数据上传有误", Toast.LENGTH_SHORT).show();
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                    Toast.makeText(mActivity, "解析数据异常", Toast.LENGTH_SHORT).show();
                }
                if (LoadingDialogUtil.show(mActivity).isShowing()) {
                    LoadingDialogUtil.dismiss();
                }
            }
        });
    }

    /**
     * @param mActivity
     * @param dataType:上传图片方式(6:手机类型)
     * @param file:图片集合
     * @param alarmid:告警信息关联ID
     */

    public static void sendImageRequest(final Activity mActivity, int dataType, File file, String fileName, int alarmid, final int mapSize, final int i) {
        LoadingDialogUtil.show(mActivity);
        Map<String, Object> map = new HashMap<>();
        map.put("imgbs", FileToByteUtils.getBytesFromFile(file));
        map.put("imgname", fileName);
        map.put("alarmid", alarmid);
        Map<String, Object> params = new HashMap<>();
        params.put("subSysType", 10);
        params.put("dataType", dataType);
        params.put("mark", "patrolphone");
        params.put("data", map);
        String sendImage = JSON.toJSONString(params);
        Log.i("事件上报图片上传返回结果:", "" + sendImage);
        OkHttpUtils.postString().url(UrlConfig.URL_SENDIMAGE).mediaType(MediaType.parse("application/json; charset=utf-8")).content(sendImage).build().execute(new GenericsCallback<String>(new JsonGenericsSerializator()) {
            @Override
            public void onError(Call call, Exception e, int id) {
                ToastUtil.show(mActivity, "错误代码" + e.getMessage().toString());
                if (LoadingDialogUtil.show(mActivity).isShowing()) {
                    LoadingDialogUtil.dismiss();
                }
            }

            @Override
            public void onResponse(String response, int id) {
                try {
                    JSONObject jsonObject = new JSONObject(response);
                    int d = jsonObject.getInt("d");
                    if (d != 1) {
                        Toast.makeText(mActivity, "第" + i + "张图片上传失败!", Toast.LENGTH_SHORT).show();
                    }
                    if (i == mapSize) {
                        Toast.makeText(mActivity, "图片上产成功", Toast.LENGTH_SHORT).show();
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                    Toast.makeText(mActivity, "数据解析失败!", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
}
