package com.app.cloudpet.ui.pet;

import android.os.Bundle;
import android.view.LayoutInflater;

import androidx.annotation.Nullable;

import com.app.cloudpet.base.ThemeActivity;
import com.app.cloudpet.bean.PetType;
import com.app.cloudpet.databinding.ActivityMyPetBinding;
import com.app.cloudpet.model.Pet;

public class MyPetActivity extends ThemeActivity {

    private ActivityMyPetBinding myPetBinding;

    private Pet pet;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        myPetBinding = ActivityMyPetBinding.inflate(LayoutInflater.from(this));
        setContentView(myPetBinding.getRoot());
        setTitle("我的宠物");
        pet = (Pet) getIntent().getSerializableExtra("pet");
        if (pet != null) {
            myPetBinding.tvGender.setText(pet.getGender());
            myPetBinding.tvName.setText(pet.getMyPetName());
            myPetBinding.tvStatus.setText(pet.isSterilization() ? "已绝育" : "未绝育");
            myPetBinding.tvType.setText(PetType.valueOf(pet.getPetType()).name);
            myPetBinding.iconPet.setImageResource(PetType.valueOf(pet.getPetType()).iconResId);
        }

    }


}
