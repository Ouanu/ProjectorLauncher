package com.android.projectorlauncher.presenter;

import android.app.Activity;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.net.wifi.ScanResult;
import android.net.wifi.WifiConfiguration;
import android.net.wifi.WifiManager;

import com.android.projectorlauncher.ui.fragment.WifiFragment;
import com.android.projectorlauncher.ui.view.WifiView;
import com.android.projectorlauncher.utils.WifiManagerUtils;

import java.util.ArrayList;
import java.util.List;

public class WifiPresenter {
    private Activity activity;
    private WifiManager wifiManager;
    private List<ScanResult> nearbyResults;
    private List<ScanResult> saveResults;
    private WifiView view;

    public WifiPresenter(WifiFragment fragment) {
        this.activity = fragment.requireActivity();
        wifiManager = (WifiManager) activity.getSystemService(Context.WIFI_SERVICE);
        view = fragment;
    }

    /**
     * 改变wifi状态，若已经打开则关闭，反之
     */
    public void changeWifiState() {
        if (isWifiEnabled()) {
            wifiManager.setWifiEnabled(false);
        } else {
            wifiManager.setWifiEnabled(true);
        }
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

    public List<ScanResult> getNearbyResults() {
        return nearbyResults;
    }

    public List<ScanResult> getSaveResults() {
        return saveResults;
    }


}
