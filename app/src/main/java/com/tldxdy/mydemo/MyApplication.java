package com.tldxdy.mydemo;

import android.app.Application;

import com.tldxdy.base.util.Base;

/**
 * Created by Administrator on 2016/4/27.
 */
public class MyApplication extends Application {

    @Override
    public void onCreate() {
        super.onCreate();
        Base.initialize(this);
        initLogUtils();
    }

    private void initLogUtils() {

    }
}
