package com.example.yaodaojia.yaodaojia;

import android.app.Application;
import android.content.Context;
import android.util.SparseArray;

import com.example.yaodaojia.yaodaojia.base.BaseActivity;
import com.example.yaodaojia.yaodaojia.base.BaseFragment;
import com.nostra13.universalimageloader.cache.disc.naming.Md5FileNameGenerator;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;
import com.nostra13.universalimageloader.core.assist.QueueProcessingType;
import com.umeng.socialize.Config;
import com.umeng.socialize.PlatformConfig;

import cn.jpush.android.api.JPushInterface;

/**
 * Created by axi on 2017/8/8.
 */

public class App extends Application {
    public static BaseActivity activity;
    public static SparseArray<BaseFragment> lastSparse;
    public static Context applicationContext;
    private static App instance;
    {
        PlatformConfig.setWeixin("wx4ec14e4b93bb4782","1198907a1ec849fb011278c5d88c9782");
    }
    @Override
    public void onCreate() {
        super.onCreate();
        Config.DEBUG = true;
        initImageLoader(getApplicationContext());
        JPushInterface.setDebugMode(true);
        JPushInterface.init(this);
    }

    public static void initImageLoader(Context context) {

        ImageLoaderConfiguration.Builder config = new ImageLoaderConfiguration.Builder(context);
        config.threadPriority(Thread.NORM_PRIORITY - 2);
        config.denyCacheImageMultipleSizesInMemory();
        config.diskCacheFileNameGenerator(new Md5FileNameGenerator());
        config.diskCacheSize(50 * 1024 * 1024); // 50 MiB
        config.tasksProcessingOrder(QueueProcessingType.LIFO);
        config.writeDebugLogs(); // Remove for release app
        ImageLoader.getInstance().init(config.build());
    }
    public static App getInstance() {
        return instance;
    }


}
