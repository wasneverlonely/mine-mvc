package com.was.core.utils;

import android.content.Context;
import android.os.Environment;

import java.io.File;

/**
 * SD卡相关的辅助类
 */
public class SDCardUtils {

    public static final String MAIN = "mine";// sd卡 主要目录
    public static final String PROJECT = "jiazhangjia";// sd卡 主要目录
    public static final String DOWNLOAD = "download";// 下载目录
    public static final String UPLOAD = "upload";// 上传目录
    public static final String COMPRESS = "compress";// 压缩目录
    public static final String MEDIASTORE = "mediastore";// 媒体目录
    public static final String CROP = "crop";// 压缩目录
    public static final String CAMERA = "camera";// 相机目录
    public static final String VIDEO = "video";// 视频

    /**
     * 判断SDCard是否可用
     */
    public static boolean isMounted() {
        return Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED);
    }

    /**
     * 表示外部内存卡根目录，对应根目录等价于   /storage/emulated/0
     *
     * @return
     */
    public static String getExternalStorageDirectoryPath() {
        return Environment.getExternalStorageDirectory().getAbsolutePath();
    }

    /**
     * 表示应用默认缓存根目录  /data/user/0/com.xinying.test/cache
     *
     * @param context
     * @return
     */
    public static String getCacheDirPath(Context context) {

        return context.getCacheDir().getAbsolutePath();
    }

    /**
     * 对应内部内存卡根目录：Context.getFileDir()-->  /data/user/0/com.xinying.test/files
     *
     * @param context
     * @return
     */
    public static String getFilesDirPath(Context context) {
        return context.getFilesDir().getAbsolutePath();
    }

    /**
     * 表示外部内存卡根目录下的APP公共目录，  /storage/emulated/0/Android/data/com.xinying.test/files
     *
     * @param context
     * @return
     */
    public static String getExternalFilesDirPath(Context context, String tag) {
        return context.getExternalFilesDir(tag).getAbsolutePath();
    }

    /**
     * 表示外部内存卡根目录下的APP缓存目录， /storage/emulated/0/Android/data/com.xinying.test/cache
     *
     * @param context
     * @return
     */
    public static String getExternalCacheDirPath(Context context) {
        return context.getFilesDir().getAbsolutePath();
    }

    /**
     * 得到默认压缩目录
     *
     * @return
     */
    public static String getCacheDirCompressPath(Context context) {
        return getCacheDirPath(context) + File.separator + COMPRESS;
    }

    /**
     * 得到外部储存卡的  项目目录
     *
     * @return
     */
    public static String getExternalStorageProjectPath() {
        return getExternalStorageDirectoryPath() + File.separator + PROJECT;
    }


    /**
     * 得到外部储存卡的   下载目录
     *
     * @return
     */
    public static String getExternalStorageDownloadPath() {
        return getExternalStorageProjectPath() + File.separator + DOWNLOAD;
    }

    /**
     * 得到外部储存卡的  上传目录
     *
     * @return
     */
    public static String getExternalStorageUploadPath() {
        return getExternalStorageProjectPath() + File.separator + UPLOAD;
    }


    /**
     * 得到外部储存卡的   媒体 Mediastore文件
     *
     * @return
     */
    public static String getExternalStorageMediastorePath() {
        return getExternalStorageProjectPath() + File.separator + MEDIASTORE;
    }


    /**
     * 得到外部储存卡的   媒体 相机
     *
     * @return
     */
    public static String getExternalStorageCameraPath() {
        return getExternalStorageMediastorePath() + File.separator + CAMERA;
    }

    /**
     * 得到外部储存卡的   媒体 视频
     *
     * @return
     */
    public static String getExternalStorageVideoPath() {
        return getExternalStorageMediastorePath() + File.separator + VIDEO;
    }

    /**
     * 得到外部储存卡的  剪切目录
     *
     * @return
     */
    public static String getExternalStorageCropPath() {
        return getExternalStorageMediastorePath() + File.separator + CROP;
    }


    /**
     * 获取系统存储路径    /system
     *
     * @return
     */
    public static String getRootDirectoryPath() {
        return Environment.getRootDirectory().getAbsolutePath();
    }

}
