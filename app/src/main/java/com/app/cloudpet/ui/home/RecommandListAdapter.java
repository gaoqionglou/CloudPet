package com.app.cloudpet.ui.home;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.app.cloudpet.R;
import com.app.cloudpet.bean.PetType;
import com.app.cloudpet.common.Constants;
import com.app.cloudpet.databinding.RecommandListViewItemBinding;
import com.app.cloudpet.model.Comment;
import com.app.cloudpet.model.Follow;
import com.app.cloudpet.model.Recommand;
import com.app.cloudpet.model._User;
import com.app.cloudpet.utils.DialogUtil;
import com.bumptech.glide.Glide;

import java.util.List;

import cn.bmob.v3.BmobUser;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.SaveListener;
import cn.bmob.v3.listener.UpdateListener;

import static com.app.cloudpet.utils.ToastUtil.toast;

public class RecommandListAdapter extends RecyclerView.Adapter<RecommandListAdapter.ViewHolder> {


    private List<Recommand> mInfoList;
    private Context mContext;
    private RecoomandViewModel recoomandViewModel;

    //是否是查看关注列表
    private boolean isFollowList = false;

    public boolean isFollowList() {
        return isFollowList;
    }

    public void setFollowList(boolean followList) {
        isFollowList = followList;
    }

    public RecommandListAdapter(RecoomandViewModel recoomandViewModel, List<Recommand> mInfoList, Context mContext) {
        this.mInfoList = mInfoList;
        this.mContext = mContext;
        this.recoomandViewModel = recoomandViewModel;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.recommand_list_view_item, parent, false);
        return new ViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Recommand recommand = mInfoList.get(position);
        holder.viewItemBinding.commentCount.setText(recommand.getCommentCount());
        holder.viewItemBinding.likeCount.setText(recommand.getLikeCount());
        holder.viewItemBinding.iconPet.setImageResource(PetType.valueOf(recommand.getPettype()).iconResId);
        holder.viewItemBinding.petname.setText(recommand.getPetname());
        holder.viewItemBinding.username.setText(recommand.getUsername());
        holder.viewItemBinding.content.setText(recommand.getContent());
        holder.viewItemBinding.time.setText(recommand.getCreatedAt());
        if (!TextUtils.isEmpty(recommand.getImage())) {
            holder.viewItemBinding.image.setVisibility(View.VISIBLE);
            Glide.with(mContext).load(recommand.getImage()).apply(Constants.OPTIONS).into(holder.viewItemBinding.image);
        } else {
            holder.viewItemBinding.image.setVisibility(View.GONE);
        }
        if (!TextUtils.isEmpty(recommand.getAvatar())) {
            Glide.with(mContext).load(recommand.getAvatar()).apply(Constants.OPTIONS).into(holder.viewItemBinding.iconAvatar);
        } else {
            holder.viewItemBinding.iconAvatar.setImageResource(R.mipmap.def_icon);
        }

        holder.viewItemBinding.like.setOnClickListener(v -> {
            int count = Integer.parseInt(recommand.getLikeCount());
            recommand.setLikeCount((count + 1) + "");
            recommand.update(new UpdateListener() {
                @Override
                public void done(BmobException e) {
                    if (e == null) {
                        toast("点赞成功");
                        refreshData();
                    } else {
                        e.printStackTrace();
                    }
                }
            });
        });


        recoomandViewModel.comments(recommand, new RecoomandViewModel.QueryCommentListener() {
            @Override
            public void onCommentQuerySuccess(List<Comment> list) {
                setComment(list, holder);
            }

            @Override
            public void onCommentQueryFail(BmobException e) {

            }
        });

        recoomandViewModel.checkIfFollow(BmobUser.getCurrentUser(_User.class).getUserId(), recommand.getUserId(), new RecoomandViewModel.QueryFollowListener() {
            @Override
            public void onFollowQuerySuccess(List<Follow> list) {
                if (list != null && !list.isEmpty()) {
//                    Drawable drawable = mContext.getDrawable(R.drawable.ic_add_black_24dp);
//                    assert drawable != null;
//                    drawable.setBounds(0, 0, drawable.getMinimumWidth(), drawable.getMinimumHeight());
                    holder.viewItemBinding.follow.setCompoundDrawables(null, null, null, null);
                    holder.viewItemBinding.follow.setBackgroundResource(R.drawable.follow_disable);
                    holder.viewItemBinding.follow.setText("取消关注");
                    holder.viewItemBinding.follow.setTag(list.get(0));
                    holder.viewItemBinding.follow.setTextColor(mContext.getResources().getColor(R.color.white));
                } else {
                    Drawable drawable = mContext.getDrawable(R.drawable.ic_add_red_24dp);
                    assert drawable != null;
                    drawable.setBounds(0, 0, drawable.getMinimumWidth(), drawable.getMinimumHeight());
                    holder.viewItemBinding.follow.setCompoundDrawables(drawable, null, null, null);
                    holder.viewItemBinding.follow.setBackgroundResource(R.drawable.follow_enable);
                    holder.viewItemBinding.follow.setText("点击关注");
                    holder.viewItemBinding.follow.setTag(null);
                    holder.viewItemBinding.follow.setTextColor(mContext.getResources().getColor(R.color.colorPrimary));
                }
            }

            @Override
            public void onFollowQueryFail(BmobException e) {

            }
        });

        holder.viewItemBinding.follow.setOnClickListener(v -> {
            Follow follow = new Follow();
            follow.setFollowUserId(recommand.getUserId());
            follow.setFollowUserAvatar(recommand.getAvatar());
            follow.setFollowUsername(recommand.getUsername());
            follow.setUserId(BmobUser.getCurrentUser(_User.class).getUserId());


            if (v.getTag() != null) {
                //取消关注
                follow = (Follow) v.getTag();
                follow.delete(new UpdateListener() {
                    @Override
                    public void done(BmobException e) {
                        if (e == null) {
                            toast("取消关注成功");
                            refreshData();
                        } else {
                            toast("取消关注失败");
                            e.printStackTrace();
                        }
                    }
                });
            } else {
                //点击关注
                follow.save(new SaveListener<String>() {

                    @Override
                    public void done(String s, BmobException e) {
                        if (e == null) {
                            toast("关注成功");
                            refreshData();
                        } else {
                            toast("关注失败");
                            e.printStackTrace();
                        }
                    }
                });
            }


        });


        holder.viewItemBinding.comment.setOnClickListener(v -> {
            doComment(recommand);

        });

        holder.viewItemBinding.iconComment.setOnClickListener(v -> {
            if (recommand.getCommentCount().equals("0")) {
                doComment(recommand);
                return;
            }
            if (holder.viewItemBinding.comment.getVisibility() == View.GONE) {
                holder.viewItemBinding.comment.setVisibility(View.VISIBLE);
            } else {
                holder.viewItemBinding.comment.setVisibility(View.GONE);
            }
        });


    }

    private void doComment(Recommand recommand) {
        comment(recommand, () -> {
            recommand.setCommentCount((Integer.parseInt(recommand.getCommentCount()) + 1) + "");

            recommand.update(new UpdateListener() {
                @Override
                public void done(BmobException e) {
                    if (e == null) {
                        toast("数据更新完成");
                        refreshData();
                    }
                }
            });


        });
    }

    private void setComment(List<Comment> list, @NonNull ViewHolder holder) {
        holder.viewItemBinding.comment.removeAllViews();
        for (Comment comment : list) {
            String name = comment.getCommentname();
            String content = comment.getCommentcontent();
            View view = LayoutInflater.from(mContext).inflate(R.layout.comment_content, null);
            TextView tvname = view.findViewById(R.id.comment_name);
            TextView tvcontent = view.findViewById(R.id.comment_content);
            tvcontent.setText(content);
            tvname.setText(name);
            LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(
                    LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);
            view.setLayoutParams(lp);
            holder.viewItemBinding.comment.addView(view);
        }
    }

    private void comment(Recommand recommand, CommentListener commentListener) {
        DialogUtil.alertEditTextDialog(mContext, "评论", (inputText) -> {
            if (TextUtils.isEmpty(inputText)) {
                toast("评论不可以为空");
                return;
            }
            _User user = BmobUser.getCurrentUser(_User.class);
            Comment comment = new Comment();
            comment.setCommentcontent(inputText);
            comment.setCommentname(user.getUsername());
            comment.setMomentId(recommand.getMomentId());
            comment.save(new SaveListener<String>() {
                @Override
                public void done(String s, BmobException e) {
                    if (e == null) {
                        toast("评论成功");
                        if (commentListener != null) commentListener.onCommentSuccess();
                    } else {
                        e.printStackTrace();
                        toast("评论失败");
                    }
                }

            });
        });
    }

    public void refreshData() {
        if (!isFollowList) {
            recoomandViewModel.recommands();
        } else {
            recoomandViewModel.recommands(BmobUser.getCurrentUser(_User.class).getUserId());
        }
    }

    @Override
    public int getItemCount() {
        return mInfoList == null ? 0 : mInfoList.size();
    }

    public void setData(List<Recommand> infoList) {
        this.mInfoList = infoList;
        notifyDataSetChanged();
    }

    static class ViewHolder extends RecyclerView.ViewHolder {

        RecommandListViewItemBinding viewItemBinding;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            viewItemBinding = RecommandListViewItemBinding.bind(itemView);
        }
    }

    public interface CommentListener {
        public void onCommentSuccess();
    }
}
