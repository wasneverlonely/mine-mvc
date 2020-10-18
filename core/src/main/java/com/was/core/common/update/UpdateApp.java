package com.was.core.common.update;

import android.app.Activity;
import android.app.DownloadManager;
import android.content.Context;
import android.content.DialogInterface;
import android.content.IntentFilter;
import android.content.pm.PackageInfo;
import android.net.Uri;
import android.text.TextUtils;

import androidx.appcompat.app.AlertDialog;

import com.was.core.utils.AppUtils;
import com.was.core.utils.SDCardUtils;
import com.was.core.utils.SPUtils;
import com.was.core.utils.StartUtils;
import com.was.core.utils.ToastUtils;

import java.io.File;

/**
 * 更新appUtils
 */
public class UpdateApp {

    private boolean isForce = false;      //是否强制更新
    private int serverVersionCode = 0;   //网络版本号
    private String serverPath;
    private String updateMessage;
    private Activity activity;
    private String localDownloadPath;

    public static long downloadUpdateApkId;
    private InstallAppCallback installAppCallback;

    private UpdateApp(Builder builder) {
        this.activity = builder.activity;
        this.isForce = builder.isForce;
        this.serverVersionCode = builder.serverVersionCode;
        this.updateMessage = builder.updateMessage;
        this.serverPath = builder.serverPath;
        this.installAppCallback = builder.installAppCallback;
    }

    public static final String UPDATE_CHECK = "updateCheck";// 更新

    public void update() {

        int verssionCode = AppUtils.getVerssionCode(activity);
        if (serverVersionCode > verssionCode) {
            int saveVersion = SPUtils.getSharePreInt(activity, UPDATE_CHECK);
            if (saveVersion != 0 && saveVersion >= serverVersionCode) {
                return;
            }
            // 强制更新
            if (isForce) {
                new AlertDialog.Builder(activity)
                        .setTitle("版本更新提示")
                        .setMessage(updateMessage)
                        .setPositiveButton("更新版本", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                downloadForAutoInstall();

                            }
                        }).setCancelable(false)
                        .create().show();
            } else {
                new AlertDialog.Builder(activity)
                        .setTitle("版本更新提示")
                        .setMessage(updateMessage)
                        .setPositiveButton("更新版本", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                downloadForAutoInstall();
                            }
                        }).setNeutralButton("不再提示", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        SPUtils.putSharePre(activity, UPDATE_CHECK, serverVersionCode);
                    }
                }).setNegativeButton("取消", null)
                        .create().show();
            }
        }
    }


    /**
     * 下载和安装
     */
    public void downloadForAutoInstall() {
        ToastUtils.showShort("开始更新!");
        // 获取
        PackageInfo packageInfo = AppUtils.getPackageInfo(activity);
        String packageName = packageInfo.packageName;
        String name = activity.getResources().getString(packageInfo.applicationInfo.labelRes);
        int postIndex = packageName.lastIndexOf(".");
        String fileName = packageName.substring(postIndex + 1, packageName.length());
        downloadForAutoInstall(fileName, name);
    }


    /**
     * 下载并且安装
     *
     * @param fileName
     * @param title
     */
    private void downloadForAutoInstall(String fileName, String title) {
        try {
            Uri uri = Uri.parse(serverPath);
            DownloadManager downloadManager = (DownloadManager) activity.getSystemService(Context.DOWNLOAD_SERVICE);
            DownloadManager.Request request = new DownloadManager.Request(uri);
            request.setVisibleInDownloadsUi(true);//在通知栏中显示
            request.setTitle(title);

            if (TextUtils.isEmpty(localDownloadPath)) {
                localDownloadPath = getDownloadFile(fileName);
            }

            Uri fileUri = Uri.fromFile(new File(localDownloadPath)); // 传递路径
            request.setDestinationUri(fileUri);
            downloadUpdateApkId = downloadManager.enqueue(request);
        } catch (Exception e) {
            StartUtils.startBrowser(activity, serverPath);
        } finally {
            UpdateAppReceiver updateAppReceiver = new UpdateAppReceiver(downloadUpdateApkId, localDownloadPath, installAppCallback);
            IntentFilter intentFilter = new IntentFilter(DownloadManager.ACTION_DOWNLOAD_COMPLETE);
            activity.registerReceiver(updateAppReceiver, intentFilter);
        }
    }


    /**
     * 得到下载文件
     *
     * @param fileName
     * @return
     */
    public String getDownloadFile(String fileName) {
        String projectPath = SDCardUtils.getExternalStorageProjectPath();
        File file = new File(projectPath, "apk");
        if (!file.exists()) {
            file.mkdirs();
        }
        File apkFile = new File(file, fileName + ".apk");
        apkFile.delete();
        return apkFile.getAbsolutePath();
    }


    /**
     * builder 类
     */
    public static class Builder {

        private boolean isForce = false;      //是否强制更新
        private int serverVersionCode = 0;   //网络版本号
        private String serverPath;// 网络地址
        private String updateMessage;
        private Activity activity;
        private InstallAppCallback installAppCallback;

        public Builder(Activity activity) {
            this.activity = activity;
        }

        public Builder isForce(boolean isForce) {
            this.isForce = isForce;
            return this;
        }


        public Builder updateMessage(String updateMessage) {
            this.updateMessage = updateMessage;
            return this;
        }

        public Builder serverPath(String serverPath) {
            this.serverPath = serverPath;
            return this;
        }

        public Builder serverVersionCode(int serverVersionCode) {
            this.serverVersionCode = serverVersionCode;
            return this;
        }

        public Builder setInstallAppCallback(InstallAppCallback installAppCallback) {
            this.installAppCallback = installAppCallback;
            return this;
        }

        public UpdateApp build() {
            return new UpdateApp(this);
        }
    }


    public interface InstallAppCallback {

        void installApp(Activity activity, String localDownloadPath);

    }

}
