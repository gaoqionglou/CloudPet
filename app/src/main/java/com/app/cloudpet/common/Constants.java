package com.app.cloudpet.common;

import com.app.cloudpet.R;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.request.RequestOptions;

public class Constants {
    public static String TAG = "CloudPet";
    public static String USER_ID = "userId";
    public static RequestOptions OPTIONS = RequestOptions.diskCacheStrategyOf(DiskCacheStrategy.ALL)
            .placeholder(R.mipmap.lost)
            .error(R.mipmap.lost).dontAnimate();
    public static  int REQUEST_CODE_REGISTER=1;
    public static int REQUEST_ALBUM = 2;
}
