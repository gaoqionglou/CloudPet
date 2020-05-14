package com.app.cloudpet.ui;

import android.Manifest;
import android.app.Activity;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import com.app.cloudpet.R;
import com.app.cloudpet.base.BaseActivity;
import com.app.cloudpet.base.CommonActivity;
import com.app.cloudpet.common.Constants;
import com.app.cloudpet.model._User;
import com.app.cloudpet.ui.login.LoginActivity;
import com.gyf.immersionbar.ImmersionBar;

import java.lang.ref.WeakReference;

import cn.bmob.v3.BmobUser;

/**
 * 欢迎界面
 * 首次进入
 */
public class WelcomeActivity extends CommonActivity {


    private MyHandler mHandler = new MyHandler(this);


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ImmersionBar.with(this).transparentBar().init();
        setContentView(R.layout.activity_welcome);

        mHandler.sendEmptyMessageDelayed(1, 1000);
    }



    static class MyHandler extends Handler {
        WeakReference<Activity> mActivityReference;

        MyHandler(Activity activity) {
            mActivityReference = new WeakReference<Activity>(activity);
        }

        @Override
        public void handleMessage(Message msg) {
            final Activity activity = mActivityReference.get();
            if (activity != null) {
                if(BmobUser.getCurrentUser(_User.class)==null) {
                    Intent intent = new Intent(activity, LoginActivity.class);
                    activity.startActivity(intent);
                    activity.finish();
                }else {
                    Intent intent = new Intent(activity, MainActivity.class);
                    activity.startActivity(intent);
                    activity.finish();
                }
            }
        }
    }
}
