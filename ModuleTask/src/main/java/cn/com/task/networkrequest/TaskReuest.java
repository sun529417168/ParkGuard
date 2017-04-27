package cn.com.task.networkrequest;

import android.app.Activity;
import android.util.Log;

import com.alibaba.fastjson.JSON;
import com.linked.erfli.library.config.UrlConfig;
import com.linked.erfli.library.okhttps.OkHttpUtils;
import com.linked.erfli.library.okhttps.callback.GenericsCallback;
import com.linked.erfli.library.okhttps.utils.JsonGenericsSerializator;
import com.linked.erfli.library.utils.SharedUtil;
import com.linked.erfli.library.utils.ToastUtil;

import java.util.HashMap;
import java.util.Map;

import cn.com.task.bean.UserBean;
import cn.com.task.interfaces.TaskLoginInterface;
import cn.com.task.utils.LoadingDialogUtil;
import okhttp3.Call;

/**
 * 类  名:TaskReuest
 * 描  述:Task网络请求管理类
 * 作  者:zzq
 * 时  间:2017年4月26日
 * 版  本:V1.0,0
 */

public class TaskReuest {
    /**
     * 功能:登录接口
     *
     * @param mActivity 当前类
     * @param userName  用户名
     * @param password  密码
     */

    public static void loginRequest(final Activity mActivity, final String userName, final String password) {
        LoadingDialogUtil.show(mActivity);//显示加载
        final TaskLoginInterface taskLoginInterface = (TaskLoginInterface) mActivity;
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
}
