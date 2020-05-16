package com.app.cloudpet.ui.follow;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.app.cloudpet.R;
import com.app.cloudpet.common.Constants;
import com.app.cloudpet.databinding.FollowListItemBinding;
import com.app.cloudpet.model.Follow;
import com.bumptech.glide.Glide;

import java.util.List;

import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.UpdateListener;

import static com.app.cloudpet.utils.ToastUtil.toast;

public class FollowListAdapter extends RecyclerView.Adapter<FollowListAdapter.ViewHolder> {


    private List<Follow> mInfoList;
    private Context mContext;

    public FollowListAdapter(List<Follow> mInfoList, Context mContext) {
        this.mInfoList = mInfoList;
        this.mContext = mContext;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.follow_list_item, parent, false);
        return new ViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Follow follow = mInfoList.get(position);
        Glide.with(mContext).load(follow.getFollowUserAvatar()).apply(Constants.OPTIONS).into(holder.viewItemBinding.followAvatar);
        holder.viewItemBinding.followName.setText(follow.getFollowUsername());
        holder.viewItemBinding.unfollow.setOnClickListener(v -> {
            follow.delete(new UpdateListener() {
                @Override
                public void done(BmobException e) {
                    if (e == null) {
                        toast("取消关注成功");
                    } else {
                        toast("取消关注失败");
                    }
                }
            });
        });
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

        FollowListItemBinding viewItemBinding;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            viewItemBinding = FollowListItemBinding.bind(itemView);
        }
    }
}
