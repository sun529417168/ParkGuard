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
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import cn.com.watchman.bean.UserBean;
import cn.com.watchman.chatui.enity.ChatProblemTypeLeftEntity;
import cn.com.watchman.chatui.enity.WarningDetailsInfo;
import cn.com.watchman.chatui.interfaces.ChatMsgInterface;
import cn.com.watchman.chatui.interfaces.ChatProblemTypeLeftInterface;
import cn.com.watchman.chatui.interfaces.ChatSendPhotoInterface;
import cn.com.watchman.chatui.interfaces.ChatSendPicTureInterface;
import cn.com.watchman.chatui.interfaces.ChatWarningDetailsInterface;
import cn.com.watchman.config.WMUrlConfig;
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
        map.put("specified_point", "-1");//规定巡更点id（后期扩展用）
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
        OkHttpUtils.postString().url(WMUrlConfig.URL).mediaType(MediaType.parse("application/json; charset=utf-8")).content(json).build().execute(new GenericsCallback<String>(new JsonGenericsSerializator()) {
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
                        eventReportDataInterface.getERDinterface(d);
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
        OkHttpUtils.postString().url(WMUrlConfig.URL).mediaType(MediaType.parse("application/json; charset=utf-8")).content(sendImage).build().execute(new GenericsCallback<String>(new JsonGenericsSerializator()) {
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
                        Toast.makeText(mActivity, "上传成功", Toast.LENGTH_SHORT).show();
                        mActivity.finish();
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                    Toast.makeText(mActivity, "数据解析失败!", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    /***
     * 聊天信息发送(文本)
     * @param mActivity
     * @param subSysType
     * @param dataType
     * @param mark
     * @param DeviceGUID
     * @param message_type
     * @param message
     * @param send_time
     * @param longitude
     * @param latitude
     * @param user_id
     */
    public static void sendChatMsg(final Activity mActivity, int subSysType, int dataType, String mark, String DeviceGUID, int message_type, String message, String send_time, String longitude, String latitude, int user_id) {
        Log.i("经纬度:", "发送文字从application获取的全局变量,lat:" + latitude + ",lon:" + longitude);
        final ChatMsgInterface chatMsgInterface = (ChatMsgInterface) mActivity;
        Map<String, Object> params = new HashMap<>();
        params.put("DeviceGUID", DeviceGUID);
        params.put("message_type", message_type);
        params.put("message", message);
        params.put("send_time", send_time);
        params.put("Longitude", longitude);
        params.put("Latitude", latitude);
        params.put("user_id", user_id);
        Map<String, Object> map = new HashMap<>();
        map.put("subSysType", subSysType);
        map.put("dataType", dataType);
        map.put("mark", mark);
        map.put("data", params);
        String sendChatJson = JSON.toJSONString(map);
        Log.i("事件上报图片上传返回结果:", "" + sendChatJson);
        OkHttpUtils.postString().url(WMUrlConfig.URL).mediaType(MediaType.parse("application/json; charset=utf-8")).content(sendChatJson).build().execute(new GenericsCallback<String>(new JsonGenericsSerializator()) {
            @Override
            public void onError(Call call, Exception e, int id) {
                Log.i("即时通讯发送数据返回结果:1", "" + e.getMessage());
//                ToastUtil.show(mActivity, "错误代码" + e.getMessage());
                ToastUtil.show(mActivity, "发送失败!");
                chatMsgInterface.onChatMsgError();
            }

            @Override
            public void onResponse(String response, int id) {
                Log.i("即时通讯发送数据返回结果:2", "" + response);
                try {
                    JSONObject jsonObject = new JSONObject(response);
                    int d = jsonObject.getInt("d");
                    if (d > 0) {
                        chatMsgInterface.onChatMsgResponse();
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        });
    }

    /**
     * 方法名：getProblemTypeLeft
     * 功    能：获取一级节点信息
     * 参    数：Activity activity final String username, final String password
     * 返回值：无
     */
    public static void getChatProblemTypeLeft(final Activity activity, ChatProblemTypeLeftInterface problemTypeLeftInterfaces) {
        final ChatProblemTypeLeftInterface problemTypeLeftInterface = problemTypeLeftInterfaces;
        OkHttpUtils.get().url(UrlConfig.URL_GETBEGINNINGENTITY).build().execute(new GenericsCallback<String>(new JsonGenericsSerializator()) {
            @Override
            public void onResponse(String response, int id) {
                ArrayList<ChatProblemTypeLeftEntity> problemTypeLeftList = (ArrayList<ChatProblemTypeLeftEntity>) JSON.parseArray(response, ChatProblemTypeLeftEntity.class);
                problemTypeLeftInterface.getChatTypeLeft(problemTypeLeftList);
            }

            @Override
            public void onError(Call call, Exception e, int id) {
                ToastUtil.show(activity, "服务器有错误，请稍候再试");
            }
        });
    }

    /**
     * 方法名：newAddChatProblemPicture
     * 功    能：巡更事件上报  图片上传
     * 参    数：Context activity, Map<String,File> fileMap, Object... strings
     * 返回值：无
     */
    public static void newAddChatProblemPricture(final Activity mActivity, List<File> listFile, List<String> fileName, String problemDes, int alarmid, int dataType, int subSysType, String longitude, String latitude, String deviceUuid, String time) {
        LoadingDialogUtil.show(mActivity);
        final ChatSendPicTureInterface chatSendPicTureInterface = (ChatSendPicTureInterface) mActivity;
        Log.i("photofilelog", "图片:" + listFile.size() + "," + fileName.size());

        List<Map<String, Object>> list = new ArrayList<>();
        //图片流  文件名保存到map里
        Map<String, Object> fileMap = null;
//        List<byte[]> listByte;
        if (listFile.size() == fileName.size()) {
            for (int i = 0; i < listFile.size(); i++) {
                fileMap = new HashMap<>();
                String[] s = fileName.get(i).split("\\.");
                fileMap.put("imgbs", FileToByteUtils.getBytesFromFile(listFile.get(i)));
//                fileMap.put("imgbs", s[0]);
                fileMap.put("imgname", s[0]);
                fileMap.put("extname", s[1]);
                list.add(fileMap);
            }
        }
        Map<String, Object> dataMap = new HashMap<>();
        dataMap.put("alarm_related_info", 1);//如果告警类型为长时间未移动 ，此字段为持续为移动时间 如果此字段为巡更点异常，1、为巡更点故障  2、恢复
        dataMap.put("alarmtext", !"".equals(problemDes) ? problemDes : " ");//告警描述信息,是否为空,如果为空 传递一个空格 空字符串
        dataMap.put("alarmtime", time);//时间戳
        dataMap.put("alarmtype", 3);//告警类型 1:长时间未移动 3:人工上报告警信息
        dataMap.put("deviceguid", deviceUuid);//当前设备唯一编号
        dataMap.put("specified_point", -1);//规定巡更点id（后期扩展用）
        dataMap.put("point_code", "");//巡更点编号，可为无
        dataMap.put("routeid", -1);//路线id（后期扩展用）
        dataMap.put("Longitude", !("-1").equals(longitude) ? longitude : "-1");//	经度，-1为无
        dataMap.put("Latitude", !("-1").equals(latitude) ? latitude : "-1");//纬度, -1为无
        dataMap.put("file_num", listFile.size());//文件个数
        dataMap.put("file_list", list);//文件保存到list中

        Map<String, Object> params = new HashMap<>();
        params.put("subSysType", subSysType);
        params.put("dataType", dataType);
        params.put("mark", "patrolphone");
        params.put("data", dataMap);
        String sendChatWarningimg = JSON.toJSONString(params);
        Log.i("巡更巡检事件上报log", "json:" + sendChatWarningimg);
        OkHttpUtils.postString().url(WMUrlConfig.URL).mediaType(MediaType.parse("application/json; charset=utf-8")).content(sendChatWarningimg).build().execute(new GenericsCallback<String>(new JsonGenericsSerializator()) {
            @Override
            public void onError(Call call, Exception e, int id) {
                ToastUtil.show(mActivity, "错误代码" + e.getMessage().toString());
                if (LoadingDialogUtil.show(mActivity).isShowing()) {
                    LoadingDialogUtil.dismiss();
                }
            }

            @Override
            public void onResponse(String response, int id) {
                Log.i("发送图片返回json:", "成功:" + response);
                try {
                    JSONObject jsonObject = new JSONObject(response);
                    int d = jsonObject.getInt("d");
                    if (d > 0) {
                        chatSendPicTureInterface.getChatSenPictureResponce(d);
                    } else {
                        Toast.makeText(mActivity, "上传失败", Toast.LENGTH_SHORT).show();
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                if (LoadingDialogUtil.show(mActivity).isShowing()) {
                    LoadingDialogUtil.dismiss();
                }
            }
        });
    }

    public static void getWarningDetails(final Activity mActivity, int subSysType, int dataType, String DeviceGUID, int user_id, int id) {
        LoadingDialogUtil.show(mActivity);
        final ChatWarningDetailsInterface chatWarningDetailsInterface = (ChatWarningDetailsInterface) mActivity;
        Map<String, Object> dataMap = new HashMap<>();
        dataMap.put("DeviceGUID", DeviceGUID);
        dataMap.put("user_id", user_id);
        dataMap.put("id", id);

        Map<String, Object> map = new HashMap<>();
        map.put("subSysType", subSysType);
        map.put("dataType", dataType);
        map.put("mark", "patrolphone");
        map.put("data", dataMap);
        String warningDetails = JSON.toJSONString(map);
        Log.i("告警事件详情log", "json:" + warningDetails);
        OkHttpUtils.postString().url(WMUrlConfig.URL).mediaType(MediaType.parse("application/json; charset=utf-8")).content(warningDetails).build().execute(new GenericsCallback<String>(new JsonGenericsSerializator()) {
            @Override
            public void onError(Call call, Exception e, int id) {
                Log.i("告警事件详情log", "" + e.getMessage());
                if (LoadingDialogUtil.show(mActivity).isShowing()) {
                    LoadingDialogUtil.dismiss();
                }
            }

            @Override
            public void onResponse(String response, int id) {
                Log.i("告警事件详情log", "" + response);
                try {
                    JSONObject jsonObject = new JSONObject(response);
                    String json = jsonObject.getString("d");
                    Log.i("告警事件详情logjson,", "" + json);
                    JsonGenericsSerializator jsonGenericsSerializator = new JsonGenericsSerializator();
                    WarningDetailsInfo warningDetailsInfo = jsonGenericsSerializator.transform(json, WarningDetailsInfo.class);
                    chatWarningDetailsInterface.getWarningDetailsInterface(warningDetailsInfo);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                if (LoadingDialogUtil.show(mActivity).isShowing()) {
                    LoadingDialogUtil.dismiss();
                }
            }
        });
    }

    /**
     * 即时通讯 发送图片
     *
     * @param
     * @param device_code
     */
    public static void ChatSendPhoto(Activity mActivity, String device_code, File file, String fileName, String fileTxt, String time, int user_id, String lon, String lat) {
        Log.i("经纬度:", "发送图片从application获取的全局变量,lat:" + lat + ",lon:" + lon);
        final ChatSendPhotoInterface chatSendPhotoInterface = (ChatSendPhotoInterface) mActivity;
        Map<String, Object> dataMap = new HashMap<>();
        dataMap.put("device_code", device_code);//唯一设备编号
        dataMap.put("message_type", 6);//6:即时通讯图片消息
        dataMap.put("file_name", fileName);//
        dataMap.put("file_ext", fileTxt);//
        dataMap.put("file", FileToByteUtils.getBytesFromFile(file));//图片流
        dataMap.put("send_time", time);//时间戳
        dataMap.put("Longitude", lon);//
        dataMap.put("Latitude", lat);//
        dataMap.put("user_id", user_id);//

        Map<String, Object> map = new HashMap<>();
        map.put("subSysType", 10);
        map.put("dataType", 13);
        map.put("mark", "patrolphone");
        map.put("data", dataMap);
        String sendChatPhoto = JSON.toJSONString(map);
        Log.i("通讯发送数据json", "json:" + sendChatPhoto);
        OkHttpUtils.postString().url(WMUrlConfig.URL).mediaType(MediaType.parse("application/json; charset=utf-8")).content(sendChatPhoto).build().execute(new GenericsCallback<String>(new JsonGenericsSerializator()) {

            @Override
            public void onError(Call call, Exception e, int id) {
                Log.i("通讯发送数据error", "" + e.getMessage());
                chatSendPhotoInterface.getChatSendPhotoError();
            }

            @Override
            public void onResponse(String response, int id) {
                Log.i("通讯发送数据success", "" + response);
                try {
                    JSONObject jsonObject = new JSONObject(response);
                    int d = jsonObject.getInt("d");
                    if (d > 0) {
                        chatSendPhotoInterface.getChatSendPhotoSuccess();
                    } else {
                        chatSendPhotoInterface.getChatSendPhotoError();
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }

            }
        });
    }

    /*
     * 检查设备在线信息
     * @param dataType:上传方式(4:检查设备状态)
     * @param DeviceGUID：设备号
     * @param userId：用户ID
     * @param status：状态
     */
    public static void sendStatus(int dataType, String DeviceGUID, int userId, int status) {
        Map<String, Object> params = new HashMap<>();
        params.put("dataType", dataType);
        params.put("DeviceGUID", DeviceGUID);
        params.put("userId", userId);
        params.put("status", status);
        String json = JSON.toJSONString(params);
        OkHttpUtils.postString().url(WMUrlConfig.URL).mediaType(MediaType.parse("application/json; charset=utf-8")).content(json).build().execute(new GenericsCallback(new JsonGenericsSerializator()) {
            @Override
            public void onError(Call call, Exception e, int id) {
                Log.i("status", "fail");
            }

            @Override
            public void onResponse(Object response, int id) {
                Log.i("status", "success");
            }
        });
    }

}
