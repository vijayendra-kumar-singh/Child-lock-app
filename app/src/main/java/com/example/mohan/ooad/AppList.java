package com.example.mohan.ooad;

import android.graphics.drawable.Drawable;

public class AppList {

    private String name;
    private Drawable icon;
    private String pack;

    public AppList(String name, Drawable icon, String pack) {
        this.name = name;
        this.icon = icon;
        this.pack = pack;
    }

    public String getName() {
        return name;
    }

    public Drawable getIcon() {
        return icon;
    }

    public String getPack() {
        return pack;
    }
}