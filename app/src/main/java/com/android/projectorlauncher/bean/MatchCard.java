package com.android.projectorlauncher.bean;

public class MatchCard {
    private String name;
    private String imgSrc;
    private String id;

    public MatchCard(String name, String imgSrc, String id) {
        this.name = name;
        this.imgSrc = imgSrc;
        this.id = id;
    }

    public MatchCard() {
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getImgSrc() {
        return imgSrc;
    }

    public void setImgSrc(String imgSrc) {
        this.imgSrc = imgSrc;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }
}
