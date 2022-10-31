package com.android.projectorlauncher.utils;

import android.content.ActivityNotFoundException;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.widget.Toast;

public class JumpToApplication {

    public static void turnToKtcp(Context context, String id){
        Intent intent = new Intent("com.tencent.qqlivetv.open");
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);//可选
        intent.setData(Uri.parse("tenvideo2://?action=7&cover_id=" + id));
        try {
            context.startActivity(intent);
        } catch (ActivityNotFoundException e) {
            e.printStackTrace();
            Toast.makeText(context, "未找到应用", Toast.LENGTH_SHORT).show();
        }

    }
}
