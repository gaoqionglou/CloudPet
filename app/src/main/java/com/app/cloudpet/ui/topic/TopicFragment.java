package com.app.cloudpet.ui.topic;

import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
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
import com.app.cloudpet.databinding.FragmentTopicBinding;
import com.app.cloudpet.model.Topic;
import com.app.cloudpet.ui.MainActivity;

import java.util.ArrayList;
import java.util.List;

public class TopicFragment extends Fragment {

    private FragmentTopicBinding topicBinding;
    private TopicViewModel topicViewModel;
    private TopicListAdapter mAdapter;

    public TopicFragment() {
        // Required empty public constructor
    }

    public static TopicFragment newInstance(String param1, String param2) {
        TopicFragment fragment = new TopicFragment();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        topicBinding = FragmentTopicBinding.inflate(inflater);
        topicViewModel = new ViewModelProvider(this).get(TopicViewModel.class);
        MainActivity activity = (MainActivity) getActivity();
        activity.setCustomActionBar("社区话题");
        mAdapter = new TopicListAdapter(null, getContext());
        DividerItemDecoration dec = new DividerItemDecoration(getActivity(), DividerItemDecoration.VERTICAL);
        dec.setDrawable(new ColorDrawable(ContextCompat.getColor(getActivity(), R.color.divider_line_color)));
        topicBinding.rvTopic.addItemDecoration(dec);
        topicBinding.rvTopic.setLayoutManager(new LinearLayoutManager(getContext()));
        topicBinding.rvTopic.setAdapter(mAdapter);
        topicViewModel.getTopics().observe(getViewLifecycleOwner(), new Observer<List<Topic>>() {
            @Override
            public void onChanged(List<Topic> infoList) {
                mAdapter.setData(infoList);
            }
        });
        topicBinding.search.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {
                String t = editable.toString();
                if (TextUtils.isEmpty(t)) {
                    topicViewModel.topics();
                    return;
                }
                List<Topic> topics = topicViewModel.getTopics().getValue();
                List<Topic> arrayList = new ArrayList<>();
                for (Topic topic : topics) {
                    if (topic.getTitle().contains(t)) {
                        arrayList.add(topic);
                    }
                }

                topicViewModel.getTopics().setValue(arrayList);
            }
        });
        return topicBinding.getRoot();
    }


    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        topicViewModel.topics();
    }
}
