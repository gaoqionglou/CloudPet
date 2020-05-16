package com.app.cloudpet.ui.post;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;

import androidx.annotation.Nullable;
import androidx.appcompat.app.ActionBar;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import com.app.cloudpet.base.BaseActivity;
import com.app.cloudpet.common.Constants;
import com.app.cloudpet.databinding.ActivityPostBinding;
import com.app.cloudpet.databinding.PostBarLayoutBinding;
import com.app.cloudpet.model.Comment;
import com.app.cloudpet.model.Recommand;
import com.app.cloudpet.model._User;
import com.app.cloudpet.utils.UUIDCreator;
import com.bumptech.glide.Glide;
import com.yalantis.ucrop.UCrop;
import com.yalantis.ucrop.util.FileUtils;

import java.io.File;

import cn.bmob.v3.BmobUser;
import cn.bmob.v3.datatype.BmobFile;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.SaveListener;
import cn.bmob.v3.listener.UpdateListener;
import cn.bmob.v3.listener.UploadFileListener;

import static com.app.cloudpet.ui.post.RequestCodes.TAKE_PHOTO;
import static com.app.cloudpet.utils.ToastUtil.toast;

public class PostActivity extends BaseActivity {
    private ActivityPostBinding activityPostBinding;
    private PostBarLayoutBinding mActionBarViewBinding;
    private CameraHandler cameraHandler;
    private Uri imageUri;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        activityPostBinding = ActivityPostBinding.inflate(LayoutInflater.from(this));
        setContentView(activityPostBinding.getRoot());

        //判断是否有相机权限
        if (!(ContextCompat.checkSelfPermission(this, Manifest.permission.CAMERA) == PackageManager.PERMISSION_GRANTED)) {

            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.CAMERA}, 0);
        }

        cameraHandler = new CameraHandler(this);
        activityPostBinding.add.setVisibility(View.VISIBLE);
        activityPostBinding.imageView.setVisibility(View.GONE);
        activityPostBinding.add.setOnClickListener(view -> {
            //判断是否有相机权限
            if (!(ContextCompat.checkSelfPermission(this, Manifest.permission.CAMERA) == PackageManager.PERMISSION_GRANTED)) {
                toast("请赋予相机权限");
                return;
            }
            cameraHandler.beginCameraDialog();
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
                imageUri = cropUri;
                activityPostBinding.add.setVisibility(View.GONE);
                activityPostBinding.imageView.setVisibility(View.VISIBLE);
                Glide.with(this).load(cropUri).apply(Constants.OPTIONS).into(activityPostBinding.imageView);
            } else if (requestCode == RequestCodes.CROP_ERROR) {
                toast("剪裁失败");
            }


        }
    }


    @Override
    public void setCustomActionBar() {
        mActionBarViewBinding = PostBarLayoutBinding.inflate(LayoutInflater.from(this));
        ActionBar.LayoutParams lp = new ActionBar.LayoutParams(ActionBar.LayoutParams.MATCH_PARENT, ActionBar.LayoutParams.MATCH_PARENT, Gravity.CENTER);
        View mActionBarView = mActionBarViewBinding.getRoot();
        ActionBar actionBar = getSupportActionBar();
        if (actionBar == null) return;
        actionBar.setCustomView(mActionBarView, lp);
        actionBar.setDisplayOptions(ActionBar.DISPLAY_SHOW_CUSTOM);
        actionBar.setDisplayShowCustomEnabled(true);
        actionBar.setDisplayShowHomeEnabled(false);
        actionBar.setDisplayShowTitleEnabled(false);
        mActionBarViewBinding.title.setText("发布");
        mActionBarViewBinding.title.setOnClickListener(v -> {
            //发布操作
            post();
        });
        mActionBarViewBinding.arrowBack.setOnClickListener(v -> {
            this.finish();
        });
        mActionBarViewBinding.arrowBack.setVisibility(View.VISIBLE);
    }

    private void post() {

        String content = activityPostBinding.content.getText().toString();

        Recommand recommand = new Recommand();
        _User user = BmobUser.getCurrentUser(_User.class);


        String path = FileUtils.getPath(this, imageUri);
        File mfile = new File(path);
        recommand.setCommentCount("0");
        recommand.setLikeCount("0");
        recommand.setImage(path);
        recommand.setMomentId(UUIDCreator.uuid());
        recommand.setCity(user.getCity());
        recommand.setPetname(user.getMyPetName());
        recommand.setContent(content);
        recommand.setPettype(user.getMyPet());
        recommand.setUserId(user.getUserId());
        recommand.setUsername(user.getUsername());
        recommand.setAvatar(user.getAvatar());
        recommand.save(new SaveListener<String>() {
            @Override
            public void done(String s, BmobException e) {

                if (e == null) {
                    toast("发布成功");
                    PostActivity.this.finish();
                } else {
                    e.printStackTrace();
                    toast("发布失败");
                }
            }
        });


    }
}
