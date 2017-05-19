package cn.com.problem.networkrequest;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.ListView;

import com.alibaba.fastjson.JSON;
import com.linked.erfli.library.config.UrlConfig;
import com.linked.erfli.library.okhttps.OkHttpUtils;
import com.linked.erfli.library.okhttps.callback.GenericsCallback;
import com.linked.erfli.library.okhttps.callback.StringCallback;
import com.linked.erfli.library.okhttps.utils.JsonGenericsSerializator;
import com.linked.erfli.library.utils.DialogUtils;
import com.linked.erfli.library.utils.LoadingDialogUtil;
import com.linked.erfli.library.utils.SharedUtil;
import com.linked.erfli.library.utils.ToastUtil;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import cn.com.problem.ProblemActivity;
import cn.com.problem.adapter.PopProblemTypeRightAdapter;
import cn.com.problem.bean.PersonBean;
import cn.com.problem.bean.ProblemBean;
import cn.com.problem.bean.ProblemDetailBean;
import cn.com.problem.bean.ProblemTypeLeft;
import cn.com.problem.bean.ProblemTypeRight;
import cn.com.problem.bean.UserBean;
import cn.com.problem.interfaces.PersonInfoInterface;
import cn.com.problem.interfaces.ProblemDetailInterface;
import cn.com.problem.interfaces.ProblemListInterface;
import cn.com.problem.interfaces.ProblemLoginInterface;
import cn.com.problem.interfaces.ProblemTypeLeftInterface;
import cn.com.problem.interfaces.ProblemTypeRightInterface;
import okhttp3.Call;

/**
 * Created by 志强 on 2017.5.2.
 */

public class ProblemRequest {
    /**
     * 功能:登录接口
     *
     * @param mActivity 当前类
     * @param userName  用户名
     * @param password  密码
     */

    public static void loginRequest(final Activity mActivity, final String userName, final String password) {
        LoadingDialogUtil.show(mActivity);//显示加载
        final ProblemLoginInterface problemLoginInterface = (ProblemLoginInterface) mActivity;
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
                    problemLoginInterface.getLoginResult(userBean);
                }
                if (LoadingDialogUtil.show(mActivity).isShowing()) {
                    LoadingDialogUtil.dismiss();
                }
            }
        });
    }

    /**
     * 方法名：problemListRequest
     * 功    能：问题列表
     * 参    数：Context activity, TaskListInterface myTaskListInterface, Object... strings
     * 返回值：无
     */
    public static void problemListRequest(final Context activity, ProblemListInterface mProblemListInterface, Object... strings) {
        final ProblemListInterface problemListInterface = mProblemListInterface;
        LoadingDialogUtil.show(activity);//显示加载
        Map<String, Object> params = new HashMap<>();
        try {
            params.put("pageindex", strings[0]);
            params.put("SearchState", strings[1]);
            params.put("SearchProblemType", strings[2]);
            params.put("SearchDateNumber", strings[3]);
            params.put("pagesize", "5");//每页条数
            params.put("PersonID", SharedUtil.getString(activity, "PersonID"));
        } catch (Exception e) {
            e.printStackTrace();
        }
        OkHttpUtils.post().url(UrlConfig.URL_GETPROBLEMREPORTINFO).params(params).build().execute(new GenericsCallback<String>(new JsonGenericsSerializator()) {
            @Override
            public void onResponse(String response, int id) {
                response = response.replace(":null,", ":\"\",");
                Log.i("liebiao", response);
                ProblemBean problemBean = JSON.parseObject(response, ProblemBean.class);
                problemListInterface.showTaskList(problemBean);
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
     * 方法名：getTypeListByCodeRequest
     * 功    能：获取二级节点信息（根据code）
     * 参    数：Activity activity final String username, final String password
     * 返回值：无
     */
    public static void getTypeListByCodeRequest(final Context activity, String code, final ListView listView, final ProblemTypeRightInterface problemTypeRightInterfaces) {
        Map<String, Object> params = new HashMap<>();
        try {
            params.put("SearchProblemType", code);
        } catch (Exception e) {
            e.printStackTrace();
        }
        OkHttpUtils.post().url(UrlConfig.URL_GETTYPELISTBYCODE).params(params).build().execute(new GenericsCallback<String>(new JsonGenericsSerializator()) {
            @Override
            public void onResponse(String response, int id) {
                listView.setVisibility(View.VISIBLE);
                ArrayList<ProblemTypeRight> problemTypeRight = (ArrayList<ProblemTypeRight>) JSON.parseArray(response, ProblemTypeRight.class);
                PopProblemTypeRightAdapter problemTypeRightAdapter = new PopProblemTypeRightAdapter(activity, problemTypeRight, problemTypeRightInterfaces);
                listView.setAdapter(problemTypeRightAdapter);
            }

            @Override
            public void onError(Call call, Exception e, int id) {
                ToastUtil.show(activity, "服务器有错误，请稍候再试");
            }
        });
    }

    /**
     * 方法名：getProblemTypeLeft
     * 功    能：获取一级节点信息
     * 参    数：Activity activity final String username, final String password
     * 返回值：无
     */
    public static void getProblemTypeLeft(final Activity activity, ProblemTypeLeftInterface problemTypeLeftInterfaces) {
        final ProblemTypeLeftInterface problemTypeLeftInterface = problemTypeLeftInterfaces;
        OkHttpUtils.get().url(UrlConfig.URL_GETBEGINNINGENTITY).build().execute(new GenericsCallback<String>(new JsonGenericsSerializator()) {
            @Override
            public void onResponse(String response, int id) {
                ArrayList<ProblemTypeLeft> problemTypeLeftList = (ArrayList<ProblemTypeLeft>) JSON.parseArray(response, ProblemTypeLeft.class);
                problemTypeLeftInterface.getTypeLeft(problemTypeLeftList);
            }

            @Override
            public void onError(Call call, Exception e, int id) {
                ToastUtil.show(activity, "服务器有错误，请稍候再试");
            }
        });
    }

    /**
     * 方法名：addProblemRequestsb
     * 功    能：上报问题最新方法不管有没有图片
     * 参    数：Context activity, Map<String,File> fileMap, Object... strings
     * 返回值：无
     */
    public static void addProblemRequestsb(final Activity activity, Map<String, File> fileMap, Object... strings) {
        LoadingDialogUtil.show(activity);//显示加载
        Map<String, Object> params = new HashMap<>();
        params.put("ProblemTitle", "问题名称");
        params.put("SearchProblemType", strings[0]);
        params.put("Position", strings[1]);
        params.put("GPS", strings[2]);
        params.put("FindDate", strings[3]);
        params.put("ProblemDes", strings[4]);
        params.put("ReportPerson", SharedUtil.getString(activity, "PersonID"));
        OkHttpUtils.post().files("mFile", fileMap).url(UrlConfig.URL_IMGUPLOAD).params(params).build().execute(new StringCallback() {
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
                ToastUtil.show(activity, "服务器异常，请稍后再试");
                if (LoadingDialogUtil.show(activity).isShowing()) {
                    LoadingDialogUtil.dismiss();
                }
            }
        });
    }

    /**
     * 方法名：problemDetailRequest
     * 功    能：问题详情
     * 参    数：Context activity, TaskListInterface myTaskListInterface, Object... strings
     * 返回值：无
     */
    public static void problemDetailRequest(final Activity activity, String id) {
        final ProblemDetailInterface problemDetail = (ProblemDetailInterface) activity;
        LoadingDialogUtil.show(activity);//显示加载
        Map<String, Object> params = new HashMap<>();
        try {
            params.put("ProblemReportID", id);
        } catch (Exception e) {
            e.printStackTrace();
        }
        OkHttpUtils.post().url(UrlConfig.URL_GETPROBLEMDETAIL).params(params).build().execute(new GenericsCallback<String>(new JsonGenericsSerializator()) {
            @Override
            public void onResponse(String response, int id) {
                Log.i("liebiao", response);
                ProblemDetailBean problemBean = JSON.parseObject(response, ProblemDetailBean.class);
                problemDetail.getProblemDetail(problemBean);
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
                    Intent intent = new Intent(activity, ProblemActivity.class);
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
}
