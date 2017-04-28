package cn.com.task.interfaces;

import java.util.ArrayList;

import cn.com.task.bean.TaskChoosePersonBean;


/**
 * 文件名：ChoosePersonInterface
 * 描    述：获取人员部门的接口
 * 作    者：stt
 * 时    间：2017.03.21
 * 版    本：V1.0.7
 */
public interface ChoosePersonInterface {
    void getPerson(ArrayList<TaskChoosePersonBean> choosePersonList);
}
