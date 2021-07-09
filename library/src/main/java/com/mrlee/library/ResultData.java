package com.mrlee.library;


import android.text.TextUtils;


public class ResultData{
    private String url = "";
    private String thumbnail = "";
    private int itemType;

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

}
