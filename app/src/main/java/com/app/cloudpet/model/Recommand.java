package com.app.cloudpet.model;

import java.util.List;

import cn.bmob.v3.BmobObject;


/**
 * 推荐内容
 */
public class Recommand extends BmobObject {
    private String momentId;
    private String username;
    private String city;
    private String image;
    private String avatar;
    private String petname;
    private String pettype;
    private String content;
    private String userId;
    private String likeCount;
    private String commentCount;


    public String getMomentId() {
        return momentId;
    }

    public void setMomentId(String momentId) {
        this.momentId = momentId;
    }

    public String getLikeCount() {
        return likeCount;
    }

    public void setLikeCount(String likeCount) {
        this.likeCount = likeCount;
    }

    public String getCommentCount() {
        return commentCount;
    }

    public void setCommentCount(String commentCount) {
        this.commentCount = commentCount;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getAvatar() {
        return avatar;
    }

    public void setAvatar(String avatar) {
        this.avatar = avatar;
    }

    public String getPetname() {
        return petname;
    }

    public void setPetname(String petname) {
        this.petname = petname;
    }

    public String getPettype() {
        return pettype;
    }

    public void setPettype(String pettype) {
        this.pettype = pettype;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    @Override
    public String toString() {
        return "Recommand{" +
                "momentId='" + momentId + '\'' +
                ", username='" + username + '\'' +
                ", city='" + city + '\'' +
                ", image='" + image + '\'' +
                ", avatar='" + avatar + '\'' +
                ", petname='" + petname + '\'' +
                ", pettype='" + pettype + '\'' +
                ", content='" + content + '\'' +
                ", userId='" + userId + '\'' +
                ", likeCount='" + likeCount + '\'' +
                ", commentCount='" + commentCount + '\'' +
                '}';
    }
}
