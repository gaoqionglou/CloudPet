package com.app.cloudpet.ui.mine;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import com.app.cloudpet.R;
import com.app.cloudpet.bean.PetType;
import com.app.cloudpet.databinding.FragmentMineBinding;
import com.app.cloudpet.model._User;
import com.app.cloudpet.utils.DialogUtil;

import cn.bmob.v3.BmobUser;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.UpdateListener;

import static com.app.cloudpet.utils.ToastUtil.toast;

public class MineFragment extends Fragment implements View.OnClickListener {

    private FragmentMineBinding mineBinding;
    private MineViewModel mineViewModel;
    private _User loginUser;

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
        mineBinding.tvPet.setText(type.name);
        mineBinding.iconPet.setImageResource(type.iconResId);


    }

    @Override
    public void onClick(View v) {
        int id = v.getId();
        _User user = BmobUser.getCurrentUser(_User.class);
        String title = "提示";
        switch (id) {
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
                        }
                    });
                });
                break;
            case R.id.pet:
                title = "修改养宠";

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
                        }
                    });
                });
                break;
            default:
                break;
        }

    }
}
