package com.intent.wx.util;

import android.util.Base64;
import org.bouncycastle.jce.provider.BouncyCastleProvider;

import javax.crypto.Cipher;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;
import java.nio.charset.StandardCharsets;
import java.security.AlgorithmParameters;
import java.security.Key;
import java.security.Security;

/**
 * @author intent zzy.main@gmail.com
 * @date 2021/11/5 下午11:12
 * @since 1.0
 */
public class DecryptUtils {

    private static final String AES = "AES";
    private static final String AES_CBC_PKCS7 = "AES/CBC/PKCS7Padding";

    static {
        Security.addProvider(new BouncyCastleProvider());
    }

    public static String decrypt(String encryptedData, String iv, String key) {
        try {
            byte[] data = Base64.encode(Hex.decodeHex(encryptedData), Base64.DEFAULT);
            byte[] aseKey = key.getBytes(StandardCharsets.UTF_8);
            byte[] ivData = iv.getBytes(StandardCharsets.UTF_8);

            Cipher cipher = Cipher.getInstance(AES_CBC_PKCS7);
            Key sKeySpec = new SecretKeySpec(aseKey, AES);
            cipher.init(Cipher.DECRYPT_MODE, sKeySpec, generateIv(ivData));

            byte[] result = cipher.doFinal(Base64.decode(data, Base64.DEFAULT));
            return new String(result);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public static AlgorithmParameters generateIv(byte[] iv) throws Exception {
        AlgorithmParameters params = AlgorithmParameters.getInstance(AES);
        params.init(new IvParameterSpec(iv));
        return params;
    }
}
