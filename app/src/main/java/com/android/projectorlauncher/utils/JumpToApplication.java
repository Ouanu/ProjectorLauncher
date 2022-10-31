package com.android.projectorlauncher.utils;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;

public class JumpToApplication {

    public static void turnToKtcp(Context context, String id){
        Intent intent = new Intent("com.tencent.qqlivetv.open");
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);//可选
        intent.setData(Uri.parse("tenvideo2://?action=7&cover_id=" + id));
        context.startActivity(intent);
    }
}
