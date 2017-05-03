package cn.com.problem.interfaces;


import cn.com.problem.bean.UserBean;

/**
 * 接口名:ProblemLoginInterface
 * 描  述: 登录成功回调接口
 * 作  者:zzq
 * 时  间:2017年4月26日
 * 版  本:V1.0,0
 */

public interface ProblemLoginInterface {
    /**
     * 登录成功回调方法
     *
     * @param userBean
     */
    void getLoginResult(UserBean userBean);
}
