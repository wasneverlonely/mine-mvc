package com.was.core.common.base;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.was.core.common.http.ProgressDialogHandler;
import com.was.core.utils.IntentUtils;
import com.was.core.utils.ToastUtils;
import com.was.core.utils.Utils;


public class BaseToolActivity extends AppCompatActivity {


    /**
     * 事件返回
     *
     * @param resID
     * @param click
     */
    protected void setBack(int resID, View.OnClickListener click) {
        ImageView ivBack = findViewById(resID);
        TitleManager.setBack(ivBack, click);
    }

    /**
     * 设置中间字体
     *
     * @param resID
     * @param title
     */
    protected void setTitleText(int resID, CharSequence title) {
        TextView tvTitle = findViewById(resID);
        TitleManager.setTitleText(tvTitle, title);
    }


    /**
     * 设置右边的字体
     *
     * @param resID
     * @param rightText
     * @param click
     */
    protected void setTitleRightText(int resID, String rightText, View.OnClickListener click) {
        TextView tvTitleRight = findViewById(resID);
        TitleManager.setTitleRightText(tvTitleRight, rightText, click);
    }

    /**
     * 设置右边的图片
     *
     * @param resID
     * @param iconResID
     * @param click
     */
    protected void setTitleRightIcon(int resID, int iconResID, View.OnClickListener click) {
        ImageView ivTitleRightIcon = findViewById(resID);
        TitleManager.setTitleRightIcon(ivTitleRightIcon, iconResID, click);
    }


    /**
     * 设置次级右边图片
     *
     * @param resID
     * @param iconResID
     * @param click
     */
    protected void setTitleSecondRightIcon(int resID, int iconResID, View.OnClickListener click) {
        ImageView ivTitleSecondRightIcon = findViewById(resID);
        TitleManager.setTitleSecondRightIcon(ivTitleSecondRightIcon, iconResID, click);
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
        IntentUtils.startActivity(this, activity, bundle);
    }


    /**
     * @param bundle
     */
    protected void setResultActivity(Bundle bundle) {
        IntentUtils.setResultBundleActivity(this, bundle);
    }

    //加载条的引用
    ProgressDialogHandler mProgressDialogHandler;

    /**
     * 显示加载条
     */
    public void showProgressDialog() {
        if (mProgressDialogHandler != null) {
            dismissProgressDialog();
            mProgressDialogHandler = new ProgressDialogHandler(this);
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
    public boolean showToastTextEmpty(CharSequence text, CharSequence hint) {
        return Utils.toastTextEmpty(text, hint);
    }

    /**
     * textView 文本为空就toast提示语句
     *
     * @param textView
     * @param hint
     * @return
     */
    public boolean showToastTextEmpty(TextView textView, CharSequence hint) {
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
    public <T> boolean showToastNull(T t, CharSequence hint) {
        return Utils.toastNull(t, hint);
    }


    /**
     * 弹出Toast 提示框
     *
     * @param text
     */
    public void showToast(CharSequence text) {
        ToastUtils.showShort(text);
    }

}
