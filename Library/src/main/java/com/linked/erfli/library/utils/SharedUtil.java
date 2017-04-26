package com.linked.erfli.library.utils;


import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

/**
 * 文件名：SharedUtil
 * 描    述：Shared工具类
 * 作    者：stt
 * 时    间：2016.12.30
 * 版    本：V1.0.0
 */
public class SharedUtil {

    public static SharedPreferences mPreference;


    /**
     * 是否切换了账户
     */
    public static final String IS_SWITCH_ACCOUNTS = "IS_SWITCH_ACCOUNTS";
    /**
     * 是否登录
     */
    public static final String IS_LOGINED = "isLogined";
    /**
     * 是否初次登录进入
     */
    public static final String IS_FIRST_IN = "isFirstIn";
    /**
     * 行程计划是否发布成功
     */
    public static final String IS_POST_STROKE = "isPostStroke";


    /**
     * 是否同意免责条款
     */
    public static final String IS_AGREE_STATE = "isAgreeState";

    public static SharedPreferences getPreference(Context context) {
        if (mPreference == null)
            mPreference = PreferenceManager.getDefaultSharedPreferences(context);
        return mPreference;
    }

    public static void setInteger(Context context, String name, int value) {
        getPreference(context).edit().putInt(name, value).commit();
    }

    public static int getInteger(Context context, String name, int default_i) {
        return getPreference(context).getInt(name, default_i);
    }

    public static void setString(Context context, String name, String value) {
        getPreference(context).edit().putString(name, value).commit();
    }

    public static void setString(Context context, String name, Double value) {
        getPreference(context).edit().putString(name, value + "").commit();
    }

    public static String getString(Context context, String name) {
        return getPreference(context).getString(name, null);
    }

    public static String getString(Context context, String name, String defalt) {
        return getPreference(context).getString(name, defalt);
    }

    public static void setStringArr(Context context, String name, String[] value) {
        Set<String> set =new HashSet<String>(Arrays.asList(value));
        getPreference(context).edit().putStringSet(name, set).commit();
    }

    public static String[] getStringArr(Context context, String name) {
        Set<String> siteno = new HashSet<String>();
        siteno = getPreference(context).getStringSet(name, siteno);
        String[] data = siteno.toArray(new String[siteno.size()]);
        return  data;
    }

    /**
     * 获取boolean类型的配置
     *
     * @param context
     * @param name
     * @param defaultValue
     * @return
     */
    public static boolean getBoolean(Context context, String name, boolean defaultValue) {
        return getPreference(context).getBoolean(name, defaultValue);
    }

    /**
     * 设置boolean类型的配置
     *
     * @param context
     * @param name
     * @param value
     */
    public static void setBoolean(Context context, String name, boolean value) {
        getPreference(context).edit().putBoolean(name, value).commit();
    }

    public static Long getLong(Context context, String name, long defaultValue) {
        return getPreference(context).getLong(name, defaultValue);
    }

    public static void setLong(Context context, String name, long value) {
        getPreference(context).edit().putLong(name, value).commit();
    }

    public static void setMoreService(Context context, String name, String value) {
        getPreference(context).edit().putString(name, value).commit();
    }

    public static String getMoreService(Context context, String name, String defaultValue) {
        return getPreference(context).getString(name, defaultValue);
    }

    // 通过键删除值
    public static void isValue(Context context, String name) {
        getPreference(context).edit().remove(name);
    }


}
