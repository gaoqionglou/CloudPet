package com.app.cloudpet.ui.register;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;

import androidx.annotation.Nullable;

import com.app.cloudpet.R;
import com.app.cloudpet.base.ThemeActivity;
import com.app.cloudpet.common.Constants;
import com.app.cloudpet.databinding.ActiviyRegisterBinding;
import com.app.cloudpet.model.Pet;
import com.app.cloudpet.model._User;
import com.app.cloudpet.ui.login.LoginActivity;
import com.app.cloudpet.utils.UUIDCreator;

import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.SaveListener;

import static com.app.cloudpet.utils.ToastUtil.toast;

public class RegisterActivity extends ThemeActivity {
    private ActiviyRegisterBinding activiyRegisterBinding;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        activiyRegisterBinding = ActiviyRegisterBinding.inflate(LayoutInflater.from(this));
        setTitle("注册");
        setContentView(activiyRegisterBinding.getRoot());
        activiyRegisterBinding.btnRegister.setOnClickListener(v -> {
            String username = activiyRegisterBinding.etName.getText().toString().trim();
            String password = activiyRegisterBinding.etPwd.getText().toString().trim();
            int genderButtonId = activiyRegisterBinding.genderRG.getCheckedRadioButtonId();
            String gender = genderButtonId == R.id.male ? "男" : "女";
            String city = activiyRegisterBinding.etCity.getText().toString().trim();
            String hobby = activiyRegisterBinding.etHobby.getText().toString().trim();
            String petName = activiyRegisterBinding.etPetName.getText().toString().trim();
            String petYear = activiyRegisterBinding.etPetYear.getText().toString().trim();

            if (!TextUtils.isEmpty(username) &&
                    !TextUtils.isEmpty(password) &&
                    !TextUtils.isEmpty(city) &&
                    !TextUtils.isEmpty(hobby) &&
                    !TextUtils.isEmpty(petName) &&
                    !TextUtils.isEmpty(petYear)

            ) {
                toast("请填写完整你的信息再进行注册");
                return;
            }

            String userId = UUIDCreator.uuid();
            String petId = UUIDCreator.uuid();

            int petGenderButtonId = activiyRegisterBinding.petGenderRG.getCheckedRadioButtonId();
            String petGender = "MM";
            if (petGenderButtonId == R.id.mm) {
                petGender = "MM";
            } else if (petGenderButtonId == R.id.gg) {
                petGender = "GG";
            }

            int sterButtonId = activiyRegisterBinding.sterRG.getCheckedRadioButtonId();
            boolean ster = false;
            if (sterButtonId == R.id.sterTrue) {
                ster = true;
            } else if (petGenderButtonId == R.id.sterFalse) {
                ster = false;
            }


            int petTypeButtonId = activiyRegisterBinding.typeRG.getCheckedRadioButtonId();
            String petType = "CAT";
            if (petTypeButtonId == R.id.cat) {
                petType = "CAT";
            } else if (petTypeButtonId == R.id.dog) {
                petType = "DOG";
            } else if (petTypeButtonId == R.id.rabbit) {
                petType = "RABBIT";
            }

            _User user = new _User();
            user.setUsername(username);
            user.setPassword(password);
            user.setCity(city);
            user.setGender(gender);
            user.setHobby(hobby);
            user.setMyPet(petType);
            user.setMyPetId(petId);
            user.setUserId(userId);
            user.setRaisedPetYear(petYear);
            user.signUp(new SaveListener<_User>() {
                @Override
                public void done(_User s, BmobException e) {

                    if (e == null) {
                        toast("注册成功!");
                        Intent localIntent = new Intent(
                                getApplicationContext(), LoginActivity.class);
                        startActivity(localIntent);
                        finish();

                    } else {
                        toast("注册失败:" + s);
                        e.printStackTrace();
                    }
                }
            });


            savePet(petName, userId, petId, petGender, ster);

        });
    }

    private void savePet(String petName, String userId, String petId, String petGender, boolean ster) {
        Pet pet = new Pet();
        pet.setGender(petGender);
        pet.setHostId(userId);
        pet.setMyPetName(petName);
        pet.setSterilization(ster);
        pet.setPetId(petId);
        pet.save(new SaveListener<String>() {
            @Override
            public void done(String s, BmobException e) {
                if (e == null) {
                    Log.i(Constants.TAG, "Pet加入成功");
                    finish();// 关闭这个页面
                } else {
                    Log.i(Constants.TAG, "Pet加入失败");
                    e.printStackTrace();
                }
            }
        });
    }
}
