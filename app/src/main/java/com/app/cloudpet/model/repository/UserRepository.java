package com.app.cloudpet.model.repository;

import androidx.lifecycle.MutableLiveData;

import com.app.cloudpet.model._User;

import cn.bmob.v3.BmobQuery;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.QueryListener;

import static com.app.cloudpet.utils.ToastUtil.toast;

public class UserRepository {

    public void queryUser(String objectId, MutableLiveData<_User> loginUser) {
        BmobQuery<_User> query = new BmobQuery<_User>();

        // 执行查询方法
        query.getObject(objectId, new QueryListener<_User>() {
            @Override
            public void done(_User object, BmobException e) {
                if (e == null) {
                    //获取到信息之后设置到对应的位置并且判断性别设置不同的头像
                    loginUser.setValue(object);
                } else {
                    toast("获取用戶信息失败，请检查网络");
                }
            }
        });
    }

}
