package com.was.core.common.base;

import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;


/**
 * 标题管理类
 */
public class TitleManager {

    /**
     * 设置返回
     *
     * @param ivBack
     * @param click
     */
    public static void setBack(ImageView ivBack, OnClickListener click) {
        setTitleIcon(ivBack, 0, click);
    }

    /**
     * 设置中间字体(标题)
     *
     * @param tvTitle
     * @param titleText
     */
    public static void setTitleText(TextView tvTitle, CharSequence titleText) {
        setTitleText(tvTitle, titleText, null);
    }

    /**
     * 设置右边字体
     *
     * @param tvRight
     * @param rightText
     * @param click
     */
    public static void setTitleRightText(TextView tvRight, CharSequence rightText, OnClickListener click) {
        setTitleText(tvRight, rightText, click);
    }


    /**
     * 设置次级右边字体
     *
     * @param tvSecondRight
     * @param secondRightText
     * @param click
     */
    public static void setTitleSecondRightText(TextView tvSecondRight, CharSequence secondRightText, OnClickListener click) {
        setTitleText(tvSecondRight, secondRightText, click);
    }


    /**
     * 设置右边图片
     *
     * @param tvRight
     * @param rightResId
     * @param click
     */
    public static void setTitleRightIcon(ImageView tvRight, int rightResId, OnClickListener click) {
        setTitleIcon(tvRight, rightResId, click);
    }

    /**
     * 设置次级右边图片
     *
     * @param ivSecondRight
     * @param rightResId
     * @param click
     */
    public static void setTitleSecondRightIcon(ImageView ivSecondRight, int rightResId, OnClickListener click) {
        setTitleIcon(ivSecondRight, rightResId, click);
    }


    /**
     * @param
     * @return void
     * @Description: 设置中间搜索框
     */
    protected static void setTitleSearch(LinearLayout llSearch, TextView tvTitleMiddleSearchHint, String searchHint, OnClickListener click) {
        if (llSearch == null) {
            return;
        }
        llSearch.setVisibility(View.VISIBLE);
        tvTitleMiddleSearchHint.setVisibility(View.VISIBLE);
        tvTitleMiddleSearchHint.setText(searchHint);
        llSearch.setOnClickListener(click);
    }


    /**
     * 隐藏标头
     *
     * @param title
     */
    public static void hint(View title) {
        title.setVisibility(View.GONE);
    }


    /**
     * 设置图片
     *
     * @param imageView
     * @param rightResId
     * @param click
     */
    public static void setTitleIcon(ImageView imageView, int rightResId, OnClickListener click) {
        if (imageView == null)
            return;
        imageView.setVisibility(View.VISIBLE);
        if (rightResId != 0) {
            imageView.setImageResource(rightResId);
        }
        if (click != null) {
            imageView.setOnClickListener(click);
        }
    }

    /**
     * 设置文字
     *
     * @param textView
     * @param text
     * @param click
     */
    public static void setTitleText(TextView textView, CharSequence text, OnClickListener click) {
        if (textView == null)
            return;
        textView.setVisibility(View.VISIBLE);
        if (click != null) {
            textView.setOnClickListener(click);
        }
        textView.setText(text);
    }

    /**
     * @param viewGroup
     * @param isVisible
     */
    public static void setVisibleTitle(ViewGroup viewGroup, boolean isVisible) {
        if (viewGroup == null) {
            return;
        }
        viewGroup.setVisibility(isVisible ? View.VISIBLE : View.GONE);
    }
}
