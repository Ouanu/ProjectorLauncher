package com.android.projectorlauncher.utils;

import android.util.Log;

import com.android.projectorlauncher.bean.Model;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class ScanImageUtils {
    public static List<Model> getImageList(String path) {
        File dir = new File(path);
        List<Model> models = new ArrayList<>();
        if (!dir.exists() || dir.listFiles() == null) {
            return models;
        }
        for (File file : Objects.requireNonNull(dir.listFiles())) {
            Log.d("ResourceActivity", "getImageList: " + file.getName());
            if (file.getName().contains(".jpg") || file.getName().contains(".png") ||
                    file.getName().contains(".svg") || file.getName().contains("webp") ||
                    file.getName().contains(".gif") || file.getName().contains(".jpeg")){
                Model model = new Model();
                model.setName(file.getName());
                model.setSrc(file.getAbsolutePath());
                models.add(model);

            }
        }
        return models;
    }

}
