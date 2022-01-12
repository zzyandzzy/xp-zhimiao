package com.intent.wx;

import android.app.Application;
import io.reactivex.plugins.RxJavaPlugins;

/**
 * @author intent zzy.main@gmail.com
 * @date 2021/11/8 上午12:27
 * @since 1.0
 */
public class MyApplication extends Application {
    @Override
    public void onCreate() {
        super.onCreate();
        RxJavaPlugins.setErrorHandler(Throwable::printStackTrace);
    }
}
