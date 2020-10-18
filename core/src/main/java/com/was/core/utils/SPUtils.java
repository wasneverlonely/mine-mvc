package com.was.core.utils;

import android.content.Context;
import android.content.SharedPreferences;
import android.text.TextUtils;
import android.util.Base64;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

public class SPUtils {

    public static String IMGSELECTOR = "IMGSELECTOR";

    /*
     * 获取sharedpreferences String类型
     */
    public static final String LOCATION = "location";

    /**
     * 获取字符串
     *
     * @param context
     * @param field
     * @return
     */
    public static String getSharePreStr(Context context, String field) {
        if (Utils.isNull(context)) {
            return "";
        }
        SharedPreferences sp = context.getSharedPreferences(IMGSELECTOR, 0);
        return sp.getString(field, null);
    }

    /**
     * 获取字符串 int   没有返回0
     *
     * @param context
     * @param field
     * @return
     */
    public static int getSharePreInt(Context context, String field) {
        if (Utils.isNull(context)) {
            return 0;
        }
        SharedPreferences sp = context.getSharedPreferences(IMGSELECTOR, 0);
        return sp.getInt(field, 0);
    }

    /**
     * 获取字符串 boolean 类型   没有返回false
     *
     * @param context
     * @param field
     * @return
     */
    public static boolean getSharePreBoolean(Context context, String field) {
        SharedPreferences sp = (SharedPreferences) context.getSharedPreferences(IMGSELECTOR, 0);
        return sp.getBoolean(field, false);
    }


    public static boolean getSharePreBoolean(Context context, String field, boolean defValue) {
        SharedPreferences sp = context.getSharedPreferences(IMGSELECTOR, 0);
        return sp.getBoolean(field, defValue);
    }


    /**
     * 得到 object
     *
     * @param context
     * @param field
     * @return
     */
    public static Object getSharePreObject(Context context, String field) {
        String sharePreStr = getSharePreStr(context, field);
        if (TextUtils.isEmpty(sharePreStr)) {
            return null;
        }
        byte[] decode = Base64.decode(sharePreStr.getBytes(), Base64.DEFAULT);

        String decodeStr = new String(decode);
        L.e(decodeStr);

        ByteArrayInputStream bais = null;
        ObjectInputStream ois = null;
        try {
            bais = new ByteArrayInputStream(decode);
            ois = new ObjectInputStream(bais);
            Object obj = ois.readObject();
            return obj;
        } catch (IOException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } finally {
            try {
                bais.close();
                ois.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        return null;
    }


    /**
     * put String
     *
     * @param context
     * @param field
     * @param value
     */
    public static boolean putSharePre(Context context, String field, String value) {
        SharedPreferences sp = (SharedPreferences) context.getSharedPreferences(IMGSELECTOR, 0);
        return sp.edit().putString(field, value).commit();
    }

    /**
     * put int
     *
     * @param context
     * @param field
     * @param value
     */
    public static boolean putSharePre(Context context, String field, int value) {
        SharedPreferences sp = (SharedPreferences) context.getSharedPreferences(IMGSELECTOR, 0);
        return sp.edit().putInt(field, value).commit();
    }

    /**
     * put  boolean
     *
     * @param context
     * @param field
     * @param value
     */
    public static boolean putSharePre(Context context, String field, Boolean value) {
        SharedPreferences sp = (SharedPreferences) context.getSharedPreferences(IMGSELECTOR, 0);
        return sp.edit().putBoolean(field, value).commit();
    }


    /**
     * f放进去 object
     *
     * @param context
     * @param field
     * @param value
     * @return
     */
    public static boolean putSharePre(Context context, String field, Object value) {
        if (value == null) {
            return false;
        }

        ByteArrayOutputStream baos = null;
        ObjectOutputStream oos = null;
        try {
            baos = new ByteArrayOutputStream();
            oos = new ObjectOutputStream(baos);
            oos.writeObject(value);
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                baos.close();
                oos.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        String objectStr = new String(Base64.encode(baos.toByteArray(),
                Base64.DEFAULT));

        return putSharePre(context, field, objectStr);
    }


    /**
     * 移除 某个key
     *
     * @param context
     * @param key
     */
    public static void removeSharePre(Context context, String key) {
        try {
            SharedPreferences sp = (SharedPreferences) context.getSharedPreferences(IMGSELECTOR, 0);
            SharedPreferences.Editor edit = sp.edit();
            edit.remove(key).commit();
        } catch (Exception e) {
            L.e("移除key值失败");
        }
    }

    /**
     * 清除key
     *
     * @param context
     */
    public static void clearSharePre(Context context) {
        try {
            SharedPreferences sp = (SharedPreferences) context.getSharedPreferences(IMGSELECTOR, 0);
            sp.edit().clear().commit();
        } catch (Exception e) {
        }
    }

}
