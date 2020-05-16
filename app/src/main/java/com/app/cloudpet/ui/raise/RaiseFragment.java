package com.app.cloudpet.ui.raise;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.app.cloudpet.databinding.FragmentRaiseBinding;
import com.app.cloudpet.model.Raisepet;
import com.app.cloudpet.ui.MainActivity;

import java.util.List;

public class RaiseFragment extends Fragment {

    private FragmentRaiseBinding raiseBinding;
    private RaiseViewModel raiseViewModel;
    private RaiseListAdapter mAdapter;

    public RaiseFragment() {
        // Required empty public constructor
    }

    public static RaiseFragment newInstance(String param1, String param2) {
        RaiseFragment fragment = new RaiseFragment();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        raiseBinding = FragmentRaiseBinding.inflate(inflater);
        raiseViewModel = new ViewModelProvider(this).get(RaiseViewModel.class);

        MainActivity activity = (MainActivity) getActivity();
        activity.setCustomActionBar("认养公告");

        mAdapter = new RaiseListAdapter(null, getContext());
        raiseBinding.rvRaise.setLayoutManager(new LinearLayoutManager(getContext()));
        raiseBinding.rvRaise.setAdapter(mAdapter);
        raiseViewModel.getRaisepets().observe(getViewLifecycleOwner(), new Observer<List<Raisepet>>() {
            @Override
            public void onChanged(List<Raisepet> infoList) {
                mAdapter.setData(infoList);
            }
        });

        return raiseBinding.getRoot();

    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        raiseViewModel.raisepets();
    }


}
