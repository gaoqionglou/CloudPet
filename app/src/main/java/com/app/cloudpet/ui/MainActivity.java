package com.app.cloudpet.ui;

import android.Manifest;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.ActionBar;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.navigation.NavController;
import androidx.navigation.NavDestination;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;

import com.app.cloudpet.R;
import com.app.cloudpet.base.BaseActivity;
import com.app.cloudpet.databinding.ActionBarLayoutBinding;
import com.app.cloudpet.databinding.HomeActionBarLayoutBinding;
import com.app.cloudpet.databinding.MineActionBarLayoutBinding;
import com.google.android.material.bottomnavigation.BottomNavigationView;

public class MainActivity extends BaseActivity {
    private ActionBarLayoutBinding mActionBarViewBinding;
    private HomeActionBarLayoutBinding homeActionBarLayoutBinding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        //判断是否有相机权限
        if (!(ContextCompat.checkSelfPermission(this, Manifest.permission.CAMERA) == PackageManager.PERMISSION_GRANTED)) {

            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.CAMERA}, 0);
        }

        BottomNavigationView navView = findViewById(R.id.nav_view);
        AppBarConfiguration appBarConfiguration = new AppBarConfiguration.Builder(
                R.id.navigation_home, R.id.navigation_community, R.id.navigation_raise,
                R.id.navigation_mine)
                .build();
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment);
        NavigationUI.setupActionBarWithNavController(this, navController, appBarConfiguration);
        NavigationUI.setupWithNavController(navView, navController);
        navController.addOnDestinationChangedListener(new NavController.OnDestinationChangedListener() {
            @Override
            public void onDestinationChanged(@NonNull NavController controller, @NonNull NavDestination destination, @Nullable Bundle arguments) {
                CharSequence lebel = destination.getLabel();
                mActionBarViewBinding.title.setText(lebel);
}
        });
                }

    @Override
    public void setCustomActionBar() {
        mActionBarViewBinding = ActionBarLayoutBinding.inflate(LayoutInflater.from(this));
        ActionBar.LayoutParams lp = new ActionBar.LayoutParams(ActionBar.LayoutParams.MATCH_PARENT, ActionBar.LayoutParams.MATCH_PARENT, Gravity.CENTER);
        View mActionBarView = mActionBarViewBinding.getRoot();
        ActionBar actionBar = getSupportActionBar();
        if (actionBar == null) return;
        actionBar.setCustomView(mActionBarView, lp);
        actionBar.setDisplayOptions(ActionBar.DISPLAY_SHOW_CUSTOM);
        actionBar.setDisplayShowCustomEnabled(true);
        actionBar.setDisplayShowHomeEnabled(false);
        actionBar.setDisplayShowTitleEnabled(false);
    }

    public void setCustomActionBar(String title) {
        mActionBarViewBinding = ActionBarLayoutBinding.inflate(LayoutInflater.from(this));
        ActionBar.LayoutParams lp = new ActionBar.LayoutParams(ActionBar.LayoutParams.MATCH_PARENT, ActionBar.LayoutParams.MATCH_PARENT, Gravity.CENTER);
        View mActionBarView = mActionBarViewBinding.getRoot();
        ActionBar actionBar = getSupportActionBar();
        if (actionBar == null) return;
        actionBar.setCustomView(mActionBarView, lp);
        actionBar.setDisplayOptions(ActionBar.DISPLAY_SHOW_CUSTOM);
        actionBar.setDisplayShowCustomEnabled(true);
        actionBar.setDisplayShowHomeEnabled(false);
        actionBar.setDisplayShowTitleEnabled(false);
        mActionBarViewBinding.title.setText(title);
    }


    public HomeActionBarLayoutBinding setHomeFragmentActionBar() {
        homeActionBarLayoutBinding = HomeActionBarLayoutBinding.inflate(LayoutInflater.from(this));
        ActionBar.LayoutParams lp = new ActionBar.LayoutParams(ActionBar.LayoutParams.MATCH_PARENT, ActionBar.LayoutParams.MATCH_PARENT, Gravity.CENTER);
        View mActionBarView = homeActionBarLayoutBinding.getRoot();
        ActionBar actionBar = getSupportActionBar();
        if (actionBar == null) return null;
        actionBar.setCustomView(mActionBarView, lp);
        actionBar.setDisplayOptions(ActionBar.DISPLAY_SHOW_CUSTOM);
        actionBar.setDisplayShowCustomEnabled(true);
        actionBar.setDisplayShowHomeEnabled(false);
        actionBar.setDisplayShowTitleEnabled(false);
        return homeActionBarLayoutBinding;
    }

    public MineActionBarLayoutBinding setMineFragmentActionBar() {
        MineActionBarLayoutBinding mineActionBarLayoutBinding = MineActionBarLayoutBinding.inflate(LayoutInflater.from(this));
        ActionBar.LayoutParams lp = new ActionBar.LayoutParams(ActionBar.LayoutParams.MATCH_PARENT, ActionBar.LayoutParams.MATCH_PARENT, Gravity.CENTER);
        View mActionBarView = mineActionBarLayoutBinding.getRoot();
        ActionBar actionBar = getSupportActionBar();
        if (actionBar == null) return null;
        actionBar.setCustomView(mActionBarView, lp);
        actionBar.setDisplayOptions(ActionBar.DISPLAY_SHOW_CUSTOM);
        actionBar.setDisplayShowCustomEnabled(true);
        actionBar.setDisplayShowHomeEnabled(false);
        actionBar.setDisplayShowTitleEnabled(false);
        return mineActionBarLayoutBinding;
    }

}
