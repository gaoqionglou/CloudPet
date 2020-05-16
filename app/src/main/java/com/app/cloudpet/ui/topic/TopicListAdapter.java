package com.app.cloudpet.ui.topic;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.app.cloudpet.R;
import com.app.cloudpet.common.Constants;
import com.app.cloudpet.databinding.TopicListViewItemBinding;
import com.app.cloudpet.model.Topic;
import com.app.cloudpet.web.H5Activity;
import com.bumptech.glide.Glide;

import java.util.List;

public class TopicListAdapter extends RecyclerView.Adapter<TopicListAdapter.ViewHolder> {


    private List<Topic> mInfoList;
    private Context mContext;

    public TopicListAdapter(List<Topic> mInfoList, Context mContext) {
        this.mInfoList = mInfoList;
        this.mContext = mContext;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.topic_list_view_item, parent, false);
        return new ViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Topic topic = mInfoList.get(position);

        Glide.with(mContext).load(topic.getImg()).apply(Constants.OPTIONS).into(holder.viewItemBinding.image);
        holder.viewItemBinding.hot.setText("热度:" + topic.getHot());
        holder.viewItemBinding.read.setText("阅读量:" + topic.getRead() + "次");
        holder.viewItemBinding.title.setText("#" + topic.getTitle() + "#");
        holder.viewItemBinding.getRoot().setOnClickListener(v -> {

            int readCount = Integer.parseInt(topic.getRead());
            topic.setRead((readCount + 1) + "");
            topic.update(null);

            Intent intent = new Intent(mContext, H5Activity.class);
            intent.putExtra("url", topic.getUrl());
            intent.putExtra("title", holder.viewItemBinding.title.getText().toString().trim());
            mContext.startActivity(intent);
        });

    }

    @Override
    public int getItemCount() {
        return mInfoList == null ? 0 : mInfoList.size();
    }

    public void setData(List<Topic> infoList) {
        this.mInfoList = infoList;
        notifyDataSetChanged();
    }

    static class ViewHolder extends RecyclerView.ViewHolder {

        TopicListViewItemBinding viewItemBinding;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            viewItemBinding = TopicListViewItemBinding.bind(itemView);
        }
    }
}
