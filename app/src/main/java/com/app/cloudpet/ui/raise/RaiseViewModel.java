package com.app.cloudpet.ui.raise;


import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.app.cloudpet.model.Raisepet;

import java.util.List;

import cn.bmob.v3.BmobQuery;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.FindListener;

import static com.app.cloudpet.utils.ToastUtil.toast;

public class RaiseViewModel extends ViewModel {
    private MutableLiveData<List<Raisepet>> raisepets = new MutableLiveData<>();


    public void raisepets() {
        BmobQuery<Raisepet> query = new BmobQuery<>();
        query.findObjects(new FindListener<Raisepet>() {
            @Override
            public void done(List<Raisepet> list, BmobException e) {
                if (e == null) {
                    raisepets.setValue(list);
//                    toast("认养信息加载成功");

                } else {
                    toast("认养信息加载失败");
                    e.printStackTrace();
                }
            }
        });

    }

    public MutableLiveData<List<Raisepet>> getRaisepets() {
        return raisepets;
    }
}
