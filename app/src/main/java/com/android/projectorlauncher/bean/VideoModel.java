package com.android.projectorlauncher.bean;

import android.graphics.Bitmap;

public class VideoModel extends Model{
    private String duration;
    private Bitmap image;

    public VideoModel(String name, String src, String duration, Bitmap image) {
        this.name = name;
        this.src = src;
        this.duration = duration;
        this.image = image;
    }

    public VideoModel() {
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSrc() {
        return src;
    }

    public void setSrc(String src) {
        this.src = src;
    }

    public String getDuration() {
        return duration;
    }

    public void setDuration(String duration) {
        this.duration = duration;
    }

    public Bitmap getImage() {
        return image;
    }

    public void setImage(Bitmap image) {
        this.image = image;
    }
}
