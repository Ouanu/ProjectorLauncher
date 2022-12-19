package com.android.projectorlauncher.utils;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

public class NetworkUtils {
    @SuppressWarnings("deprecation")
    public static boolean isOnline(Context context) {
        ConnectivityManager manager = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        if (manager != null) {
            NetworkInfo networkInfo = manager.getActiveNetworkInfo();
            if (networkInfo != null && networkInfo.isConnected()) {
                return networkInfo.getType() == ConnectivityManager.TYPE_WIFI ||
                        networkInfo.getType() == ConnectivityManager.TYPE_MOBILE ||
                        networkInfo.getType() == ConnectivityManager.TYPE_ETHERNET;
            }
        }
        return false;
    }
}
