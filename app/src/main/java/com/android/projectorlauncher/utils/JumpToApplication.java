package com.android.projectorlauncher.utils;

import android.annotation.SuppressLint;
import android.content.ActivityNotFoundException;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.widget.Toast;

public class JumpToApplication {

    @SuppressLint("WrongConstant")
    public static void turnToKtcp(Context context, String id){
        // 视频播放
//        Intent intent = new Intent("com.tencent.qqlivetv.open");
//        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);//可选
//        intent.setData(Uri.parse("tenvideo2://?action=7&cover_id=" + id)); // 视频播放
//        intent.setData(Uri.parse("tenvideo2://tvrecommendation/%s?action=1&cover_id=" + id)); // 播放详情页

      // 首页
//        Intent intent = new Intent();
//        intent.setData(Uri.parse("tenvideo2://?action=4"));
//        intent.setAction("com.tencent.qqlivetv.open");
//        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
//        intent.setPackage("com.ktcp.video");

        Intent intent = new Intent("com.tencent.qqlivetv.open");
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);//可选
//        intent.setData(Uri.parse("tenvideo2://?action=3&channel_code=hot_competitions&channel_name=hot_competitions")); // 所有赛事
//        intent.setData(Uri.parse("tenvideo2://?action=3&channel_code=eccc&channel_name=eccc")); // 欧冠
//        intent.setData(Uri.parse("tenvideo2://?action=3&channel_code=cba&channel_name=cba"));   //CBA
//        intent.setData(Uri.parse("tenvideo2://?action=3&channel_code=nba&channel_name=nba")); // NBA


        intent.setData(Uri.parse("tenvideo2://?action=3&channel_code=competitions&channel_name=competitions"));  //电视剧


        try {
            context.startActivity(intent);
        } catch (ActivityNotFoundException e) {
            e.printStackTrace();
            Toast.makeText(context, "未找到应用", Toast.LENGTH_SHORT).show();
        }

    }
}
