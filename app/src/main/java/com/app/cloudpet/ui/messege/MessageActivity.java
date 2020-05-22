package com.app.cloudpet.ui.messege;

import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;

import androidx.annotation.Nullable;
import androidx.appcompat.app.ActionBar;
import androidx.core.content.ContextCompat;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.app.cloudpet.R;
import com.app.cloudpet.base.BaseActivity;
import com.app.cloudpet.common.Constants;
import com.app.cloudpet.databinding.ActionBarLayoutBinding;
import com.app.cloudpet.databinding.ActivityMessageBinding;
import com.app.cloudpet.ui.follow.FollowListAdapter;
import com.app.cloudpet.ui.follow.MyFollowViewModel;
import com.app.cloudpet.model.Follow;
import com.app.cloudpet.model._User;

import java.util.List;

import cn.bmob.v3.BmobUser;

public class MessageActivity extends BaseActivity {

    private ActivityMessageBinding activityMessageBinding;
    private MyFollowViewModel myFollowViewModel;

    private MessageListAdapter messageListAdapter;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        activityMessageBinding = ActivityMessageBinding.inflate(LayoutInflater.from(this));
        setContentView(activityMessageBinding.getRoot());
        setCustomActionBar();
        myFollowViewModel = new ViewModelProvider(this).get(MyFollowViewModel.class);


        DividerItemDecoration dec = new DividerItemDecoration(this, DividerItemDecoration.VERTICAL);
        dec.setDrawable(new ColorDrawable(ContextCompat.getColor(this, R.color.divider_line_color)));
        activityMessageBinding.rvMsgList.addItemDecoration(dec);
        messageListAdapter = new MessageListAdapter(null, this);
        activityMessageBinding.rvMsgList.setLayoutManager(new LinearLayoutManager(this));
        activityMessageBinding.rvMsgList.setAdapter(messageListAdapter);

        myFollowViewModel.getFollows().observe(this, new Observer<List<Follow>>() {
            @Override
            public void onChanged(List<Follow> follows) {
                messageListAdapter.setData(follows);
            }
        });

        myFollowViewModel.msgfollows(BmobUser.getCurrentUser(_User.class).getUserId());

    }


    @Override
    public void setCustomActionBar() {
        ActionBarLayoutBinding actionBarViewBinding = ActionBarLayoutBinding.inflate(LayoutInflater.from(this));
        ActionBar.LayoutParams lp = new ActionBar.LayoutParams(ActionBar.LayoutParams.MATCH_PARENT, ActionBar.LayoutParams.MATCH_PARENT, Gravity.CENTER);
        View mActionBarView = actionBarViewBinding.getRoot();
        ActionBar actionBar = getSupportActionBar();
        if (actionBar == null) {
            Log.e(Constants.TAG, "actionBar null set style!");
            return;
        }
        actionBarViewBinding.arrowBack.setVisibility(View.VISIBLE);
        actionBarViewBinding.arrowBack.setOnClickListener(v -> {
            this.finish();
        });
        actionBar.setCustomView(mActionBarView, lp);
        actionBar.setDisplayOptions(ActionBar.DISPLAY_SHOW_CUSTOM);
        actionBar.setDisplayShowCustomEnabled(true);
        actionBar.setDisplayShowHomeEnabled(false);
        actionBar.setDisplayShowTitleEnabled(false);
        actionBarViewBinding.title.setText("消息列表");
    }
}
