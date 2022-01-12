package com.intent.wx.xposed;

import android.app.Application;
import android.content.Context;
import android.util.Log;
import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.JSONValidator;
import com.intent.wx.api.ApiConst;
import com.intent.wx.constant.Const;
import com.intent.wx.model.Config;
import com.intent.wx.util.RequestUtils;
import de.robv.android.xposed.IXposedHookLoadPackage;
import de.robv.android.xposed.XC_MethodHook;
import de.robv.android.xposed.XposedBridge;
import de.robv.android.xposed.XposedHelpers;
import de.robv.android.xposed.callbacks.XC_LoadPackage;
import org.apache.commons.lang3.StringUtils;

import java.lang.reflect.Method;
import java.lang.reflect.Modifier;

import static com.intent.wx.constant.Const.TAG;

/**
 * @author intent zzy.main@gmail.com
 * @date 2021/11/2 上午10:17
 * @since 1.0
 */
public class XposedInit implements IXposedHookLoadPackage {
    public static Config config;

    @Override
    public void handleLoadPackage(final XC_LoadPackage.LoadPackageParam lpparam) throws Throwable {
        if (!Const.WX_PACKAGE_NAME.equals(lpparam.packageName)) {
            return;
        }
        initConfig();
        hookApplication();
        hookWx(lpparam);
        debug(lpparam);
    }

    private void initConfig() {
        XposedInit.config = XposedUtils.readConfig();
        Log.d(TAG, XposedInit.config.toString());
    }

    private void hookWx(XC_LoadPackage.LoadPackageParam lpparam) {
        hookWxSignature(lpparam);
        hookCookie(lpparam);
    }

    private void hookCookie(XC_LoadPackage.LoadPackageParam lpparam) {
        try {
            Method keyMethod = XposedHelpers.findMethodExactIfExists(
                    Const.HOOK_CLASS_NAME,
                    lpparam.classLoader,
                    Const.HOOK_COOKIE_METHOD_NAME,
                    String.class);
            XposedBridge.hookMethod(keyMethod, new XC_MethodHook() {
                @Override
                protected void beforeHookedMethod(MethodHookParam param) throws Throwable {
                    super.beforeHookedMethod(param);
                    String json = (String) param.args[0];
                    JSONObject resJson = JSONObject.parseObject(json);
                    if (resJson == null) {
                        return;
                    }
                    String url = resJson.getString(Const.URL_KEY);
                    if (StringUtils.isBlank(url) || !url.contains(ApiConst.CUSTOMER_PRODUCT_URL)) {
                        return;
                    }
                    String id = RequestUtils.getQueryString(url, "id");
                    if (StringUtils.isBlank(id)) {
                        return;
                    }
                    try {
                        int hid = Integer.parseInt(id);
                        JSONObject headerJson = resJson.getJSONObject(Const.HEADER_KEY);
                        if (headerJson == null) {
                            return;
                        }
                        String cookieStr = headerJson.getString(Const.COOKIE_KEY);
                        if (StringUtils.isBlank(cookieStr)) {
                            return;
                        }
                        initConfig();
                        XposedInit.config.setCookie(cookieStr);
                        XposedInit.config.setHid(hid);
                        XposedUtils.writeConfig(XposedInit.config);
                        Log.d(TAG, id);
                        Log.d(TAG, cookieStr);
                    } catch (Exception e) {
                    }
                }
            });
        } catch (Exception e) {
            Log.e(TAG, "Hook error.", e);
        }
    }

    private void hookWxSignature(XC_LoadPackage.LoadPackageParam lpparam) {
        try {
            Class<?> oClazz = XposedHelpers.findClassIfExists(Const.O_CLASS_NAME, lpparam.classLoader);
            Method keyMethod = XposedHelpers.findMethodExactIfExists(
                    Const.HOOK_CLASS_NAME,
                    lpparam.classLoader,
                    Const.HOOK_SIGNATURE_METHOD_NAME,
                    int.class, String.class, oClazz);
            XposedBridge.hookMethod(keyMethod, new XC_MethodHook() {
                @Override
                protected void beforeHookedMethod(MethodHookParam param) throws Throwable {
                    super.beforeHookedMethod(param);
                    String json = (String) param.args[1];
                    JSONObject dataJson = JSONObject.parseObject(json);
                    String dataStr = dataJson.getString(Const.DATA_KEY);
                    if (StringUtils.isBlank(dataStr)) {
                        return;
                    }
                    if (JSONValidator.from(dataStr).getType() != JSONValidator.Type.Object) {
                        return;
                    }
                    JSONObject userJson = JSONObject.parseObject(dataStr);
                    if (userJson == null) {
                        return;
                    }
                    String signatureStr = userJson.getString(Const.SIGNATURE_KEY);
                    if (StringUtils.isBlank(signatureStr)) {
                        return;
                    }
                    initConfig();
                    XposedInit.config.setSignature(signatureStr);
                    XposedUtils.writeConfig(XposedInit.config);
                    XposedBridge.log(TAG + ":" + signatureStr);
                    XposedUtils.copyToClip(signatureStr);
                    XposedUtils.showToast("密钥: " + signatureStr + " 复制成功!");
                    Log.d(TAG, signatureStr);
                }
            });
        } catch (Exception e) {
            Log.e(TAG, "Hook error.", e);
        }
    }

    private void hookApplication() {
        try {
            XposedHelpers.findAndHookMethod(Application.class,
                    "attach",
                    Context.class, new XC_MethodHook() {
                        @Override
                        protected void afterHookedMethod(MethodHookParam param) throws Throwable {
                            super.afterHookedMethod(param);
                            XposedUtils.setContext((Context) param.args[0]);
                        }
                    });
        } catch (Exception e) {
            Log.e(TAG, "Hook method attach error", e);
        }
    }

    private void debug(XC_LoadPackage.LoadPackageParam lpparam) {
        if (XposedInit.config.getDebug()) {
            Class<?> oClazz = XposedHelpers.findClassIfExists(Const.HOOK_CLASS_NAME, lpparam.classLoader);
            Method[] methods = oClazz.getDeclaredMethods();
            for (final Method m : methods) {
                if (Modifier.isInterface(m.getModifiers())) {
                    continue;
                }
                XposedBridge.hookMethod(m, new XC_MethodHook() {
                    @Override
                    protected void beforeHookedMethod(MethodHookParam param) throws Throwable {
                        super.beforeHookedMethod(param);
                        StringBuilder sb = new StringBuilder();
                        sb.append(m.getName());
                        for (int i = 0; i < param.args.length; i++) {
                            sb.append(" | ").append(param.args[i]);
                        }
                        Log.d(TAG, sb.toString());
                    }

                    @Override
                    protected void afterHookedMethod(MethodHookParam param) throws Throwable {
                        super.afterHookedMethod(param);
                        Log.d(TAG, "result: " + param.getResult());
                    }
                });
            }
        }
    }
}
