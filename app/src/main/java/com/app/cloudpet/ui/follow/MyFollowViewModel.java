package com.app.cloudpet.ui.follow;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.app.cloudpet.model.Follow;

import java.util.List;

import cn.bmob.v3.BmobQuery;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.FindListener;

import static com.app.cloudpet.utils.ToastUtil.toast;

public class MyFollowViewModel extends ViewModel {

    private MutableLiveData<List<Follow>> follows = new MutableLiveData<>();

    public MutableLiveData<List<Follow>> getFollows() {
        return follows;
    }



    public void msgfollows(String followUserId) {
        BmobQuery<Follow> query = new BmobQuery<>();
        query.addWhereEqualTo("followUserId", followUserId);
        query.order("-createdAt");
        query.findObjects(new FindListener<Follow>() {
            @Override
            public void done(List<Follow> list, BmobException e) {
                if (e == null) {
                    toast("获取关注列表成功");
                    follows.setValue(list);
                } else {
                    toast("获取关注列表失败");
                    e.printStackTrace();
                }
            }
        });
    }


    public void follows(String userId) {
        BmobQuery<Follow> query = new BmobQuery<>();
        query.addWhereEqualTo("userId", userId);
        query.order("-createdAt");
        query.findObjects(new FindListener<Follow>() {
            @Override
            public void done(List<Follow> list, BmobException e) {
                if (e == null) {
                    toast("获取关注列表成功");
                    follows.setValue(list);
                } else {
                    toast("获取关注列表失败");
                    e.printStackTrace();
                }
            }
        });
    }



}
