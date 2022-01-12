package com.intent.wx.util;

import com.intent.wx.constant.Const;
import okhttp3.ConnectionPool;
import okhttp3.OkHttpClient;
import okhttp3.Protocol;
import okhttp3.internal.Util;

import java.util.concurrent.TimeUnit;

/**
 * @author intent <a>zzy.main@gmail.com</a>
 * @date 2021/5/13 3:28 下午
 * @since 1.0
 */
public class OkHttpClientUtils {
    private static volatile OkHttpClient okHttpClient;

    private OkHttpClientUtils() {
    }

    public static OkHttpClient getInstance() {
        if (okHttpClient == null) {
            synchronized (OkHttpClient.class) {
                if (okHttpClient == null) {
                    okHttpClient = new OkHttpClient.Builder()
                            .connectionPool(new ConnectionPool(
                                    Const.DEFAULT_MAX_IDLE_CONNECTIONS, Const.KEEP_ALIVE_DURATION_MILLS, TimeUnit.SECONDS))
                            //连接超时
                            .connectTimeout(Const.DEFAULT_HTTP_CONNECT_TIMEOUT, TimeUnit.SECONDS)
                            //读取超时
                            .readTimeout(Const.DEFAULT_HTTP_READ_TIMEOUT, TimeUnit.SECONDS)
                            //写超时
                            .writeTimeout(Const.DEFAULT_HTTP_WRITE_TIMEOUT, TimeUnit.SECONDS)
                            .protocols(Util.immutableList(Protocol.HTTP_1_1))
                            .sslSocketFactory(SSLSocketClient.getSSLSocketFactory(), SSLSocketClient.getX509TrustManager())
                            .hostnameVerifier(SSLSocketClient.getHostnameVerifier())
                            .build();
//                    okHttpClient.hostnameVerifier();
//                    okHttpClient.hostnameVerifier().verify("s",new SSLContextImpl())
//                    okHttpClient.sslSocketFactory(createSSLSocketFactory());
                    okHttpClient.dispatcher().setMaxRequests(1024);
                    okHttpClient.dispatcher().setMaxRequestsPerHost(12);
                }
            }
        }
        return okHttpClient;
    }


}
