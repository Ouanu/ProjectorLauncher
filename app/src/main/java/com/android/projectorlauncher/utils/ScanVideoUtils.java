package com.android.projectorlauncher.utils;
import android.util.Log;
import com.android.projectorlauncher.bean.VideoModel;
import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class ScanVideoUtils {
    public static List<VideoModel> getVideoList(String path) {
        File dir = new File(path);
        List<VideoModel> models = new ArrayList<>();
        Log.d("ResourceActivity", "getVideoList: " + dir.listFiles());
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
                models.add(model);

            }
        }
        return models;
    }

}
