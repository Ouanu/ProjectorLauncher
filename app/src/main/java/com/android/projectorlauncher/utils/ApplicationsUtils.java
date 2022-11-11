package com.android.projectorlauncher.utils;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.content.pm.ResolveInfo;

import java.util.List;

public class ApplicationsUtils {
    @SuppressLint("QueryPermissionsNeeded")
    public static List<ResolveInfo> getAllApplications(Context context) {
        List<ResolveInfo> infoList;
        Intent filter = new Intent(Intent.ACTION_MAIN, null);
        filter.addCategory(Intent.CATEGORY_LAUNCHER);
        infoList = context.getPackageManager().queryIntentActivities(filter, 0);
        return infoList;
    }
}
