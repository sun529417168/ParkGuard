package cn.com.watchman.interfaces;

import cn.com.watchman.service.GPSService;

/**
 * Created by 志强 on 2017.6.1.
 */

public interface PlayingNotification {
    int NOTIFICATION_ID = 1;

    void init(GPSService service);

    void update();

    void stop();
}
