package com.app.cloudpet.ui.raise;

import android.content.Context;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.app.cloudpet.R;
import com.app.cloudpet.bean.PetType;
import com.app.cloudpet.common.Constants;
import com.app.cloudpet.databinding.RaiseListViewItemBinding;
import com.app.cloudpet.model.Raisepet;
import com.app.cloudpet.model._User;
import com.bumptech.glide.Glide;

import java.util.List;

import cn.bmob.v3.BmobUser;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.UpdateListener;

import static com.app.cloudpet.utils.ToastUtil.toast;

public class RaiseListAdapter extends RecyclerView.Adapter<RaiseListAdapter.ViewHolder> {


    private List<Raisepet> mInfoList;
    private Context mContext;

    public RaiseListAdapter(List<Raisepet> mInfoList, Context mContext) {
        this.mInfoList = mInfoList;
        this.mContext = mContext;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.raise_list_view_item, parent, false);
        return new ViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Raisepet raisepet = mInfoList.get(position);
        holder.viewItemBinding.desc.setText(raisepet.getDesc());
        holder.viewItemBinding.icon.setImageResource(PetType.valueOf(raisepet.getPettype()).iconResId);
        Glide.with(mContext).load(raisepet.getImg()).apply(Constants.OPTIONS).into(holder.viewItemBinding.imageView);
        holder.viewItemBinding.raise.setEnabled(TextUtils.isEmpty(raisepet.getRaisePersonPhone()));
        if (TextUtils.isEmpty(raisepet.getRaisePersonPhone())) {
            holder.viewItemBinding.raise.setText("认养");
        } else {
            holder.viewItemBinding.raise.setText("已认养");
        }
        holder.viewItemBinding.petname.setText(raisepet.getPetname());
        holder.viewItemBinding.raise.setOnClickListener(v -> {
            _User user = BmobUser.getCurrentUser(_User.class);
            raisepet.setRaisePersonPhone(user.getPhone());
            raisepet.update(new UpdateListener() {
                @Override
                public void done(BmobException e) {
                    if (e == null) {
                        toast("认养成功，7个工作日之后会工作人员联系你的手机" + user.getPhone());
                    } else {
                        toast("认养失败");
                    }
                }
            });
        });
    }

    @Override
    public int getItemCount() {
        return mInfoList == null ? 0 : mInfoList.size();
    }

    public void setData(List<Raisepet> infoList) {
        this.mInfoList = infoList;
        notifyDataSetChanged();
    }

    static class ViewHolder extends RecyclerView.ViewHolder {

        RaiseListViewItemBinding viewItemBinding;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            viewItemBinding = RaiseListViewItemBinding.bind(itemView);
        }
    }
}
