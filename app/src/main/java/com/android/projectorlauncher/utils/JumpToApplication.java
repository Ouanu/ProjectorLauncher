package com.android.projectorlauncher.utils;

import android.content.ActivityNotFoundException;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.util.Log;
import android.widget.Toast;

import com.android.projectorlauncher.bean.GameCard;

import java.io.IOException;

public class JumpToApplication {

    public static void playVideo(Context context, String id) {
        // 视频播放
        Intent intent = new Intent("com.tencent.qqlivetv.open");
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);//可选
        intent.setData(Uri.parse("tenvideo2://tvrecommendation/%s?action=1&cover_id=" + id)); // 播放详情页
        try {
            context.startActivity(intent);
        } catch (ActivityNotFoundException e) {
            e.printStackTrace();
            Toast.makeText(context, "未找到应用", Toast.LENGTH_SHORT).show();
        }
    }

    public static void turnToSearch(Context context) {
        Intent intent = new Intent("com.tencent.qqlivetv.open");
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        intent.setData(Uri.parse("tenvideo2://?action=9"));
        try {
            context.startActivity(intent);
        } catch (ActivityNotFoundException e) {
            e.printStackTrace();
            Toast.makeText(context, "未找到应用", Toast.LENGTH_SHORT).show();
        }
    }

    public static void turnToCategory(Context context, String tag) {
        Intent intent = new Intent("com.tencent.qqlivetv.open");
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        intent.setData(Uri.parse("tenvideo2://?action=3&channel_code=" + tag + "&channel_name=" + tag));
        try {
            context.startActivity(intent);
        } catch (ActivityNotFoundException e) {
            e.printStackTrace();
            Toast.makeText(context, "未找到应用", Toast.LENGTH_SHORT).show();
        }
    }

    //体育频道
    public static void turnToChannel(Context context, String channel) {
        Intent intent = new Intent("com.tencent.qqlivetv.open");
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        intent.setData(Uri.parse("tenvideo2://?action=3&channel_code=" + channel + "&channel_name="+ channel)); // NBA
        try {
            context.startActivity(intent);
        } catch (ActivityNotFoundException e) {
            e.printStackTrace();
            Toast.makeText(context, "未找到应用", Toast.LENGTH_SHORT).show();
        }
    }

    //体育频道
    public static void turnToHistory(Context context) {
        Intent intent = new Intent("com.tencent.qqlivetv.open");
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        intent.setData(Uri.parse("tenvideo2://?action=10")); // 观看历史
        try {
            context.startActivity(intent);
        } catch (ActivityNotFoundException e) {
            e.printStackTrace();
            Toast.makeText(context, "未找到应用", Toast.LENGTH_SHORT).show();
        }
    }

    public static void turnToSource(Context context) {
        Intent intent1 = context.getPackageManager().getLaunchIntentForPackage("com.android.quicksourcesettings");
        if (intent1 != null) {
            context.startActivity(intent1);
            return;
        }
        Toast.makeText(context, "未找到应用", Toast.LENGTH_SHORT).show();
    }

    public static void turnToGame(Context context, GameCard card) {
        try {
            Intent intent = new Intent();
            ComponentName componentName = new ComponentName(card.getPackageName(), card.getClassName());
            intent.setComponent(componentName);
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            context.startActivity(intent);
        } catch (Exception e) {
            Log.d("JumpToApplication", "turnToGame: 没有该应用或者该类，跳转到更新页面进行更新");
            Intent intent = new Intent();
            ComponentName componentName = new ComponentName("com.android.projectorupdate", "com.android.projectorupdate.MainActivity");
            intent.setComponent(componentName);
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            context.startActivity(intent);
        }
    }

    public static void turnToPictureSettings() {
        try {
            Runtime.getRuntime().exec("am start -n com.rx.rxtvsettings/.PictureActivity");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void turnToAudioSettings() {
        try {
            Runtime.getRuntime().exec("am start -n com.rx.rxtvsettings/.AudioActivity");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
