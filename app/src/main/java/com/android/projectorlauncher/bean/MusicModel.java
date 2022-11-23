package com.android.projectorlauncher.bean;

public class MusicModel extends Model{
    private String author;
    private String duration;

    public MusicModel() {
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public String getDuration() {
        return duration;
    }

    public void setDuration(String duration) {
        this.duration = duration;
    }

    public String getResSrc() {
        return src;
    }

    public void setResSrc(String resSrc) {
        this.src = resSrc;
    }

}
