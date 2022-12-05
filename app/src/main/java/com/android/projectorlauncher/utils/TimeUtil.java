package com.android.projectorlauncher.utils;

import android.app.AlarmManager;
import android.content.Context;
import android.os.SystemClock;
import android.provider.Settings;
import android.util.Log;

import java.util.Calendar;
import java.util.Date;
import java.util.Locale;
import java.util.TimeZone;

@SuppressWarnings("unused")
public class TimeUtil {

    /**
     * 设置时间
     * @param hour   小时
     * @param minute 分钟
     */
    public static void setTime(int hour, int minute) {
        Calendar calendar = Calendar.getInstance();
        calendar.set(Calendar.HOUR_OF_DAY, hour);
        calendar.set(Calendar.MINUTE, minute);
        long time = calendar.getTimeInMillis();
        Log.d("TimeFragment", "setTime: " + time);
        if (time / 1000 < Integer.MAX_VALUE) {
            SystemClock.setCurrentTimeMillis(time);
        }
    }

    public static void setData(int year, int month, int day) {
        Calendar calendar = Calendar.getInstance();
        calendar.set(Calendar.YEAR, year);
        calendar.set(Calendar.MONTH, month);
        calendar.set(Calendar.DAY_OF_MONTH, day);

        long when = calendar.getTimeInMillis();
        if (when / 1000 < Integer.MAX_VALUE) {
            SystemClock.setCurrentTimeMillis(when);
        }
    }

    public static void setData(long date) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(new Date(date));
        long when = calendar.getTimeInMillis();
        if (when / 1000 < Integer.MAX_VALUE) {
            SystemClock.setCurrentTimeMillis(when);
        }
    }

    public static void setAutoDateTime(Context context, int checked) {
        Settings.Global.putInt(context.getContentResolver(), Settings.Global.AUTO_TIME, checked);
    }

    public static boolean isDateAutoSet(Context context) {
        try {
            return Settings.Global.getInt(context.getContentResolver(), Settings.Global.AUTO_TIME) > 0;
        } catch (Settings.SettingNotFoundException e) {
            e.printStackTrace();
            return false;
        }
    }

    /**
     * 设置时区
     * @param context 上下文
     * @param timezone 时区
     */
    public static void setTimeZone(Context context, String timezone) {
        AlarmManager manager = (AlarmManager) context.getSystemService(Context.ALARM_SERVICE);
        manager.setTimeZone(timezone);
    }

    /**
     * 自动设置时区
     * @param context 上下文
     * @param checked 新值
     */
    public static void setAutoTimeZone(Context context, int checked) {
        Settings.Global.putInt(context.getContentResolver(),
                Settings.Global.AUTO_TIME_ZONE, checked);

    }

    /*
    获取时区
     */
    public static String getTimeZone() {
        return TimeZone.getDefault().getDisplayName(false, TimeZone.SHORT, Locale.US);
    }

    //时
    public static int hourOfDay() {
        return Calendar.getInstance().get(Calendar.HOUR_OF_DAY);
    }

    //分
    public static int minuteOfDay() {
        return Calendar.getInstance().get(Calendar.MINUTE);
    }

    /*
    获取所有的时区城市
     */
    public static String[] getTimeZoneCity() {
        return TimeZone.getDefault().getID().split("/");
    }


}
