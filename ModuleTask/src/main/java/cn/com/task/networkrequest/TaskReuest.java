package cn.com.task.networkrequest;

import android.app.Activity;

import java.util.HashMap;
import java.util.Map;

import cn.com.task.interfaces.TaskLoginInterface;
import cn.com.task.utils.LoadingDialogUtil;

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
        TaskLoginInterface taskLoginInterface = (TaskLoginInterface) mActivity;
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
//        OkHttpUtils.post().url();

    }
}
