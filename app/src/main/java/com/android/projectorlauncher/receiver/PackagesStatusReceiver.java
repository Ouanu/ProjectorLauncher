package com.android.projectorlauncher.receiver;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.widget.Toast;

/**
 * 应用安装、卸载、更新提醒
 */
public class PackagesStatusReceiver extends BroadcastReceiver {
    private PackagesCallback callback;
    @Override
    public void onReceive(Context context, Intent intent) {
        //安装广播
        if (intent.getAction().equals("android.intent.action.PACKAGE_ADDED")) {
            String packageName = intent.getDataString();
            Toast.makeText(context, "安装了应用："+packageName, Toast.LENGTH_SHORT).show();
        }
        //卸载广播
        if (intent.getAction().equals("android.intent.action.PACKAGE_REMOVED")) {
            String packageName = intent.getDataString();
            Toast.makeText(context, "卸载了应用："+packageName, Toast.LENGTH_SHORT).show();
        }
        //覆盖安装广播
        if (intent.getAction().equals("android.intent.action.PACKAGE_REPLACED")) {
            String packageName = intent.getDataString();
            Toast.makeText(context, "覆盖安装了应用："+packageName, Toast.LENGTH_SHORT).show();
        }
        callback.statusChanged();
//        Log.d("Packages", "onReceive: changeeeeeeeeeeeeeeeeeee");
    }

    public interface PackagesCallback {
        void statusChanged();
    }

    public void setCallback(PackagesCallback callback) {
        this.callback = callback;
    }
}
