package com.mrlee.library;


import android.text.TextUtils;

import com.chad.library.adapter.base.entity.MultiItemEntity;


public class ImageBean implements MultiItemEntity {
    private String url = "";
    private String thumbnail = "";
    private int itemType;
    public final static int ADD = 1;
    public final static int IMAGE = 2;

    public String getThumbnail() {
        if(TextUtils.isEmpty(thumbnail)){
            return url;
        }
        return thumbnail;
    }

    public void setThumbnail(String thumbnail) {
        this.thumbnail = thumbnail;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }


    public ImageBean(int itemType) {
        this.itemType = itemType;
    }

    @Override
    public int getItemType() {
        return itemType;
    }
}
