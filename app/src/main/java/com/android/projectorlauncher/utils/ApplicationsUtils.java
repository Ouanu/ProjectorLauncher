package com.android.projectorlauncher.utils;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.content.pm.ResolveInfo;
import android.util.Log;

import java.util.ArrayList;
import java.util.List;

public class ApplicationsUtils {
    @SuppressLint("QueryPermissionsNeeded")
    public static List<ResolveInfo> getAllApplications(Context context) {
        List<ResolveInfo> infoList;
        List<ResolveInfo> finalList = new ArrayList<>();
        Intent filter = new Intent(Intent.ACTION_MAIN, null);
        filter.addCategory(Intent.CATEGORY_LAUNCHER);
        infoList = context.getPackageManager().queryIntentActivities(filter, 0);
        for (ResolveInfo resolveInfo : infoList) {
            if (resolveInfo.activityInfo.packageName.contains("android")
                    || resolveInfo.activityInfo.packageName.contains("hisilicon")
                    || resolveInfo.activityInfo.packageName.contains("hikeymasterprovision")
                    || resolveInfo.activityInfo.packageName.contains("rx")
                    || resolveInfo.activityInfo.packageName.contains("chromium")) {
                continue;
            }
            finalList.add(resolveInfo);
        }
        return finalList;
    }
}
