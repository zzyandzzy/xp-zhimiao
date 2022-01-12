package com.intent.wx.constant;

import android.os.Environment;

import java.io.File;

/**
 * @author intent zzy.main@gmail.com
 * @date 2021/11/2 上午10:20
 * @since 1.0
 */
public class Const {
    public static final String TAG = "微信小程序: ";
    // hook
    public static final String WX_PACKAGE_NAME = "com.tencent.mm";
    public static final String O_CLASS_NAME = "com.tencent.mm.plugin.appbrand.n.o";
    public static final String HOOK_CLASS_NAME = "com.tencent.mm.plugin.appbrand.jsapi.f";
    public static final String HOOK_SIGNATURE_METHOD_NAME = "a";
    public static final String HOOK_CODE_METHOD_NAME = "ad";
    public static final String HOOK_COOKIE_METHOD_NAME = "akV";
    // decrypt
    public static final String IV = "1234567890000000";
    // storage
    public static final boolean DEBUG_DEFAULT = true;
    public static final String PREF_FILE_NAME = "intent";
    public static final String DEBUG_KEY = "debug";
    public static final String APP_STORAGE_PATH = "wx_hpv";
    public static final String APP_CONFIG_FILE_PATH = "config.json";
    public static final String CONFIG_FILE_PATH = Const.getAppPath() + APP_CONFIG_FILE_PATH;
    // user info
    public static final String DATA_KEY = "data";
    public static final String SIGNATURE_KEY = "signature";
    public static final String CODE_KEY = "code";
    public static final String HEADER_KEY = "header";
    public static final String COOKIE_KEY = "Cookie";
    // key
    public static final String LIST_KEY = "list";
    public static final String MXID_KEY = "mxid";
    public static final String USER_KEY = "user";
    public static final String ENABLE_KEY = "enable";
    public static final String DATE_KEY = "date";
    public static final String QTY_KEY = "qty";
    public static final String URL_KEY = "url";
    // http
    public static final long DEFAULT_HTTP_CONNECT_TIMEOUT = 10;
    public static final long DEFAULT_HTTP_READ_TIMEOUT = 10;
    public static final long DEFAULT_HTTP_WRITE_TIMEOUT = 10;
    public static final long KEEP_ALIVE_DURATION_MILLS = 30;
    public static final int DEFAULT_MAX_IDLE_CONNECTIONS = 256;
    // const
    public static final int[] MONTH_OF_DAY = {31, 29, 31, 30, 31, 30, 31, 31, 30, 31, 30, 31};


    public static String getAppPath() {
        File file = Environment.getExternalStorageDirectory();
        if (file == null) {
            throw new NullPointerException("File cannot be null.");
        }
        return file.getAbsolutePath() + "/" + APP_STORAGE_PATH + "/";
    }
}
