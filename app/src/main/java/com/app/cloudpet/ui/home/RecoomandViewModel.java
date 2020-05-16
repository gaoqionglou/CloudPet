package com.app.cloudpet.ui.home;

import android.util.Log;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.app.cloudpet.common.Constants;
import com.app.cloudpet.model.Comment;
import com.app.cloudpet.model.Follow;
import com.app.cloudpet.model.Recommand;

import java.util.ArrayList;
import java.util.List;

import cn.bmob.v3.BmobQuery;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.FindListener;

import static com.app.cloudpet.utils.ToastUtil.toast;

public class RecoomandViewModel extends ViewModel {

    private MutableLiveData<List<Recommand>> recommands = new MutableLiveData<>();

    public void checkIfFollow(String userId, String followUserId, QueryFollowListener followListener) {
        BmobQuery<Follow> followBmobQuery = new BmobQuery<>();
        followBmobQuery.addWhereEqualTo("userId", userId);
        followBmobQuery.addWhereEqualTo("followUserId", followUserId);
        followBmobQuery.findObjects(new FindListener<Follow>() {
            @Override
            public void done(List<Follow> list, BmobException e) {
                if (e == null) {
                    if (followListener != null) followListener.onFollowQuerySuccess(list);
                } else {
                    if (followListener != null) followListener.onFollowQueryFail(e);
                }
            }
        });
    }

    public void recommands(String userId) {
        BmobQuery<Follow> followBmobQuery = new BmobQuery<Follow>();
        followBmobQuery.addWhereEqualTo("userId", userId);
        followBmobQuery.findObjects(new FindListener<Follow>() {
            @Override
            public void done(List<Follow> list, BmobException e) {
                if (e == null) {
                    if (list == null || list.isEmpty()) {
                        toast("你还没有关注任何人");
                        recommands.setValue(new ArrayList<>());
                        return;
                    }
                    BmobQuery<Recommand> recommandFinal = new BmobQuery<>();
                    List<BmobQuery<Recommand>> queries = new ArrayList<BmobQuery<Recommand>>();
                    for (Follow follow : list) {
                        BmobQuery<Recommand> eq = new BmobQuery<Recommand>();
                        eq.addWhereEqualTo("userId", follow.getFollowUserId());
                        queries.add(eq);
                    }
                    recommandFinal.or(queries);
                    recommandFinal.findObjects(new FindListener<Recommand>() {
                        @Override
                        public void done(List<Recommand> list, BmobException e) {
                            if (e == null) {
                                Log.i(Constants.TAG, list.toString());
                                recommands.setValue(list);
                            }
                        }
                    });
                } else {

                }
            }
        });

    }

    public void recommands() {
        BmobQuery<Recommand> query = new BmobQuery<>();
        query.findObjects(new FindListener<Recommand>() {
            @Override
            public void done(List<Recommand> list, BmobException e) {
                if (e == null) {
                    recommands.setValue(list);
                }
            }
        });

    }

    public MutableLiveData<List<Recommand>> getRecommands() {
        return recommands;
    }

    public void setRecommands(MutableLiveData<List<Recommand>> recommands) {
        this.recommands = recommands;
    }

    public void comments(Recommand recommand, QueryCommentListener queryCommentListener) {
        BmobQuery<Comment> query = new BmobQuery<>();
        query.addWhereEqualTo("momentId", recommand.getMomentId());
        query.findObjects(new FindListener<Comment>() {
            @Override
            public void done(List<Comment> list, BmobException e) {
                if (e == null) {
                    if (queryCommentListener != null)
                        queryCommentListener.onCommentQuerySuccess(list);
                } else {
                    if (queryCommentListener != null) queryCommentListener.onCommentQueryFail(e);
                }
            }
        });
    }

    interface QueryCommentListener {
        public void onCommentQuerySuccess(List<Comment> list);

        public void onCommentQueryFail(BmobException e);
    }


    interface QueryFollowListener {
        public void onFollowQuerySuccess(List<Follow> list);

        public void onFollowQueryFail(BmobException e);
    }
}
