package cn.com.task.utils;
import java.text.ParsePosition;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

/**
 * Created by zhangyunlong on 2017/3/29.
 */

public class GetWeek {
    public static String getWeek(String time)
    {
        Date date=getDate(time);
        Calendar c= Calendar.getInstance();
        c.setTime(date);
        int hour=c.get(Calendar.DAY_OF_WEEK);
        switch (hour)
        {
            case 1:
                return "星期日";
            case 2:
                return "星期一";
            case 3:
                return "星期二";
            case 4:
                return "星期三";
            case 5:
                return "星期四";
            case 6:
                return "星期五";
            case 7:
                return "星期六";
            default:
                return "";
        }
    }
    private static Date getDate(String date)
    {
        SimpleDateFormat formatter=new SimpleDateFormat("yyyy/MM/dd");
        ParsePosition pos=new ParsePosition(0);
        Date formatterDate=formatter.parse(date,pos);
        return formatterDate;
    }
}
