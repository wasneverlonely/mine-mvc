package com.was.core.utils;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

/**
 * 网络连接工具类
 */
public class NetConnectionUtils {

    public static int TYPE_WIFI = 1;           //wifi
    public static int TYPE_MOBILE = 2;         // 手机卡连接
    public static int TYPE_NOT_CONNECTED = 0;  //无网络连接


    public static int getConnectivityStatus(Context context) {
        ConnectivityManager cm = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetwork = cm.getActiveNetworkInfo();

        if (activeNetwork != null && activeNetwork.isConnected()) {
            int type = activeNetwork.getType();
            if (type == ConnectivityManager.TYPE_WIFI)
                return TYPE_WIFI;
            if (type == ConnectivityManager.TYPE_MOBILE)
                return TYPE_MOBILE;
        }
        return TYPE_NOT_CONNECTED;
    }


    /**
     * 判断网络是否连接
     *
     * @param context
     * @return
     */
    public static boolean isConnected(Context context) {
        return getConnectivityStatus(context) != TYPE_NOT_CONNECTED;
    }


    /**
     * @param
     * @return boolean
     * @Description: 判断是否是wifi连接
     */
    public static boolean isWifi(Context context) {
        return getConnectivityStatus(context) == TYPE_WIFI;
    }


    /**
     * @param
     * @return boolean
     * @Description: 判断漫游连接
     */
    public static boolean isMoblie(Context context) {
        return getConnectivityStatus(context) == TYPE_MOBILE;
    }
}
