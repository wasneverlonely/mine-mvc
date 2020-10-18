package com.was.core.utils;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.Parcelable;
import android.text.TextUtils;
import android.util.Log;

import androidx.core.content.FileProvider;

import com.was.core.R;

import java.io.File;
import java.io.Serializable;

/**
 * intent  工具类
 */
public class IntentUtils {

    public static String TAG = "IntentUtils";

    /**
     * 100 - 150
     */
    public static final int REQUEST_CAMERA = 101;// 相机
    public static final int REQUEST_ALBUM = 102;// 相册
    public static final int REQUEST_CROP_PIC = 103;// 裁剪图片
    public static final int REQUEST_GALLERY = 104;// 图库

    /**
     * 跳转activity
     *
     * @param context
     * @param activity
     */
    public static void startActivity(Activity context, Class<? extends Activity> activity) {
        startActivity(context, activity, null, false);
    }

    /**
     * 跳转activity
     *
     * @param context
     * @param activity
     * @param bundle
     */
    public static void startActivity(Activity context, Class<? extends Activity> activity, Bundle bundle) {
        startActivity(context, activity, bundle, false);
    }

    /**
     * 跳转一个activity
     *
     * @param context
     * @param activity
     * @param bundle
     * @param closeCurrent
     */
    public static void startActivity(Activity context, Class<? extends Activity> activity, Bundle bundle, boolean closeCurrent) {

        if (context == null || activity.getPackage().getName() == null)
            throw new IllegalArgumentException("context or  activity package is null ");

        Intent intent = new Intent(context, activity);
        if (bundle != null) {
            intent.putExtras(bundle);
        }
        context.startActivity(intent);
        if (closeCurrent)
            context.finish();
    }

    /**
     * 跳转activity 返回
     *
     * @param context
     * @param activity
     * @param requestCode
     */

    public static void startResultActivity(Activity context, Class<? extends Activity> activity, int requestCode) {
        startResultActivity(context, activity, null, false, requestCode);
    }

    /**
     * 跳转一个activity 返回
     *
     * @param context
     * @param activity
     * @param requestCode
     */

    public static void startResultActivity(Activity context, Class<? extends Activity> activity, Bundle bundle, int requestCode) {
        startResultActivity(context, activity, bundle, false, requestCode);
    }

    /**
     * 跳转一个activity 返回
     *
     * @param context
     * @param activity
     * @param bundle
     * @param closeCurrent
     * @param requestCode
     */

    public static void startResultActivity(Activity context, Class<? extends Activity> activity, Bundle bundle, boolean closeCurrent, int requestCode) {
        Intent intent = new Intent(context, activity);
        if (bundle != null) {
            intent.putExtras(bundle);
        }
        context.startActivityForResult(intent, requestCode);
        if (closeCurrent) {
            context.finish();
        }
    }


    /**
     * 返回 null
     *
     * @param activity
     */
    public static void setResultNullActivity(Activity activity) {
        setResultBundleActivity(activity, null);
    }

    /**
     * 返回  String
     *
     * @param activity
     * @param result
     */
    public static void setResultStringActivity(Activity activity, String result) {
        if (TextUtils.isEmpty(result)) {
            Log.e(TAG, "String  is  Null");
            setResultBundleActivity(activity, null);
            return;
        }
        setResultBundleActivity(activity, new BundleBuilder().putResult(result).build());
    }

    /**
     * 返回  Serializable 对象
     *
     * @param activity
     * @param serializable
     */
    public static void setResultSerializableActivity(Activity activity, Serializable serializable) {
        if (serializable == null) {
            Log.e(TAG, "serializable  is  Null");
            setResultBundleActivity(activity, null);
            return;
        }
        Bundle bundle = new Bundle();
        bundle.putSerializable(BundleBuilder.RESULT, serializable);
        setResultBundleActivity(activity, bundle);
    }

    /**
     * 返回  Parcelable  对象
     *
     * @param activity
     * @param p
     */
    public static void setResultParcelableActivity(Activity activity, Parcelable p) {
        if (p == null) {
            Log.e(TAG, "Parcelable  is  Null");
            setResultBundleActivity(activity, null);
            return;
        }
        setResultBundleActivity(activity, new BundleBuilder().putResult(p).build());
    }


    /**
     * @param activity
     * @param bundle
     */
    public static void setResultBundleActivity(Activity activity, Bundle bundle) {
        Intent intent = new Intent();
        if (bundle != null)
            intent.putExtras(bundle);

        activity.setResult(activity.RESULT_OK, intent);
        activity.finish();
    }


    /**
     * 获取id值
     *
     * @param activity
     * @return
     */
    public static int getId(Activity activity) {
        return activity.getIntent().getIntExtra(BundleBuilder.ID, -1);
    }

    /**
     * 从intent 得到int 类型的
     *
     * @param name
     * @return
     */
    public static int getIntExtra(Activity activity, String name) {
        return activity.getIntent().getIntExtra(name, -1);
    }

    /**
     * 从intent 得到String 类型的
     *
     * @param activity
     * @param name
     * @return
     */
    public static String getStringExtra(Activity activity, String name) {
        Intent intent = activity.getIntent();
        String stringExtra = intent.getStringExtra(name);
        return stringExtra;
    }


    /**
     * 获取安装App(支持7.0)的意图 7.0及以上安装需要传入清单文件中的{@code <provider>}的authorities属性
     *
     * @param context
     * @param file
     * @return
     */
    public static Intent getInstallAppIntent(Context context, final File file) {
        if (file == null) return null;
        Intent intent = new Intent(Intent.ACTION_VIEW);
        Uri data;
        String type = "application/vnd.android.package-archive";
        if (Utils.isVersionN()) {
            intent.setFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
            data = FileProvider.getUriForFile(context, context.getResources().getString(R.string.fileprovider_authorities), file);
        } else {
            data = Uri.fromFile(file); // 传递路径
        }
        intent.setDataAndType(data, type);
        return intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
    }

    /**
     * 获取卸载App的意图
     *
     * @param packageName 包名
     * @return intent
     */
    public static Intent getUninstallAppIntent(final String packageName) {
        Intent intent = new Intent(Intent.ACTION_DELETE);
        intent.setData(Uri.parse("package:" + packageName));
        return intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
    }

    /**
     * 获取打开App的意图
     *
     * @param packageName 包名
     * @return intent
     */
    public static Intent getOpenAppIntent(Context context, String packageName) {
        return context.getPackageManager().getLaunchIntentForPackage(packageName);
    }

    /**
     * 获取App具体设置的意图
     *
     * @param packageName 包名
     * @return intent
     */
    public static Intent getAppDetailsSettingsIntent(String packageName) {
        Intent intent = new Intent("android.settings.APPLICATION_DETAILS_SETTINGS");
        intent.setData(Uri.parse("package:" + packageName));
        return intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
    }

    /**
     * 获取分享文本的意图
     *
     * @param content 分享文本
     * @return intent
     */
    public static Intent getShareTextIntent(String content) {
        Intent intent = new Intent(Intent.ACTION_SEND);
        intent.setType("text/plain");
        intent.putExtra(Intent.EXTRA_TEXT, content);
        return intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
    }

    /**
     * 获取跳至拨号界面意图
     *
     * @param phoneNumber 电话号码
     */
    public static Intent getDialIntent(final String phoneNumber) {
        Intent intent = new Intent(Intent.ACTION_DIAL, Uri.parse("tel:" + phoneNumber));
        return intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
    }

    /**
     * 获取拨打电话意图
     * <p>需添加权限 {@code <uses-permission android:name="android.permission.CALL_PHONE"/>}</p>
     *
     * @param phoneNumber 电话号码
     */
    public static Intent getCallIntent(final String phoneNumber) {
        Intent intent = new Intent("android.intent.action.CALL", Uri.parse("tel:" + phoneNumber));
        return intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
    }


    /**
     * 获取跳至发送短信界面的意图
     *
     * @param phoneNumber 接收号码
     * @param content     短信内容
     */
    public static Intent getSendSmsIntent(final String phoneNumber, final String content) {
        Uri uri = Uri.parse("smsto:" + phoneNumber);
        Intent intent = new Intent(Intent.ACTION_SENDTO, uri);
        intent.putExtra("sms_body", content);
        return intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
    }

    /**
     * 获取网络设置
     *
     * @return
     */
    public static Intent getWirelessSettings() {
        return new Intent(android.provider.Settings.ACTION_WIRELESS_SETTINGS).setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
    }
}
