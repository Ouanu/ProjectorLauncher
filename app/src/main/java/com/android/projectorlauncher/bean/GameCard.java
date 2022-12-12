package com.android.projectorlauncher.bean;

public class GameCard {
    private String image;   // 封面
    private String title;   // 名称
    private String packageName; // 包名
    private String className;   // 类名，可以为空，默认启动带"android.intent.action.MAIN"的Activity
    private int tag;        // 类型

    public GameCard() {
    }

    public GameCard(String image, String title, String packageName, String className, int tag) {
        this.image = image;
        this.title = title;
        this.packageName = packageName;
        this.className = className;
        this.tag = tag;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getPackageName() {
        return packageName;
    }

    public void setPackageName(String packageName) {
        this.packageName = packageName;
    }

    public String getClassName() {
        return className;
    }

    public void setClassName(String className) {
        this.className = className;
    }

    public int getTag() {
        return tag;
    }

    public void setTag(int tag) {
        this.tag = tag;
    }
}
