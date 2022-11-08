package com.android.projectorlauncher.utils;

import android.content.Context;

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
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public class TestFileUtils {

    public static void writeTestFiles(Context context) {
        File dir = context.getFilesDir();
        if (!dir.exists()) {
            boolean b = dir.mkdirs();
            if (!b) {
                return;
            }
        }
        File file = new File(dir, "Video.json");
//        List<VideoCard> cards = Arrays.asList(
//                new VideoCard("蝙蝠侠", dir + "/batman.jpeg", "mzc00200977rx4h", true, "02:48:34"),
//                new VideoCard("神秘海域", dir + "/uncharted.jpeg", "mzc00200buz4cah", true, "01:55:58"),
//                new VideoCard("独行月球", dir + "/moon_man.jpeg", "mzc002000y0q8uy", true, "02:01:51"),
//                new VideoCard("新神榜：杨戬", dir + "/new_gods.webp", "mzc00200mjy32e7", true, "02:07:30")
//        );
        JSONArray array = new JSONArray(Arrays.asList(
                new VideoCard("蝙蝠侠", dir + "/batman.jpeg", "mzc00200977rx4h", true, "02:48:34"),
                new VideoCard("神秘海域", dir + "/uncharted.jpeg", "mzc00200buz4cah", true, "01:55:58"),
                new VideoCard("独行月球", dir + "/moon_man.jpeg", "mzc002000y0q8uy", true, "02:01:51"),
                new VideoCard("新神榜：杨戬", dir + "/new_gods.webp", "mzc00200mjy32e7", true, "02:07:30")
        ));
        try {
            JSONWriter writer = new JSONWriter(new FileWriter(file));
            writer.writeObject(array);
            writer.flush();
            writer.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static List<VideoCard> getCards(Context context) {
        List<VideoCard> cards = new ArrayList<>();
        File dir = context.getFilesDir();
        if (!dir.exists()) {
            boolean b = dir.mkdirs();
            if (!b) {
                return cards;
            }
        }
        File file = new File(dir, "Video.json");
        try {
            JSONReader reader = new JSONReader(new FileReader(file));
            reader.startArray();
            while (reader.hasNext()) {
                VideoCard card = reader.readObject(VideoCard.class);
                cards.add(card);
            }
            reader.endArray();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        return cards;
    }
}
