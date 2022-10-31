package com.android.projectorlauncher.utils;

import android.content.Context;
import android.os.Handler;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONReader;
import com.alibaba.fastjson.JSONWriter;
import com.android.projectorlauncher.bean.VideoCard;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;


public class JsonUtils {

    public static void writeVideoCards(Context context, Handler handler) {
        File dir = context.getFilesDir();
        File file = new File(dir, "Movie.json");

        JSONArray array = new JSONArray();
        array.add(new VideoCard("新蝙蝠侠", "", "mzc00200977rx4h", true, "02:48:43"));
        array.add(new VideoCard("独行月球", "", "mzc002000y0q8uy", true, "02:01:51"));
        array.add(new VideoCard("神秘海域", "", "mzc00200buz4cah", true, "01:55:58"));
        array.add(new VideoCard("新蝙蝠侠", "", "mzc00200977rx4h", true, "02:48:43"));
        array.add(new VideoCard("独行月球", "", "mzc002000y0q8uy", true, "02:01:51"));
        array.add(new VideoCard("神秘海域", "", "mzc00200buz4cah", true, "01:55:58"));
        array.add(new VideoCard("新蝙蝠侠", "", "mzc00200977rx4h", true, "02:48:43"));
        array.add(new VideoCard("独行月球", "", "mzc002000y0q8uy", true, "02:01:51"));
        array.add(new VideoCard("神秘海域", "", "mzc00200buz4cah", true, "01:55:58"));
        array.add(new VideoCard("神秘海域", "", "mzc00200buz4cah", true, "01:55:58"));
        array.add(new VideoCard("神秘海域", "", "mzc00200buz4cah", true, "01:55:58"));
        array.add(new VideoCard("独行月球", "", "mzc002000y0q8uy", true, "02:01:51"));
        array.add(new VideoCard("独行月球", "", "mzc002000y0q8uy", true, "02:01:51"));
        array.add(new VideoCard("独行月球", "", "mzc002000y0q8uy", true, "02:01:51"));
        array.add(new VideoCard("独行月球", "", "mzc002000y0q8uy", true, "02:01:51"));

        try {
            JSONWriter writer = new JSONWriter(new FileWriter(file));
            writer.writeObject(array);
            writer.flush();
            writer.close();
            handler.sendEmptyMessage(100);
        } catch (IOException e) {
            e.printStackTrace();
            handler.sendEmptyMessage(101);
        }
    }

//    public static void readJson(Context context, Handler handler) {
//        new Thread(()->{
//
//        })
//    }

    // 读取.json文件，获取需要更新的内容
    public static List<VideoCard> readVideoCards(Context context, String json) {
        File dir = context.getFilesDir();
        File file = new File(dir, json);
        List<VideoCard> resList = new ArrayList<>();
        try {
            JSONReader reader = new JSONReader(new FileReader(file));
            reader.startArray();
            while (reader.hasNext()) {
                VideoCard card = reader.readObject(VideoCard.class);
                resList.add(card);
            }
            reader.endArray();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        return resList;
    }
}
