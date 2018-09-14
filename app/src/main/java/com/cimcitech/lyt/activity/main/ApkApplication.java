package com.cimcitech.lyt.activity.main;

import android.app.Application;
import android.app.Service;
import android.os.Vibrator;
import android.view.inputmethod.InputMethodManager;

import com.cimcitech.lyt.utils.Config;

/**
 * Created by cimcitech on 2017/7/31.
 */

public class ApkApplication extends Application {

    //public LocationService locationService;
    public Vibrator mVibrator;
    //public static ImageLoader imageLoader = ImageLoader.getInstance();
    public static InputMethodManager imm;

    @Override
    public void onCreate() {
        super.onCreate();
        Config.context = getApplicationContext();

        imm = (InputMethodManager) getSystemService(INPUT_METHOD_SERVICE);
        // 图片缓存配置
//        ImageLoaderConfiguration config = new ImageLoaderConfiguration.Builder(getApplicationContext()).threadPriority(Thread.NORM_PRIORITY - 2)
//                .denyCacheImageMultipleSizesInMemory().discCacheFileNameGenerator(new Md5FileNameGenerator()).discCacheFileCount(100)
//                .tasksProcessingOrder(QueueProcessingType.LIFO).enableLogging().build();
//        imageLoader.init(config);
        /***
         * 初始化定位sdk，建议在Application中创建
         */
//        locationService = new LocationService(getApplicationContext());
//        mVibrator = (Vibrator) getApplicationContext().getSystemService(Service.VIBRATOR_SERVICE);
//        SDKInitializer.initialize(getApplicationContext());
    }
}
