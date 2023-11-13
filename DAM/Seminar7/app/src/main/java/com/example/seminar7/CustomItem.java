package com.example.seminar7;

import android.graphics.Bitmap;

import java.io.Serializable;

public class CustomItem implements Serializable {

    protected String url;
    protected Bitmap img;
    protected String description;

    public CustomItem(String url, Bitmap img, String description) {
        this.url = url;
        this.img = img;
        this.description = description;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }


    public Bitmap getImg() {
        return img;
    }

    public void setImg(Bitmap img) {
        this.img = img;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
