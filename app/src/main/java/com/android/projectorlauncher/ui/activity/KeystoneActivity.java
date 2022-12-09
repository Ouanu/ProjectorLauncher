package com.android.projectorlauncher.ui.activity;

import android.app.Activity;
import android.os.Bundle;
import android.os.RemoteException;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.content.res.AppCompatResources;

import com.android.projectorlauncher.R;
import com.android.projectorlauncher.databinding.ActivityKeystoneBinding;
import com.android.projectorlauncher.utils.KeystoneUtils;

import java.util.Arrays;

import vendor.hisilicon.hardware.hwsysmanager.V1_0.IHwSysManager;

public class KeystoneActivity extends Activity implements View.OnClickListener, View.OnFocusChangeListener {
    private static final int MAX_W = 1920;
    private static final int MAX_L = 1080;
    private static final int MIN_W = 192;
    private static final int MIN_L = 108;
    private ActivityKeystoneBinding keystoneBinding;
    private IHwSysManager sysManager;
    private int cnt = 0;
    private boolean isSetting = false;
    private int[] xy = new int[]{-1, -1};
    private int[] maxXy = new int[]{-1, -1};
    private int[] minXy = new int[]{-1, -1};
    private int mode = -1;

    static {
        System.loadLibrary("projectorlauncher");
    }

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
        keystoneBinding.keystoneX.setText("0");
        keystoneBinding.keystoneY.setText("0");
        try {
            sysManager = IHwSysManager.getService();
        } catch (RemoteException e) {
            e.printStackTrace();
        }
    }

    @Override
    protected void onStart() {
        super.onStart();
        Log.d("KeystoneActivity", "onStart: " + KeystoneUtils.getKs());
    }

    @Override
    public void onBackPressed() {
        if (cnt < 1) {
            keystoneBinding.keystoneLeftDown.setFocusable(true);
            keystoneBinding.keystoneLeftUp.setFocusable(true);
            keystoneBinding.keystoneRightDown.setFocusable(true);
            keystoneBinding.keystoneRightUp.setFocusable(true);
            keystoneBinding.keystoneX.setText("0");
            keystoneBinding.keystoneY.setText("0");
            cnt++;
            isSetting = false;
            keystoneBinding.getRoot().findFocus().setForeground(AppCompatResources.getDrawable(this, R.drawable.keystone_un_selector));
            Toast.makeText(this, "再点击一次返回就退出", Toast.LENGTH_SHORT).show();
        } else {
            super.onBackPressed();
        }
    }


    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (isSetting) {
            if (keyCode == KeyEvent.KEYCODE_DPAD_LEFT) {
                if (xy[0] - MAX_W * 0.1 <= minXy[0]) {
                    xy[0] = minXy[0];
                } else {
                    xy[0] = (int) (xy[0] - MAX_W * 0.1);
                }
                refreshPropertiesAndUpdateUI();
                return false;
            } else if (keyCode == KeyEvent.KEYCODE_DPAD_RIGHT) {
                if(xy[0] + MAX_W * 0.1 >= maxXy[0]) {
                    xy[0] = maxXy[0];
                } else {
                    xy[0] = (int) (MAX_W * 0.1 + xy[0]);
                }
                refreshPropertiesAndUpdateUI();
                return false;
            } else if (keyCode == KeyEvent.KEYCODE_DPAD_UP) {
                if (MAX_L * 0.1 + xy[1] >= maxXy[1]) {
                    xy[1] = maxXy[1];
                } else {
                    xy[1] = (int) (MAX_L * 0.1 + xy[1]);
                }
                refreshPropertiesAndUpdateUI();
                return false;
            } else if (keyCode == KeyEvent.KEYCODE_DPAD_DOWN) {
                if (xy[1] - MAX_L * 0.1 <= minXy[1]) {
                    xy[1] = minXy[1];
                } else {
                    xy[1] = (int) (xy[1] - MAX_L * 0.1);
                }
                refreshPropertiesAndUpdateUI();
                return false;
            }

        }
        return super.onKeyDown(keyCode, event);
    }


    private void refreshPropertiesAndUpdateUI() {
        switch (mode) {
            case 1:
                KeystoneUtils.setLtXY(sysManager, Arrays.toString(xy).substring(1, Arrays.toString(xy).length() - 1));
                break;
            case 2:
                KeystoneUtils.setRtXY(sysManager, Arrays.toString(xy).substring(1, Arrays.toString(xy).length() - 1));
                break;
            case 3:
                KeystoneUtils.setLbXY(sysManager, Arrays.toString(xy).substring(1, Arrays.toString(xy).length() - 1));
                break;
            case 4:
                KeystoneUtils.setRbXY(sysManager, Arrays.toString(xy).substring(1, Arrays.toString(xy).length() - 1));
                break;
            default:
                break;
        }
        keystoneBinding.keystoneX.setText(String.valueOf(xy[0]));
        keystoneBinding.keystoneY.setText(String.valueOf(xy[1]));
    }

    @Override
    public void onClick(View v) {
        cnt = 0;
        v.setForeground(null);
        if (v == keystoneBinding.keystoneLeftUp) {
            keystoneBinding.keystoneLeftDown.setFocusable(false);
            keystoneBinding.keystoneRightDown.setFocusable(false);
            keystoneBinding.keystoneRightUp.setFocusable(false);
            isSetting = true;
            xy = KeystoneUtils.getLtXY();
            keystoneBinding.keystoneX.setText(String.valueOf(xy[0]));
            keystoneBinding.keystoneY.setText(String.valueOf(xy[1]));
            maxXy[0] = (int) (MAX_W * 0.9);
            maxXy[1] = MAX_L;
            minXy[0] = 0;
            minXy[1] = MIN_L;
            mode = 1;
        } else if (v == keystoneBinding.keystoneRightUp) {
            keystoneBinding.keystoneLeftDown.setFocusable(false);
            keystoneBinding.keystoneRightDown.setFocusable(false);
            keystoneBinding.keystoneLeftUp.setFocusable(false);
            isSetting = true;
            xy = KeystoneUtils.getRtXY();
            keystoneBinding.keystoneX.setText(String.valueOf(xy[0]));
            keystoneBinding.keystoneY.setText(String.valueOf(xy[1]));
            maxXy[0] = MAX_W;
            maxXy[1] = MAX_L;
            minXy[0] = MIN_W;
            minXy[1] = MIN_L;
            mode = 2;
        } else if (v == keystoneBinding.keystoneLeftDown) {
            keystoneBinding.keystoneRightUp.setFocusable(false);
            keystoneBinding.keystoneRightDown.setFocusable(false);
            keystoneBinding.keystoneLeftUp.setFocusable(false);
            isSetting = true;
            xy = KeystoneUtils.getLbXY();
            keystoneBinding.keystoneX.setText(String.valueOf(xy[0]));
            keystoneBinding.keystoneY.setText(String.valueOf(xy[1]));
            maxXy[0] = (int) (MAX_W * 0.9);
            maxXy[1] = (int) (MAX_L * 0.9);
            minXy[0] = 0;
            minXy[1] = 0;
            mode = 3;
        } else if (v == keystoneBinding.keystoneRightDown) {
            keystoneBinding.keystoneLeftDown.setFocusable(false);
            keystoneBinding.keystoneRightUp.setFocusable(false);
            keystoneBinding.keystoneLeftUp.setFocusable(false);
            isSetting = true;
            xy = KeystoneUtils.getRbXY();
            keystoneBinding.keystoneX.setText(String.valueOf(xy[0]));
            keystoneBinding.keystoneY.setText(String.valueOf(xy[1]));
            maxXy[0] = MAX_W;
            maxXy[1] = (int) (MAX_L * 0.9);
            minXy[0] = MIN_W;
            minXy[1] = 0;
            mode = 4;
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
