package com.app.cloudpet.model;

import cn.bmob.v3.BmobObject;

public class Pet extends BmobObject {
    private String hostId;
    private String myPetName;
    private String petId;
    private String petType;

    private String gender;
    private boolean sterilization;

    public String getHostId() {
        return hostId;
    }

    public void setHostId(String hostId) {
        this.hostId = hostId;
    }

    public String getMyPetName() {
        return myPetName;
    }

    public void setMyPetName(String myPetName) {
        this.myPetName = myPetName;
    }

    public String getPetId() {
        return petId;
    }

    public void setPetId(String petId) {
        this.petId = petId;
    }

    public String getPetType() {
        return petType;
    }

    public void setPetType(String petType) {
        this.petType = petType;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public boolean isSterilization() {
        return sterilization;
    }

    public void setSterilization(boolean sterilization) {
        this.sterilization = sterilization;
    }
}
