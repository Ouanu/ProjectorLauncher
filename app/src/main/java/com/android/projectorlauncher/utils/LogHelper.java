/*
 * Copyright (c) Hisilicon Technologies Co., Ltd. 2016-2020. All rights reserved.
 */

package com.android.projectorlauncher.utils;

import android.os.SystemProperties;
import android.text.TextUtils;
import android.util.Log;

/**
 * The type log helper
 *
 * @author hisilicon
 * @since 2020-02-25
 */
public class LogHelper {
    /**
     * App name HiTvApp_Service_
     */
    public static final String TAG_SERVICE = "HiTvApp_Service_";

    /**
     * App name HiTvApp_Player_
     */
    public static final String TAG_PLAYER = "HiTvApp_Player_";

    /**
     * App name HiTvApp_Setting_
     */
    public static final String TAG_SETTING = "HiTvApp_Setting_";

    /**
     * App name HiTvApp_Launcher_
     */
    public static final String TAG_LAUNCHER = "HiTvApp_Launcher_";

    /**
     * App name HiTvApp_SetupWizard_
     */
    public static final String TAG_SETUPWIZARD = "HiTvApp_SetupWizard_";

    /**
     * App name HiTvApp_FactoryMenu_
     */
    public static final String TAG_FACTORYMENU = "HiTvApp_FactoryMenu_";

    /**
     * logo level
     */
    private static final char[] LOG_LEVEL_ARRAY = new char[]{'v', 'd', 'i', 'w', 'e'};

    private static final int LOG_LEVEL_VERBOSE = 0;

    private static final int LOG_LEVEL_DEBUG = 1;

    private static final int LOG_LEVEL_INFO = 2;

    private static final int LOG_LEVEL_WARNING = 3;

    private static final int LOG_LEVEL_ERROR = 4;

    private static final int STACK_LEVEL = 5;

    private static final String MSG_EMPTY = "Empty Msg";

    private static final String LOG_LEVEL_PROPERTY_NAME = "persist.sys.loglevel.tvapp";

    /**
     * log verbose
     *
     * @param tag tag
     * @param desc message
     */
    public static void verbose(String appName, String tag, String desc) {
        if (LOG_LEVEL_VERBOSE >= getLogLevel()) {
            Log.v(appName + tag, desc);
        }
    }

    /**
     * log verbose
     *
     * @param tag tag
     * @param desc message
     * @param tr throwable
     */
    public static void verbose(String appName, String tag, String desc, Throwable tr) {
        if (LOG_LEVEL_VERBOSE >= getLogLevel()) {
            Log.v(appName + tag, desc, tr);
        }
    }

    /**
     * log debug
     *
     * @param tag tag
     * @param desc message
     */
    public static void debug(String appName, String tag, String desc) {
        if (LOG_LEVEL_DEBUG >= getLogLevel()) {
            Log.d(appName + tag, desc);
        }
    }

    /**
     * log debug
     *
     * @param tag tag
     * @param desc message
     * @param tr throwable
     */
    public static void debug(String appName, String tag, String desc, Throwable tr) {
        if (LOG_LEVEL_DEBUG >= getLogLevel()) {
            Log.d(appName + tag, desc, tr);
        }
    }

    /**
     * log info
     *
     * @param tag tag
     * @param desc message
     */
    public static void info(String appName, String tag, String desc) {
        if (LOG_LEVEL_INFO >= getLogLevel()) {
            Log.i(appName + tag, desc);
        }
    }

    /**
     * log info
     *
     * @param tag tag
     * @param desc message
     * @param tr throwable
     */
    public static void info(String appName, String tag, String desc, Throwable tr) {
        if (LOG_LEVEL_INFO >= getLogLevel()) {
            Log.i(appName + tag, desc, tr);
        }
    }

    /**
     * log warning
     *
     * @param tag tag
     * @param desc message
     */
    public static void warning(String appName, String tag, String desc) {
        if (LOG_LEVEL_WARNING >= getLogLevel()) {
            Log.w(appName + tag, desc);
        }
    }

    /**
     * log warning
     *
     * @param tag tag
     * @param desc message
     * @param tr throwable
     */
    public static void warning(String appName, String tag, String desc, Throwable tr) {
        if (LOG_LEVEL_WARNING >= getLogLevel()) {
            Log.w(appName + tag, desc, tr);
        }
    }

    /**
     * log error
     *
     * @param tag tag
     * @param desc message
     */
    public static void error(String appName, String tag, String desc) {
        if (LOG_LEVEL_ERROR >= getLogLevel()) {
            Log.e(appName + tag, getMsgWithMethodNLine(desc));
        }
    }

    /**
     * log error
     *
     * @param tag tag
     * @param desc message
     * @param tr throwable
     */
    public static void error(String appName, String tag, String desc, Throwable tr) {
        if (LOG_LEVEL_ERROR >= getLogLevel()) {
            Log.e(appName + tag, getMsgWithMethodNLine(desc), tr);
        }
    }

    /**
     * property persist.sys.loglevel.tvapp should be one of:"e","w","i","d","v"
     * or no such property
     *
     * @return logLevel
     */
    private static int getLogLevel() {
        String logLevelStr = SystemProperties.get(LOG_LEVEL_PROPERTY_NAME, "v");
        if (logLevelStr != null && logLevelStr.length() > 0) {
            char ch = logLevelStr.charAt(0);
            for (int i = 0; i < LOG_LEVEL_ARRAY.length; i++) {
                if (LOG_LEVEL_ARRAY[i] == ch) {
                    return i;
                }
            }
        }
        return -1;
    }

    private static String getMsgWithMethodNLine(String pMsg) {
        if (TextUtils.isEmpty(pMsg)) {
            pMsg = MSG_EMPTY;
        }

        StringBuilder sb = new StringBuilder();
        sb.append("[");
        appendMethodName(sb);
        sb.append(" :  ");
        appendLineNumber(sb);
        sb.append("] ");
        sb.append(pMsg);
        return sb.toString();
    }

    private static void appendMethodName(StringBuilder sb) {
        sb.append(Thread.currentThread().getStackTrace()[STACK_LEVEL]
                .getMethodName());
    }

    private static void appendLineNumber(StringBuilder sb) {
        sb.append(Thread.currentThread().getStackTrace()[STACK_LEVEL]
                .getLineNumber());
    }
}
