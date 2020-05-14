package com.app.cloudpet.base;

import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.app.cloudpet.R;
import com.gyf.immersionbar.ImmersionBar;

public abstract class BaseActivity extends AppCompatActivity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ImmersionBar.with(this).barColor(R.color.colorPrimary).init();
        setCustomActionBar();
    }


    public abstract void setCustomActionBar();

}
