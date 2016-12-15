# Image
##图片加载框架
版本：2.3.5<br>
作者：西门提督<br>
日期：2016-12-15

##Image图片加载框架用法如下：

###1.AndroidManifest.xml添加权限：

// 访问网络权限<br>
uses-permission android:name="android.permission.INTERNET"<br>
// 往sdcard中写入数据的权限<br>
uses-permission android:name="android.permission.WRITE_EXTERNAL_STORAGE"<br>
// 在sdcard中创建/删除文件的权限<br>
uses-permission android:name="android.permission.MOUNT_UNMOUNT_FILESYSTEMS"<br>
// 读取sdcard权限<br>
uses-permission android:name="android.permission.READ_EXTERNAL_STORAGE"

###2.初始化Application操作：
    @Override
        public void onCreate() {
            super.onCreate();
            // @param cacheDisc 设置缓存图片文件的目录
            // @param resLoading 设置图片下载期间显示的图片
            // @param resEmpty 设置图片Uri为空或是错误的时候显示的图片
            // @param resFail 设置图片加载或解码过程中发生错误显示的图片
            ImageLoaderConfig.initImageLoader(this, Cons.IMAGES_DIR, R.mipmap.ic_launcher, R.mipmap.ic_launcher, R.mipmap.ic_launcher);
    }

###3.图片加载用法：
        public class MainActivity extends AppCompatActivity {

            private ImageView iv1;
            private ImageView iv2;
            private ImageView iv3;
            private ImageView iv4;
            private ImageView iv5;

            // 图片来源于网络http
            String httpUri = "http://g.hiphotos.baidu.com/image/pic/item/0823dd54564e92589f2fe1019882d158cdbf4ec1.jpg";

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
                initView();
                // display();

                // 在GridView，ListView停止滑动的时候加载当前界面的图片(实例，触摸滑动是否暂停，飞滑是否暂停)
                // listView.setOnScrollListener(new PauseOnScrollListener(imageLoaderBiz, pauseOnScroll, pauseOnFling));
                // gridView.setOnScrollListener(new PauseOnScrollListener(imageLoaderBiz, pauseOnScroll, pauseOnFling));
            }

            private void initView() {
                iv1 = (ImageView) findViewById(R.id.iv1);
                iv2 = (ImageView) findViewById(R.id.iv2);
                iv3 = (ImageView) findViewById(R.id.iv3);
                iv4 = (ImageView) findViewById(R.id.iv4);
                iv5 = (ImageView) findViewById(R.id.iv5);

                ImageLoaderBiz.with().from(httpUri).into(iv1).display();
                ImageLoaderBiz.with().from(contentprividerUrl).into(iv2).display();
                ImageLoaderBiz.with().from(assetsUrl).into(iv3).display();
                ImageLoaderBiz.with().from(drawableUrl).into(iv4).display();
                ImageLoaderBiz.with().from(R.drawable.footer_user_select).into(iv4).display();
                ImageLoaderBiz.with().from(sdcardUri).into(iv5).display();
            }

            public void clearMemory() { // 清除内存缓存
                ImageLoader.getInstance().clearMemoryCache();
            }

            public void clearDisk() { // 清除本地缓存
                ImageLoader.getInstance().clearDiskCache();
            }

            public void display() {
                ImageLoaderBiz.with()
                        .from(httpUri) // 图片路径
                        .into(iv1) // 显示控件
                        .resLoading(R.mipmap.ic_launcher) // 可不填
                        .resEmpty(R.mipmap.ic_launcher) // 可不填
                        .resFail(R.mipmap.ic_launcher) // 可不填
                        .options(ImageLoaderConfig.initDisplayOptions(R.mipmap.ic_launcher
                                , R.mipmap.ic_launcher, R.mipmap.ic_launcher)) // 可不设置
                        .listener(new ImageLoadingListener() { // 可不监听
                            @Override
                            public void onLoadingStarted(String imageUri, View view) {
                                LogUtils.e("onLoadingStarted");
                            }

                            @Override
                            public void onLoadingFailed(String imageUri, View view, FailReason failReason) {
                                LogUtils.e("onLoadingFailed");
                            }

                            @Override
                            public void onLoadingComplete(String imageUri, View view, Bitmap loadedImage) {
                                LogUtils.e("onLoadingComplete");
                            }

                            @Override
                            public void onLoadingCancelled(String imageUri, View view) {
                                LogUtils.e("onLoadingCancelled");
                            }
                        })
                        .progressListener(new ImageLoadingProgressListener() {
                            @Override
                            public void onProgressUpdate(String imageUri, View view, int current, int total) {
                                LogUtils.e(imageUri + " --- " + current + " / " + total);
                            }
                        })
                        .display();
            }
        }