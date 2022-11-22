package com.android.projectorlauncher.bean;

public class Model {
    public String name;
    public String src;

    public Model(String name, String src) {
        this.name = name;
        this.src = src;
    }

    public Model() {
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
}
