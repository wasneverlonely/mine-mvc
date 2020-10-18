package com.was.core.utils;

import android.annotation.TargetApi;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Matrix;
import android.graphics.PixelFormat;
import android.graphics.drawable.Drawable;
import android.media.ThumbnailUtils;
import android.os.Build;
import android.renderscript.Allocation;
import android.renderscript.Element;
import android.renderscript.RSRuntimeException;
import android.renderscript.RenderScript;
import android.renderscript.ScriptIntrinsicBlur;

/**
 * bitmap 工具类
 */
public class BitmapUtils {

    private BitmapUtils() {

    }

    /**
     * drawable 转化 Bitmap
     *
     * @param drawable
     * @return
     */
    public static Bitmap drawableToBitmap(Drawable drawable) {

        Bitmap bitmap = Bitmap.createBitmap(
                drawable.getIntrinsicWidth(),
                drawable.getIntrinsicHeight(),
                drawable.getOpacity() != PixelFormat.OPAQUE ? Bitmap.Config.ARGB_8888
                        : Bitmap.Config.RGB_565);
        Canvas canvas = new Canvas(bitmap);
        drawable.setBounds(0, 0, drawable.getIntrinsicWidth(), drawable.getIntrinsicHeight());
        drawable.draw(canvas);
        return bitmap;
    }


    /**
     * 创建视频流的第一帧图片
     *
     * @param filePath
     * @param kind
     * @return
     */
    public static Bitmap createVideoThumbnail(String filePath, int kind) {
        return ThumbnailUtils.createVideoThumbnail(filePath, kind);
    }

    /**
     * 等比例缩放bitmap
     *
     * @param bitmap
     * @param frameWidth
     * @param frameHeight
     * @return
     */
    public static Bitmap scaleBitmap(Bitmap bitmap, int frameWidth, int frameHeight) {
        if (bitmap == null) {
            return null;
        }
        float scale = 1.0f;
        int bitmapWidth = bitmap.getWidth();
        int bitmapHeight = bitmap.getHeight();

        // 图片的宽度大于控件的宽度   高度小于控件高度
        if (bitmapWidth > frameWidth && bitmapHeight < frameHeight) {
            scale = frameWidth * 1.0f / bitmapWidth;
        }
        // 图片的宽度小于控件的宽度   高度大于控件高度
        if (bitmapWidth < frameWidth && bitmapHeight > frameHeight) {
            scale = frameHeight * 1.0f / bitmapHeight;
        }
        //图片的宽度小于控件的宽度   高度大于控件高度
        if ((bitmapWidth > frameWidth && bitmapHeight > frameHeight) || (bitmapWidth < frameWidth && bitmapHeight < frameHeight)) {
            scale = Math.min(frameWidth * 1.0f / bitmapWidth, frameHeight * 1.0f / bitmapHeight);
        }
        L.e("图片放大比例" + scale);
        Matrix matrix = new Matrix();
        matrix.postScale(scale, scale);
        // 得到新的图片
        Bitmap newBitmap = Bitmap.createBitmap(bitmap, 0, 0, bitmapWidth, bitmapHeight, matrix, true);
        return newBitmap;
    }


    /**
     * 图片模糊   版本号必须大于  17   0 < radius <= 25)   数值越大 越模糊
     *
     * @param context
     * @param bitmap
     * @param radius
     * @return
     */
    public static Bitmap blurBitmap(Context context, Bitmap bitmap, int radius) {
        if (Build.VERSION.SDK_INT > Build.VERSION_CODES.JELLY_BEAN_MR1) {
            RenderScript rs = RenderScript.create(context);
            Bitmap output = Bitmap.createBitmap(bitmap.getWidth(), bitmap.getHeight(), Bitmap.Config.ARGB_8888);
            Allocation inAlloc = Allocation.createFromBitmap(rs, bitmap, Allocation.MipmapControl.MIPMAP_NONE,
                    Allocation.USAGE_GRAPHICS_TEXTURE);
            Allocation outAlloc = Allocation.createFromBitmap(rs, output);
            ScriptIntrinsicBlur script = ScriptIntrinsicBlur.create(rs, inAlloc.getElement()); // Element.U8_4(rs));
            script.setRadius(radius);
            script.setInput(inAlloc);
            script.forEach(outAlloc);
            outAlloc.copyTo(output);
            rs.destroy();
            return output;
        } else {
            return null;
        }
    }

    /**
     * 高斯模糊
     *
     * @param context
     * @param bitmap
     * @param radius
     * @return
     * @throws RSRuntimeException
     */
    @Deprecated
    @TargetApi(Build.VERSION_CODES.JELLY_BEAN_MR2)
    public static Bitmap gaussFuzzy(Context context, Bitmap bitmap, int radius) throws RSRuntimeException {
        RenderScript rs = null;
        try {
            rs = RenderScript.create(context);
            rs.setMessageHandler(new RenderScript.RSMessageHandler());
            Allocation input =
                    Allocation.createFromBitmap(rs, bitmap, Allocation.MipmapControl.MIPMAP_NONE,
                            Allocation.USAGE_SCRIPT);
            Allocation output = Allocation.createTyped(rs, input.getType());
            ScriptIntrinsicBlur blur = ScriptIntrinsicBlur.create(rs, Element.U8_4(rs));

            blur.setInput(input);
            blur.setRadius(radius);
            blur.forEach(output);
            output.copyTo(bitmap);
        } finally {
            if (rs != null) {
                rs.destroy();
            }
        }
        return bitmap;
    }

}
