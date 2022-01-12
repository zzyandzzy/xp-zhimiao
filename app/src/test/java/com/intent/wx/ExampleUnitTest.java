package com.intent.wx;

import androidx.annotation.NonNull;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.intent.wx.constant.Const;
import com.intent.wx.util.DecryptUtils;
import org.junit.Test;

/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * @see <a href="http://d.android.com/tools/testing">Testing documentation</a>
 */
public class ExampleUnitTest {

    @Test
    public void addition_isCorrect() throws Exception {
        c c = new c();
        System.out.println(c.toString());
//        System.out.println(b.getClass().getSuperclass().getMethod("toString").invoke(b));
    }

    @Test
    public void testAes() throws Exception {
        String key = "00151b4970087dc1d7b0ddd9511410439fa25d37".substring(0, 16);
        String encryptedStr = "";
        String data = DecryptUtils.decrypt(encryptedStr, Const.IV, key);
        if (data != null) {
            JSONObject dataJson = JSONObject.parseObject(data);
            JSONArray jsonArray = dataJson.getJSONArray("list");
            if (jsonArray != null && jsonArray.size() > 0) {
                String mxid = jsonArray.getJSONObject(0).getString("mxid");
                if (mxid != null) {
                    System.out.println(mxid);
                }
            }
        }
    }

    @Test
    public void testJson() {
        String json = "{}";
        JSONObject dataJson = JSONObject.parseObject(json);
        String dataStr = dataJson.getString("data");
        if (dataStr != null) {
            JSONObject userJson = JSONObject.parseObject(dataStr);
            String signatureStr = userJson.getString("signature");
            System.out.println(signatureStr);
        }
    }

    static class a {

        @NonNull
        @Override
        public String toString() {
            return "aaaa";
        }
    }

    class c extends a {

    }
}