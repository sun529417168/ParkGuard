package cn.com.watchman.networkrequest;

import android.app.Activity;
import android.util.Log;

import com.alibaba.fastjson.JSON;
import com.linked.erfli.library.config.UrlConfig;
import com.linked.erfli.library.okhttps.OkHttpUtils;
import com.linked.erfli.library.okhttps.callback.GenericsCallback;
import com.linked.erfli.library.okhttps.utils.JsonGenericsSerializator;
import com.linked.erfli.library.utils.LoadingDialogUtil;
import com.linked.erfli.library.utils.SharedUtil;
import com.linked.erfli.library.utils.ToastUtil;

import java.io.File;
import java.util.HashMap;
import java.util.Map;

import cn.com.watchman.bean.UserBean;
import cn.com.watchman.interfaces.EventReportDataInterface;
import cn.com.watchman.interfaces.WatchManLoginInterface;
import okhttp3.Call;

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
     * 上传数据
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
    public static void dataRequest(final Activity mActivity, int dataType, String userId, String alarmtext, String alarmtime, int alarmtype, String deviceguid, String Longitude) {
        LoadingDialogUtil.show(mActivity);//显示加载
        final EventReportDataInterface eventReportDataInterface = (EventReportDataInterface) mActivity;
        Map<String, Object> params = new HashMap<>();
        try {
            params.put("dataType", dataType);
            params.put("userId", userId);
            params.put("alarmtext", alarmtext);
            params.put("alarmtime", alarmtime);
            params.put("alarmtype", alarmtype);
            params.put("deviceguid", deviceguid);
            params.put("Longitude", Longitude);
        } catch (Exception e) {
            e.printStackTrace();
        }
        String json = "11";
        OkHttpUtils.postString().url(UrlConfig.URL_LOGIN).content(json).build().execute(new GenericsCallback<String>(new JsonGenericsSerializator()) {
            @Override
            public void onError(Call call, Exception e, int id) {
                ToastUtil.show(mActivity, "服务器有错误，请稍候再试");
                if (LoadingDialogUtil.show(mActivity).isShowing()) {
                    LoadingDialogUtil.dismiss();
                }
            }

            @Override
            public void onResponse(String response, int id) {
                //response：返回的json串
                //json转成实体对象
                //判断,返回数据是否正常
                if (1 == 1) {
                    //数据正常
                    eventReportDataInterface.getERDinterface(response);
                } else {
                    ToastUtil.show(mActivity, "数据有误");
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
     * @param fileMap:图片集合
     * @param alarmid:告警信息关联ID
     */
    public static void sendImageRequest(final Activity mActivity, int dataType, Map<String, File> fileMap, int alarmid) {
        LoadingDialogUtil.show(mActivity);
        Map<String, Object> params = new HashMap<>();
        params.put("dataType", dataType);
        params.put("alarmid", alarmid);
        OkHttpUtils.post().files("imgbs", fileMap).url(UrlConfig.URL_LOGIN).params(params).build().execute(new GenericsCallback<String>(new JsonGenericsSerializator()) {
            @Override
            public void onError(Call call, Exception e, int id) {
                ToastUtil.show(mActivity, "错误代码" + e.getMessage().toString());
                if (LoadingDialogUtil.show(mActivity).isShowing()) {
                    LoadingDialogUtil.dismiss();
                }
            }

            @Override
            public void onResponse(String response, int id) {
                //操作成功 结果回调,或者关闭页面
            }

        });
    }


}
