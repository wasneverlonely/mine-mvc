package com.was.core.common.update;

import android.app.Activity;
import android.app.DownloadManager;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;


/**
 * apk 更新广播
 */
public class UpdateAppReceiver extends BroadcastReceiver {

    private long mDownloadUpdateApkId;
    private String mLocalDownloadPath;
    private UpdateApp.InstallAppCallback mInstallAppCallback;

    public UpdateAppReceiver() {
    }

    public UpdateAppReceiver(long downloadUpdateApkId, String localDownloadPath, UpdateApp.InstallAppCallback installAppCallback) {
        this.mDownloadUpdateApkId = downloadUpdateApkId;
        this.mLocalDownloadPath = localDownloadPath;
        this.mInstallAppCallback = installAppCallback;
    }

    @Override
    public void onReceive(Context context, Intent intent) {
        // 处理下载完成
        Cursor c = null;
        if (DownloadManager.ACTION_DOWNLOAD_COMPLETE.equals(intent.getAction())) {
            if (mDownloadUpdateApkId >= 0) {
                long downloadId = mDownloadUpdateApkId;
                DownloadManager.Query query = new DownloadManager.Query();
                query.setFilterById(downloadId);
                DownloadManager downloadManager = (DownloadManager) context
                        .getSystemService(Context.DOWNLOAD_SERVICE);
                c = downloadManager.query(query);
                if (c.moveToFirst()) {
                    int status = c.getInt(c
                            .getColumnIndex(DownloadManager.COLUMN_STATUS));
                    if (status == DownloadManager.STATUS_FAILED) {
                        downloadManager.remove(downloadId);
                    } else if (status == DownloadManager.STATUS_SUCCESSFUL) {
                        if (mInstallAppCallback != null)
                            mInstallAppCallback.installApp((Activity) context, mLocalDownloadPath);
                    }
                }
                c.close();
            }
        }
    }
}

