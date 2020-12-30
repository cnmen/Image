package com.cmonbaby.imageloader.demo;

import android.app.Application;

import com.cmonbaby.images.ImageLoaderConfig;
import com.cmonbaby.utils.Cons;
import com.cmonbaby.utils.UtilsManager;

public class AppApplication extends Application {

    @Override
    public void onCreate() {
        super.onCreate();

        // 初始化ImageLoader及配置
        ImageLoaderConfig.initImageLoader(this, Cons.IMAGES_DIR, R.mipmap.ic_launcher, R.mipmap.ic_launcher, true);
    }
}
