package com.app.cloudpet.ui.mine;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.app.cloudpet.model._User;
import com.app.cloudpet.model.repository.UserRepository;

import cn.bmob.v3.BmobQuery;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.QueryListener;

import static com.app.cloudpet.utils.ToastUtil.toast;

public class MineViewModel extends ViewModel {
    private MutableLiveData<_User> currentUser = new MutableLiveData<>();
    private UserRepository userRepository;

    public MineViewModel() {
        userRepository = new UserRepository();
    }

    public void getCurrentUser(String objectId) {
        BmobQuery<_User> query = new BmobQuery<_User>();

        // 执行查询方法
        query.getObject(objectId, new QueryListener<_User>() {
            @Override
            public void done(_User object, BmobException e) {
                if (e == null) {

                    currentUser.setValue(object);
                } else {
                    toast("获取用戶信息失败，请检查网络");
                }
            }
        });
    }

    public MutableLiveData<_User> currentUser() {
        return currentUser;
    }
}
