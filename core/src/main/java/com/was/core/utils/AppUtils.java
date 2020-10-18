package com.was.core.utils;

import android.app.Activity;
import android.content.Context;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager.NameNotFoundException;
import android.net.wifi.WifiManager;
import android.telephony.TelephonyManager;
import android.text.TextUtils;

import java.io.File;


/**
 * app 操作类
 */
public class AppUtils {


    /**
     * 判断App是否安装
     *
     * @param context
     * @param packageName
     * @return
     */
    public static boolean isInstallApp(Context context, final String packageName) {
        return !TextUtils.isEmpty(packageName) && context.getPackageManager().getLaunchIntentForPackage(packageName) != null;
    }

    /**
     * 获取安装App(支持7.0)的意图    7.0及以上安装需要传入清单文件中的{@code <provider>}的authorities属性
     *
     * @param context
     * @param filePath
     */
    public static boolean installApp(Activity context, String filePath) {
        if (context == null || TextUtils.isEmpty(filePath)) {
            return false;
        }
        File file = new File(filePath);
        if (file == null || !file.exists()) {
            return false;
        }
        context.startActivity(IntentUtils.getInstallAppIntent(context, file));
        return true;
    }

    /**
     * 卸载App
     *
     * @param context
     * @param packageName
     */
    public static void uninstallApp(Activity context, String packageName) {
        if (context == null || TextUtils.isEmpty(packageName)) {
            return;
        }
        context.startActivity(IntentUtils.getUninstallAppIntent(packageName));
    }


    /**
     * 打开App
     *
     * @param context
     * @param packageName
     */
    public static void openApp(Activity context, String packageName) {
        if (context == null || TextUtils.isEmpty(packageName)) {
            return;
        }
        context.startActivity(IntentUtils.getOpenAppIntent(context, packageName));
    }


    /**
     * 获取App具体设置
     *
     * @param packageName 包名
     */
    public static void openAppDetailsSettings(Activity context, String packageName) {
        if (context == null || TextUtils.isEmpty(packageName)) {
            return;
        }
        context.startActivity(IntentUtils.getAppDetailsSettingsIntent(packageName));
    }


    /**
     * 获取应用程序信息
     *
     * @param context
     * @return
     */
    public static PackageInfo getPackageInfo(Context context) {
        PackageInfo packageInfo = null;
        try {
            return context.getPackageManager().getPackageInfo(context.getPackageName(), 0);
        } catch (NameNotFoundException e) {
            L.e("异常", e.getMessage());
        }
        return packageInfo;
    }

    /**
     * 获取软件版本号
     *
     * @param context
     * @return
     */
    public static int getVerssionCode(Context context) {
        try {
            return context.getPackageManager().getPackageInfo(context.getPackageName(), 0).versionCode;
        } catch (NameNotFoundException e) {
            L.e("msg", e.getMessage());
            return -1;
        }
    }

    /**
     * 获取版本名称
     *
     * @param context
     * @return
     */
    public static String getVerssionName(Context context) {
        try {
            return context.getPackageManager().getPackageInfo(context.getPackageName(), 0).versionName;
        } catch (NameNotFoundException e) {
            return null;
        }
    }

    /**
     * 获取应用程序的包名
     *
     * @param context
     * @return
     */
    public static String getPackageName(Context context) {
        String packageName = null;
        try {
            packageName = context.getPackageManager().getPackageInfo(context.getPackageName(), 0).packageName;
        } catch (NameNotFoundException e) {
            L.e("应用程序的包名  异常", e.getMessage());
        }
        return packageName;
    }


    /**
     * 获取应用程序的名字
     *
     * @param context
     * @return
     */
    public static String getAppName(Context context) {
        String appName = null;
        try {
            int labelRes = context.getPackageManager().getPackageInfo(context.getPackageName(), 0).applicationInfo.labelRes;
            appName = context.getResources().getString(labelRes);
        } catch (NameNotFoundException e) {
            L.e("异常", e.getMessage());
        }
        return appName;
    }

    /**
     * 获取一个适配的唯一标识      // 获取 UniqueId
     *
     * @param context
     * @return
     */
    public static String getUniqueId(Context context) {
        TelephonyManager telephonyManager = (TelephonyManager) context.getSystemService(Context.TELEPHONY_SERVICE);
        String deviceId = telephonyManager.getDeviceId();
        if (!TextUtils.isEmpty(deviceId)) {
            return deviceId;
        }
        WifiManager wifiManager = (WifiManager) context.getSystemService(Context.WIFI_SERVICE);
        String macAddress = wifiManager.getConnectionInfo().getMacAddress();
        if (!TextUtils.isEmpty(macAddress)) {
            return macAddress;
        }
        return "";
    }

}
