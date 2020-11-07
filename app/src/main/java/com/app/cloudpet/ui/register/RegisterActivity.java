package com.app.cloudpet.ui.register;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;

import androidx.annotation.Nullable;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import com.app.cloudpet.R;
import com.app.cloudpet.base.ThemeActivity;
import com.app.cloudpet.common.Constants;
import com.app.cloudpet.databinding.ActiviyRegisterBinding;
import com.app.cloudpet.model.Pet;
import com.app.cloudpet.model._User;
import com.app.cloudpet.ui.login.LoginActivity;
import com.app.cloudpet.ui.post.CameraHandler;
import com.app.cloudpet.ui.post.CameraImageBean;
import com.app.cloudpet.ui.post.CameraUtil;
import com.app.cloudpet.ui.post.RequestCodes;
import com.app.cloudpet.utils.UUIDCreator;
import com.bumptech.glide.Glide;
import com.yalantis.ucrop.UCrop;
import com.yalantis.ucrop.util.FileUtils;

import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.SaveListener;

import static com.app.cloudpet.ui.post.RequestCodes.TAKE_PHOTO;
import static com.app.cloudpet.utils.ToastUtil.toast;

public class RegisterActivity extends ThemeActivity {
    private ActiviyRegisterBinding activiyRegisterBinding;
    private CameraHandler cameraHandler;
    private String avatarImagePath = "";

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        activiyRegisterBinding = ActiviyRegisterBinding.inflate(LayoutInflater.from(this));
        setTitle("注册");
        setContentView(activiyRegisterBinding.getRoot());
        //判断是否有相机权限
        if (!(ContextCompat.checkSelfPermission(this, Manifest.permission.CAMERA) == PackageManager.PERMISSION_GRANTED)) {

            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.CAMERA}, 0);
        }
        cameraHandler = new CameraHandler(this);
        activiyRegisterBinding.avatar.setOnClickListener(v -> {
            //判断是否有相机权限
            if (!(ContextCompat.checkSelfPermission(this, Manifest.permission.CAMERA) == PackageManager.PERMISSION_GRANTED)) {
                toast("请赋予相机权限");
                return;
            }
            cameraHandler.beginCameraDialog();
        });
        activiyRegisterBinding.btnRegister.setOnClickListener(v -> {
            String username = activiyRegisterBinding.etName.getText().toString().trim();
            String password = activiyRegisterBinding.etPwd.getText().toString().trim();
            int genderButtonId = activiyRegisterBinding.genderRG.getCheckedRadioButtonId();
            String gender = genderButtonId == R.id.male ? "男" : "女";
            String city = activiyRegisterBinding.etCity.getText().toString().trim();
            String hobby = activiyRegisterBinding.etHobby.getText().toString().trim();
            String petName = activiyRegisterBinding.etPetName.getText().toString().trim();
            String petYear = activiyRegisterBinding.etPetYear.getText().toString().trim();

            if (TextUtils.isEmpty(username) ||
                    TextUtils.isEmpty(password) ||
                    TextUtils.isEmpty(city) ||
                    TextUtils.isEmpty(hobby) ||
                    TextUtils.isEmpty(petName) ||
                    TextUtils.isEmpty(petYear)
            ) {
                toast("请填写完整你的信息再进行注册");
                return;
            }

            String userId = UUIDCreator.uuid();
            String petId = UUIDCreator.uuid();

            String phone = activiyRegisterBinding.etPhone.getText().toString();
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
            user.setMyPetName(petName);
            user.setUserId(userId);
            user.setRaisedPetYear(petYear);
            user.setLevel("1");
            user.setPhone(phone);
            user.setAvatar(avatarImagePath);
            user.signUp(new SaveListener<_User>() {
                @Override
                public void done(_User s, BmobException e) {

                    if (e == null) {
                        toast("注册成功!");
                        Intent localIntent = new Intent();
                        localIntent.putExtra("username", user.getUsername());
                        localIntent.putExtra("password", password);
                        RegisterActivity.this.setResult(RESULT_OK, localIntent);
                        finish();

                    } else {
                        toast("注册失败:" + s);
                        e.printStackTrace();
                    }
                }
            });


            savePet(petName, userId, petId, petGender, petType, ster);

        });
    }

    private void savePet(String petName, String userId, String petId, String petGender, String petType, boolean ster) {
        Pet pet = new Pet();
        pet.setGender(petGender);
        pet.setHostId(userId);
        pet.setMyPetName(petName);
        pet.setSterilization(ster);
        pet.setPetId(petId);
        pet.setPetType(petType);
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

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK) {
            if (requestCode == TAKE_PHOTO) {
                Uri resultUri = CameraImageBean.getInstance().getPath();
                UCrop.of(resultUri, resultUri).start(this);
            } else if (requestCode == RequestCodes.PICK_PHOTO) {
                if (data != null) {
                    Uri pickPath = data.getData();
                    //从相册选择后需要有个路径存放剪裁后的图片
                    String pickCropPath = CameraUtil.createCropFile().getPath();
                    UCrop.of(pickPath, Uri.parse(pickCropPath)).start(this);
                }
            } else if (requestCode == RequestCodes.CROP_PHOTO) {
                if (data == null) return;
                Uri cropUri = UCrop.getOutput(data);
                avatarImagePath = FileUtils.getPath(this, cropUri);
                Glide.with(this).load(cropUri).apply(Constants.OPTIONS).into(activiyRegisterBinding.avatarImage);
            } else if (requestCode == RequestCodes.CROP_ERROR) {
                toast("剪裁失败");
            }


        }
    }
}
