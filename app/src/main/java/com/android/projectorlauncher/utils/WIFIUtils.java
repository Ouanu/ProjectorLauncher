package com.android.projectorlauncher.utils;

import android.Manifest;
import android.annotation.SuppressLint;
import android.content.Context;
import android.content.pm.PackageManager;
import android.net.wifi.ScanResult;
import android.net.wifi.WifiConfiguration;
import android.net.wifi.WifiInfo;
import android.net.wifi.WifiManager;
import android.util.Log;

import androidx.core.app.ActivityCompat;

import com.android.projectorlauncher.bean.Wifi;
import com.android.projectorlauncher.presenter.WIFIPresenter;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;

public class WIFIUtils {
    private final WifiManager manager;
    private final Context context;
    private final WIFIPresenter.WIFIHandler handler;
    private final List<Wifi> scanResults = new ArrayList<>();
    private final List<Wifi> configurations = new ArrayList<>();
    @SuppressLint("StaticFieldLeak")
    private static WIFIUtils INSTANCE = null;
    private final HashMap<Integer, String> networkIdToSSID = new HashMap<>();


    public List<Wifi> getScanResults() {
        return scanResults;
    }

    public List<Wifi> getConfigurations() {
        return configurations;
    }

    public static WIFIUtils init(Context context, WIFIPresenter.WIFIHandler handler) {
        INSTANCE = new WIFIUtils(context, handler);
        return INSTANCE;
    }

    public WIFIUtils(Context context, WIFIPresenter.WIFIHandler handler) {
        this.context = context;
        manager = (WifiManager) context.getApplicationContext().getSystemService(Context.WIFI_SERVICE);
        this.handler = handler;
    }

    public static WIFIUtils getInstance() {
        return INSTANCE;
    }

    //搜索wifi列表
    public synchronized void searchWifiList() {
        scanResults.clear();
        configurations.clear();
        HashSet<String> sets = new HashSet<>();
        if (manager.startScan()) {
            for (ScanResult scanResult : manager.getScanResults()) {
                if (ActivityCompat.checkSelfPermission(context, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
                    return;
                }
                if (scanResult.SSID.equals("")) continue;
                if (!sets.add(scanResult.SSID)) continue;    //去掉重名wifi
                Wifi item = new Wifi();
                item.setSSID(scanResult.SSID);
                item.setBSSID(scanResult.BSSID);
                item.setLevel(scanResult.level);
                item.setCapabilities(scanResult.capabilities);
                if (scanResult.capabilities.contains("WPA")) {
                    item.setType(3);
                } else if (scanResult.capabilities.contains("WEP")) {
                    item.setType(2);
                } else {
                    item.setType(1);
                }
                if (ActivityCompat.checkSelfPermission(context, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
                    return;
                }
                boolean isAdd = false;
                if (manager.getConfiguredNetworks() != null) {
                    for (WifiConfiguration configuredNetwork : manager.getConfiguredNetworks()) {
//                    Log.d("WIFIUtils", "searchWifiList: " + configuredNetwork.BSSID + " " + scanResult.BSSID);
                        if (configuredNetwork.SSID.contains(scanResult.SSID)) {
                            isAdd = true;
                            String s = configuredNetwork.SSID.replace("\"", "");
                            if (!configurations.contains(s)) {
                                configurations.add(item);
                            }
                            break;
                        }
                    }
                }
                //避免曾经连接wifi列表和周围wifi列表出现重复wifi
                if (!isAdd) {
                    scanResults.add(item);
                }
            }
            //按照信号强度排序
            Collections.sort(scanResults, WIFIUtils::compare);
            Collections.sort(configurations, WIFIUtils::compare);
            handler.sendEmptyMessage(WIFIPresenter.REFRESH_SCAN_LIST);
        }
    }

    //启用wifi
    public void enableWifi() {
        manager.setWifiEnabled(true);
    }

    //停用wifi
    public void disableWifi() {
        manager.setWifiEnabled(false);
    }

    //获取wifi状态（停用或者启用）
    public boolean stateWifi() {
        return manager.isWifiEnabled();
    }

    /**
     * 连接wifi
     *
     * @param SSID   wifi名称
     * @param hidden wifi是否隐藏
     * @param passwd wifi密码
     * @param Type   wifi安全性
     */
    public void connectWifi(String SSID, boolean hidden, String passwd, int Type) {
        WifiConfiguration configuration = CreateWifiInfo(SSID, hidden, passwd, Type);
        if (ActivityCompat.checkSelfPermission(context, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            return;
        }
        List<WifiConfiguration> configuredNetworks = manager.getConfiguredNetworks();
        for (WifiConfiguration configuredNetwork : configuredNetworks) {
            manager.removeNetwork(configuredNetwork.networkId);
        }

        int i = manager.addNetwork(configuration);
        if (ActivityCompat.checkSelfPermission(context, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            Log.d("WifiUtils", "connectWifi: 权限有问题");
            return;
        }
        WifiInfo connectionInfo = manager.getConnectionInfo();
        manager.disableNetwork(connectionInfo.getNetworkId());
        networkIdToSSID.put(i, connectionInfo.getSSID());
        boolean b = manager.enableNetwork(i, true);
        Log.d("WIFIUtils", "connectWifi: 连接===" + b);
        if (!b) {
            manager.removeNetwork(i);
        }
        manager.saveConfiguration();

    }

    /**
     * 创建wifi配置
     * @param SSID   wifi名称
     * @param hidden wifi是否隐藏
     * @param password wifi密码
     * @param type   wifi安全性
     * @return      wifi配置
     */
    public WifiConfiguration CreateWifiInfo(String SSID, boolean hidden, String password, int type) {
        WifiConfiguration config = new WifiConfiguration();
        config.SSID = "\"" + SSID + "\"";
        config.hiddenSSID = hidden;
//        config.BSSID = "\"" + BSSID + "\"";
        if (type == 3) {    //WPA
            config.preSharedKey = "\"" + password + "\"";
            config.allowedKeyManagement.set(WifiConfiguration.KeyMgmt.WPA_PSK);
        } else if (type == 2) { // WEP
            int i = password.length();
            if (((i == 10 || (i == 26) || (i == 58))) && (password.matches("[0-9A-Fa-f]*"))) {
                config.wepKeys[0] = password;
            } else {
                config.wepKeys[0] = "\"" + password + "\"";
            }
            config.allowedAuthAlgorithms
                    .set(WifiConfiguration.AuthAlgorithm.SHARED);
            config.allowedAuthAlgorithms.set(WifiConfiguration.AuthAlgorithm.OPEN);
            config.allowedKeyManagement.set(WifiConfiguration.KeyMgmt.NONE);
            config.wepTxKeyIndex = 0;
        } else if (type == 1) { // NO PASS
            config.allowedKeyManagement.set(WifiConfiguration.KeyMgmt.NONE);
        }  // do nothing

        return config;
    }

    /*
    获取已连接 wifi 名称
     */
    public String connectedName() {
        return manager.getConnectionInfo().getSSID().length() == 0 ? manager.getConnectionInfo().getSSID() : manager.getConnectionInfo().getSSID().replace("\"", "");
    }

    /*
    比较连个 wifi 信号强弱
     */
    private static int compare(Wifi o1, Wifi o2) {
        return Integer.compare(o1.getLevel(), o2.getLevel());
    }
}
