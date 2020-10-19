package com.was.minemvc.helper;

import android.app.Activity;
import android.content.pm.ActivityInfo;
import android.text.TextUtils;

import com.luck.picture.lib.PictureSelector;
import com.luck.picture.lib.animators.AnimationType;
import com.luck.picture.lib.config.PictureConfig;
import com.luck.picture.lib.config.PictureMimeType;
import com.luck.picture.lib.entity.LocalMedia;
import com.luck.picture.lib.language.LanguageConfig;
import com.luck.picture.lib.listener.OnResultCallbackListener;
import com.was.minemvc.R;
import com.was.minemvc.helper.GlideEngine;

import java.util.List;

/**
 * Created by Administrator on 2018/2/28.
 */


public class PictureSeleteHelper {

    /**
     * 选择单张图片
     *
     * @param context
     * @param resultListener
     */
    public static void selectSinglePicture(Activity context, OnResultCallbackListener<LocalMedia> resultListener) {
        PictureSelector.create(context)
                .openGallery(PictureMimeType.ofImage())
                .imageEngine(GlideEngine.createGlideEngine())
                .theme(R.style.picture_white_style)
                .selectionMode(PictureConfig.SINGLE)// 多选 or 单选  PictureConfig.MULTIPLE PictureConfig.SINGLE
                .isSingleDirectReturn(true)  // 单选模式下是否直接返回，PictureConfig.SINGLE模式下有效
                .isPreviewImage(false)// 是否可预览图片
                .imageSpanCount(4)// 每行显示个数
                .setLanguage(LanguageConfig.CHINESE)
                .setRecyclerAnimationMode(AnimationType.DEFAULT_ANIMATION)// 列表动画效果
                .cutOutQuality(60)// 裁剪输出质量 默认100
                .minimumCompressSize(100).forResult(resultListener);
    }

    /**
     * 选择多张图片
     *
     * @param context
     * @param selectionData
     * @param resultListener
     */
    public static void selectMultiplePicture(Activity context, List<LocalMedia> selectionData, int maxSelectNum, OnResultCallbackListener<LocalMedia> resultListener) {
        PictureSelector.create(context)
                .openGallery(PictureMimeType.ofImage())
                .imageEngine(GlideEngine.createGlideEngine())
                .theme(R.style.picture_white_style)
                .selectionMode(PictureConfig.MULTIPLE)// 多选 or 单选  PictureConfig.MULTIPLE PictureConfig.SINGLE
                .maxSelectNum(maxSelectNum)// 最大图片选择数量
                .selectionData(selectionData)// 是否传入已选图片
                .imageSpanCount(4)// 每行显示个数
                .setLanguage(LanguageConfig.CHINESE)
                .setRecyclerAnimationMode(AnimationType.DEFAULT_ANIMATION)// 列表动画效果
                .cutOutQuality(90)// 裁剪输出质量 默认100
                .minimumCompressSize(100).forResult(resultListener);
    }


    public static void selectMultipleVideo(Activity context, List<LocalMedia> selectionData, int maxSelectNum, OnResultCallbackListener<LocalMedia> resultListener) {
        PictureSelector.create(context)
                .openGallery(PictureMimeType.ofVideo())
                .imageEngine(GlideEngine.createGlideEngine())
                .theme(R.style.picture_white_style)
                .selectionMode(PictureConfig.MULTIPLE)// 多选 or 单选  PictureConfig.MULTIPLE PictureConfig.SINGLE
                .maxSelectNum(maxSelectNum)// 最大图片选择数量
                .selectionData(selectionData)// 是否传入已选图片
                .imageSpanCount(4)// 每行显示个数
                .setLanguage(LanguageConfig.CHINESE)
                .setRecyclerAnimationMode(AnimationType.DEFAULT_ANIMATION)// 列表动画效果
                .forResult(resultListener);
    }


    /**
     * 预览图片
     *
     * @param context
     * @param selectList
     * @param position
     */
    public static void previewPictures(Activity context, List<LocalMedia> selectList, int position) {
        PictureSelector.create(context)
                .themeStyle(R.style.picture_white_style) // xml设置主题
                .imageEngine(GlideEngine.createGlideEngine())// 外部传入图片加载引擎，必传项
                .setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_UNSPECIFIED)// 设置相册Activity方向，不设置默认使用系统
                .isNotPreviewDownload(false)// 预览图片长按是否可以下载
                .openExternalPreview(position, selectList);
    }

    /**
     * 播放视频
     *
     * @param context
     * @param media
     */
    public static void previewVideo(Activity context, LocalMedia media) {
        PictureSelector.create(context)
                .themeStyle(R.style.picture_white_style) // xml设置主题
                .externalPictureVideo(TextUtils.isEmpty(media.getAndroidQToPath()) ? media.getPath() : media.getAndroidQToPath());
    }
}
