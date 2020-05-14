package com.app.cloudpet.base;

import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.ActionBar;

import com.app.cloudpet.R;
import com.app.cloudpet.common.Constants;
import com.app.cloudpet.databinding.ActionBarLayoutBinding;
import com.gyf.immersionbar.ImmersionBar;

public class ThemeActivity extends BaseActivity {

    private ActionBarLayoutBinding actionBarLayoutBinding;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ImmersionBar.with(this).barColor(R.color.colorPrimary).init();
        setCustomActionBar();
    }

    @Override
    public void setCustomActionBar() {
        actionBarLayoutBinding = ActionBarLayoutBinding.inflate(LayoutInflater.from(this));
        ActionBar.LayoutParams lp = new ActionBar.LayoutParams(ActionBar.LayoutParams.MATCH_PARENT, ActionBar.LayoutParams.MATCH_PARENT, Gravity.CENTER);
        View mActionBarView = actionBarLayoutBinding.getRoot();
        ImageView arrow = mActionBarView.findViewById(R.id.arrow_back);
        arrow.setVisibility(View.VISIBLE);
        arrow.setOnClickListener(v -> {
            this.finish();
        });
        ActionBar actionBar = getSupportActionBar();
        if (actionBar == null) {
            Log.e(Constants.TAG, "actionBar null set style!");
            return;
        }
        actionBar.setCustomView(mActionBarView, lp);
        actionBar.setDisplayOptions(ActionBar.DISPLAY_SHOW_CUSTOM);
        actionBar.setDisplayShowCustomEnabled(true);
        actionBar.setDisplayShowHomeEnabled(false);
        actionBar.setDisplayShowTitleEnabled(false);
        actionBarLayoutBinding.title.setText(R.string.app_name);
    }

    public void setTitle(String title) {
        actionBarLayoutBinding.title.setText(title);
    }
}
