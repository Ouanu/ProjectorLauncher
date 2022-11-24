package com.android.projectorlauncher.ui.activity;

import android.app.Activity;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.widget.Toast;
import androidx.annotation.Nullable;
import androidx.appcompat.content.res.AppCompatResources;

import com.android.projectorlauncher.R;
import com.android.projectorlauncher.databinding.ActivityKeystoneBinding;

public class KeystoneActivity extends Activity implements View.OnClickListener, View.OnFocusChangeListener {
    ActivityKeystoneBinding keystoneBinding;
    int cnt = 0;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        keystoneBinding = ActivityKeystoneBinding.inflate(getLayoutInflater());
        setContentView(keystoneBinding.getRoot());

        keystoneBinding.keystoneLeftUp.setOnClickListener(this);
        keystoneBinding.keystoneRightUp.setOnClickListener(this);
        keystoneBinding.keystoneRightDown.setOnClickListener(this);
        keystoneBinding.keystoneLeftDown.setOnClickListener(this);

        keystoneBinding.keystoneLeftUp.setOnFocusChangeListener(this);
        keystoneBinding.keystoneRightUp.setOnFocusChangeListener(this);
        keystoneBinding.keystoneRightDown.setOnFocusChangeListener(this);
        keystoneBinding.keystoneLeftDown.setOnFocusChangeListener(this);
    }

    @Override
    public void onBackPressed() {
        if (cnt < 1) {
            keystoneBinding.keystoneLeftDown.setFocusable(true);
            keystoneBinding.keystoneLeftUp.setFocusable(true);
            keystoneBinding.keystoneRightDown.setFocusable(true);
            keystoneBinding.keystoneRightUp.setFocusable(true);
            cnt++;
            keystoneBinding.getRoot().findFocus().setForeground(AppCompatResources.getDrawable(this, R.drawable.keystone_un_selector));
            Toast.makeText(this, "再点击一次返回就退出", Toast.LENGTH_SHORT).show();
        } else {
            super.onBackPressed();
        }
    }


    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {

        return super.onKeyDown(keyCode, event);
    }

    @Override
    public void onClick(View v) {
        cnt = 0;
        v.setForeground(null);
        if (v == keystoneBinding.keystoneLeftUp) {
            keystoneBinding.keystoneLeftDown.setFocusable(false);
            keystoneBinding.keystoneRightDown.setFocusable(false);
            keystoneBinding.keystoneRightUp.setFocusable(false);
        } else if (v == keystoneBinding.keystoneRightUp) {
            keystoneBinding.keystoneLeftDown.setFocusable(false);
            keystoneBinding.keystoneRightDown.setFocusable(false);
            keystoneBinding.keystoneLeftUp.setFocusable(false);
        } else if (v == keystoneBinding.keystoneLeftDown) {
            keystoneBinding.keystoneRightUp.setFocusable(false);
            keystoneBinding.keystoneRightDown.setFocusable(false);
            keystoneBinding.keystoneLeftUp.setFocusable(false);
        } else if (v == keystoneBinding.keystoneRightDown) {
            keystoneBinding.keystoneLeftDown.setFocusable(false);
            keystoneBinding.keystoneRightUp.setFocusable(false);
            keystoneBinding.keystoneLeftUp.setFocusable(false);
        }
    }

    @Override
    public void onFocusChange(View v, boolean hasFocus) {
        if (hasFocus) {
            v.setForeground(AppCompatResources.getDrawable(this, R.drawable.keystone_un_selector));
        } else {
            v.setForeground(null);
        }
    }
}
