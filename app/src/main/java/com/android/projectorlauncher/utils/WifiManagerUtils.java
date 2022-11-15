package com.android.projectorlauncher.utils;

import android.Manifest;
import android.content.Context;
import android.content.pm.PackageManager;
import android.net.wifi.ScanResult;
import android.net.wifi.WifiConfiguration;
import android.net.wifi.WifiManager;
import android.util.Log;

import androidx.core.app.ActivityCompat;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class WifiManagerUtils {
    private WifiManager manager;
    private Context context;

    public WifiManagerUtils(Context context) {
        this.context = context;
        manager = (WifiManager) context.getApplicationContext().getSystemService(Context.WIFI_SERVICE);

    }

    public static void searchWifi(WifiManager manager) {
        if (!manager.isWifiEnabled()) {
            manager.setWifiEnabled(true);
        }
        manager.startScan();
    }

    /**
     * 去掉重名
     * @param manager   WifiManager
     * @return 返回扫描到的列表
     */
    public static List<ScanResult> scanResults(WifiManager manager) {
        HashSet<String> hs = new HashSet<>();
        List<ScanResult> scanResults = new ArrayList<>();
        for (ScanResult scanResult : manager.getScanResults()) {
            if (hs.add(scanResult.SSID)) {
                scanResults.add(scanResult);
                Log.d("WIFIManager", "scanResults: " + scanResult.SSID + " " + scanResult.capabilities);
            }
        }
        return scanResults;
    }

    /**
     * 得到配置好的网络
     * @param context   上下文
     * @param manager   WifiManager
     * @return  配置好的列表
     */
    public static List<WifiConfiguration> getConfiguredNetworks(Context context, WifiManager manager) {
        if (ActivityCompat.checkSelfPermission(context, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            return new ArrayList<>();
        }
        List<WifiConfiguration> configurations = manager.getConfiguredNetworks();
        return configurations;
    }

    /**
     * 将已经配置好的网络从附近的网络列表中去掉
     * @param scanResults 附近的网络列表
     * @param configurations 已经配置好的网络
     */
    public static List<ScanResult> getSaveWifiList(List<ScanResult> scanResults, List<WifiConfiguration> configurations) {
        Set<String> sets = new HashSet<>();
        for (WifiConfiguration configuration : configurations) {
            sets.add(configuration.SSID);
        }
        List<ScanResult> saveResults = new ArrayList<>();
        List<ScanResult> nearbyResults = new ArrayList<>();
        for (ScanResult scanResult : scanResults) {
            if (sets.add(scanResult.SSID)) {
                nearbyResults.add(scanResult);
            } else {
                saveResults.add(scanResult);
            }
        }
        scanResults.clear();
        scanResults.addAll(nearbyResults);
        return saveResults;
    }

    /**
     * 创建Wifi配置
     * @param SSID  wifi名称
     * @param password  wifi密码
     * @param hidden    网络是否隐藏（该方法与添加隐藏网络通用）
     * @param capabilities  网络安全协议
     * @return 配置好的wifi
     */
    public static WifiConfiguration createWifiInfo(String SSID, String password, boolean hidden, String capabilities) {
        WifiConfiguration configuration = new WifiConfiguration();
        configuration.SSID = SSID;
        configuration.hiddenSSID = hidden;
        if (capabilities.contains("WPA-PSK") || capabilities.contains("WPA2-PSK")) {
            setWPA(configuration, password);
        } else if (capabilities.contains("WEP")) {
            setWEP(configuration, password);
        } else {
            setESS(configuration);
        }
        return configuration;
    }

    // WPA协议
    public static void setWPA(WifiConfiguration configuration, String password) {
        configuration.preSharedKey =  "\"" + password + "\"";
        configuration.hiddenSSID = true;
        configuration.allowedAuthAlgorithms.set(WifiConfiguration.AuthAlgorithm.OPEN);
        configuration.allowedGroupCiphers.set(WifiConfiguration.GroupCipher.TKIP);
        configuration.allowedKeyManagement.set(WifiConfiguration.KeyMgmt.WPA_PSK);
        configuration.allowedPairwiseCiphers.set(WifiConfiguration.PairwiseCipher.TKIP);
        configuration.allowedGroupCiphers.set(WifiConfiguration.GroupCipher.CCMP);
        configuration.allowedPairwiseCiphers.set(WifiConfiguration.PairwiseCipher.CCMP);
        configuration.allowedProtocols.set(WifiConfiguration.Protocol.RSN);
        configuration.allowedProtocols.set(WifiConfiguration.Protocol.WPA);
        configuration.status = WifiConfiguration.Status.ENABLED;
    }
    // WEP协议
    public static void setWEP(WifiConfiguration configuration, String password) {
        configuration.wepKeys[0] = "\"" + password + "\"";
        configuration.wepTxKeyIndex = 0;
        configuration.allowedKeyManagement.set(WifiConfiguration.KeyMgmt.NONE);
        configuration.allowedGroupCiphers.set(WifiConfiguration.GroupCipher.WEP40);
    }
    // 无密码
    public static void setESS(WifiConfiguration configuration) {
        configuration.allowedKeyManagement.set(WifiConfiguration.KeyMgmt.NONE);
    }
}