package com.android.projectorlauncher.utils;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.util.Log;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

public class ImageUtils {
    public static String getCacheImage(Context context, String tag, int index) {
        File dir = new File(context.getFilesDir(), tag);
        boolean isMk = true;
        if (!dir.exists() || !dir.isDirectory()) {
            isMk = dir.mkdirs();
        }
        if (!isMk) return null;
        File file = new File(dir, tag + index);
        if (file.exists() && file.isFile()) {
            Log.d("ImageUtils", "getCacheImage: " + file.getAbsolutePath());
            return file.getAbsolutePath();
        }
        return null;
    }

    @SuppressWarnings("ResultOfMethodCallIgnored")
    public static void saveImage(Context context, String tag, int index, Drawable resource) {
        File dir = new File(context.getFilesDir(), tag);
        boolean isMk = true;
        if (!dir.exists() || !dir.isDirectory()) {
            isMk = dir.mkdirs();
        }
        if (!isMk) return;

        try {
            File file = new File(dir, tag + index);
            if (file.exists()) {
                file.delete();
            }
            if (!file.exists())
                file.createNewFile();
            FileOutputStream out;
            out = new FileOutputStream(file);
            Bitmap.CompressFormat format = Bitmap.CompressFormat.PNG;
            ((BitmapDrawable) resource).getBitmap().compress(format, 100, out);
            out.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
