package cn.com.watchman.interfaces;

import cn.com.watchman.bean.GPSBean;

/**
 * 文件名：GPSInfoInterface
 * 描    述：GPS信息的回调接口
 * 作    者：stt
 * 时    间：2017.5.10
 * 版    本：V1.0.0
 */
public interface GPSInfoInterface {
    void getGPSInfo(GPSBean gpsBean);
}
