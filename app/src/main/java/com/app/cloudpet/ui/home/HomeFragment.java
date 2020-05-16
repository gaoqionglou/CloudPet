package com.app.cloudpet.ui.home;

import android.content.Intent;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.app.cloudpet.R;
import com.app.cloudpet.databinding.FragmentHomeBinding;
import com.app.cloudpet.databinding.HomeActionBarLayoutBinding;
import com.app.cloudpet.model.Recommand;
import com.app.cloudpet.model._User;
import com.app.cloudpet.ui.MainActivity;
import com.app.cloudpet.ui.post.PostActivity;

import java.util.List;

import cn.bmob.v3.BmobUser;

public class HomeFragment extends Fragment {
    private FragmentHomeBinding homeBinding;
    private HomeViewModel homeViewModel;
    private HomeActionBarLayoutBinding homeActionBarLayoutBinding;
    private RecoomandViewModel recoomandViewModel;

    private RecommandListAdapter recommandListAdapter;

    public static HomeFragment newInstance(String param1, String param2) {
        HomeFragment fragment = new HomeFragment();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        homeBinding = FragmentHomeBinding.inflate(inflater);


        homeViewModel = new ViewModelProvider(this).get(HomeViewModel.class);
        recoomandViewModel = new ViewModelProvider(this).get(RecoomandViewModel.class);
        MainActivity activity = (MainActivity) getActivity();
        homeActionBarLayoutBinding = activity.setHomeFragmentActionBar();


        DividerItemDecoration dec = new DividerItemDecoration(getActivity(), DividerItemDecoration.VERTICAL);
        dec.setDrawable(new ColorDrawable(ContextCompat.getColor(getActivity(), R.color.divider_line_color)));
        homeBinding.rvRecommand.addItemDecoration(dec);
        recommandListAdapter = new RecommandListAdapter(recoomandViewModel, null, getContext());
        homeBinding.rvRecommand.setLayoutManager(new LinearLayoutManager(getContext()));
        homeBinding.rvRecommand.setAdapter(recommandListAdapter);

        homeActionBarLayoutBinding.post.setOnClickListener(v -> {
            //发布
            startActivity(new Intent(getActivity(), PostActivity.class));
        });

        showRecommand();
        recoomandViewModel.getRecommands().observe(getViewLifecycleOwner(), new Observer<List<Recommand>>() {
            @Override
            public void onChanged(List<Recommand> recommands) {
                recommandListAdapter.setData(recommands);
            }
        });

        homeActionBarLayoutBinding.recommend.setOnClickListener(v -> {
            //推荐
            doRecommand();


        });
        homeActionBarLayoutBinding.follow.setOnClickListener(v -> {
            doFollowList();
        });
        return homeBinding.getRoot();
    }

    private void doFollowList() {
        showFollow();
        recommandListAdapter.setFollowList(true);
        recoomandViewModel.recommands(BmobUser.getCurrentUser(_User.class).getUserId());
    }

    private void doRecommand() {
        showRecommand();
        recommandListAdapter.setFollowList(false);
        recoomandViewModel.recommands();
    }

    private void showFollow() {
        homeActionBarLayoutBinding.follow.setBackgroundResource(R.drawable.home_bar_right_white_btn);
        homeActionBarLayoutBinding.follow.setTextColor(getResources().getColor(R.color.colorPrimary));
        homeActionBarLayoutBinding.recommend.setBackgroundResource(R.drawable.home_bar_left_red_btn);
        homeActionBarLayoutBinding.recommend.setTextColor(getResources().getColor(R.color.white));
    }

    private void showRecommand() {
        homeActionBarLayoutBinding.recommend.setBackgroundResource(R.drawable.home_bar_left_white_btn);
        homeActionBarLayoutBinding.recommend.setTextColor(getResources().getColor(R.color.colorPrimary));
        homeActionBarLayoutBinding.follow.setBackgroundResource(R.drawable.home_bar_right_red_btn);
        homeActionBarLayoutBinding.follow.setTextColor(getResources().getColor(R.color.white));
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        //首页优先展示推荐列表
        doRecommand();


    }

    @Override
    public void onStart() {
        super.onStart();
        doRecommand();

    }
}
