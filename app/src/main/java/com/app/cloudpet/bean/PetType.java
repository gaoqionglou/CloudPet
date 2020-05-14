package com.app.cloudpet.bean;

import com.app.cloudpet.R;

public enum PetType {

    CAT("猫猫", R.mipmap.icon_cat),
    DOG("狗狗", R.mipmap.icon_dog),
    RAT("仓鼠", R.mipmap.icon_rat),
    RABBIT("兔兔", R.mipmap.icon_rabbit);
    public String name;

    public int iconResId;

    PetType(String name, int iconResId) {
        this.name = name;
        this.iconResId = iconResId;
    }
}
