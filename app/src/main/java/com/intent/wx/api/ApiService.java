package com.intent.wx.api;

import android.text.TextUtils;
import com.intent.wx.model.Config;
import com.intent.wx.model.User;
import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;
import okhttp3.ResponseBody;
import retrofit2.Response;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

/**
 * @author intent zzy.main@gmail.com
 * @date 2021/11/6 下午9:22
 * @since 1.0
 */
public class ApiService {

    public static Observable<ResponseBody> getUser(Config config) {
        ApiInterface request = RetrofitHttpService.getRetrofit(ApiConst.BASE_URL).create(ApiInterface.class);
        return request.getUser(config.getCookie(), getZftsl())
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread());
    }

    public static Observable<ResponseBody> getSubDate(Config config, String date) {
        ApiInterface request = RetrofitHttpService.getRetrofit(ApiConst.BASE_URL).create(ApiInterface.class);
        return request.getSubDate(config.getCookie(), getZftsl(),
                config.getPid(), config.getHid(), date)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread());
    }

    public static Observable<ResponseBody> getSubDateAll(Config config) {
        ApiInterface request = RetrofitHttpService.getRetrofit(ApiConst.BASE_URL).create(ApiInterface.class);
        return request.getSubDateAll(config.getCookie(), getZftsl(),
                config.getPid(), config.getHid(), "202111")
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread());
    }

    public static Observable<Response<ResponseBody>> getCaptcha(Config config, String mxid) {
        ApiInterface request = RetrofitHttpService.getRetrofit(ApiConst.BASE_URL).create(ApiInterface.class);
        return request.getCaptcha(config.getCookie(), getZftsl(), mxid)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread());
    }

    public static Observable<ResponseBody> save20(Config config, String mxid, String date) {
        ApiInterface request = RetrofitHttpService.getRetrofit(ApiConst.BASE_URL).create(ApiInterface.class);
        User user = config.getUser();
        return request.save20(config.getCookie(), getZftsl(),
                user.getBirthday(), user.getTel(), user.getSex(), user.getCname(), user.getDoctype(), user.getIdcard(),
                mxid, date, config.getPid())
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread());
    }

    private static String getZftsl() {
//        return DigestUtils.md5Hex("zfsw_" + System.currentTimeMillis() / 10000);
        return md5("zfsw_" + System.currentTimeMillis() / 10000);
    }

    public static String md5(String string) {
        if (TextUtils.isEmpty(string)) {
            return "";
        }
        MessageDigest md5 = null;
        try {
            md5 = MessageDigest.getInstance("MD5");
            byte[] bytes = md5.digest(string.getBytes());
            StringBuilder result = new StringBuilder();
            for (byte b : bytes) {
                String temp = Integer.toHexString(b & 0xff);
                if (temp.length() == 1) {
                    temp = "0" + temp;
                }
                result.append(temp);
            }
            return result.toString();
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
        return "";
    }
}
