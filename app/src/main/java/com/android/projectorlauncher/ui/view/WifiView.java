package com.android.projectorlauncher.ui.view;

public interface WifiView {
    void update();
    void wifiStateChange();
    void wifiConnectState(boolean isWifiConnected);
}
