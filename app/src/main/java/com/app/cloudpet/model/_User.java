package com.app.cloudpet.model;

import cn.bmob.v3.BmobUser;

public class _User extends BmobUser {
    //用户信息对象表，其中包含的属性，和bomb数据中的表对应
    //头像
    private String avatar;
    //用户ID
    private String userId;
    //手机
    private String phone;
    //性别
    private String gender;
    //姓名
    private String name;
    //所在城市
    private String city;
    //等级
    private String level;
    //我的宠物(类型)
    private String myPet;
    //我的宠物id
    private String myPetId;
    //我的宠物name
    private String myPetName;

    public String getMyPetName() {
        return myPetName;
    }

    public void setMyPetName(String myPetName) {
        this.myPetName = myPetName;
    }

    //收养年限
    private String raisedPetYear;
    //兴趣爱好
    private String hobby;

    public String getAvatar() {
        return avatar;
    }

    public void setAvatar(String avatar) {
        this.avatar = avatar;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getLevel() {
        return level;
    }

    public void setLevel(String level) {
        this.level = level;
    }

    public String getMyPet() {
        return myPet;
    }

    public void setMyPet(String myPet) {
        this.myPet = myPet;
    }

    public String getMyPetId() {
        return myPetId;
    }

    public void setMyPetId(String myPetId) {
        this.myPetId = myPetId;
    }

    public String getRaisedPetYear() {
        return raisedPetYear;
    }

    public void setRaisedPetYear(String raisedPetYear) {
        this.raisedPetYear = raisedPetYear;
    }

    public String getHobby() {
        return hobby;
    }

    public void setHobby(String hobby) {
        this.hobby = hobby;
    }
}