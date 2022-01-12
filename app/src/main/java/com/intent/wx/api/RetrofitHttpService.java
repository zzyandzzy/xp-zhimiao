package com.intent.wx.api;

import com.intent.wx.util.OkHttpClientUtils;
import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * @author intent zzy.main@gmail.com
 * @date 2021/11/7 下午8:47
 * @since 1.0
 */
public class RetrofitHttpService {
    private static final long TIMEOUT = 10;

    private static RetrofitHttpService retrofitHttpService;

    private static OkHttpClient client = OkHttpClientUtils.getInstance();

    private static Retrofit retrofit = null;

    public static RetrofitHttpService instance(String baseUrl) {
        if (retrofitHttpService == null) {
            synchronized (RetrofitHttpService.class) {
                if (retrofitHttpService == null) {
                    retrofitHttpService = new RetrofitHttpService();
                    retrofit = createRetrofit(baseUrl);
                }
            }
        }
        return retrofitHttpService;
    }

    public static Retrofit createRetrofit(String baseUrl) {
        return new Retrofit.Builder()
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .baseUrl(baseUrl)
                .client(client)
                .build();
    }

    public static Retrofit getRetrofit(String baseUrl) {
        if (retrofit == null) {
            RetrofitHttpService.instance(baseUrl);
        }
        return retrofit;
    }
}
