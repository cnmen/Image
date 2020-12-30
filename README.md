### If you don't know, please click here : [CmonBaby](https://www.cmonbaby.com)

## ImageLoader ![Build Status](https://travis-ci.org/greenrobot/EventBus.svg?branch=master)

## About ImageLoader Code (2016-12-15)

### 1.AndroidManifest.xml add permissions：
```xml
<uses-permission android:name="android.permission.INTERNET" />
<uses-permission android:name="android.permission.WRITE_EXTERNAL_STORAGE" />
<uses-permission android:name="android.permission.READ_EXTERNAL_STORAGE" />
```

### 2.Application init：
```java
@Override
public void onCreate() {
    super.onCreate();
    // @param cacheDisc cache path
    // @param resLoading loading image
    // @param resEmpty empty image
    // @param resFail fail image
    ImageLoaderConfig.initImageLoader(this, Cons.IMAGES_DIR, R.mipmap.ic_launcher, R.mipmap.ic_launcher, R.mipmap.ic_launcher);
}
```

### 3.Coding demo：
```java
public class MainActivity extends AppCompatActivity {

    private ImageView iv1;
    private ImageView iv2;
    private ImageView iv3;
    private ImageView iv4;
    private ImageView iv5;

    // from net
    String httpUri = "http://g.hiphotos.baidu.com/image/pic/item/0823dd54564e92589f2fe1019882d158cdbf4ec1.jpg";

    // from Content Provider
    String contentprividerUrl = "content://media/external/audio/albumart/13";

    // from assets
    String assetsUrl = ImageDownloader.Scheme.ASSETS.wrap("splash.png"); // "assets://image.png";

    // from resources
    String drawableUrl = ImageDownloader.Scheme.DRAWABLE.wrap("" + R.drawable.footer_user_select); //"drawable://" + R.drawable.image;
    // "drawable://" + R.drawable.footer_user_select

    // from SDCard
    String sdcardUri = "file:///mnt/sdcard/simon.jpg"; // from SD card

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initView();
        // display();

        // listView.setOnScrollListener(new PauseOnScrollListener(imageLoaderBiz, pauseOnScroll, pauseOnFling));
        // gridView.setOnScrollListener(new PauseOnScrollListener(imageLoaderBiz, pauseOnScroll, pauseOnFling));
    }

    private void initView() {
        iv1 = findViewById(R.id.iv1);
        iv2 = findViewById(R.id.iv2);
        iv3 = findViewById(R.id.iv3);
        iv4 = findViewById(R.id.iv4);
        iv5 = findViewById(R.id.iv5);

        ImageLoaderBiz.with().from(httpUri).into(iv1).display();
        ImageLoaderBiz.with().from(contentprividerUrl).into(iv2).display();
        ImageLoaderBiz.with().from(assetsUrl).into(iv3).display();
        ImageLoaderBiz.with().from(drawableUrl).into(iv4).display();
        ImageLoaderBiz.with().from(R.drawable.footer_user_select).into(iv4).display();
        ImageLoaderBiz.with().from(sdcardUri).into(iv5).display();
    }

    public void clearMemory() {
        ImageLoader.getInstance().clearMemoryCache();
    }

    public void clearDisk() {
        ImageLoader.getInstance().clearDiskCache();
    }

    public void display() {
        ImageLoaderBiz.with()
                .from(httpUri)
                .into(iv1)
                .resLoading(R.mipmap.ic_launcher)
                .resEmpty(R.mipmap.ic_launcher)
                .resFail(R.mipmap.ic_launcher)
                .options(ImageLoaderConfig.initDisplayOptions(R.mipmap.ic_launcher
                        , R.mipmap.ic_launcher, R.mipmap.ic_launcher))
                .listener(new ImageLoadingListener() {
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
}
```

Via Gradle:
```gradle
implementation 'com.cmonbaby:imageloader:1.0.0'
```

Via Maven:
```xml
<dependency>
    <groupId>com.cmonbaby</groupId>
    <artifactId>imageloader</artifactId>
    <version>1.0.0</version>
</dependency>
```

## License

Copyright (C) 2013-2020 Markus Junginger, Simon (https://www.cmonbaby.com)
ImageLoader binaries and source code can be used according to the [Apache License, Version 2.0](LICENSE).