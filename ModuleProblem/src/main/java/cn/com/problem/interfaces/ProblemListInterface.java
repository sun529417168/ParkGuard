package cn.com.problem.interfaces;


import cn.com.problem.bean.ProblemBean;

/**
 * 文件名：ProblemListInterface
 * 描    述：问题列表的接口回调
 * 作    者：stt
 * 时    间：2017.02.18
 * 版    本：V1.0.0
 */

public interface ProblemListInterface {
    void showTaskList(ProblemBean problemBean);
}
