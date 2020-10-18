package com.was.core.utils;

import android.content.Context;
import android.graphics.Bitmap;

import java.io.ByteArrayOutputStream;
import java.io.File;

/**
 * 压缩图片工具类
 */
public class Compressor {

    private static volatile Compressor instance;//实体类
    private Context mContext;
    private float maxWidth = 612.0f;//最大宽度
    private float maxHeight = 816.0f;// 最大高度
    private Bitmap.CompressFormat compressFormat = Bitmap.CompressFormat.JPEG;//图片格式
    private Bitmap.Config bitmapConfig = Bitmap.Config.ARGB_8888;//图片类型
    private int quality = 80;// 压缩比例
    private String destinationDirectoryPath;//压缩根目录

    private int qualitySize = 100;// 最多压缩质量大小
    private boolean isQualityCompress = false;//质量压缩


    private Compressor(Context context) {
        this.mContext = context;
        destinationDirectoryPath = SDCardUtils.getCacheDirCompressPath(context);
    }

    /**
     * 得到默认的实体类
     *
     * @param context
     * @return
     */
    public static Compressor getDefault(Context context) {
        if (instance == null) {
            synchronized (Compressor.class) {
                if (instance == null)
                    instance = new Compressor(context);
            }
        }
        return instance;
    }


    /**
     * 压缩文件到图片
     *
     * @param file
     * @return
     */
    public Bitmap compressToBitmap(File file) {
        Bitmap bitmap = ImageUtils.compressSize(file, maxWidth, maxHeight, bitmapConfig);

        if (isQualityCompress) {
            return ImageUtils.compressQuality(bitmap, qualitySize);
        }
        return bitmap;
    }

    /**
     * 压缩文件到字节
     *
     * @param file
     * @return
     */
    public byte[] compressToByte(File file) {

        Bitmap compressBitmap = compressToBitmap(file);

        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        compressBitmap.compress(Bitmap.CompressFormat.JPEG, quality, outputStream);
        return outputStream.toByteArray();
    }

    /**
     * 压缩文件到文件
     *
     * @param file
     * @return
     */
    public File compressToFile(File file) {
        Bitmap compressBitmap = compressToBitmap(file);

        File saveBitmap = ImageUtils.saveBitmap(compressBitmap, destinationDirectoryPath);

        return saveBitmap;
    }

    /**
     * 图片压缩的  builder
     */
    public static class Builder {
        private Compressor compressor;

        public Builder(Context context) {
            compressor = new Compressor(context);
        }

        public Builder setMaxWidth(float maxWidth) {
            compressor.maxWidth = maxWidth;
            return this;
        }

        public Builder setMaxHeight(float maxHeight) {
            compressor.maxHeight = maxHeight;
            return this;
        }

        public Builder setCompressFormat(Bitmap.CompressFormat compressFormat) {
            compressor.compressFormat = compressFormat;
            return this;
        }

        public Builder setBitmapConfig(Bitmap.Config bitmapConfig) {
            compressor.bitmapConfig = bitmapConfig;
            return this;
        }

        public Builder setQuality(int quality) {
            compressor.quality = quality;
            return this;
        }

        public Builder setDestinationDirectoryPath(String destinationDirectoryPath) {
            compressor.destinationDirectoryPath = destinationDirectoryPath;
            return this;
        }

        public Builder setQualitySize(int qualitySize) {
            compressor.qualitySize = qualitySize;
            return this;
        }

        public void setQualityCompress(boolean qualityCompress) {
            compressor.isQualityCompress = qualityCompress;
        }


        public Compressor build() {
            return compressor;
        }
    }
}
