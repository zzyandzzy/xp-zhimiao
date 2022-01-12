package com.intent.wx.api;

/**
 * @author intent zzy.main@gmail.com
 * @date 2021/11/6 下午9:21
 * @since 1.0
 */
public class ApiConst {
    // http
    public static final String HTTP_PROTO_PREFIX = "https://";
    public static final String HOST = "cloud.cn2030.com";
    public static final String BASE_URL = HTTP_PROTO_PREFIX + HOST;
    // path const
    public static final String GET_SUB_DATE_DETAIL_URL = BASE_URL + "/sc/wx/HandlerSubscribe.ashx?act=GetCustSubscribeDateDetail";
    public static final String GET_SUB_DATE_ALL_URL = BASE_URL + "/sc/wx/HandlerSubscribe.ashx?act=GetCustSubscribeDateAll";
    public static final String GET_USER_URL = BASE_URL + "/sc/wx/HandlerSubscribe.ashx?act=User";
    public static final String GET_CAPTCHA_URL = BASE_URL + "/sc/wx/HandlerSubscribe.ashx?act=GetCaptcha";
    public static final String CUSTOMER_PRODUCT_URL = BASE_URL + "/sc/wx/HandlerSubscribe.ashx?act=CustomerProduct";
    public static final String SAVE20_URL = BASE_URL + "/sc/wx/HandlerSubscribe.ashx?act=Save20&Ftime=1&guid=";
    // header const
    public static final String CONNECTION = "keep-alive";
    public static final String CHARSET = "keep-alive";
    public static final String USER_ANENT = "Mozilla/5.0 (Linux; Android 9;) AppleWebKit/537.36 (KHTML, like Gecko) Version/4.0 Chrome/86.0.4240.99 XWEB/3141 MMWEBSDK/20210902 Mobile Safari/537.36 MMWEBID/921 MicroMessenger/8.0.15.2020(0x28000F31) Process/appbrand0 WeChat/arm64 Weixin NetType/WIFI Language/zh_CN ABI/arm64 MiniProgramEnv/android";
    public static final String CONTENT_TYPE = "application/json";
    public static final String ACCEPT_ENCODING = "gzip,compress,br,deflate";
    public static final String REFERER = "https://servicewechat.com/wx2c7f0f3c30d99445/90/page-frame.html";
    // query const
    public static final int HPV_9_PID = 1;
    public static final int HPV_2_PID = 54;
}
