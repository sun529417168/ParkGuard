package cn.com.task.networkrequest;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.text.TextUtils;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import com.alibaba.fastjson.JSON;
import com.linked.erfli.library.config.UrlConfig;
import com.linked.erfli.library.okhttps.OkHttpUtils;
import com.linked.erfli.library.okhttps.callback.GenericsCallback;
import com.linked.erfli.library.okhttps.callback.StringCallback;
import com.linked.erfli.library.okhttps.utils.JsonGenericsSerializator;
import com.linked.erfli.library.utils.DialogUtils;
import com.linked.erfli.library.utils.SharedUtil;
import com.linked.erfli.library.utils.ToastUtil;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import cn.com.task.TaskActivity;
import cn.com.task.bean.PersonBean;
import cn.com.task.bean.TaskBean;
import cn.com.task.bean.TaskChoosePersonBean;
import cn.com.task.bean.TaskDetailBean;
import cn.com.task.bean.TaskPriorityBean;
import cn.com.task.bean.TaskTypeBean;
import cn.com.task.bean.UserBean;
import cn.com.task.interfaces.ChoosePersonInterface;
import cn.com.task.interfaces.PersonInfoInterface;
import cn.com.task.interfaces.TaskAssignedInterface;
import cn.com.task.interfaces.TaskListInterface;
import cn.com.task.interfaces.TaskLoginInterface;
import cn.com.task.interfaces.TaskTypeValuesInterface;
import cn.com.task.utils.LoadingDialogUtil;
import cn.com.task.weight.AddTaskPriorityPopwindow;
import cn.com.task.weight.AddTaskTypePopwindow;
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


    /**
     * 方法名：tasIssuedListRequest
     * 功    能：任务列表
     * 参    数：Activity activity String... strings
     * 返回值：无
     */
    public static void tasIssuedListRequest(final Context activity, TaskListInterface myTaskListInterface, Object... strings) {
        final TaskListInterface taskListInterface = myTaskListInterface;
        LoadingDialogUtil.show(activity);
        Map<String, Object> params = new HashMap<>();
        try {
            params.put("pageindex", strings[0]);
            params.put("pagesize", strings[1]);
            params.put("state", strings[2]);
            params.put("PersonID", SharedUtil.getString(activity, "PersonID"));
        } catch (Exception e) {
            e.printStackTrace();
        }
        OkHttpUtils.post().url(UrlConfig.URL_GETTASISSUEDLIST).params(params).build().execute(new GenericsCallback<String>(new JsonGenericsSerializator()) {
            @Override
            public void onResponse(String response, int id) {
                Log.i("taskList", response);
                TaskBean taskList = JSON.parseObject(response, TaskBean.class);
                taskListInterface.showTaskList(taskList);
                if (LoadingDialogUtil.show(activity).isShowing()) {
                    LoadingDialogUtil.dismiss();
                }
            }

            @Override
            public void onError(Call call, Exception e, int id) {
                ToastUtil.show(activity, "连接超时，请稍后再试");
                if (LoadingDialogUtil.show(activity).isShowing()) {
                    LoadingDialogUtil.dismiss();
                }
            }
        });
    }

    /**
     * 方法名：taskDetailRequest
     * 功    能：任务详情+反馈
     * 参    数：final Context activity, TaskListInterface myTaskListInterface, String id
     * 返回值：无
     */
    public static void taskDetailTaskAssignedRequest(final Activity activity, String id) {
        LoadingDialogUtil.show(activity);
        final TaskAssignedInterface taskAssignedInterface = (TaskAssignedInterface) activity;
        Map<String, Object> params = new HashMap<>();
        try {
            params.put("TaskAssignedID", id);
        } catch (Exception e) {
            e.printStackTrace();
        }
        OkHttpUtils.post().url(UrlConfig.URL_GETTASKINFOANDTASKASSIGNEDINFOBYTASKASSIGNEDID).params(params).build().execute(new GenericsCallback<String>(new JsonGenericsSerializator()) {
            @Override
            public void onResponse(String response, int id) {
                Log.i("taskDetailRequest", response);
                TaskDetailBean taskBean = JSON.parseObject(response, TaskDetailBean.class);
                taskAssignedInterface.getTaskDetail(taskBean);
                if (LoadingDialogUtil.show(activity).isShowing()) {
                    LoadingDialogUtil.dismiss();
                }
            }

            @Override
            public void onError(Call call, Exception e, int id) {
                ToastUtil.show(activity, "连接超时，请稍后再试");
                if (LoadingDialogUtil.show(activity).isShowing()) {
                    LoadingDialogUtil.dismiss();
                }
            }
        });
    }

    /**
     * 方法名：filesRequest
     * 功    能：任务反馈+上传图片
     * 参    数：Context activity, Map<String, File> params
     * 返回值：无
     */
    public static void filesRequest(final Activity activity, final ImageView reverse, Map<String, File> fileMap, Object... strings) {
        LoadingDialogUtil.show(activity);
        Map<String, Object> params = new HashMap<>();
        params.put("FeedBackContent", strings[0]);
        params.put("TaskAssignedID", strings[1]);
        params.put("FeedbackState", strings[2]);
        OkHttpUtils.post().files("mFile", fileMap).url(UrlConfig.URL_TASKASSIGNEDINFO).params(params).build().execute(new StringCallback() {
            @Override
            public void onResponse(String response, int id) {
                if ("true".equals(response)) {
                    ToastUtil.show(activity, "反馈成功");
                    reverse.setVisibility(View.GONE);
                    activity.finish();
                } else {
                    ToastUtil.show(activity, "反馈失败，请稍候再试");
                    reverse.setVisibility(View.VISIBLE);
                }
                if (LoadingDialogUtil.show(activity).isShowing()) {
                    LoadingDialogUtil.dismiss();
                }
            }

            @Override
            public void onError(Call call, Exception e, int id) {
                ToastUtil.show(activity, "错误代码" + e.getMessage().toString());
                reverse.setVisibility(View.VISIBLE);
                if (LoadingDialogUtil.show(activity).isShowing()) {
                    LoadingDialogUtil.dismiss();
                }
            }
        });
    }

    /**
     * 方法名：getTaskTypeRequest
     * 功    能：获取任务下发类型
     * 参    数：final Activity activity, final RelativeLayout typeLayout
     * 返回值：无
     */
    public static void getTaskTypeRequest(final Activity activity, final RelativeLayout typeLayout, final int index) {
        Map<String, Object> params = new HashMap<>();
        LoadingDialogUtil.show(activity);
        final TaskTypeValuesInterface taskTypeValuesInterface = (TaskTypeValuesInterface) activity;
        OkHttpUtils.post().url(UrlConfig.URL_GETTASKTYPE).params(params).build().execute(new GenericsCallback<String>(new JsonGenericsSerializator()) {
            @Override
            public void onResponse(String response, int id) {
                ArrayList<TaskTypeBean> listBean = (ArrayList<TaskTypeBean>) JSON.parseArray(response, TaskTypeBean.class);
                AddTaskTypePopwindow mAddTaskType = new AddTaskTypePopwindow(activity, listBean);
                mAddTaskType.showAtLocation(typeLayout, Gravity.BOTTOM, 0, 0);
                mAddTaskType.setAddresskListener(new AddTaskTypePopwindow.OnAddressCListener() {
                    @Override
                    public void onClick(String name, String code) {
                        taskTypeValuesInterface.getTaskType(name, code, index);
                    }
                });
                if (LoadingDialogUtil.show(activity).isShowing()) {
                    LoadingDialogUtil.dismiss();
                }
            }

            @Override
            public void onError(Call call, Exception e, int id) {
                ToastUtil.show(activity, "服务器有错误，请稍候再试");
                if (LoadingDialogUtil.show(activity).isShowing()) {
                    LoadingDialogUtil.dismiss();
                }
            }
        });
    }

    /**
     * 方法名：getPriorityRequest
     * 功    能：获取优先级类型
     * 参    数：final Activity activity, final RelativeLayout typeLayout
     * 返回值：无
     */
    public static void getPriorityRequest(final Activity activity, final RelativeLayout typeLayout, final int index) {
        LoadingDialogUtil.show(activity);
        final TaskTypeValuesInterface taskTypeValuesInterface = (TaskTypeValuesInterface) activity;
        OkHttpUtils.get().url(UrlConfig.URL_GETTASKPRIORITY).build().execute(new GenericsCallback<String>(new JsonGenericsSerializator()) {
            @Override
            public void onResponse(String response, int id) {
                ArrayList<TaskPriorityBean> listBean = (ArrayList<TaskPriorityBean>) JSON.parseArray(response, TaskPriorityBean.class);
                AddTaskPriorityPopwindow mAddTaskPriority = new AddTaskPriorityPopwindow(activity, listBean);
                mAddTaskPriority.showAtLocation(typeLayout, Gravity.BOTTOM, 0, 0);
                mAddTaskPriority.setAddresskListener(new AddTaskPriorityPopwindow.OnAddressCListener() {
                    @Override
                    public void onClick(String name, String code) {
                        taskTypeValuesInterface.getTaskType(name, code, index);
                    }
                });
                if (LoadingDialogUtil.show(activity).isShowing()) {
                    LoadingDialogUtil.dismiss();
                }
            }

            @Override
            public void onError(Call call, Exception e, int id) {
                ToastUtil.show(activity, "服务器有错误，请稍候再试");
                if (LoadingDialogUtil.show(activity).isShowing()) {
                    LoadingDialogUtil.dismiss();
                }
            }
        });
    }

    /**
     * 方法名：addTaskRequests
     * 功    能：新增任务
     * 参    数：Context activity, Map<String,File> fileMap, Object... strings
     * 返回值：无
     */
    public static void addTaskRequests(final Activity activity, Map<String, File> fileMap, Object... strings) {
        LoadingDialogUtil.show(activity);
        Map<String, Object> params = new HashMap<>();
        params.put("AddTaskName", strings[0]);
        params.put("AddTaskType", strings[1]);
        params.put("AddTaskAddr", strings[2]);
        params.put("AddTaskPriority", strings[3]);
        params.put("AddStartDate", strings[4]);
        params.put("AddEndDate", strings[5]);
        params.put("PersonIDs", strings[6]);
        params.put("AddTaskDes", strings[7]);
        params.put("PersonID", SharedUtil.getString(activity, "PersonID"));
        OkHttpUtils.post().files("AddImageZ", fileMap).url(UrlConfig.URL_UPLOADTASK).params(params).build().execute(new StringCallback() {
            @Override
            public void onResponse(String response, int id) {
                if ("true".equals(response)) {
                    ToastUtil.show(activity, "上报成功");
                    activity.finish();
                } else {
                    ToastUtil.show(activity, "上报失败，请稍候再试");
                }
                if (LoadingDialogUtil.show(activity).isShowing()) {
                    LoadingDialogUtil.dismiss();
                }
            }

            @Override
            public void onError(Call call, Exception e, int id) {
                Log.i("addTaskRequestsError", e.getMessage().toString());
                ToastUtil.show(activity, "服务器异常，请稍后再试");
                if (LoadingDialogUtil.show(activity).isShowing()) {
                    LoadingDialogUtil.dismiss();
                }
            }
        });
    }

    /**
     * 方法名：GetPersonInfoByDepartmentRequest
     * 功    能：获取人员部门树状结构数据
     * 参    数：final Activity activity
     * 返回值：无
     */
    public static void GetPersonInfoByDepartmentRequest(final Activity activity) {
        LoadingDialogUtil.show(activity);
        final ChoosePersonInterface personInterface = (ChoosePersonInterface) activity;
        OkHttpUtils.get().url(UrlConfig.URL_GETPERSONINFOBYDEPARTMENT).build().execute(new GenericsCallback<String>(new JsonGenericsSerializator()) {
            @Override
            public void onResponse(String response, int id) {
                ArrayList<TaskChoosePersonBean> choosePersonList = (ArrayList<TaskChoosePersonBean>) JSON.parseArray(response, TaskChoosePersonBean.class);
                personInterface.getPerson(choosePersonList);
                if (LoadingDialogUtil.show(activity).isShowing()) {
                    LoadingDialogUtil.dismiss();
                }
            }

            @Override
            public void onError(Call call, Exception e, int id) {
                ToastUtil.show(activity, "服务器有错误，请稍候再试");
                if (LoadingDialogUtil.show(activity).isShowing()) {
                    LoadingDialogUtil.dismiss();
                }
            }
        });
    }

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
                    Intent intent = new Intent(activity, TaskActivity.class);
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
}
