package com.app.cloudpet.ui.messege;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.app.cloudpet.R;
import com.app.cloudpet.common.Constants;
import com.app.cloudpet.databinding.FollowListItemBinding;
import com.app.cloudpet.databinding.MessageListItemBinding;
import com.app.cloudpet.model.Follow;
import com.bumptech.glide.Glide;

import java.util.List;

import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.UpdateListener;

import static com.app.cloudpet.utils.ToastUtil.toast;

public class MessageListAdapter extends RecyclerView.Adapter<MessageListAdapter.ViewHolder> {


    private List<Follow> mInfoList;
    private Context mContext;

    public MessageListAdapter(List<Follow> mInfoList, Context mContext) {
        this.mInfoList = mInfoList;
        this.mContext = mContext;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.message_list_item, parent, false);
        return new ViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Follow follow = mInfoList.get(position);
        String text = "用户" + follow.getUsername() + "在" + follow.getCreatedAt() + "关注了你";
        holder.viewItemBinding.message.setText(text);

    }


    @Override
    public int getItemCount() {
        return mInfoList == null ? 0 : mInfoList.size();
    }

    public void setData(List<Follow> infoList) {
        this.mInfoList = infoList;
        notifyDataSetChanged();
    }

    static class ViewHolder extends RecyclerView.ViewHolder {

        MessageListItemBinding viewItemBinding;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            viewItemBinding = MessageListItemBinding.bind(itemView);
        }
    }
}
