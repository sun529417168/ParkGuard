package cn.com.task.interfaces;

import cn.com.task.bean.UserBean;

/**
 * 接口名:TaskLoginInterface
 * 描  述: 登录成功回调接口
 * 作  者:zzq
 * 时  间:2017年4月26日
 * 版  本:V1.0,0
 */

public interface TaskLoginInterface {
    /**
     * 登录成功回调方法
     *
     * @param userBean
     */
    void getLoginResult(UserBean userBean);
}
