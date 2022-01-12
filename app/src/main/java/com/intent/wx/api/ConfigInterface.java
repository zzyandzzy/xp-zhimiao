package com.intent.wx.api;

import com.intent.wx.model.ServerConfig;
import io.reactivex.Observable;
import okhttp3.ResponseBody;
import retrofit2.http.Body;
import retrofit2.http.POST;

/**
 * @author intent zzy.main@gmail.com
 * @date 2021/11/7 下午8:55
 * @since 1.0
 */
public interface ConfigInterface {
    @POST(value = ConfigConst.UPLOAD_COOKIE_URL)
    Observable<ResponseBody> uploadCookie(@Body ServerConfig config);
}
