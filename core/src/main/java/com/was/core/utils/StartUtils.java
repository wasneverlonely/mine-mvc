package com.was.core.utils;

import android.app.Activity;
import android.content.ComponentName;
import android.content.Intent;
import android.net.Uri;
import android.provider.MediaStore;

import androidx.core.content.FileProvider;

import com.was.core.R;

import java.io.File;

/**
 * startUtils  打开activity
 */
public class StartUtils {

    /**
     * 100 - 150
     */
    public static final int REQUEST_CAMERA = 101;// 相机
    public static final int REQUEST_CAMERA_VIDOE = 102;// 相机
    public static final int REQUEST_ALBUM = 103;// 相册
    public static final int REQUEST_CROP_PIC = 104;// 裁剪图片
    public static final int REQUEST_GALLERY = 105;// 图库

    /**
     * 启动系统相机
     *
     * @param ac
     * @param requestCode
     */
    public static void startCamera(Activity ac, int requestCode) {
        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);// 启动系统相机
        ac.startActivityForResult(intent, requestCode);
    }

    /**
     * 启动系统相机   保存到一个目录
     *
     * @param ac
     * @param file
     * @param requestCode
     */
    public static void startCamera(Activity ac, File file, int requestCode) {
        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);// 启动系统相机
        if (intent.resolveActivity(ac.getPackageManager()) != null) {
            FileUtils.fileMkdirs(file.getParentFile());
            Uri photoUri;
            if (Utils.isVersionN()) {
                photoUri = FileProvider.getUriForFile(ac, ac.getResources().getString(R.string.fileprovider_authorities), file);
            } else {
                photoUri = Uri.fromFile(file); // 传递路径
            }
            intent.putExtra(MediaStore.EXTRA_OUTPUT, photoUri);// 更改系统默认存储路径
            ac.startActivityForResult(intent, requestCode);
        }
    }


    /**
     * 打开录像
     *
     * @param ac
     * @param file
     * @param requestCode
     */
    public static void startCameraVideo(Activity ac, File file, int requestCode) {
        startCameraVideo(ac, file, 0, 3, requestCode);
    }


    /**
     * 打开录像
     *
     * @param ac
     * @param file
     * @param recordVideoSecond
     * @param definition
     * @param requestCode
     */
    public static void startCameraVideo(Activity ac, File file, int recordVideoSecond, int definition, int requestCode) {
        Intent cameraIntent = new Intent(MediaStore.ACTION_VIDEO_CAPTURE);
        if (cameraIntent.resolveActivity(ac.getPackageManager()) != null) {
            FileUtils.fileMkdirs(file.getParentFile());
            Uri photoUri;
            if (Utils.isVersionN()) {
                photoUri = FileProvider.getUriForFile(ac, ac.getResources().getString(R.string.fileprovider_authorities), file);
            } else {
                photoUri = Uri.fromFile(file); // 传递路径
            }
            cameraIntent.putExtra(MediaStore.EXTRA_OUTPUT, photoUri);// 更改系统默认存储路径
            cameraIntent.putExtra(MediaStore.EXTRA_DURATION_LIMIT, recordVideoSecond);//时间限制
            //cameraIntent.putExtra(MediaStore.EXTRA_SIZE_LIMIT, 5491520L);//5*1048*1048=5MB
            cameraIntent.putExtra(MediaStore.EXTRA_VIDEO_QUALITY, definition);
            ac.startActivityForResult(cameraIntent, requestCode);
        }
    }


    /**
     * 相册
     *
     * @param ac
     * @param requestCode
     */
    public static void startAlbum(Activity ac, int requestCode) {
        Intent intent = new Intent(
                Intent.ACTION_PICK,
                MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        ac.startActivityForResult(intent, requestCode);// 打开相册
    }

    /**
     * 裁剪图片方法实现   以150为单位
     *
     * @param ac
     * @param uri
     * @param aspectX
     * @param aspectY
     * @param requestCode
     */
    public static void startCropPicture(Activity ac, Uri uri, int aspectX, int aspectY, int requestCode) {
        Intent intent = new Intent("com.android.camera.action.CROP");
        intent.setDataAndType(uri, "image/*");
        // 设置裁剪
        intent.putExtra("crop", "true");
        // aspectX aspectY 是宽高的比例
        intent.putExtra("aspectX", aspectX);
        intent.putExtra("aspectY", aspectY);
        // outputX outputY 是裁剪图片宽高
        intent.putExtra("outputX", 150 * aspectX);
        intent.putExtra("outputY", 150 * aspectY);
        intent.putExtra("return-data", true);
        ac.startActivityForResult(intent, requestCode);
    }

    /**
     * 用 浏览器打开
     *
     * @param ac
     * @param url
     */
    public static void startBrowser(Activity ac, String url) {
        Intent intent = new Intent();
        intent.setAction("android.intent.action.VIEW");
        Uri uri = Uri.parse(url);
        intent.setData(uri);
        ac.startActivity(intent);
    }

    /**
     * 跳转到qq
     *
     * @param context
     * @param qqNumber
     */
    public static boolean startQQ(Activity context, String qqNumber) {
        String packageName = "com.tencent.mobileqq";
        boolean install = AppUtils.isInstallApp(context, packageName);
        if (install) {  // 安装了
            Intent intent = new Intent();
            intent.setAction(Intent.ACTION_VIEW);
            Uri parse = Uri.parse("mqqwpa://im/chat?chat_type=wpa&uin=" + qqNumber + "&version=1");
            intent.setData(parse);
            context.startActivity(intent);
        }
        return install;
    }

    /**
     * 跳转到微信
     *
     * @param context
     */
    public static void startWeiXin(Activity context) {
        try {
            Intent intent = new Intent();
            ComponentName cmp = new ComponentName("com.tencent.mm", "com.tencent.mm.ui.LauncherUI");
            intent.setAction(Intent.ACTION_MAIN);
            intent.addCategory(Intent.CATEGORY_LAUNCHER);
            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            intent.setComponent(cmp);
            context.startActivityForResult(intent, 0);
        } catch (Exception e) {
            ToastUtils.showShort("没有找到微信！");
        }
    }


//    /**
//     * 单独拍照
//     *
//     * @param context
//     * @param type
//     */
//    public static void selectCameraPicture(Activity context, int type) {
//        if (type == 0) {
//            type = FunctionConfig.TYPE_IMAGE;
//        }
//        FunctionOptions options = new FunctionOptions.Builder()
//                .setTakePhoto(true)
//                .setType(type).create();
//        PictureConfig.getInstance().init(options).startOpenCamera(context);
//    }
//
//
//    /**
//     * 选择 多张图片
//     *
//     * @param context
//     * @param maxSelectNum
//     * @param selectMedia
//     * @param resultCallback
//     */
//    public static void selectMorePicture(Activity context, int maxSelectNum, List<LocalMedia> selectMedia, PictureConfig.OnSelectResultCallback resultCallback) {
//        FunctionOptions options = new FunctionOptions.Builder()
//                .setType(FunctionConfig.TYPE_IMAGE)
//                .setCompress(true)
//                .setCheckNumMode(true)
//                .setEnablePixelCompress(true) //是否启用像素压缩
//                .setEnableQualityCompress(true) //是否启质量压缩
//                .setMaxSelectNum(maxSelectNum)
//                .setMinSelectNum(0)
//                .setSelectMode(FunctionConfig.MODE_MULTIPLE)
//                .setShowCamera(true)
//                .setImageSpanCount(4) // 每行个数
//                .create();
//        PictureConfig.getInstance().init(options).openPhoto(context, resultCallback);
//    }
//
//
//    /**
//     * 选择 单张图片
//     *
//     * @param context
//     * @param resultCallback
//     */
//    public static void selectSinglePicture(Context context, PictureConfig.OnSelectResultCallback resultCallback) {
//
//    }
}
