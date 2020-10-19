package com.was.minemvc.common.base;

import android.os.Build;
import android.view.View;

import com.was.core.common.base.BaseToolActivity;
import com.was.core.utils.StatusBarUtils;
import com.was.minemvc.R;

/**
 * 项目基类
 */
public class BaseActivity extends BaseToolActivity {

    /**
     * 返回
     */
    protected void setBack() {
        this.setBack((v) -> {
            onBackPressed();
        });
    }

    /**
     * 设置返回
     *
     * @param click
     */
    protected void setBack(View.OnClickListener click) {
        super.setBack(R.id.iv_common_back, click);
    }

    /**
     * 设置标题
     *
     * @param textRcesID
     */
    protected void setTitleText(int textRcesID) {
        String title = getResources().getString(textRcesID);
        this.setTitleText(title);
    }

    /**
     * 设置中间字体
     *
     * @param title
     */
    protected void setTitleText(CharSequence title) {
        super.setTitleText(R.id.tv_common_title, title);
    }

    /**
     * 设置右边的字体
     *
     * @param rightText
     * @param click
     */
    protected void setTitleRightText(String rightText, View.OnClickListener click) {
        super.setTitleRightText(R.id.tv_common_right, rightText, click);
    }

    /**
     * 设置右边图片
     *
     * @param iconResID
     * @param click
     */
    protected void setTitleRightIcon(int iconResID, View.OnClickListener click) {
        super.setTitleRightIcon(R.id.iv_common_right, iconResID, click);
    }

    /**
     * 设置次级右边图片
     *
     * @param iconResID
     * @param click
     */
    protected void setTitleSecondRightIcon(int iconResID, View.OnClickListener click) {
        super.setTitleSecondRightIcon(R.id.iv_common_second_right, iconResID, click);
    }


    /**
     * 设置蓝色  状态栏
     */
    public void setBlueStatusBar() {
        int color = getResources().getColor(R.color.colorPrimary);
        StatusBarUtils.setColor(this, color, 30);
    }


    /**
     * 设置标题栏  白色
     */
    public void setStatusBar() {

        if (Build.VERSION.SDK_INT > Build.VERSION_CODES.LOLLIPOP) {
            View decorView = getWindow().getDecorView();
            decorView.setSystemUiVisibility(View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN | View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR);
            View contentView = decorView.findViewById(android.R.id.content);

            int statusBarHeight = StatusBarUtils.getStatusBarHeight(this);
            contentView.setPadding(0, statusBarHeight, 0, 0);
        }
    }


    /**
     * @param needOffsetView
     */
    public void setStatusBarForImageView(View needOffsetView) {
        StatusBarUtils.setTranslucentForImageView(this, 20, needOffsetView);
    }


}
