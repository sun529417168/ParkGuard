package cn.com.watchman.utils;

import android.app.Activity;
import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.support.v4.app.NotificationCompat;
import android.widget.RemoteViews;

import com.linked.erfli.library.utils.ActivityCollector;
import com.linked.erfli.library.utils.SharedUtil;

import cn.com.watchman.R;
import cn.com.watchman.activity.WatchMainActivity;
import cn.com.watchman.interfaces.PlayingNotification;
import cn.com.watchman.service.GPSService;

import static android.content.Context.NOTIFICATION_SERVICE;
import static com.alibaba.sdk.android.ams.common.global.AmsGlobalHolder.getPackageName;

/**
 * 文件名：NotifyUtils
 * 描    述：通知栏的工具类
 * 作    者：stt
 * 时    间：2017.4.25
 * 版    本：V1.0.0
 */

public class NotifyUtils implements PlayingNotification {
    /**
     * 是否在播放
     */
    public boolean isPlay = false;
    /**
     * 通知栏按钮广播
     */
    public ButtonBroadcastReceiver bReceiver;
    /**
     * 通知栏按钮点击事件对应的ACTION
     */
    public final static String ACTION_BUTTON = "com.notifications.intent.action.ButtonClick";
    private Activity activity;
    /**
     * Notification管理
     */
    public NotificationManager mNotificationManager;
    //    public MyNotifyBroadcastClickInterface myNotifyBroadcastClickInterface;
//    public boolean b;
    public GPSService gpsService;
    public static final String ACTION_NOTIFICATION_REWIND = "action_media_rewind";


    public NotifyUtils(Activity activity) {
        this.activity = activity;
//        this.b = b;
//        this.myNotifyBroadcastClickInterface = notifyBroadcastClickInterface;
        mNotificationManager = (NotificationManager) activity.getSystemService(NOTIFICATION_SERVICE);
//        initButtonReceiver();
    }

    RemoteViews mRemoteViews;

    @Override
    public synchronized void init(GPSService service) {
        this.gpsService = service;
    }

    @Override
    public void update() {

    }

    @Override
    public void stop() {
        gpsService.stopForeground(true);
        clearAllNotify();
    }

    public void showButtonNotify(int count) {
        NotificationCompat.Builder mBuilder = new NotificationCompat.Builder(activity);
        mRemoteViews = new RemoteViews(getPackageName(), R.layout.view_custom_button);
        mRemoteViews.setImageViewResource(R.id.custom_song_icon, R.mipmap.watch_logo);
        //API3.0 以上的时候显示按钮，否则消失
        mRemoteViews.setTextViewText(R.id.tv_custom_song_singer, "当前上传次数:" + count + "次");
        mRemoteViews.setTextViewText(R.id.tv_day_send_count, "当天上传次数:"+SharedUtil.getInteger(activity.getApplicationContext(), "totalCount", 0) + "次");
        //如果版本号低于（3。0），那么不显示按钮
//        if (getSystemVersion() <= 9) {
//            mRemoteViews.setViewVisibility(R.id.ll_custom_button, View.GONE);
//        } else {
//            mRemoteViews.setViewVisibility(R.id.ll_custom_button, View.VISIBLE);
//            //
//            Log.i("", "服务状态打印:" + SharedUtil.getBoolean(activity, "serviceFlag", true));
//            if (!SharedUtil.getBoolean(activity, "serviceFlag", true)) {
//                mRemoteViews.setImageViewResource(R.id.btn_custom_play, R.drawable.btn_pause);
//            } else {
//                mRemoteViews.setImageViewResource(R.id.btn_custom_play, R.drawable.btn_play);
//            }
//        }

        //点击的事件处理
//        Intent buttonIntent = new Intent(ACTION_BUTTON);
//        /* 播放/暂停  按钮 */
//        buttonIntent.putExtra(INTENT_BUTTONID_TAG, BUTTON_PALY_ID);
//        PendingIntent intent_paly = PendingIntent.getBroadcast(activity.getApplication(), 2, buttonIntent, PendingIntent.FLAG_UPDATE_CURRENT);
//        mRemoteViews.setOnClickPendingIntent(R.id.btn_custom_play, intent_paly);

        //=====

//        // Previous track
//        intent_paly = buildPendingIntent(gpsService, GPSService.ACTION_NOTIFICATION_REWIND, serviceName);
//        mRemoteViews.setOnClickPendingIntent(R.id.btn_custom_play, intent_paly);



        /* 下一首 按钮  */
//        buttonIntent.putExtra(INTENT_BUTTONID_TAG, BUTTON_NEXT_ID);
//        PendingIntent intent_next = PendingIntent.getBroadcast(activity.getApplication(), 3, buttonIntent, PendingIntent.FLAG_UPDATE_CURRENT);
//        mRemoteViews.setOnClickPendingIntent(R.id.btn_custom_next, intent_next);

//        buttonIntent.putExtra(INTENT_BUTTONID_TAG, RELATIVE_ID);
//        PendingIntent intent_relative = PendingIntent.getBroadcast(activity, 4, buttonIntent, PendingIntent.FLAG_UPDATE_CURRENT);
//        mRemoteViews.setOnClickPendingIntent(R.id.rl_intentActivity, intent_relative);
        mBuilder.setContent(mRemoteViews)
                .setContentIntent(getDefalutIntent(Notification.FLAG_ONGOING_EVENT))
                .setWhen(System.currentTimeMillis())// 通知产生的时间，会在通知信息里显示
                .setTicker("开始上传")
                .setPriority(Notification.PRIORITY_DEFAULT)// 设置该通知优先级
                .setOngoing(true)
//                .setDefaults(Notification.DEFAULT_VIBRATE)//向通知添加声音、闪灯和振动效果的最简单、最一致的方式是使用当前的用户默认设置，使用defaults属性，可以组合：
                .setSmallIcon(R.mipmap.watch_logo);
        Notification notify = mBuilder.build();
        notify.flags = Notification.FLAG_ONGOING_EVENT;
        //会报错，还在找解决思路
//		notify.contentView = mRemoteViews;
//		notify.contentIntent = PendingIntent.getActivity(activity, 0, new Intent(), 0);
        mNotificationManager.notify(200, notify);
    }

    /**
     * 获取当前应用版本号
     *
     * @param context
     * @return version
     * @throws Exception
     */
    public static String getAppVersion(Context context) throws Exception {
        // 获取packagemanager的实例
        PackageManager packageManager = context.getPackageManager();
        // getPackageName()是你当前类的包名，0代表是获取版本信息
        PackageInfo packInfo = packageManager.getPackageInfo(context.getPackageName(), 0);
        String versionName = packInfo.versionName;
        return versionName;
    }

    /**
     * 获取当前系统SDK版本号
     */
    public static int getSystemVersion() {
        /*获取当前系统的android版本号*/
        int version = android.os.Build.VERSION.SDK_INT;
        return version;
    }

    public final static String INTENT_BUTTONID_TAG = "ButtonId";
    /**
     * 上一首 按钮点击 ID
     */
    public final static int BUTTON_PREV_ID = 1;
    /**
     * 播放/暂停 按钮点击 ID
     */
    public final static int BUTTON_PALY_ID = 2;
    /**
     * 下一首 按钮点击 ID
     */
    public final static int BUTTON_NEXT_ID = 3;

    public final static int RELATIVE_ID = 4;

    /**
     * 带按钮的通知栏点击广播接收
     */
    public void initButtonReceiver() {
        bReceiver = new ButtonBroadcastReceiver();
        IntentFilter intentFilter = new IntentFilter();
        intentFilter.addAction(ACTION_BUTTON);
        activity.registerReceiver(bReceiver, intentFilter);
    }


    /**
     * 广播监听按钮点击时间
     */
    public class ButtonBroadcastReceiver extends BroadcastReceiver {


        @Override
        public void onReceive(Context context, Intent intent) {
            // TODO Auto-generated method stub
            String action = intent.getAction();
            if (action.equals(ACTION_BUTTON)) {
                //通过传递过来的ID判断按钮点击属性或者通过getResultCode()获得相应点击事件
                int buttonId = intent.getIntExtra(INTENT_BUTTONID_TAG, 0);
                switch (buttonId) {
                    case BUTTON_PREV_ID:
                        //  上一首
                        break;
                    case BUTTON_PALY_ID:
                        isPlay = !isPlay;
                        if (isPlay) {
                            //开始播放
//                            myNotifyBroadcastClickInterface.startServiceInterface();
                        } else {
                            //已暂停
//                            myNotifyBroadcastClickInterface.pauseServiceInterface();
                        }
//                        showButtonNotify();
                        break;
                    case BUTTON_NEXT_ID:
//                        Toast.makeText(activity, "下一首", Toast.LENGTH_SHORT).show();
//                        myNotifyBroadcastClickInterface.stopServiceInterface();
//                        SharedUtil.setBoolean(activity, "serviceFlag", true);
//                        clearAllNotify();
                        break;
                    default:
                        break;
                }
            }
        }
    }


    /**
     * 清除所有通知栏
     */
    public void clearAllNotify() {
        mNotificationManager.cancelAll();// 删除你发的所有通知
    }

    /**
     * @获取默认的pendingIntent,为了防止2.3及以下版本报错
     * @flags属性: 在顶部常驻:Notification.FLAG_ONGOING_EVENT
     * 点击去除： Notification.FLAG_AUTO_CANCEL
     */
    public PendingIntent getDefalutIntent(int flags) {
//        Intent[] appIntent = null;
//        appIntent = makeIntentStack(context);//上面有改方法
//        appIntent[1].setAction(Intent.ACTION_MAIN);
//        appIntent[1].addCategory(Intent.CATEGORY_LAUNCHER);
//        appIntent[1].setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_RESET_TASK_IF_NEEDED);//关键的一步，设置启动模式
//        PendingIntent contentIntent = PendingIntent.getActivities(context, 0, appIntent, PendingIntent.FLAG_UPDATE_CURRENT);

//        Intent broadcastIntent = new Intent(activity, NotificationReceiver.class);
//        PendingIntent pendingIntent = PendingIntent.getBroadcast(activity, 0, broadcastIntent, PendingIntent.FLAG_UPDATE_CURRENT);
//        PendingIntent pendingIntent = PendingIntent.getActivity(activity, 1, new Intent(activity, MainActivity.class), flags);
//        return pendingIntent;
        Intent notificationIntent = null;
        if (ActivityCollector.isActivityExist(WatchMainActivity.class)) {
            ActivityCollector.removeActivity(activity);
        }
        PendingIntent pendingIntent = PendingIntent.getActivity(activity, 0, new Intent(activity, WatchMainActivity.class), flags);
        return pendingIntent;
    }

//    Intent[] makeIntentStack(Context context) {
//        Intent[] intents = new Intent[2];
//        intents[0] = Intent.makeRestartActivityTask(new ComponentName(context, Home.class));
//        intents[1] = new Intent(context, MainActivity.class);
//        return intents;
//    }

}
