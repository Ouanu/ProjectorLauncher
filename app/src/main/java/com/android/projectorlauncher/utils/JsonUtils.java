package com.android.projectorlauncher.utils;

import android.content.Context;
import android.os.Handler;
import android.util.Log;
import com.alibaba.fastjson.JSONReader;
import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;


public class JsonUtils {
    public static final int DOWNLOAD_SUCCESS = 101;
    public static final int DOWNLOAD_FAILURE = 202;

    public static <T> List<T> readCards(Context context, String json, Class<T> type) {
        File dir = context.getFilesDir();
        File file = new File(dir, json);
        List<T> resList = new ArrayList<>();
        try {
            JSONReader reader = new JSONReader(new FileReader(file));
            reader.startArray();
            while (reader.hasNext()) {
                T card = reader.readObject(type);
                resList.add(card);
            }
            reader.endArray();
        } catch (FileNotFoundException e) {
            Log.d("JsonUtils", "readVideoCards: " + e.getMessage());
        }
        return resList;
    }

    public static void downloadJson(Context context, String fileName, Handler handler) {
        File dir = context.getFilesDir();
        File file = new File(dir, fileName);
        String uri = "http://www.rxvs8.com/projector/json/" + fileName;
        new Thread(() -> {
            try {
                URL url = new URL(uri);
                HttpURLConnection conn = (HttpURLConnection) url.openConnection();
                conn.setConnectTimeout(5000);
                InputStream is = conn.getInputStream();
                FileOutputStream fos = new FileOutputStream(file);
                BufferedInputStream bis = new BufferedInputStream(is);
                byte[] buff = new byte[1024];
                int len;
                while ((len = bis.read(buff)) != -1) {
                    fos.write(buff, 0, len);
                }
                fos.close();
                bis.close();
                is.close();
                handler.sendEmptyMessage(DOWNLOAD_SUCCESS);
            } catch (IOException e) {
                e.printStackTrace();
                handler.sendEmptyMessage(DOWNLOAD_FAILURE);
            }
        }).start();
    }

}
