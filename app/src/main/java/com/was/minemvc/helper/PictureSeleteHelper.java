package com.was.minemvc.helper;

import android.app.Activity;

import androidx.fragment.app.Fragment;

import com.luck.picture.lib.PictureSelector;
import com.luck.picture.lib.config.PictureConfig;
import com.luck.picture.lib.config.PictureMimeType;

/**
 * Created by Administrator on 2018/2/28.
 */

public class PictureSeleteHelper {

    /**
     * 选择单张图片
     *
     * @param context
     * @param requestCode
     */
    public static void selectSinglePicture(Activity context, int requestCode) {
        PictureSelector.create(context)
                .openGallery(PictureMimeType.ofImage())
                .selectionMode(PictureConfig.SINGLE)
                .previewImage(false)
                .isCamera(true)// 是否显示拍照按钮
                .isZoomAnim(true)
                .compress(true)// 是否压缩
                .cropCompressQuality(50)
                .glideOverride(160, 160)// glide 加载宽高，越小图片列表越流畅，但会影响列表图片浏览的清晰度
                .minimumCompressSize(100)// 小于100kb的图片不压缩
                .forResult(requestCode);//结果回调onActivityResult code
    }

    public static void selectSinglePicture(Fragment fragment, int requestCode) {
        PictureSelector.create(fragment)
                .openGallery(PictureMimeType.ofImage())
                .selectionMode(PictureConfig.SINGLE)
                .previewImage(false)
                .isCamera(true)// 是否显示拍照按钮
                .isZoomAnim(true)
                .compress(true)// 是否压缩
                .cropCompressQuality(50)
                .glideOverride(160, 160)// glide 加载宽高，越小图片列表越流畅，但会影响列表图片浏览的清晰度
                .minimumCompressSize(100)// 小于100kb的图片不压缩
                .forResult(requestCode);//结果回调onActivityResult code
    }


    public static void selectMorePicture(Fragment fragment, int maxCount, int requestCode) {

        PictureSelector.create(fragment)
                .openGallery(PictureMimeType.ofImage())
                .maxSelectNum(maxCount)// 最大图片选择数量
                .selectionMode(PictureConfig.MULTIPLE)
                .previewImage(true)
                .isCamera(true)// 是否显示拍照按钮
                .isZoomAnim(true)
                .compress(true)// 是否压缩
                .cropCompressQuality(50)
                .glideOverride(160, 160)// glide 加载宽高，越小图片列表越流畅，但会影响列表图片浏览的清晰度
                .minimumCompressSize(100)// 小于100kb的图片不压缩
                .forResult(requestCode);//结果回调onActivityResult code

    }


    public static void selectMorePicture(Activity context, int maxCount, int requestCode) {

        PictureSelector.create(context)
                .openGallery(PictureMimeType.ofImage())
                .maxSelectNum(maxCount)// 最大图片选择数量
                .selectionMode(PictureConfig.MULTIPLE)
                .previewImage(true)
                .isCamera(true)// 是否显示拍照按钮
                .isZoomAnim(true)
                .compress(true)// 是否压缩
                .cropCompressQuality(50)
                .glideOverride(160, 160)// glide 加载宽高，越小图片列表越流畅，但会影响列表图片浏览的清晰度
                .minimumCompressSize(100)// 小于100kb的图片不压缩
                .forResult(requestCode);//结果回调onActivityResult code

    }
}
