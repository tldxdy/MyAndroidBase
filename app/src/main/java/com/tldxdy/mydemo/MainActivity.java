package com.tldxdy.mydemo;

import android.os.Bundle;
import android.util.Log;

import com.tldxdy.base.frame.activity.BaseActivity;

public class MainActivity extends BaseActivity {

    @Override
    protected int getlayoutId() {
        return R.layout.activity_main;

    }

    @Override
    protected void initView() {
        Log.e("aa","aa");
    }

    @Override
    protected void initData(Bundle savedInstanceState) {

    }
}
