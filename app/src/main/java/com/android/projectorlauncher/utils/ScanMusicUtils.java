package com.android.projectorlauncher.utils;

import android.annotation.SuppressLint;
import android.content.Context;
import android.database.Cursor;
import android.media.MediaMetadataRetriever;
import android.provider.MediaStore;
import android.util.Log;

import com.android.projectorlauncher.bean.MusicModel;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Objects;

public class ScanMusicUtils {

    public static List<MusicModel> getMusicData(Context context) {
        List<MusicModel> models = new ArrayList<>();
        String[] selectionArgs = new String[]{"%Music%"};
        String selection = MediaStore.Audio.Media.DATA + " like ? ";
        Cursor cursor = context.getContentResolver().query(
                MediaStore.Audio.Media.EXTERNAL_CONTENT_URI,
                null,
                selection,
                selectionArgs,
                MediaStore.Audio.AudioColumns.IS_MUSIC
        );

        if (cursor != null) {
            while (cursor.moveToNext()) {
                MusicModel musicModel = new MusicModel();
                musicModel.setName(cursor.getString(cursor.getColumnIndexOrThrow(MediaStore.Audio.Media.DISPLAY_NAME)));
                musicModel.setAuthor(cursor.getString(cursor.getColumnIndexOrThrow(MediaStore.Audio.Media.ARTIST)));
                musicModel.setResSrc(cursor.getString(cursor.getColumnIndexOrThrow(MediaStore.Audio.Media.DATA)));
                musicModel.setResSrc(cursor.getString(cursor.getColumnIndexOrThrow(MediaStore.Audio.Media.DURATION)));
                models.add(musicModel);
                Log.d("ScanMusicUtils", "getMusicData: " + musicModel.toString());
            }
            cursor.close();
        }
        Log.d("ScanMusicUtils", "getMusicData: " + models.size());
        return models;
    }


    public static List<MusicModel> getMusicList(String path) {
        File dir = new File(path);
        List<MusicModel> models = new ArrayList<>();
        if (!dir.exists() || dir.listFiles() == null) {
            return models;
        }
        for (File file : Objects.requireNonNull(dir.listFiles())) {
            if (file.getName().contains(".mp3") || file.getName().contains(".wav") || file.getName().contains(".flac")) {
                MusicModel model = new MusicModel();
                model.setName(getMusicName(file.getAbsolutePath()));
                model.setAuthor(getMusicArtist(file.getAbsolutePath()));
                model.setResSrc(file.getAbsolutePath());
                model.setDuration(formatTime(Integer.parseInt(getMusicInformation(file.getAbsolutePath()))));
                models.add(model);
                Log.d("ScanMusicUtils", "getMusicData: " + model.toString());
            }
        }
        return models;
    }

    public static String getMusicInformation(String path) {
        MediaMetadataRetriever mediaMetadataRetriever = new MediaMetadataRetriever();
        try {
            mediaMetadataRetriever.setDataSource(path);
            return mediaMetadataRetriever.extractMetadata(MediaMetadataRetriever.METADATA_KEY_DURATION);
        } catch (Exception e) {
            return " ";
        }

    }

    public static String getMusicName(String path) {
        MediaMetadataRetriever mediaMetadataRetriever = new MediaMetadataRetriever();
        try {
            mediaMetadataRetriever.setDataSource(path);
            return mediaMetadataRetriever.extractMetadata(MediaMetadataRetriever.METADATA_KEY_TITLE);
        } catch (Exception e) {
            return " ";
        }
    }

    public static String getMusicArtist(String path) {
        MediaMetadataRetriever mediaMetadataRetriever = new MediaMetadataRetriever();
        try {
            mediaMetadataRetriever.setDataSource(path);
            return mediaMetadataRetriever.extractMetadata(MediaMetadataRetriever.METADATA_KEY_ARTIST);
        } catch (Exception e) {
            return " ";
        }
    }

    public static String formatTime(int duration) {
        int mT = duration / 1000 / 60;
        int sT = duration / 1000 % 60;
        String format = "mm:ss";
        @SuppressLint("SimpleDateFormat")
        SimpleDateFormat sdf = new SimpleDateFormat(format);
        return sdf.format(new Date(0, 0, 0, 0, mT, sT));
    }
}
