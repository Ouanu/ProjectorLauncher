package com.android.projectorlauncher.ui.view;


import com.android.projectorlauncher.bean.Wifi;

import java.util.List;

public interface WIFIView {
    void updateSaveList(List<Wifi> list);
    void updateWIFIList(List<Wifi> list);
    void updateSwitchState(boolean state);
}
