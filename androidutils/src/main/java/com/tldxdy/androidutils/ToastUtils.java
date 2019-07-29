package com.tldxdy.androidutils;

import android.annotation.SuppressLint;
import android.widget.Toast;

public class ToastUtils {
    private static Toast toast = null; //Toast的对象！

    @SuppressLint("ShowToast")
    public static void showToast(String str) {
        if (toast == null) {
            toast = Toast.makeText(Base.getContext(), str, Toast.LENGTH_SHORT);
        }
        else {
            toast.setText(str);
        }
        toast.show();
    }

    @SuppressLint("ShowToast")
    public static void showToast(String str,int duration) {
        if (toast == null) {
            toast = Toast.makeText(Base.getContext(), str, Toast.LENGTH_SHORT);
        }
        else {
            toast.setText(str);
        }
        toast.setDuration(duration);
        toast.show();
    }
}
