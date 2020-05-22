package com.app.cloudpet.model;

import cn.bmob.v3.BmobObject;

public class Follow extends BmobObject {
    //用户
    private String userId;
    private String username;
    //这个用户关注的人
    private String followUserId;

    private String followUserAvatar;
    private String followUsername;

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getFollowUsername() {
        return followUsername;
    }

    public void setFollowUsername(String followUsername) {
        this.followUsername = followUsername;
    }

    public String getFollowUserAvatar() {
        return followUserAvatar;
    }

    public void setFollowUserAvatar(String followUserAvatar) {
        this.followUserAvatar = followUserAvatar;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getFollowUserId() {
        return followUserId;
    }

    public void setFollowUserId(String followUserId) {
        this.followUserId = followUserId;
    }
}
