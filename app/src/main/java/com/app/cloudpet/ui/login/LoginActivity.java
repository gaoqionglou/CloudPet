package com.app.cloudpet.ui.login;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.lifecycle.ViewModelProvider;
import androidx.lifecycle.ViewModelProviders;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Toast;

import com.app.cloudpet.R;
import com.app.cloudpet.base.BaseActivity;
import com.app.cloudpet.base.CommonActivity;
import com.app.cloudpet.common.Constants;
import com.app.cloudpet.databinding.ActionBarLayoutBinding;
import com.app.cloudpet.databinding.ActivityLoginBinding;
import com.app.cloudpet.model._User;
import com.app.cloudpet.ui.MainActivity;
import com.app.cloudpet.ui.WelcomeActivity;
import com.app.cloudpet.ui.register.RegisterActivity;
import com.app.cloudpet.utils.ToastUtil;

public class LoginActivity extends CommonActivity {

    private String[] premissions = new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE, Manifest.permission.READ_EXTERNAL_STORAGE};
    private int REQUEST_PERMISSION_EXTERNAL_STORAGE_CODE = 200;

    private ActivityLoginBinding activityLoginBinding;
    private LoginViewModel loginViewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        activityLoginBinding = ActivityLoginBinding.inflate(LayoutInflater.from(this));
        setContentView(activityLoginBinding.getRoot());
        loginViewModel = new ViewModelProvider(this).get(LoginViewModel.class);
        setCustomActionBar();
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            if (ContextCompat.checkSelfPermission(LoginActivity.this, Manifest.permission.WRITE_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED
                    && ContextCompat.checkSelfPermission(LoginActivity.this, Manifest.permission.READ_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED
            ) {
                Log.i(Constants.TAG, "已赋予权限");
            } else {

                ActivityCompat.requestPermissions(LoginActivity.this, premissions, REQUEST_PERMISSION_EXTERNAL_STORAGE_CODE);
            }
        }

        loginViewModel.getLoginUser().observe(this, loginUser -> {
            if (loginUser == null) {
                return;
            }
            Intent intent = new Intent(LoginActivity.this, MainActivity.class);
            startActivity(intent);
            LoginActivity.this.finish();

        });
        activityLoginBinding.btnRegister.setOnClickListener(v -> {
            Intent intent = new Intent(LoginActivity.this, RegisterActivity.class);
            startActivity(intent);
        });
    }


    public void userLogin(View view) {
        String username = activityLoginBinding.loginId.getText().toString().trim();
        String pswd = activityLoginBinding.loginPassword.getText().toString().trim();
        if (TextUtils.isEmpty(username)) {
            ToastUtil.toast("用户名不能为空");
            return;
        }
        if (TextUtils.isEmpty(pswd)) {
            ToastUtil.toast("密码不能为空");
            return;
        }
        loginViewModel.login(username, pswd);

    }


    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == REQUEST_PERMISSION_EXTERNAL_STORAGE_CODE) {
            int count = 0;
            for (int j = 0; j < grantResults.length; j++) {
                if (grantResults[j] == PackageManager.PERMISSION_GRANTED) {
                    count++;
                }
            }
            if (count == premissions.length) {
                Toast.makeText(this, "赋予权限成功", Toast.LENGTH_SHORT).show();

            } else {
                Toast.makeText(this, "请赋予权限", Toast.LENGTH_SHORT).show();
            }
        }


    }

}
