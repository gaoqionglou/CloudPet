package com.app.cloudpet.utils;

import android.widget.Toast;

import com.app.cloudpet.MyApplication;

public class ToastUtil {
    public static void toast( String text) {
        Toast.makeText(MyApplication.getMyApplication().getApplicationContext(), text, Toast.LENGTH_SHORT).show();
    }
}
