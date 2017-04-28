package cn.com.task.utils;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.net.Uri;
import android.provider.CalendarContract;
import android.text.TextUtils;
import android.util.Log;
import android.widget.TextView;
import android.widget.Toast;

import com.linked.erfli.library.utils.MyUtils;
import com.linked.erfli.library.utils.SharedUtil;

import java.util.Calendar;
import java.util.TimeZone;

import cn.com.task.bean.TaskDetailBean;


/**
 * 文件名：CalendarUtils
 * 描    述：把任务添加到系统日历的工具类
 * 作    者：stt
 * 时    间：2017.3.31
 * 版    本：V1.1.1
 */

public class CalendarUtils {
    // Android2.2版本以后的URL
    private static String calanderURL = "content://com.android.calendar/calendars";
    private static String calanderEventURL = "content://com.android.calendar/events";
    private static String calanderRemiderURL = "content://com.android.calendar/reminders";
    private TextView tv_account_info, tv_events_info;
    private static String searchName = "";
    private static String[] searchPar = new String[1];
    static String calId = "";

    /**
     * 方法名：addCalendarsAccount
     * 功    能：添加账户
     * 参    数：Activity activity
     * 返回值：
     */
    public static void addCalendarsAccount(Context activity, String taskId) {
        SharedUtil.setString(activity, taskId, "1");
        TimeZone timeZone = TimeZone.getDefault();
        ContentValues value = new ContentValues();

        value.put(CalendarContract.Calendars.ACCOUNT_NAME, "account_name_local");// 账户名称
        value.put(CalendarContract.Calendars.ACCOUNT_TYPE, "LOCAL");// 账户类型
        value.put(CalendarContract.Calendars._SYNC_ID, "");// 同步id
        value.put(CalendarContract.Calendars.DIRTY, "1");// et_account_dirty
        value.put(CalendarContract.Calendars.NAME, taskId);// 日历名称
        value.put(CalendarContract.Calendars.CALENDAR_DISPLAY_NAME, SharedUtil.getString(activity, "personName"));// 显示名称
        value.put(CalendarContract.Calendars.CALENDAR_COLOR, "-16737844");// 日历颜色
        value.put(CalendarContract.Calendars.CALENDAR_ACCESS_LEVEL, "700");// et_calendar_access_level
        value.put(CalendarContract.Calendars.CALENDAR_TIME_ZONE, timeZone.getID());
        value.put(CalendarContract.Calendars.VISIBLE, "1");//
        value.put(CalendarContract.Calendars.SYNC_EVENTS, "1");//事件
        value.put(CalendarContract.Calendars.OWNER_ACCOUNT, SharedUtil.getString(activity, "userName"));// 创建者账户
        value.put(CalendarContract.Calendars.CAN_ORGANIZER_RESPOND, "1");//组织者
        value.put(CalendarContract.Calendars.CAN_MODIFY_TIME_ZONE, "1");// 可以修改的时间范围
        value.put(CalendarContract.Calendars.MAX_REMINDERS, "1024");// 最大提醒

        Uri calendarUri = CalendarContract.Calendars.CONTENT_URI;
        calendarUri = calendarUri.buildUpon().appendQueryParameter(CalendarContract.CALLER_IS_SYNCADAPTER, "true").appendQueryParameter(CalendarContract.Calendars.ACCOUNT_NAME,
                // "mygmailaddress@gmail.com")
                "ymq")// 经测试.添加本地账户的话,该值没什么影响
                .appendQueryParameter(CalendarContract.Calendars.ACCOUNT_TYPE, "ymq").build();
        String str = CalendarContract.CALLER_IS_SYNCADAPTER;
        activity.getContentResolver().insert(calendarUri, value);
        Log.i("calendarUtilsAddAccount", "添加账户成功!");
    }

    /**
     * 方法名：insertCalendarInfo
     * 功    能：插入日历信息
     * 参    数：Activity activity
     * 返回值：
     */
    public static void insertCalendarInfo(Context activity, TaskDetailBean taskDetailBean, String taskId) {
        searchName = taskId;
        searchPar[0] = searchName;
        SharedUtil.setString(activity, taskDetailBean.getTask().getTaskSno(), "1");
        // 获取账户的id
        Cursor userCursor = activity.getContentResolver().query(Uri.parse(calanderURL), null, "name=?", searchPar, null);
        if (userCursor.getCount() > 0) {
            userCursor.moveToLast(); // 注意：是向符合条件的最后一个账户添加，开发者可以根据需要改变添加事件的账户
            calId = userCursor.getString(userCursor.getColumnIndex("_id"));
        } else {
            Toast.makeText(activity, "没有账户，请先添加账户", Toast.LENGTH_LONG).show();
            return;
        }

        Calendar mCalendar = Calendar.getInstance();// 获取当前时间
        mCalendar.set(Calendar.HOUR_OF_DAY, mCalendar.get(Calendar.HOUR_OF_DAY));// 时
        mCalendar.set(Calendar.MINUTE, mCalendar.get(Calendar.MINUTE) + 11);// 分
        long start = mCalendar.getTime().getTime();
        mCalendar.set(Calendar.HOUR_OF_DAY, mCalendar.get(Calendar.HOUR_OF_DAY) + 1);// 时
        long end = mCalendar.getTime().getTime();
        Log.i("时间======", "开始" + start + "结束" + end);
        // 插入事件
        ContentValues event = new ContentValues();
        event.put(CalendarContract.Events.TITLE, taskDetailBean.getTask().getTaskName());
        event.put(CalendarContract.Events.DESCRIPTION, taskDetailBean.getTask().getTaskDes());
        event.put(CalendarContract.Events.CALENDAR_ID, calId);
        event.put(CalendarContract.Events.EVENT_LOCATION, taskDetailBean.getTask().getTaskAddr());
        event.put(CalendarContract.Events.DTSTART, MyUtils.dateToStamp(taskDetailBean.getTask().getStartDateApi())); // 开始时间
        event.put(CalendarContract.Events.DTEND, MyUtils.dateToStamp(taskDetailBean.getTask().getEndDateApi()));// 结束时间
        event.put(CalendarContract.Events.STATUS, 1);//
        event.put(CalendarContract.Events.HAS_ATTENDEE_DATA, 1);//
        event.put(CalendarContract.Events.HAS_ALARM, 1);// 是否生效?
        event.put(CalendarContract.Events.EVENT_TIMEZONE, TimeZone.getDefault().getID()); // 这个是时区，必须有!!
        // 添加事件
        Uri newEvent = activity.getContentResolver().insert(Uri.parse(calanderEventURL), event);
        // 事件提醒的设定
        long id = Long.parseLong(newEvent.getLastPathSegment());
        ContentValues values = new ContentValues();
        values.put(CalendarContract.Reminders.EVENT_ID, id);
        values.put(CalendarContract.Reminders.MINUTES, 60);// 提前60分钟有提醒
        values.put(CalendarContract.Reminders.METHOD, CalendarContract.Reminders.METHOD_ALERT);// 提醒方式
        activity.getContentResolver().insert(Uri.parse(calanderRemiderURL), values);
    }

    /**
     * 方法名：deleteCalendarInfo
     * 功    能：删除日历信息
     * 参    数：Activity activity
     * 返回值：
     */
    public static void deleteCalendarInfo(Context context, String taskId) {
        searchName = taskId;
        searchPar[0] = searchName;
        Cursor userCursor = context.getContentResolver().query(Uri.parse(calanderURL), null, "name=?", searchPar, null);
        if (userCursor.getCount() > 0) {
            userCursor.moveToLast(); // 注意：是向符合条件的最后一个账户添加，开发者可以根据需要改变添加事件的账户
            calId = userCursor.getString(userCursor.getColumnIndex("_id"));
        } else {
            return;
        }
        if (!TextUtils.isEmpty(calId))
            context.getContentResolver().delete(Uri.parse(calanderURL), "_id=" + calId, null);
    }
}
