package com.app.cloudpet.base;

import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;

import androidx.annotation.Nullable;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import com.app.cloudpet.R;
import com.app.cloudpet.common.Constants;
import com.app.cloudpet.databinding.ActionBarLayoutBinding;
import com.gyf.immersionbar.ImmersionBar;

public class CommonActivity extends BaseActivity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ImmersionBar.with(this).barColor(R.color.colorPrimary).init();
        setCustomActionBar();
    }

    @Override
    public void setCustomActionBar() {
        ActionBarLayoutBinding actionBarViewBinding = ActionBarLayoutBinding.inflate(LayoutInflater.from(this));
        ActionBar.LayoutParams lp = new ActionBar.LayoutParams(ActionBar.LayoutParams.MATCH_PARENT, ActionBar.LayoutParams.MATCH_PARENT, Gravity.CENTER);
        View mActionBarView = actionBarViewBinding.getRoot();
        ActionBar actionBar = getSupportActionBar();
        if(actionBar==null){
            Log.e(Constants.TAG,"actionBar null set style!");
            return;
        }
        actionBar.setCustomView(mActionBarView, lp);
        actionBar.setDisplayOptions(ActionBar.DISPLAY_SHOW_CUSTOM);
        actionBar.setDisplayShowCustomEnabled(true);
        actionBar.setDisplayShowHomeEnabled(false);
        actionBar.setDisplayShowTitleEnabled(false);
        actionBarViewBinding.title.setText(R.string.app_name);
    }
}
