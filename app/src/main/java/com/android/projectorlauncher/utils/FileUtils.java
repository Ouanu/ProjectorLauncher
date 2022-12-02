package com.android.projectorlauncher.utils;

import android.util.Log;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;

public class FileUtils {
    public static HashSet<String> getUsbDisk() {
        HashSet<String> paths = new HashSet<>();
        String mntPath = "/proc/mounts";
        File file = new File(mntPath);
        List<String> list = new ArrayList<>();
        try {
            InputStream is = new FileInputStream(file);
            InputStreamReader isr = new InputStreamReader(is);
            BufferedReader br = new BufferedReader(isr);
            String line;
            while ((line = br.readLine()) != null) {
                if (line.contains("media_rw") && line.contains("storage")) {
                    list.add(line);
                    Log.d("PrepareWork", "getUsbDisk: " + line);
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        if (list.size() == 0) {
            return paths;
        }

        for (int i = 0; i < list.size(); i++) {
            String finalStr = list.get(i);
            int start = finalStr.indexOf("/storage");
            int end = finalStr.indexOf(" sdcardfs");
            String s = finalStr.substring(start, end);
            paths.add(s);
        }
        paths.size();
        return paths;
    }


}
