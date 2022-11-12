package com.android.projectorlauncher.presenter;

import android.app.Activity;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import com.android.projectorlauncher.ui.view.WIFIView;
import com.android.projectorlauncher.utils.WIFIUtils;


public class WIFIPresenter {

    private final WIFIView view;
    private final WIFIUtils utils;
    public static final int REFRESH_SCAN_LIST = 100;
    private static final int DISABLE_WIFI = 111;
    public static boolean refresh = false;

    public WIFIPresenter(WIFIView view, Activity activity) {
        this.view = view;
        WIFIHandler handler = new WIFIHandler(activity.getMainLooper());
        utils = WIFIUtils.init(activity, handler);
    }

    public void init() {
        Log.d("WIFI", "init: " + utils.stateWifi() + " " + refresh);
        if (utils.stateWifi()) {
            if (!refresh) {
                new Thread(()->{
                    utils.searchWifiList();
                    refresh = true;
                }).start();
            }
        }
    }

    public class WIFIHandler extends Handler {
        public WIFIHandler(@NonNull Looper looper) {
            super(looper);
        }

        @Override
        public void handleMessage(@NonNull Message msg) {
//            super.handleMessage(msg);
            if (msg.what == REFRESH_SCAN_LIST) {
//                Log.d("WIFIPresenter", "handleMessage: " + utils.getConfigurations().size() + " " + utils.getScanResults().size());
                Log.d("WIFI", "handleMessage: " + utils.getScanResults().size());
                refresh = false;
                if (utils.getScanResults().size() == 0) {
                    init();
                }
                view.updateSaveList(utils.getConfigurations());
                view.updateWIFIList(utils.getScanResults());
                view.updateSwitchState(utils.stateWifi());
            }
        }
    }

    /*
    启停Wifi
     */
    public void enable() {
        if (utils.stateWifi()) {
            utils.disableWifi();
        } else {
            utils.enableWifi();
        }
    }

    public void stateInit() {
        refresh = false;
    }

    //关闭wifi后清空列表
    public void clearList() {
        refresh = false;
        utils.getConfigurations().clear();
        utils.getScanResults().clear();
        view.updateSaveList(utils.getConfigurations());
        view.updateWIFIList(utils.getScanResults());
        view.updateSwitchState(utils.stateWifi());
    }

    public String connectedName() {
        return utils.connectedName();
    }
}
