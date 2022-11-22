package com.android.projectorlauncher.utils;

import android.content.Context;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.media.MediaMetadataRetriever;
import android.provider.MediaStore;
import android.util.Log;

import com.android.projectorlauncher.bean.MusicModel;
import com.android.projectorlauncher.bean.VideoModel;
import com.bumptech.glide.Glide;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class ScanVideoUtils {
    public static List<VideoModel> getVideoList(String path) {
        File dir = new File(path);
        List<VideoModel> models = new ArrayList<>();
        if (!dir.exists() || dir.listFiles() == null) {
            return models;
        }
        for (File file : Objects.requireNonNull(dir.listFiles())) {
            Log.d("ResourceActivity", "getVideoList: " + file.getName());
            if (file.getName().contains(".mp4") || file.getName().contains(".rmvb") ||
                    file.getName().contains(".3gp") || file.getName().contains(".mov") ||
                    file.getName().contains(".flv") || file.getName().contains(".mkv")){
                VideoModel model = new VideoModel();
                model.setName(file.getName());
                model.setSrc(file.getAbsolutePath());
//                model.setImage(getFrameBitmap(file.getAbsolutePath()));
                model.setDuration(getDuration(file.getAbsolutePath()));
                models.add(model);
            }
        }
        return models;
    }

//    public static Bitmap getFrameBitmap(String src) {
//        MediaMetadataRetriever retriever = new MediaMetadataRetriever();
//        //添加路径
//        retriever.setDataSource(src);
//        //获取封面
//        Bitmap frameAtTime = retriever.getFrameAtTime();
//        frameAtTime.setConfig(Bitmap.Config.RGB_565);
//        return frameAtTime;
//    }

    public static String getDuration(String src) {
        MediaMetadataRetriever retriever = new MediaMetadataRetriever();
        //添加路径
        retriever.setDataSource(src);
        //获取时长
        return retriever.extractMetadata(MediaMetadataRetriever.METADATA_KEY_DURATION);
    }
}
