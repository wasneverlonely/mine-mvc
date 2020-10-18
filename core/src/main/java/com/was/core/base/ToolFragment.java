package com.was.core.base;


import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.fragment.app.Fragment;

import com.was.core.common.http.ProgressDialogHandler;
import com.was.core.utils.IntentUtils;
import com.was.core.utils.Utils;


/**
 * 工具类
 */
public class ToolFragment extends Fragment {

    /**
     * 事件返回
     *
     * @param ivBack
     * @param click
     */
    protected void setBack(ImageView ivBack, View.OnClickListener click) {
        TitleManager.setBack(ivBack, click);
    }

    /**
     * @param tvTitle
     * @param title
     */
    protected void setTitleText(TextView tvTitle, CharSequence title) {
        TitleManager.setTitleText(tvTitle, title);
    }


    /**
     * 设置标题右边的字体
     *
     * @param tvRight
     * @param rightText
     * @param click
     */
    protected void setTitleRightText(TextView tvRight, CharSequence rightText, View.OnClickListener click) {
        TitleManager.setTitleRightText(tvRight, rightText, click);
    }


    /**
     * 设置标题右边图片
     *
     * @param ivRight
     * @param iconResID
     * @param click
     */
    public void setTitleRightIcon(ImageView ivRight, int iconResID, View.OnClickListener click) {
        TitleManager.setTitleRightIcon(ivRight, iconResID, click);
    }


    /**
     * 设置次级右边图片
     *
     * @param ivSecondRight
     * @param iconResID
     * @param click
     */
    public void setTitleSecondRightIcon(ImageView ivSecondRight, int iconResID, View.OnClickListener click) {
        TitleManager.setTitleSecondRightIcon(ivSecondRight, iconResID, click);
    }


    /**
     * 隐藏标题
     *
     * @param isVisible
     */
    public void setVisibleTitle(View view, int resID, boolean isVisible) {
        RelativeLayout rlTitle = view.findViewById(resID);
        TitleManager.setVisibleTitle(rlTitle, isVisible);
    }


    /**
     * 跳转页面
     *
     * @param activity
     */
    protected void startActivity(Class<? extends Activity> activity) {
        startActivity(activity, null);
    }

    /**
     * 跳转页面
     *
     * @param activity
     * @param bundle
     */
    protected void startActivity(Class<? extends Activity> activity, Bundle bundle) {
        IntentUtils.startActivity(getActivity(), activity, bundle);
    }


    /**
     * 退出
     *
     * @param bundle
     */
    protected void setResultActivity(Bundle bundle) {
        IntentUtils.setResultBundleActivity(getActivity(), bundle);
    }

    /**
     * 加载条的引用
     */
    ProgressDialogHandler mProgressDialogHandler;

    /**
     * 显示加载条
     */
    public void showProgressDialog() {
        if (mProgressDialogHandler != null) {
            dismissProgressDialog();
            mProgressDialogHandler = new ProgressDialogHandler(getContext());
        }
        mProgressDialogHandler.obtainMessage(ProgressDialogHandler.SHOW_PROGRESS_DIALOG).sendToTarget();
    }

    /**
     * 隐藏加载条
     */
    public void dismissProgressDialog() {
        if (mProgressDialogHandler != null) {
            mProgressDialogHandler.obtainMessage(ProgressDialogHandler.DISMISS_PROGRESS_DIALOG).sendToTarget();
            mProgressDialogHandler = null;
        }
    }


    @Override
    public void onStop() {
        super.onStop();
        dismissProgressDialog();
    }

    /**
     * 从资源id中获取数组
     *
     * @param resId
     * @return
     */
    public String[] getResArray(int resId) {
        return getResources().getStringArray(resId);
    }

    /**
     * 得到textView 文本
     *
     * @param tv
     * @return
     */
    public String getTextViewText(TextView tv) {
        return Utils.getTextViewText(tv);
    }

    /**
     * 设置textView 空的占位符
     *
     * @param value
     * @param placeHolder
     */
    public String setEmptyPlaceholder(String value, String placeHolder) {
        return Utils.setEmptyPlaceholder(value, placeHolder);
    }

    /**
     * 检测时候是否为空
     *
     * @param t
     * @param <T>
     * @return
     */
    public <T> boolean isNull(T t) {
        return Utils.isNull(t);
    }

    /**
     * 多参数检测是否为空
     *
     * @param ts
     * @param <T>
     * @return
     */
    public <T> boolean isNulls(T... ts) {
        return Utils.isNulls(ts);
    }


    /**
     * @param t
     * @param <T>
     */
    public <T> void checkNullException(T t) {
        Utils.checkNullException(t);
    }


    /**
     * text 为空就toast提示语句
     *
     * @param text
     * @param hint
     * @return
     */
    public boolean toastTextEmpty(CharSequence text, CharSequence hint) {
        return Utils.toastTextEmpty(text, hint);
    }

    /**
     * textView 文本为空就toast提示语句
     *
     * @param textView
     * @param hint
     * @return
     */
    public boolean toastTextEmpty(TextView textView, CharSequence hint) {
        return Utils.toastTextEmpty(textView, hint);
    }


    /**
     * t 为空就toast提示语句
     *
     * @param t
     * @param hint
     * @param <T>
     * @return
     */
    public <T> boolean toastNull(T t, CharSequence hint) {
        return Utils.toastNull(t, hint);
    }


}
