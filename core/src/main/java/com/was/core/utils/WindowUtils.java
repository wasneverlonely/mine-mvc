package com.was.core.utils;

import android.content.Context;
import android.graphics.Point;
import android.view.WindowManager;

/**
 * 窗口  工具类
 */
public class WindowUtils {


    /**
     * 获取屏幕的宽
     *
     * @param context
     * @return
     */
    public static int getWindowWidth(Context context) {
        WindowManager wm = (WindowManager) context.getSystemService(Context.WINDOW_SERVICE);
        int version = Integer.valueOf(android.os.Build.VERSION.SDK);
        if (version > 13) {
            Point outSize = new Point();
            wm.getDefaultDisplay().getSize(outSize);
            return outSize.x;
        } else {
            return wm.getDefaultDisplay().getWidth();
        }

    }


    /**
     * 获取屏幕的高
     *
     * @param context
     * @return
     */
    public static int getWindowHeight(Context context) {
        WindowManager wm = (WindowManager) context.getSystemService(Context.WINDOW_SERVICE);
        int version = Integer.valueOf(android.os.Build.VERSION.SDK);
        if (version > 13) {
            Point outSize = new Point();
            wm.getDefaultDisplay().getSize(outSize);
            return outSize.y;
        } else {
            return wm.getDefaultDisplay().getHeight();
        }
    }


    /**
     * dp 转化 px
     *
     * @param context
     * @param dp
     * @return
     */
    public static int Dp2Px(Context context, float dp) {
        final float scale = context.getResources().getDisplayMetrics().density;
        return (int) (dp * scale + 0.5f);
    }

    /**
     * px 转化 dp
     *
     * @param context
     * @param px
     * @return
     */
    public static int Px2Dp(Context context, float px) {
        final float scale = context.getResources().getDisplayMetrics().density;
        return (int) (px / scale + 0.5f);
    }
}
