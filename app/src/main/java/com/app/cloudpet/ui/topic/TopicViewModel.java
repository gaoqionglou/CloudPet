package com.app.cloudpet.ui.topic;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.app.cloudpet.model.Topic;

import java.util.List;

import cn.bmob.v3.BmobQuery;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.FindListener;

public class TopicViewModel extends ViewModel {

    private MutableLiveData<List<Topic>> topics = new MutableLiveData<List<Topic>>();


    public void topics() {


        BmobQuery<Topic> query = new BmobQuery<Topic>();
        query.findObjects(new FindListener<Topic>() {
            @Override
            public void done(List<Topic> list, BmobException e) {
                if (e == null) {
                    topics.setValue(list);
//                    toast("认养信息加载成功");

                } else {
//                    toast("认养信息加载失败");
                    e.printStackTrace();
                }
            }
        });
    }

    public MutableLiveData<List<Topic>> getTopics() {
        return topics;
    }
}
