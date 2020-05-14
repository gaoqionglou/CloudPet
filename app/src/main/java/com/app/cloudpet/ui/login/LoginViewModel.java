package com.app.cloudpet.ui.login;

import android.util.Log;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.app.cloudpet.MyApplication;
import com.app.cloudpet.common.Constants;
import com.app.cloudpet.model._User;
import com.app.cloudpet.model.repository.UserRepository;
import com.app.cloudpet.utils.SharedPreferencesUtils;

import cn.bmob.v3.BmobQuery;
import cn.bmob.v3.BmobUser;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.QueryListener;
import cn.bmob.v3.listener.SaveListener;

import static com.app.cloudpet.utils.ToastUtil.toast;

public class LoginViewModel extends ViewModel {
    private MutableLiveData<_User> loginUser = new MutableLiveData<>();

    public MutableLiveData<_User> getLoginUser() {
        return loginUser;
    }

    private UserRepository userRepository;

    public LoginViewModel() {
        Log.i(Constants.TAG, "LoginViewModel init");
        userRepository = new UserRepository();
    }


    public void login(String id, String pwd) {
        final _User localUser = new _User();
        localUser.setUsername(id);
        localUser.setPassword(pwd);
        localUser.login(new SaveListener<BmobUser>() {

            @Override
            public void done(BmobUser s, BmobException e) {
                if (e == null) {
//                    //保存登录验证（用于修改个人信息）并且跳转主界面
                    SharedPreferencesUtils.saveString(MyApplication.getMyApplication().getApplicationContext(), Constants.USER_ID, localUser.getObjectId());
                    String oid = s.getObjectId();
                    Log.i(Constants.TAG, oid + "登录成功");
                    userRepository.queryUser(oid, loginUser);
                } else {
                    toast("账号或者密码错误");//提示
                    e.printStackTrace();
                }
            }
        });
    }


}
