package com.android.projectorlauncher.ui.dialog;

import android.content.Context;
import android.view.View;
import android.widget.PopupWindow;

public class SpinnerPopupWindow extends PopupWindow {
    public SpinnerPopupWindow(Context context) {
        super(context);
    }

    public SpinnerPopupWindow(View contentView) {
        super(contentView);
    }

    public SpinnerPopupWindow(View contentView, int width, int height) {
        super(contentView, width, height);
    }

    public SpinnerPopupWindow(View contentView, int width, int height, boolean focusable) {
        super(contentView, width, height, focusable);
    }

}
