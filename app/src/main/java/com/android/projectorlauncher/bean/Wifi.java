package com.android.projectorlauncher.bean;

@SuppressWarnings("unused")
public class Wifi {
    private String SSID;
    private String BSSID;
    private String passWd;
    private int level;
    private String capabilities;
    private int type;

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public String getSSID() {
        return SSID;
    }

    public void setSSID(String SSID) {
        this.SSID = SSID;
    }

    public String getPassWd() {
        return passWd;
    }

    public void setPassWd(String passWd) {
        this.passWd = passWd;
    }

    public int getLevel() {
        return level;
    }

    public void setLevel(int level) {
        this.level = level;
    }

    public String getCapabilities() {
        return capabilities;
    }

    public void setCapabilities(String capabilities) {
        this.capabilities = capabilities;
    }

    public void setBSSID(String BSSID) {
        this.BSSID = BSSID;
    }

    public String getBSSID() {
        return BSSID;
    }
}
