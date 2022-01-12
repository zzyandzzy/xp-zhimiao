package com.intent.wx.api;

import io.reactivex.Observable;
import okhttp3.ResponseBody;
import retrofit2.Response;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.Headers;
import retrofit2.http.Query;

/**
 * @author intent zzy.main@gmail.com
 * @date 2021/11/7 下午8:55
 * @since 1.0
 */
public interface ApiInterface {
    @Headers({
            "Host:" + ApiConst.HOST,
            "Connection:" + ApiConst.CONNECTION,
            "charset:" + ApiConst.CHARSET,
            "User-Agent:" + ApiConst.USER_ANENT,
            "content-type:" + ApiConst.CONTENT_TYPE,
            "Accept-Encoding:" + ApiConst.ACCEPT_ENCODING,
            "Referer:" + ApiConst.REFERER,
    })
    @GET(value = ApiConst.GET_USER_URL)
    Observable<ResponseBody> getUser(
            @Header("cookie") String cookie,
            @Header("zftsl") String zftsl
    );

    @Headers({
            "Host:" + ApiConst.HOST,
            "Connection:" + ApiConst.CONNECTION,
            "charset:" + ApiConst.CHARSET,
            "User-Agent:" + ApiConst.USER_ANENT,
            "content-type:" + ApiConst.CONTENT_TYPE,
            "Accept-Encoding:" + ApiConst.ACCEPT_ENCODING,
            "Referer:" + ApiConst.REFERER,
    })
    @GET(value = ApiConst.GET_SUB_DATE_DETAIL_URL)
    Observable<ResponseBody> getSubDate(
            @Header("cookie") String cookie,
            @Header("zftsl") String zftsl,
            @Query("pid") int pid,
            @Query("id") int id,
            @Query("scdate") String date);

    @Headers({
            "Host:" + ApiConst.HOST,
            "Connection:" + ApiConst.CONNECTION,
            "charset:" + ApiConst.CHARSET,
            "User-Agent:" + ApiConst.USER_ANENT,
            "content-type:" + ApiConst.CONTENT_TYPE,
            "Accept-Encoding:" + ApiConst.ACCEPT_ENCODING,
            "Referer:" + ApiConst.REFERER,
    })
    @GET(value = ApiConst.GET_SUB_DATE_ALL_URL)
    Observable<ResponseBody> getSubDateAll(
            @Header("cookie") String cookie,
            @Header("zftsl") String zftsl,
            @Query("pid") int pid,
            @Query("id") int id,
            @Query("month") String month);

    @Headers({
            "Host:" + ApiConst.HOST,
            "Connection:" + ApiConst.CONNECTION,
            "charset:" + ApiConst.CHARSET,
            "User-Agent:" + ApiConst.USER_ANENT,
            "content-type:" + ApiConst.CONTENT_TYPE,
            "Accept-Encoding:" + ApiConst.ACCEPT_ENCODING,
            "Referer:" + ApiConst.REFERER,
    })
    @GET(value = ApiConst.GET_CAPTCHA_URL)
    Observable<Response<ResponseBody>> getCaptcha(
            @Header("cookie") String cookie,
            @Header("zftsl") String zftsl,
            @Query("mxid") String mxid);

    @Headers({
            "Host:" + ApiConst.HOST,
            "Connection:" + ApiConst.CONNECTION,
            "charset:" + ApiConst.CHARSET,
            "User-Agent:" + ApiConst.USER_ANENT,
            "content-type:" + ApiConst.CONTENT_TYPE,
            "Accept-Encoding:" + ApiConst.ACCEPT_ENCODING,
            "Referer:" + ApiConst.REFERER,
    })
    @GET(value = ApiConst.SAVE20_URL)
    Observable<ResponseBody> save20(
            @Header("cookie") String cookie,
            @Header("zftsl") String zftsl,
            @Query("birthday") String birthday,
            @Query("tel") String tel,
            @Query("sex") int sex,
            @Query("cname") String cname,
            @Query("doctype") int doctype,
            @Query("idcard") String idcard,
            @Query("mxid") String mxid,
            @Query("date") String date,
            @Query("pid") int pid);
}
