package com.app.cloudpet.ui.home;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import com.app.cloudpet.R;
import com.app.cloudpet.databinding.FragmentHomeBinding;
import com.app.cloudpet.databinding.HomeActionBarLayoutBinding;
import com.app.cloudpet.ui.MainActivity;
import com.app.cloudpet.ui.post.PostActivity;

public class HomeFragment extends Fragment {
    private FragmentHomeBinding homeBinding;
    private HomeViewModel homeViewModel;
    private HomeActionBarLayoutBinding homeActionBarLayoutBinding;

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
        MainActivity activity = (MainActivity) getActivity();
        homeActionBarLayoutBinding = activity.setHomeFragmentActionBar();
        homeActionBarLayoutBinding.post.setOnClickListener(v -> {
            //发布
            startActivity(new Intent(getActivity(), PostActivity.class));
        });
        homeActionBarLayoutBinding.recommend.setOnClickListener(v -> {
            //推荐
            homeActionBarLayoutBinding.recommend.setBackgroundResource(R.drawable.home_bar_left_white_btn);
            homeActionBarLayoutBinding.recommend.setTextColor(getResources().getColor(R.color.colorPrimary));
            homeActionBarLayoutBinding.follow.setBackgroundResource(R.drawable.home_bar_right_red_btn);
            homeActionBarLayoutBinding.follow.setTextColor(getResources().getColor(R.color.white));

        });
        homeActionBarLayoutBinding.follow.setOnClickListener(v -> {
            homeActionBarLayoutBinding.follow.setBackgroundResource(R.drawable.home_bar_right_white_btn);
            homeActionBarLayoutBinding.follow.setTextColor(getResources().getColor(R.color.colorPrimary));
            homeActionBarLayoutBinding.recommend.setBackgroundResource(R.drawable.home_bar_left_red_btn);
            homeActionBarLayoutBinding.recommend.setTextColor(getResources().getColor(R.color.white));
        });
        return homeBinding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

    }
}
