package com.android.projectorlauncher.ui.activity;

import static com.alibaba.fastjson.util.IOUtils.close;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.os.Build;
import android.os.Bundle;
import android.os.FileUtils;
import android.util.Log;
import android.view.Window;
import android.view.WindowManager;

import com.android.projectorlauncher.R;
import com.android.projectorlauncher.databinding.ActivityPictureBinding;
import com.android.projectorlauncher.interfaces.PictureInterface;

import java.io.File;
import java.io.FileDescriptor;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.nio.channels.FileChannel;

public class PictureActivity extends AppCompatActivity {
    ActivityPictureBinding pictureBinding;

    @SuppressLint("DefaultLocale")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_TRANSLUCENT_NAVIGATION);
        setContentView(R.layout.activity_picture);
        pictureBinding = ActivityPictureBinding.bind(findViewById(R.id.picture_main));

//        pictureBinding.brightness.setText(String.format("%s%d", pictureBinding.brightness.getText().toString(), PictureInterface.getBrightness()));
//        pictureBinding.colorTemp.setText(String.format("%s%d", pictureBinding.colorTemp.getText().toString(), PictureInterface.getColorTemp()));
//        pictureBinding.contrast.setText(String.format("%s%d", pictureBinding.contrast.getText().toString(), PictureInterface.getContrast()));

    }
}