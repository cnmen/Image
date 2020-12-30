package com.cmonbaby.imageloader.demo;

import androidx.appcompat.app.AppCompatActivity;

import android.Manifest;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

import com.cmonbaby.images.ImageLoaderBiz;
import com.cmonbaby.images.ImageLoaderConfig;
import com.cmonbaby.images.core.ImageLoader;
import com.cmonbaby.images.core.assist.FailReason;
import com.cmonbaby.images.core.download.ImageDownloader;
import com.cmonbaby.images.core.listener.ImageLoadingListener;
import com.cmonbaby.images.core.listener.ImageLoadingProgressListener;

public class MainActivity extends AppCompatActivity {

    private ImageView iv1;
    private ImageView iv2;
    private ImageView iv3;
    private ImageView iv4;
    private ImageView iv5;
    private ImageLoader imageLoader;

    // 图片来源于网络http
    String httpUri = "http://www.objsp.com/content/templates/ek_auto/style/images/qrcode.png"; // from Web
//    String httpUri = ImageDownloader.Scheme.DRAWABLE.wrap("" + R.drawable.longimage); // from Web

    // 图片来源于Content provider
    String contentprividerUrl = "content://media/external/audio/albumart/13";

    // 图片来源于assets
    String assetsUrl = ImageDownloader.Scheme.ASSETS.wrap("splash.png"); // "assets://image.png";

    // 图片来源于本地资源res
    String drawableUrl = ImageDownloader.Scheme.DRAWABLE.wrap("" + R.drawable.footer_user_select); //"drawable://" + R.drawable.image;
    // "drawable://" + R.drawable.footer_user_select

    // 图片来源于SDCard
    String sdcardUri = "file:///mnt/sdcard/simon.jpg"; // from SD card

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        imageLoader = ImageLoader.getInstance();

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            String[] perms = {Manifest.permission.WRITE_EXTERNAL_STORAGE};
            if (checkSelfPermission(perms[0]) == PackageManager.PERMISSION_DENIED) {
                requestPermissions(perms, 200);
            } else {
                initView();
            }
        }

        // 在GridView，ListView停止滑动的时候加载当前界面的图片(实例，触摸滑动是否暂停，飞滑是否暂停)
        // listView.setOnScrollListener(new PauseOnScrollListener(imageLoaderBiz, pauseOnScroll, pauseOnFling));
        // gridView.setOnScrollListener(new PauseOnScrollListener(imageLoaderBiz, pauseOnScroll, pauseOnFling));
    }

    private void initView() {
        iv1 = findViewById(R.id.iv1);
        iv2 = findViewById(R.id.iv2);
        iv3 = findViewById(R.id.iv3);
        iv4 = findViewById(R.id.iv4);
        iv5 = findViewById(R.id.iv5);

        String s1 = "http://g.hiphotos.baidu.com/image/pic/item/0823dd54564e92589f2fe1019882d158cdbf4ec1.jpg";
        String s2 = "http://f.hiphotos.baidu.com/image/pic/item/4e4a20a4462309f735600bfe760e0cf3d6cad6cb.jpg";
        String s3 = "http://e.hiphotos.baidu.com/image/pic/item/72f082025aafa40f6f6d3209af64034f79f019be.jpg";
        String s4 = "http://e.hiphotos.baidu.com/image/pic/item/8cb1cb134954092359d94e479758d109b3de4952.jpg";
        String s5 = "http://g.hiphotos.baidu.com/image/pic/item/10dfa9ec8a1363270c254f53948fa0ec09fac782.jpg";

        ImageLoaderBiz.with().from(s1).into(iv1).display();
        ImageLoaderBiz.with().from(s2).into(iv2).display();
        ImageLoaderBiz.with().from(s3).into(iv3).display();
        ImageLoaderBiz.with().from(s4).into(iv4).display();
        ImageLoaderBiz.with().from(s5).into(iv5).display();

//        ImageLoaderBiz.with().from(httpUri).into(iv1).display();
//        ImageLoaderBiz.with().from(contentprividerUrl).into(iv2).display();
//        ImageLoaderBiz.with().from(assetsUrl).into(iv3).display();
//        ImageLoaderBiz.with().from(drawableUrl).into(iv4).display();
//        ImageLoaderBiz.with().from(R.drawable.footer_user_select).into(iv4).display();
//        ImageLoaderBiz.with().from(sdcardUri).into(iv5).display();
    }

    public void onClearMemoryClick(View view) {
        Toast.makeText(this, "清除内存缓存成功", Toast.LENGTH_SHORT).show();
        ImageLoader.getInstance().clearMemoryCache();  // 清除内存缓存
    }

    public void onClearDiskClick(View view) {
        Toast.makeText(this, "清除本地缓存成功", Toast.LENGTH_SHORT).show();
        ImageLoader.getInstance().clearDiskCache();  // 清除本地缓存
    }

    public void display() {
        ImageLoaderBiz.with()
                .from(httpUri)
                .into(iv1)
                .resEmpty(R.mipmap.ic_launcher) // 可不填
                .resFail(R.mipmap.ic_launcher) // 可不填
                .options(ImageLoaderConfig.initDisplayOptions(R.mipmap.ic_launcher, R.mipmap.ic_launcher)) // 可不设置
                .listener(new ImageLoadingListener() { // 可不监听
                    @Override
                    public void onLoadingStarted(String imageUri, View view) {
                        Log.e("onLoadingStarted", imageUri);
                    }

                    @Override
                    public void onLoadingFailed(String imageUri, View view, FailReason failReason) {
                        Log.e("onLoadingFailed", imageUri);
                    }

                    @Override
                    public void onLoadingComplete(String imageUri, View view, Bitmap loadedImage) {
                        Log.e("onLoadingComplete", imageUri);
                    }

                    @Override
                    public void onLoadingCancelled(String imageUri, View view) {
                        Log.e("onLoadingCancelled", imageUri);
                    }
                })
                .progressListener(new ImageLoadingProgressListener() {
                    @Override
                    public void onProgressUpdate(String imageUri, View view, int current, int total) {
                        Log.e(imageUri+ "onProgressUpdate ---> ", current + " / " + total);
                    }
                })
                .display();
    }

    public void loadImages() {
        // 图片加载时候带加载情况的监听
        imageLoader.displayImage(httpUri, iv1, ImageLoaderConfig.initDisplayOptions(R.mipmap.ic_launcher
                , R.mipmap.ic_launcher), new ImageLoadingListener() {

            @Override
            public void onLoadingStarted(String imageUri, View view) {
                // 开始加载的时候执行
            }

            @Override
            public void onLoadingFailed(String imageUri, View view, FailReason failReason) {
                // 加载失败的时候执行
            }

            @Override
            public void onLoadingComplete(String imageUri, View view, Bitmap loadedImage) {
                // 加载成功的时候执行
            }

            @Override
            public void onLoadingCancelled(String imageUri, View view) {
                // 加载取消的时候执行
            }

        });

        // 图片加载时候，带监听又带加载进度条的情况
        imageLoader.displayImage(httpUri, iv2, ImageLoaderConfig.initDisplayOptions(R.mipmap.ic_launcher
                , R.mipmap.ic_launcher), new ImageLoadingListener() {

            @Override
            public void onLoadingStarted(String imageUri, View view) {
                // 开始加载的时候执行
            }

            @Override
            public void onLoadingFailed(String imageUri, View view, FailReason failReason) {
                // 加载失败的时候执行
            }

            @Override
            public void onLoadingComplete(String imageUri, View view, Bitmap loadedImage) {
                // 加载成功的时候执行
            }

            @Override
            public void onLoadingCancelled(String imageUri, View view) {
                // 加载取消的时候执行
            }

        }, new ImageLoadingProgressListener() {

            @Override
            public void onProgressUpdate(String imageUri, View view, int current, int total) {
                // 在这里更新 ProgressBar的进度信息
            }
        });
    }
}
