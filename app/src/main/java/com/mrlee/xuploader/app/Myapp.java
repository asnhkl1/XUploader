package com.mrlee.xuploader.app;

import android.app.Application;

import com.hjq.permissions.XXPermissions;

public class Myapp extends Application {
    @Override
    public void onCreate() {
        super.onCreate();
        XXPermissions.setScopedStorage(true);
    }
}
