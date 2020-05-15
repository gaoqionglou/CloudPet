package com.app.cloudpet.ui.post;

import android.net.Uri;

import com.app.cloudpet.utils.JFileUtil;

public class CameraUtil {
    public static Uri createCropFile() {
        return Uri.parse(
                JFileUtil.createFile(
                        "crop_image",
                        JFileUtil.getFileNameByTime("IMG", "jpg")
                ).getPath()
        );
    }
}