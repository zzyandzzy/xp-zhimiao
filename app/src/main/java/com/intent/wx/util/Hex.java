package com.intent.wx.util;

/**
 * @author intent zzy.main@gmail.com
 * @date 2021/11/17 下午2:56
 * @since 1.0
 */
public class Hex {
    public static byte[] decodeHex(String data) throws Exception {
        return decodeHex(data.toCharArray());
    }


    public static byte[] decodeHex(char[] data) throws Exception {
        byte[] out = new byte[data.length >> 1];
        decodeHex(data, out, 0);
        return out;
    }

    public static int decodeHex(char[] data, byte[] out, int outOffset) throws Exception {
        int len = data.length;
        if ((len & 1) != 0) {
            throw new Exception("Odd number of characters.");
        } else {
            int outLen = len >> 1;
            if (out.length - outOffset < outLen) {
                throw new Exception("Output array is not large enough to accommodate decoded data.");
            } else {
                int i = outOffset;

                for (int j = 0; j < len; ++i) {
                    int f = toDigit(data[j], j) << 4;
                    ++j;
                    f |= toDigit(data[j], j);
                    ++j;
                    out[i] = (byte) (f & 255);
                }

                return outLen;
            }
        }
    }

    protected static int toDigit(char ch, int index) throws Exception {
        int digit = Character.digit(ch, 16);
        if (digit == -1) {
            throw new Exception("Illegal hexadecimal character " + ch + " at index " + index);
        } else {
            return digit;
        }
    }
}
