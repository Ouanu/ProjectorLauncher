package com.android.projectorlauncher.presenter;

import android.app.Activity;
import android.content.Context;
import android.net.ConnectivityManager;
import android.net.LinkProperties;
import android.net.Network;
import android.net.NetworkCapabilities;
import android.net.NetworkRequest;
import android.net.wifi.ScanResult;
import android.net.wifi.SupplicantState;
import android.net.wifi.WifiManager;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.util.Log;
import androidx.annotation.NonNull;
import com.android.projectorlauncher.ui.fragment.WifiFragment;
import com.android.projectorlauncher.ui.view.WifiView;
import com.android.projectorlauncher.utils.WifiManagerUtils;
import java.util.List;

public class WifiPresenter {
    private final Activity activity;
    private final WifiManager wifiManager;
    private final ConnectivityManager connectivityManager;
    private List<ScanResult> nearbyResults;
    private List<ScanResult> saveResults;
    private final WifiView view;
    private final ExecuteHandler handler;

    public WifiPresenter(WifiFragment fragment) {
        this.activity = fragment.requireActivity();
        handler = new ExecuteHandler(activity.getMainLooper());
        wifiManager = (WifiManager) activity.getSystemService(Context.WIFI_SERVICE);
        connectivityManager = (ConnectivityManager) activity.getSystemService(Context.CONNECTIVITY_SERVICE);
        view = fragment;
        setConnectChangeListener();
    }

    /**
     * 改变wifi状态，若已经打开则关闭，反之
     */
    public void changeWifiState() {
        wifiManager.setWifiEnabled(!isWifiEnabled());
    }

    /**
     * 查看wifi状态
     * @return boolean
     */
    public boolean isWifiEnabled() {
        return wifiManager.isWifiEnabled();
    }

    /**
     * 刷新wifi列表（不一定有用，startScan方法已经被废弃，默认是前台2分钟4次、后台30分钟1次）
     */
    public void refreshNearbyNetworks() {
        WifiManagerUtils.searchWifi(wifiManager);
    }

    /**
     * 获取附近网络的列表（搭配广播用）
     */
    public void updateNetworks() {
        clearNetworks();
        nearbyResults = WifiManagerUtils.scanResults(wifiManager);
        saveResults = WifiManagerUtils.getSaveWifiList(nearbyResults, WifiManagerUtils.getConfiguredNetworks(activity, wifiManager));
        view.update();
    }

    /**
     * 清空列表
     */
    public void clearNetworks() {
        if (nearbyResults != null) {
            nearbyResults.clear();
        }
        if (saveResults != null) {
            saveResults.clear();
        }
    }

    public String getConnectSSID() {
        Log.d("WifiPresenter", "getConnectSSID: " + wifiManager.getConnectionInfo().toString());
        return wifiManager.getConnectionInfo().getSSID();
    }

    public void setConnectChangeListener() {
        connectivityManager.registerNetworkCallback(new NetworkRequest.Builder().build(), new ConnectivityManager.NetworkCallback() {
            @Override
            public void onAvailable(@NonNull Network network) {
                super.onAvailable(network);
                Log.d("WifiPresenter", "onAvailable");
            }

            @Override
            public void onLosing(@NonNull Network network, int maxMsToLive) {
                super.onLosing(network, maxMsToLive);
                Log.d("WifiPresenter", "onLosing");
            }

            @Override
            public void onLost(@NonNull Network network) {
                super.onLost(network);
                Log.d("WifiPresenter", "onLost");
            }

            @Override
            public void onUnavailable() {
                super.onUnavailable();
                Log.d("WifiPresenter", "onUnavailable");
            }

            @Override
            public void onCapabilitiesChanged(@NonNull Network network, @NonNull NetworkCapabilities networkCapabilities) {
                super.onCapabilitiesChanged(network, networkCapabilities);
                setWifiConnectState(networkCapabilities.hasTransport(NetworkCapabilities.TRANSPORT_WIFI));
                Log.d("WifiPresenter", "onCapabilitiesChanged");
            }

            @Override
            public void onLinkPropertiesChanged(@NonNull Network network, @NonNull LinkProperties linkProperties) {
                super.onLinkPropertiesChanged(network, linkProperties);
                Log.d("WifiPresenter", "onLinkPropertiesChanged");
            }

            @Override
            public void onBlockedStatusChanged(@NonNull Network network, boolean blocked) {
                super.onBlockedStatusChanged(network, blocked);
                Log.d("WifiPresenter", "onBlockedStatusChanged");
            }
        });
    }

    public void setWifiConnectState(boolean isConnected) {
        Log.d("WifiPresenter", "setWifiConnectState: wifi状态" + isConnected);
        handler.post(() -> {
            if (isConnected) {
                if (wifiManager.getConnectionInfo().getSupplicantState() == SupplicantState.COMPLETED) {
                    view.wifiConnectState(true);
                    return;
                }
                Log.d("WifiPresenter", "setWifiConnectState: wifi状态" + wifiManager.getConnectionInfo().toString());
            }
            view.wifiConnectState(false);
        });
    }

    public List<ScanResult> getNearbyResults() {
        return nearbyResults;
    }

    public List<ScanResult> getSaveResults() {
        return saveResults;
    }

    static class ExecuteHandler extends Handler {
        public ExecuteHandler(@NonNull Looper looper) {
            super(looper);
        }
        @Override
        public void handleMessage(@NonNull Message msg) {
            super.handleMessage(msg);
        }
    }
}
