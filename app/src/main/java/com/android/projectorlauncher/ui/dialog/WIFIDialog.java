package com.android.projectorlauncher.ui.dialog;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.InputType;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.view.ViewCompat;
import androidx.fragment.app.DialogFragment;


import com.android.projectorlauncher.R;
import com.android.projectorlauncher.databinding.DialogWifiBinding;
import com.android.projectorlauncher.utils.WIFIUtils;

import java.util.Objects;

/**
 * WIFI连接设置弹窗
 */
public class WIFIDialog extends DialogFragment {
    private final WIFIUtils utils = WIFIUtils.getInstance();
    private String SSID;
    // 类型：1->未加密，2->WEP加密，3->WPA加密
    private int type;
    // 获取连接过WIFI的SharedPreference
    private SharedPreferences preferences;
    private DialogWifiBinding dialogWifiBinding;


    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        preferences = context.getSharedPreferences("WIFI", Context.MODE_PRIVATE);
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Bundle arguments = getArguments();
        if (arguments != null) {
            SSID = arguments.getString("SSID");
            type = arguments.getInt("type");
        }
        setStyle(DialogFragment.STYLE_NO_TITLE, android.R.style.Theme_DeviceDefault_Dialog_NoActionBar);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        dialogWifiBinding = DialogWifiBinding.inflate(inflater, container, false);
        Objects.requireNonNull(Objects.requireNonNull(getDialog()).getWindow()).setBackgroundDrawableResource(R.color.none);
        dialogWifiBinding.passwd.setFocusable(true);
        if (SSID != null) {
            dialogWifiBinding.name.setText(SSID);
        }
        String passwd;
        if ((passwd = preferences.getString(dialogWifiBinding.name.getText().toString(), null)) != null) {
            dialogWifiBinding.passwd.setText(passwd);
        }

        setOnClick();
        setOnChecked();
        setOnFocused();
        return dialogWifiBinding.getRoot();
    }

    //设置点击事件
    private void setOnClick() {
        dialogWifiBinding.back.setOnClickListener(v->dismiss());
        dialogWifiBinding.connect.setOnClickListener(v -> {
            SharedPreferences.Editor editor = preferences.edit();
            editor.putString(dialogWifiBinding.name.getText().toString(), dialogWifiBinding.passwd.getText().toString());
            editor.apply();
            utils.connectWifi(SSID, false, dialogWifiBinding.passwd.getText().toString(), type);
            dismiss();
        });
    }

    //设置选中事件
    public void setOnChecked() {
        dialogWifiBinding.checkPasswd.setOnCheckedChangeListener((buttonView, isChecked) -> {
            if (isChecked) {
                dialogWifiBinding.passwd.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_VISIBLE_PASSWORD);
            } else {
                dialogWifiBinding.passwd.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_PASSWORD);
            }
        });
    }

    //设置焦点捕获事件
    public void setOnFocused() {
        dialogWifiBinding.checkPasswd.setOnFocusChangeListener((v, hasFocus) -> {
            if (hasFocus) {
                ViewCompat.animate(v)
                        .setDuration(200)
                        .scaleX(1.10f)
                        .scaleY(1.10f)
                        .start();
                dialogWifiBinding.checkPasswd.setTextColor(getContext().getColor(R.color.white));
            } else {
                ViewCompat.animate(v)
                        .setDuration(200)
                        .scaleX(1f)
                        .scaleY(1f)
                        .start();
                dialogWifiBinding.checkPasswd.setTextColor(getContext().getColor(R.color.white));
            }
        });
        dialogWifiBinding.passwd.setOnFocusChangeListener((v, hasFocus) -> {
            if (hasFocus) {
                ViewCompat.animate(v)
                        .setDuration(200)
                        .scaleX(1.10f)
                        .scaleY(1.10f)
                        .start();
                dialogWifiBinding.passwd.setTextColor(getResources().getColor(R.color.self_6));
            } else {
                ViewCompat.animate(v)
                        .setDuration(200)
                        .scaleX(1f)
                        .scaleY(1f)
                        .start();
                dialogWifiBinding.passwd.setTextColor(getResources().getColor(R.color.self_5));
            }
        });
        dialogWifiBinding.connect.setOnFocusChangeListener((v, hasFocus) -> {
            if (hasFocus) {
                ViewCompat.animate(v)
                        .setDuration(200)
                        .scaleX(1.10f)
                        .scaleY(1.10f)
                        .start();
            } else {
                ViewCompat.animate(v)
                        .setDuration(200)
                        .scaleX(1f)
                        .scaleY(1f)
                        .start();
            }
        });
        dialogWifiBinding.back.setOnFocusChangeListener((v, hasFocus) -> {
            if (hasFocus) {
                ViewCompat.animate(v)
                        .setDuration(200)
                        .scaleX(1.20f)
                        .scaleY(1.20f)
                        .start();
            } else {
                ViewCompat.animate(v)
                        .setDuration(200)
                        .scaleX(1f)
                        .scaleY(1f)
                        .start();
            }
        });
    }



}
