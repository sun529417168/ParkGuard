package cn.com.task.interfaces;


import cn.com.task.bean.TaskDetailBean;

/**
 * 文件名：TaskAssignedInterface
 * 描    述：获取任务反馈信息的接口
 * 作    者：stt
 * 时    间：2017.02.23
 * 版    本：V1.0.0
 */

public interface TaskAssignedInterface {
    void getTaskDetail(TaskDetailBean taskDetailBean);
}
