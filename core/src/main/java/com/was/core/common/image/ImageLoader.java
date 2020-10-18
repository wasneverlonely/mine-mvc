package com.was.core.common.image;

import android.content.Context;
import android.widget.ImageView;

import androidx.annotation.DrawableRes;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions;
import com.bumptech.glide.request.RequestOptions;
import com.was.core.R;


/**
 * Created by Administrator on 2016/11/9.
 */
public class ImageLoader {

    public static Context mContext;

    private ImageLoader() {

    }

    /**
     * @param url
     * @param imageView
     * @param options
     */
    public static void loadImager(String url, ImageView imageView, RequestOptions options) {

        Glide.with(imageView)
                .load(url)
                .apply(options)
                .transition(DrawableTransitionOptions.withCrossFade())
                .into(imageView);
    }


    /**
     * 加载图片默认  包含
     *
     * @param url
     * @param imageView
     * @param holderRes
     */
    public static void loadImager(String url, ImageView imageView, @DrawableRes int holderRes) {
        RequestOptions options = new RequestOptions().centerInside()
                .placeholder(holderRes).error(holderRes).fallback(holderRes);

        loadImager(url, imageView, options);
    }


    /**
     * 加载列表
     *
     * @param url
     * @param imageView
     */
    public static void loadListImager(String url, ImageView imageView) {
        loadListImager(url, imageView, R.drawable.placeholder);
    }

    /**
     * 加载列表
     *
     * @param url
     * @param imageView
     * @param holderRes
     */
    public static void loadListImager(String url, ImageView imageView, @DrawableRes int holderRes) {
        RequestOptions options = new RequestOptions().centerCrop()
                .placeholder(holderRes).error(holderRes).fallback(holderRes);
        loadImager(url, imageView, options);
    }

    public static void loadBannerImager(String url, ImageView imageView) {
        loadBannerImager(url, imageView, R.drawable.placeholder);
    }

    /**
     * 加载banner 图片
     *
     * @param url
     * @param imageView
     * @param holderRes
     */
    public static void loadBannerImager(String url, ImageView imageView, @DrawableRes int holderRes) {

        RequestOptions options = new RequestOptions().fitCenter()
                .placeholder(holderRes).error(holderRes).fallback(holderRes);
        loadImager(url, imageView, options);
    }


    /**
     * 加载本地图片
     *
     * @param url
     * @param imageView
     * @param holderRes
     */
    public static void loadLocalImager(String url, ImageView imageView, @DrawableRes int holderRes) {

        Glide.with(imageView.getContext())
                .load(url)
                .apply(RequestOptions.centerInsideTransform().placeholder(holderRes))
                .into(imageView);
    }


    public static void loadCircleCropImager(String url, ImageView imageView) {
        loadCircleCropImager(url, imageView, R.drawable.placeholder);
    }

    /**
     * 加载圆形
     *
     * @param url
     * @param imageView
     * @param holderRes
     */
    public static void loadCircleCropImager(String url, ImageView imageView, int holderRes) {
        RequestOptions options = new RequestOptions().circleCrop()
                .placeholder(holderRes).error(holderRes).fallback(holderRes);

        loadImager(url, imageView, options);
    }

    public static void downloadImage(Context context, String url) {


    }

}
