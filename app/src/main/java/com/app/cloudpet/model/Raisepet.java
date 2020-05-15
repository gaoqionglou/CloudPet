package com.app.cloudpet.model;

import cn.bmob.v3.BmobObject;

public class Raisepet extends BmobObject {
    private String desc;
    private String pettype;
    private String petname;
    private String img;

    private String raisePersonPhone;

    public String getRaisePersonPhone() {
        return raisePersonPhone;
    }

    public void setRaisePersonPhone(String raisePersonPhone) {
        this.raisePersonPhone = raisePersonPhone;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

    public String getPettype() {
        return pettype;
    }

    public void setPettype(String pettype) {
        this.pettype = pettype;
    }

    public String getPetname() {
        return petname;
    }

    public void setPetname(String petname) {
        this.petname = petname;
    }

    public String getImg() {
        return img;
    }

    public void setImg(String img) {
        this.img = img;
    }
}
