package com.was.core.common.http;


import java.lang.reflect.Method;

import okio.Buffer;

/**
 * okhttp3.HttpUrl  工具类
 * <p>
 * 用于打印post body的参数
 */
public class HttpUrlUtils {

    /**
     * 反射   percentDecode方法  plusIsSpace
     *
     * @param encodedValue
     * @return
     */
    public String percentDecodeReflex(String encodedValue) {

        String decodeValue = null;

        try {
            Class<?> aClass = Class.forName("okhttp3.HttpUrl");
            Method percentDecode = aClass.getDeclaredMethod("percentDecode", String.class, boolean.class);
            percentDecode.setAccessible(true);
            decodeValue = (String) percentDecode.invoke(null, encodedValue, true);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return decodeValue;
    }

    /**
     * 解码
     *
     * @param encodedValue
     */
    public static String decode(String encodedValue) {
        return percentDecode(encodedValue, true);
    }


    static String percentDecode(String encoded, boolean plusIsSpace) {
        return percentDecode(encoded, 0, encoded.length(), plusIsSpace);
    }

    static String percentDecode(String encoded, int pos, int limit, boolean plusIsSpace) {
        for (int i = pos; i < limit; i++) {
            char c = encoded.charAt(i);
            if (c == '%' || (c == '+' && plusIsSpace)) {
                // Slow path: the character at i requires decoding!
                Buffer out = new Buffer();
                out.writeUtf8(encoded, pos, i);
                percentDecode(out, encoded, i, limit, plusIsSpace);
                return out.readUtf8();
            }
        }

        // Fast path: no characters in [pos..limit) required decoding.
        return encoded.substring(pos, limit);
    }


    static void percentDecode(Buffer out, String encoded, int pos, int limit, boolean plusIsSpace) {
        int codePoint;
        for (int i = pos; i < limit; i += Character.charCount(codePoint)) {
            codePoint = encoded.codePointAt(i);
            if (codePoint == '%' && i + 2 < limit) {
                int d1 = decodeHexDigit(encoded.charAt(i + 1));
                int d2 = decodeHexDigit(encoded.charAt(i + 2));
                if (d1 != -1 && d2 != -1) {
                    out.writeByte((d1 << 4) + d2);
                    i += 2;
                    continue;
                }
            } else if (codePoint == '+' && plusIsSpace) {
                out.writeByte(' ');
                continue;
            }
            out.writeUtf8CodePoint(codePoint);
        }
    }


    static int decodeHexDigit(char c) {
        if (c >= '0' && c <= '9') return c - '0';
        if (c >= 'a' && c <= 'f') return c - 'a' + 10;
        if (c >= 'A' && c <= 'F') return c - 'A' + 10;
        return -1;
    }


}
