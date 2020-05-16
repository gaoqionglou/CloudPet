package com.app.cloudpet.ui.mine;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import com.app.cloudpet.R;
import com.app.cloudpet.bean.PetType;
import com.app.cloudpet.common.Constants;
import com.app.cloudpet.databinding.FragmentMineBinding;
import com.app.cloudpet.databinding.MineActionBarLayoutBinding;
import com.app.cloudpet.ui.follow.MyFollowActivity;
import com.app.cloudpet.model.Pet;
import com.app.cloudpet.model._User;
import com.app.cloudpet.ui.MainActivity;
import com.app.cloudpet.ui.login.LoginActivity;
import com.app.cloudpet.ui.messege.MessageActivity;
import com.app.cloudpet.ui.pet.MyPetActivity;
import com.app.cloudpet.ui.post.CameraHandler;
import com.app.cloudpet.ui.post.CameraImageBean;
import com.app.cloudpet.ui.post.CameraUtil;
import com.app.cloudpet.ui.post.RequestCodes;
import com.app.cloudpet.utils.DialogUtil;
import com.bumptech.glide.Glide;
import com.yalantis.ucrop.UCrop;
import com.yalantis.ucrop.util.FileUtils;

import java.util.List;

import cn.bmob.v3.BmobQuery;
import cn.bmob.v3.BmobUser;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.FindListener;
import cn.bmob.v3.listener.UpdateListener;

import static android.app.Activity.RESULT_OK;
import static com.app.cloudpet.ui.post.RequestCodes.TAKE_PHOTO;
import static com.app.cloudpet.utils.ToastUtil.toast;

public class MineFragment extends Fragment implements View.OnClickListener {

    private FragmentMineBinding mineBinding;
    private MineViewModel mineViewModel;
    private _User loginUser;
    MineActionBarLayoutBinding mineActionBarLayoutBinding;
    private CameraHandler cameraHandler;

    public MineFragment() {
        // Required empty public constructor
    }

    public static MineFragment newInstance(String param1, String param2) {
        MineFragment fragment = new MineFragment();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        mineBinding = FragmentMineBinding.inflate(inflater);


        mineViewModel = new ViewModelProvider(this).get(MineViewModel.class);
        MainActivity activity = (MainActivity) getActivity();
        mineActionBarLayoutBinding = activity.setMineFragmentActionBar();
        mineActionBarLayoutBinding.title.setText("个人中心");
        mineActionBarLayoutBinding.logout.setOnClickListener(v -> {
            BmobUser.logOut();
            startActivity(new Intent(getActivity(), LoginActivity.class));
            getActivity().finish();

        });

        mineActionBarLayoutBinding.message.setOnClickListener(v -> {

            startActivity(new Intent(getActivity(), MessageActivity.class));


        });

        cameraHandler = new CameraHandler(getContext()).withFragment(this);
        return mineBinding.getRoot();
    }


    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        bindView(BmobUser.getCurrentUser(_User.class));
        mineBinding.objId.setOnClickListener(this);
        mineBinding.name.setOnClickListener(this);
        mineBinding.city.setOnClickListener(this);
        mineBinding.gender.setOnClickListener(this);
        mineBinding.hobby.setOnClickListener(this);
        mineBinding.pet.setOnClickListener(this);
        mineBinding.year.setOnClickListener(this);
        mineBinding.pet.setOnClickListener(this);
        mineBinding.avatar.setOnClickListener(this);
        mineBinding.followList.setOnClickListener(this);

    }

    @Override
    public void onResume() {
        super.onResume();
        bindView(BmobUser.getCurrentUser(_User.class));
    }

    private void bindView(_User user) {
//        mineBinding.avatar
        mineBinding.tvCity.setText(user.getCity());
        mineBinding.tvName.setText(user.getUsername());
        mineBinding.tvGender.setText(user.getGender());
        mineBinding.tvHobby.setText(user.getHobby());
        mineBinding.tvLevel.setText("Lv" + user.getLevel());
        mineBinding.tvYear.setText(user.getRaisedPetYear() + "年");
        mineBinding.tvId.setText(user.getObjectId());
        String petType = user.getMyPet();
        PetType type = PetType.valueOf(petType);
        mineBinding.tvPet.setText(user.getMyPetName());
        mineBinding.iconPet.setImageResource(type.iconResId);

    }

    @Override
    public void onClick(View v) {
        int id = v.getId();
        _User user = BmobUser.getCurrentUser(_User.class);
        String title = "提示";
        switch (id) {
            case R.id.level:
            case R.id.objId:
                toast("暂不可修改");
                break;
            case R.id.name:
                title = "修改姓名";
                DialogUtil.alertEditTextDialog(getActivity(), title, inputText -> {
                    user.setUsername(inputText);
                    user.update(new UpdateListener() {
                        @Override
                        public void done(BmobException e) {
                            if (e == null) {
                                toast("修改成功");
                            } else {
                                toast("修改失败");
                            }
                            bindView(BmobUser.getCurrentUser(_User.class));
                        }
                    });
                });
                break;
            case R.id.city:
                title = "修改所在城市";
                DialogUtil.alertEditTextDialog(getActivity(), title, inputText -> {
                    user.setCity(inputText);
                    user.update(new UpdateListener() {
                        @Override
                        public void done(BmobException e) {
                            if (e == null) {
                                toast("修改成功");
                            } else {
                                toast("修改失败");
                            }
                            bindView(BmobUser.getCurrentUser(_User.class));
                        }
                    });
                });
                break;
            case R.id.gender:
                title = "修改性別";
                DialogUtil.alertEditTextDialog(getActivity(), title, inputText -> {
                    user.setGender(inputText);
                    user.update(new UpdateListener() {
                        @Override
                        public void done(BmobException e) {
                            if (e == null) {
                                toast("修改成功");
                            } else {
                                toast("修改失败");
                            }
                            bindView(BmobUser.getCurrentUser(_User.class));
                        }
                    });
                });
                break;
            case R.id.hobby:
                title = "修改兴趣爱好";
                DialogUtil.alertEditTextDialog(getActivity(), title, inputText -> {
                    user.setHobby(inputText);
                    user.update(new UpdateListener() {
                        @Override
                        public void done(BmobException e) {
                            if (e == null) {
                                toast("修改成功");
                            } else {
                                toast("修改失败");
                            }
                            bindView(BmobUser.getCurrentUser(_User.class));
                        }
                    });
                });
                break;
            case R.id.pet:
                String petId = user.getMyPetId();
                BmobQuery<Pet> query = new BmobQuery<Pet>();
                query.addWhereEqualTo("petId", petId);
                // 执行查询方法
                query.findObjects(new FindListener<Pet>() {
                                      @Override
                                      public void done(List<Pet> list, BmobException e) {
                                          if (e == null) {
                                              Intent intent = new Intent(MineFragment.this.getActivity(), MyPetActivity.class);
                                              intent.putExtra("pet", list.get(0));
                                              startActivity(intent);
                                          } else {
                                              toast("找不到你的宠物了");
                                          }
                                      }
                                  }

                );

                break;
            case R.id.year:
                title = "修改养宠年限";
                DialogUtil.alertEditTextDialog(getActivity(), title, inputText -> {
                    user.setRaisedPetYear(inputText);
                    user.update(new UpdateListener() {
                        @Override
                        public void done(BmobException e) {
                            if (e == null) {
                                toast("修改成功");
                            } else {
                                toast("修改失败");
                            }
                            bindView(BmobUser.getCurrentUser(_User.class));
                        }
                    });
                });
                break;
            case R.id.avatar:
                //判断是否有相机权限
                if (!(ContextCompat.checkSelfPermission(getActivity(), Manifest.permission.CAMERA) == PackageManager.PERMISSION_GRANTED)) {
                    toast("请赋予相机权限");
                    return;
                }
                cameraHandler.beginCameraDialog();
                break;
            case R.id.followList:
                startActivity(new Intent(getActivity(), MyFollowActivity.class));
                break;
            default:
                break;
        }

    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK) {
            if (requestCode == TAKE_PHOTO) {
                Uri resultUri = CameraImageBean.getInstance().getPath();
                UCrop.of(resultUri, resultUri).start(getContext(), this);
            } else if (requestCode == RequestCodes.PICK_PHOTO) {
                if (data != null) {
                    Uri pickPath = data.getData();
                    //从相册选择后需要有个路径存放剪裁后的图片
                    String pickCropPath = CameraUtil.createCropFile().getPath();
                    UCrop.of(pickPath, Uri.parse(pickCropPath)).start(getContext(), this);
                }
            } else if (requestCode == RequestCodes.CROP_PHOTO) {
                if (data == null) return;
                Uri cropUri = UCrop.getOutput(data);
                Glide.with(this).load(cropUri).apply(Constants.OPTIONS).into(mineBinding.avatarImage);
                String imagePath = FileUtils.getPath(getActivity(), cropUri);
                _User user = BmobUser.getCurrentUser(_User.class);
                user.setAvatar(imagePath);
                user.update(new UpdateListener() {
                    @Override
                    public void done(BmobException e) {
                        if (e == null) {
                            toast("头像更新完成");
                        } else {
                            toast("头像更新失败");
                            e.printStackTrace();
                        }
                    }
                });

            } else if (requestCode == RequestCodes.CROP_ERROR) {
                toast("剪裁失败");
            }


        }
    }
}
