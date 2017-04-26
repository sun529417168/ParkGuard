package com.linked.erfli.library.utils;

import android.app.Activity;
import android.os.Process;

import java.util.LinkedList;

/**
 * 文件名：PGActivityUtil
 * 描    述：管理activity的工具类
 * 作    者：stt
 * 时    间：2016.12.30
 * 版    本：V1.0.0
 */
public class PGActivityUtil {

    private static LinkedList<Activity> activitys = null;
    /**
     * 程序手势是否弹出状态，true：不弹出，false：弹出
     */
    private static boolean flag = false;
    public int runFlag = 1;
    private static PGActivityUtil instance;
    private long clickTime = 0; //记录第一次点击的时间

    private PGActivityUtil() {
        activitys = new LinkedList<Activity>();
    }


    /**
     * 单例模式中获取唯一的BYApplication实例
     *
     * @return
     */
    public static PGActivityUtil getInstance() {
        if (null == instance) {
            instance = new PGActivityUtil();
        }
        return instance;

    }

    /**
     * 添加Activity到容器中
     *
     * @param activity void
     */
    public void addActivity(Activity activity) {
        if (activitys != null && activitys.size() > 0) {
            if (!activitys.contains(activity)) {
                activitys.add(activity);
            } else {
                activitys.remove(activity);
                activitys.add(activity);
            }
        } else {
            activitys.add(activity);
        }

    }

    /**
     * 提取顶部activity
     *
     * @return Activity
     */
    public Activity getTopActivity() {
        Activity aty = null;
        if (activitys != null && activitys.size() > 0) {

            aty = (Activity) activitys.get(activitys.size() - 1);

        }
        return aty;
    }

    /**
     * 遍历所有Activity并finish void
     */
    public void exit() {
        if (activitys != null && activitys.size() > 0) {
            for (int i = 0; i < activitys.size(); i++) {
                Activity aty = (Activity) activitys.get(i);
                aty.finish();

            }
            activitys.clear();
        }
        Process.killProcess(Process.myPid());
        System.exit(0);
    }

    /**
     * 关闭其他，保留第一个
     */
    public void changeOne() {
        if (activitys != null && activitys.size() > 1) {
            for (int i = 1; i < activitys.size(); i++) {
                Activity aty = (Activity) activitys.get(i);
                aty.finish();

            }
            Activity aty = (Activity) activitys.get(0);
            activitys.clear();
            activitys.add(aty);
        }

    }

    /**
     * 保留顶部
     */
    public void changeTopOne() {
        if (activitys != null && activitys.size() > 1) {
            for (int i = activitys.size() - 2; i >= 0; i--) {
                Activity aty = (Activity) activitys.get(i);
                aty.finish();

            }
            Activity aty = (Activity) activitys.get(activitys.size() - 1);
            activitys.clear();
            activitys.add(aty);
        }
    }

    /**
     * 关闭顶部activity void
     */
    public void finishTop() {
        if (activitys != null && activitys.size() > 0) {
            Activity aty = (Activity) activitys.get(activitys.size() - 1);
            aty.finish();
            activitys.remove(activitys.size() - 1);
        } else {
            Process.killProcess(Process.myPid());
            System.exit(0);
        }


    }

    public void setFlag(boolean flag) {
        PGActivityUtil.flag = flag;
    }

    public boolean getFlag() {
        return flag;
    }

    @Override
    public String toString() {
        // TODO Auto-generated method stub
        return activitys.toString();
    }

}
