package cn.com.watchman.utils;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

import cn.com.watchman.activity.WatchMainActivity;

/**
 * Created by zzq on 15/8/3.
 */
public class NotificationReceiver extends BroadcastReceiver {

    @Override
    public void onReceive(Context context, Intent intent) {
        //判断app进程是否存活
        if (SystemUtils.isAppAlive(context, "cn.com.parkguard")) {
            Intent mainIntent = new Intent(context, WatchMainActivity.class);
            mainIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            Intent detailIntent = new Intent(context, WatchMainActivity.class);
            Intent[] intents = {mainIntent, detailIntent};
            context.startActivities(intents);
        } else {
            Intent launchIntent = context.getPackageManager().
                    getLaunchIntentForPackage("cn.com.parkguard");
            launchIntent.setFlags(
                    Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_RESET_TASK_IF_NEEDED);
            context.startActivity(launchIntent);
        }
    }
}
