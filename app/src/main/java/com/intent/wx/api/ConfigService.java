package com.intent.wx.api;

import com.intent.wx.model.ServerConfig;
import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;
import okhttp3.ResponseBody;

/**
 * @author intent zzy.main@gmail.com
 * @date 2021/11/13 上午11:49
 * @since 1.0
 */
public class ConfigService {
    public static Observable<ResponseBody> uploadCookie(ServerConfig config) {
        ConfigInterface request = RetrofitHttpService.createRetrofit(ConfigConst.BASE_URL).create(ConfigInterface.class);
        return request.uploadCookie(config)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread());
    }
}
