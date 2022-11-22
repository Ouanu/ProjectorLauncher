package com.android.projectorlauncher.bean;

public class MusicModel extends Model{
    private String author;
    private String duration;
    private String imageSrc;

    public MusicModel(String name, String author, String duration, String resSrc, String imageSrc) {
        this.name = name;
        this.author = author;
        this.duration = duration;
        this.src = resSrc;
        this.imageSrc = imageSrc;
    }

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

    public String getImageSrc() {
        return imageSrc;
    }

    public void setImageSrc(String imageSrc) {
        this.imageSrc = imageSrc;
    }

    @Override
    public String toString() {
        return "MusicModel{" +
                "name='" + name + '\'' +
                ", author='" + author + '\'' +
                ", duration=" + duration +
                ", resSrc='" + src + '\'' +
                ", imageSrc='" + imageSrc + '\'' +
                '}';
    }
}
