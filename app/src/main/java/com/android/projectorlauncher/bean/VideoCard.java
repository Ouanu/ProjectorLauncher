package com.android.projectorlauncher.bean;

public class VideoCard {
    private String name;    // 视频名称
    private String imgSrc;  // 封面图链接
    private String id;         // 资源id，调用极光TV需要该id
    private boolean isVip;  // 资源是否属于VIP
    private String time;    // 上线时间

    public VideoCard(String name, String imgSrc, String id, boolean isVip, String time) {
        this.name = name;
        this.imgSrc = imgSrc;
        this.id = id;
        this.isVip = isVip;
        this.time = time;
    }

    public VideoCard() {
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

    public boolean isVip() {
        return isVip;
    }

    public void setVip(boolean vip) {
        isVip = vip;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }
}
