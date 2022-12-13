package com.android.projectorlauncher.utils;

import android.content.Context;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;

import androidx.annotation.Nullable;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.DataSource;
import com.bumptech.glide.load.engine.GlideException;
import com.bumptech.glide.request.RequestListener;
import com.bumptech.glide.request.target.Target;

import java.io.File;
import java.util.HashMap;

public class CacheUtil {

    public static final int IMAGE_PREPARED = 1000;
    public static final int IMAGE_FALSE = 1111;


    public static void downloadImage(Context context, String url, int i , HashMap<Integer, String> map, Handler handler) {
        Message msg = new Message();
        Glide.with(context)
                .downloadOnly()
                .load(url)
                .listener(new RequestListener<File>() {
                    @Override
                    public boolean onLoadFailed(@Nullable GlideException e, Object model, Target<File> target, boolean isFirstResource) {
                        Log.d("CacheUtil", "download false: " + url);
                        handler.sendEmptyMessage(IMAGE_FALSE);
                        return false;
                    }

                    @Override
                    public boolean onResourceReady(File resource, Object model, Target<File> target, DataSource dataSource, boolean isFirstResource) {
                        Log.d("CacheUtil", "onResourceReady: " + resource.getAbsolutePath());
                        map.put(i, resource.getAbsolutePath());
                        Bundle bundle = new Bundle();
                        bundle.putInt("INDEX", i);
                        msg.what = IMAGE_PREPARED;
                        msg.setData(bundle);
                        handler.sendMessage(msg);
                        return false;
                    }
                })
                .preload();
    }




}
