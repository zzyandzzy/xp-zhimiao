package com.intent.wx.xposed;

import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Context;
import android.widget.Toast;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.intent.wx.constant.Const;
import com.intent.wx.model.Config;
import org.apache.commons.io.FileUtils;

import java.io.File;
import java.io.IOException;
import java.nio.charset.StandardCharsets;

/**
 * @author intent zzy.main@gmail.com
 * @date 2021/11/7 下午1:30
 * @since 1.0
 */
public class XposedUtils {
    private static Context context;

    public static void setContext(Context context) {
        XposedUtils.context = context;
    }

    public static Context getContext() {
        return context;
    }

    public static void showToast(String s) {
        if (context != null) {
            Toast.makeText(context, s, Toast.LENGTH_LONG).show();
        }
    }

    public static void copyToClip(String s) {
        if (context != null) {
            ClipboardManager clipboardManager = (ClipboardManager) context.getSystemService(Context.CLIPBOARD_SERVICE);
            ClipData mClipData = ClipData.newPlainText(Const.SIGNATURE_KEY, s);
            clipboardManager.setPrimaryClip(mClipData);
        }
    }

    public static void writeConfig(Config config) {
        writeConfig(JSONObject.toJSONString(config));
    }

    public static void writeConfig(String json) {
        try {
            FileUtils.writeStringToFile(new File(Const.CONFIG_FILE_PATH), json, StandardCharsets.UTF_8);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static Config readConfig() {
        File appFile = new File(Const.getAppPath());
        try {
            FileUtils.forceMkdir(appFile);
        } catch (IOException e) {
            e.printStackTrace();
        }
        File configFile = new File(Const.CONFIG_FILE_PATH);
        if (configFile.exists()) {
            try {
                String json = FileUtils.readFileToString(configFile, StandardCharsets.UTF_8);
                return JSON.parseObject(json, Config.class);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return new Config();
    }
}
