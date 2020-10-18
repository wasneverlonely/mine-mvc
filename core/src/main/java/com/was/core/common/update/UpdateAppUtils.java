package com.was.core.common.update;

import android.app.Activity;
import android.app.DownloadManager;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.pm.PackageInfo;
import android.net.Uri;
import android.os.Build;
import android.os.Environment;
import android.text.TextUtils;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AlertDialog;


import com.was.core.utils.AppUtils;
import com.was.core.utils.SPUtils;

import java.io.File;

/**
 * 更新appUtils
 */
public class UpdateAppUtils {

    public static final int CHECK_BY_VERSION_NAME = 1001;//按版本名称检查
    public static final int CHECK_BY_VERSION_CODE = 1002;// 按版本号检查
    public static final int DOWNLOAD_BY_APP = 1003;//下载在app
    public static final int DOWNLOAD_BY_BROWSER = 1004;//在浏览器上下载

    public static long downloadUpdateApkId = -1;//下载更新Apk 下载任务对应的Id
    public static String downloadUpdateApkFilePath;//下载更新Apk 文件路径

    public static final String UPDATE_CHECK = "updateCheck";// 更新
    private Activity activity;
    private int downloadBy = DOWNLOAD_BY_APP;
    private int serverVersionCode = 0;//网络版本号
    private String apkNetPath, localDownloadPath;//apk下载地址   本地下载地址
    private boolean isForce = false; //是否强制更新
    public static boolean showNotification = true;
    private String updateInfo;

    private UpdateAppUtils(Activity activity) {
        this.activity = activity;
    }

    public static UpdateAppUtils from(Activity activity) {
        return new UpdateAppUtils(activity);
    }

    public UpdateAppUtils apkPath(String apkNetPath) {
        this.apkNetPath = apkNetPath;
        return this;
    }

    public UpdateAppUtils downloadBy(int downloadBy) {//下载在
        this.downloadBy = downloadBy;
        return this;
    }

    public UpdateAppUtils showNotification(boolean showNotification) {
        this.showNotification = showNotification;
        return this;
    }


    public UpdateAppUtils localDownloadPath(String localDownloadPath) {
        this.localDownloadPath = localDownloadPath;
        return this;
    }

    public UpdateAppUtils updateInfo(String updateInfo) {
        this.updateInfo = updateInfo;
        return this;
    }


    public UpdateAppUtils serverVersionCode(int serverVersionCode) {
        this.serverVersionCode = serverVersionCode;
        return this;
    }

    public UpdateAppUtils isForce(boolean isForce) {
        this.isForce = isForce;
        return this;
    }


    public void update() {

        int verssionCode = AppUtils.getVerssionCode(activity);

        if (serverVersionCode > verssionCode) {// 网络版本大于现在版本
            // 为了防止第二次进入不提示
            int saveVersion = SPUtils.getSharePreInt(activity, UPDATE_CHECK);
            if (saveVersion != 0 && saveVersion >= serverVersionCode) {
                return;
            }
            new AlertDialog.Builder(activity)
                    .setTitle("版本更新提示")
                    .setMessage(updateInfo)
                    .setPositiveButton("更新版本", new DialogInterface.OnClickListener() {
                        @RequiresApi(api = Build.VERSION_CODES.GINGERBREAD)
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            downloadForAutoInstall(apkNetPath);
                        }
                    }).setNeutralButton("不再提示", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    SPUtils.putSharePre(activity, UPDATE_CHECK, Integer.valueOf(serverVersionCode));
                }
            }).setNegativeButton("取消", null)//中间
                    .create().show();
        }
    }

    /**
     * 下载更新apk包
     *
     * @param url
     */
    private void downloadForAutoInstall(String url) {
        PackageInfo packageInfo = AppUtils.getPackageInfo(activity);
        String packageName = packageInfo.packageName;
        String name = activity.getResources().getString(packageInfo.applicationInfo.labelRes);
        int postIndex = packageName.lastIndexOf(".");  // com.xinying.auction
        String fileName = packageName.substring(postIndex + 1, packageName.length());
        downloadForAutoInstall(url, fileName, name);
    }

    /**
     * 下载更新apk包
     * 权限:1,<uses-permission android:name="android.permission.DOWNLOAD_WITHOUT_NOTIFICATION" />
     *
     * @param url
     * @param fileName
     * @param title
     */
    @RequiresApi(api = Build.VERSION_CODES.GINGERBREAD)
    private void downloadForAutoInstall(String url, String fileName, String title) {
        if (TextUtils.isEmpty(url)) {
            return;
        }
        try {
            Uri uri = Uri.parse(url);
            DownloadManager downloadManager = (DownloadManager) activity.getSystemService(Context.DOWNLOAD_SERVICE);
            DownloadManager.Request request = new DownloadManager.Request(uri);
            request.setVisibleInDownloadsUi(true);//在通知栏中显示
            request.setTitle(title);
            // api 说明
//            request.setDescription("");
//            if (!showNotification) {
//                request.setNotificationVisibility(DownloadManager.Request.VISIBILITY_HIDDEN);
//            }
            //下载地址
            if (TextUtils.isEmpty(localDownloadPath)) {
                localDownloadPath = Environment.getExternalStorageDirectory().getAbsolutePath();
            }
            downloadUpdateApkFilePath = localDownloadPath + File.separator + fileName + ".apk";
            deleteFile(downloadUpdateApkFilePath);// 若存在，则删除
            Uri fileUri = Uri.fromFile(new File(downloadUpdateApkFilePath));
            request.setDestinationUri(fileUri);
            downloadUpdateApkId = downloadManager.enqueue(request);
            long i = downloadUpdateApkId;
        } catch (Exception e) {
            e.printStackTrace();
            downloadForWebView(url);
        } finally {
            activity.registerReceiver(new UpdateAppReceiver(), new IntentFilter(DownloadManager.ACTION_DOWNLOAD_COMPLETE));
        }
    }

    /**
     * 通过浏览器下载APK包
     *
     * @param
     * @param url
     */
    public void downloadForWebView(String url) {
        Uri uri = Uri.parse(url);
        Intent intent = new Intent(Intent.ACTION_VIEW, uri);
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        activity.startActivity(intent);
    }


    private static boolean deleteFile(String fileStr) {
        File file = new File(fileStr);
        return file.delete();
    }
}
