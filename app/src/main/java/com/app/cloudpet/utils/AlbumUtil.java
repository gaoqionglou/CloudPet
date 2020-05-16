package com.app.cloudpet.utils;

import android.app.Activity;

import androidx.fragment.app.Fragment;

import com.app.cloudpet.R;
import com.app.cloudpet.common.Constants;
import com.yanzhenjie.album.Album;

import java.util.ArrayList;

public class AlbumUtil {

    public static void openAlbum(Activity activity, ArrayList<String> mImageList) {
        Album.album(activity)
                .requestCode(Constants.REQUEST_ALBUM) // 请求码，返回时onActivityResult()的第一个参数。
                .toolBarColor(activity.getResources().getColor(R.color.colorPrimary)) // Toolbar 颜色，默认蓝色。
                .statusBarColor(activity.getResources().getColor(R.color.colorPrimary)) // StatusBar 颜色，默认蓝色。
                .title("图库") // 配置title。
                .selectCount(1) // 最多选择几张图片。
                .columnCount(2) // 相册展示列数，默认是2列。
                .camera(true) // 是否有拍照功能。
                .checkedList(mImageList) // 已经选择过得图片，相册会自动选中选过的图片，并计数。
                .start();
    }


    public static void openAlbum(Fragment fragment, ArrayList<String> mImageList) {
        Album.album(fragment)
                .requestCode(Constants.REQUEST_ALBUM) // 请求码，返回时onActivityResult()的第一个参数。
                .toolBarColor(fragment.getResources().getColor(R.color.colorPrimary)) // Toolbar 颜色，默认蓝色。
                .statusBarColor(fragment.getResources().getColor(R.color.colorPrimary)) // StatusBar 颜色，默认蓝色。
                .title("图库") // 配置title。
                .selectCount(1) // 最多选择几张图片。
                .columnCount(2) // 相册展示列数，默认是2列。
                .camera(true) // 是否有拍照功能。
                .checkedList(mImageList) // 已经选择过得图片，相册会自动选中选过的图片，并计数。
                .start();
    }
}
