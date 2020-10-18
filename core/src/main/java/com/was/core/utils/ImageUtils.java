package com.was.core.utils;

import android.content.ContentResolver;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.media.ExifInterface;
import android.net.Uri;
import android.os.Environment;
import android.provider.MediaStore;
import android.provider.OpenableColumns;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import java.io.BufferedOutputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;


/**
 * Created by Administrator on 2017/3/16.
 * 操作图片的工具类
 */
public class ImageUtils {

    private ImageUtils() {

    }

    /**
     * 生成时间拼接的名字  后面带图片后缀
     *
     * @param compressFormat
     * @return
     */
    @NonNull
    public static String creatTimeName(Bitmap.CompressFormat compressFormat) {
        return Utils.toStringBuilder(FileUtils.creatTimeName(), ".", "jpg");
    }

    /**
     * uri 生成文件真实路径
     *
     * @param context
     * @param uri
     * @return
     */
    public static String getNameFromUri(Context context, Uri uri) {
        String result = null;
        if (uri.getScheme().equals("content")) {
            Cursor cursor = context.getContentResolver().query(uri, null, null, null, null);
            try {
                if (cursor != null && cursor.moveToFirst()) {
                    result = cursor.getString(cursor.getColumnIndex(OpenableColumns.DISPLAY_NAME));
                }
            } catch (Exception e) {
                e.printStackTrace();
            } finally {
                if (cursor != null) {
                    cursor.close();
                }
            }
        }

        if (result == null) {
            result = uri.getPath();
            int cut = result.lastIndexOf(File.separator);
            if (cut != -1) {
                result = result.substring(cut + 1);
            }
        }
        return result;
    }


    /**
     * 压缩尺寸图片
     *
     * @param file
     * @param maxWidth
     * @param maxHeight
     * @param bitmapConfig
     * @return
     */
    public static Bitmap compressSize(File file, float maxWidth, float maxHeight, Bitmap.Config bitmapConfig) {
        Bitmap bitmap = null;
        String filePath = file.getPath();
        try {
            BitmapFactory.Options options = new BitmapFactory.Options();
            // 开始读入图片，此时把options.inJustDecodeBounds 设回true，即只读边不读内容
            options.inJustDecodeBounds = true;

            Bitmap bmp = BitmapFactory.decodeFile(filePath, options);// 只是拿出来一个bitmap

            int actualHeight = options.outHeight;
            int actualWidth = options.outWidth;

            if (actualWidth <= 0 || actualHeight <= 0) {// 实际高度 和宽度 小于0
                Bitmap bitmap2 = BitmapFactory.decodeFile(filePath);
                actualWidth = bitmap2.getWidth();
                actualHeight = bitmap2.getHeight();
                bitmap2.recycle();
            }

            float imgRatio = (float) actualWidth / actualHeight;
            float maxRatio = maxWidth / maxHeight;

            if (actualHeight > maxHeight || actualWidth > maxWidth) {
                if (imgRatio < maxRatio) {
                    imgRatio = maxHeight / actualHeight;
                    actualWidth = (int) (imgRatio * actualWidth);
                    actualHeight = (int) maxHeight;
                } else if (imgRatio > maxRatio) {
                    imgRatio = maxWidth / actualWidth;
                    actualHeight = (int) (imgRatio * actualHeight);
                    actualWidth = (int) maxWidth;
                } else {
                    actualHeight = (int) maxHeight;
                    actualWidth = (int) maxWidth;
                }
            }
            //setting inSampleSize value allows to load a scaled down version of the original image
            options.inSampleSize = calculateInSampleSize(options, actualWidth, actualHeight);
            //inJustDecodeBounds set to false to load the actual bitmap
            options.inJustDecodeBounds = false;
            //此选项允许Android在内存不足时请求位图内存。
            options.inTempStorage = new byte[16 * 1024];

            bmp = BitmapFactory.decodeFile(filePath, options);// 真正拿图片到内存

            if (bmp == null) {// 如果解析不出来 直接通过流拿
                InputStream inputStream = null;
                inputStream = new FileInputStream(filePath);
                bmp = BitmapFactory.decodeStream(inputStream, null, options);
                inputStream.close();
            }

            bitmap = Bitmap.createBitmap(actualWidth, actualHeight, bitmapConfig);

            float ratioX = actualWidth / (float) options.outWidth;
            float ratioY = actualHeight / (float) options.outHeight;

            Matrix scaleMatrix = new Matrix();
            scaleMatrix.setScale(ratioX, ratioY, 0, 0);

            Canvas canvas = new Canvas(bitmap);
            canvas.setMatrix(scaleMatrix);
            canvas.drawBitmap(bmp, 0, 0, new Paint(Paint.FILTER_BITMAP_FLAG));

            // 调整图片的角度  防止相机拍摄角度出错
            int degree = readPictureDegree(filePath);
            bitmap = getRotateBitmap(bitmap, degree);

        } catch (Exception e) {
            return bitmap != null ? bitmap : BitmapFactory.decodeFile(filePath);
        }
        return bitmap;
    }


    /**
     * 读取图片属性：旋转的角度
     *
     * @param path 图片绝对路径
     * @return degree旋转的角度
     */
    public static int readPictureDegree(String path) {
        int degree = 0;
        try {
            ExifInterface exifInterface = new ExifInterface(path);
            int orientation = exifInterface.getAttributeInt(ExifInterface.TAG_ORIENTATION, ExifInterface.ORIENTATION_NORMAL);
            switch (orientation) {
                case ExifInterface.ORIENTATION_ROTATE_90:
                    degree = 90;
                    break;
                case ExifInterface.ORIENTATION_ROTATE_180:
                    degree = 180;
                    break;
                case ExifInterface.ORIENTATION_ROTATE_270:
                    degree = 270;
                    break;
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return degree;
    }


    /**
     * 得到旋转后的图片
     *
     * @param bitmap
     * @param angle
     * @return
     */
    public static Bitmap getRotateBitmap(Bitmap bitmap, int angle) {
        if (angle <= 0) {
            return bitmap;
        }
        //旋转图片 动作
        Matrix matrix = new Matrix();
        matrix.postRotate(angle);
        // 创建新的图片
        return Bitmap.createBitmap(bitmap, 0, 0,
                bitmap.getWidth(), bitmap.getHeight(), matrix, true);
    }


    /**
     * 质量压缩图片
     *
     * @param originalBitmap
     * @param qualitySize
     * @return
     */
    public static Bitmap compressQuality(Bitmap originalBitmap, int qualitySize) {
        if (originalBitmap == null) {
            return null;
        }
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        int quality = 100;
        originalBitmap.compress(Bitmap.CompressFormat.JPEG, quality, baos);//质量压缩方法，这里100表示不压缩，把压缩后的数据存放到baos中
        while (baos.toByteArray().length > qualitySize * 1024) {
            baos.reset();
            originalBitmap.compress(Bitmap.CompressFormat.JPEG, quality, baos);
            quality -= 10;
            L.e("qualitySize  " + qualitySize + "压缩大小 后  " + FileUtils.getFileSize(baos.toByteArray().length));
        }
        return originalBitmap;
    }


    /**
     * 没有查询到  返回""
     *
     * @param context
     * @param uri
     * @return
     */
    public static String getPathFromUri(Context context, Uri uri) {
        if (uri == null) return "";

        String scheme = uri.getScheme();
        if (scheme != null && ContentResolver.SCHEME_CONTENT.equals(scheme)) {
            Cursor cursor = context.getContentResolver().query(uri, null, null, null, null);
            if (cursor != null && cursor.moveToFirst()) {
                int columnIndex = cursor.getColumnIndex(MediaStore.Images.ImageColumns.DATA);
                String path = columnIndex != -1 ? cursor.getString(columnIndex) : "";
                cursor.close();
                return path;
            }
        } else {
            return uri.getPath();
        }
        return "";
    }


    /**
     * 创建临时文件
     *
     * @param context
     * @param uri
     * @return
     * @throws IOException
     */
    public static File createTempFile(Context context, Uri uri) throws IOException {
        InputStream inputStream = context.getContentResolver().openInputStream(uri);
        String path = getPathFromUri(context, uri);
        String fileName = FileUtils.getFileName(path);
        String fileSuffix = FileUtils.getFileSuffix(path);
        File tempFile = File.createTempFile(fileName, fileSuffix);
        tempFile = FileUtils.rename(tempFile, Utils.toStringBuilder(fileName, fileSuffix));
        tempFile.deleteOnExit();
        FileOutputStream out = new FileOutputStream(tempFile);

        int copy = FileUtils.copy(inputStream, out);
        if (copy == -1) // 输出不成功
            return null;
        return tempFile;
    }


    /**
     * @param context
     * @param bitmap
     * @param parentPath
     */
    public static void saveToSystemGallery(Context context, Bitmap bitmap, String parentPath) {

        if (bitmap == null) {
            return;
        }

        BufferedOutputStream bufferedOs = null;

        try {
            String name = ImageUtils.creatTimeName(Bitmap.CompressFormat.JPEG);
            File file = new File(parentPath, name);
            FileUtils.checkMkdirs(file);
            bufferedOs = new BufferedOutputStream(new FileOutputStream(file));
            bitmap.compress(Bitmap.CompressFormat.JPEG, 100, bufferedOs);

            // 其次把文件插入到系统图库
            MediaStore.Images.Media.insertImage(context.getContentResolver(),
                    file.getAbsolutePath(), name, null);
            // 最后通知图库更新
            context.sendBroadcast(new Intent(Intent.ACTION_MEDIA_SCANNER_SCAN_FILE, Uri.parse(file.getAbsolutePath())));
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } finally {
            try {
                bufferedOs.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    /**
     * 保存到相册
     *
     * @param context
     * @param bitmap
     */
    public static void saveToAlbum(Context context, Bitmap bitmap) {

        if (bitmap == null) {
            return;
        }
        BufferedOutputStream bufferedOs = null;
        String albumPath = Environment.getExternalStorageDirectory().getPath() + "/DCIM/Camera/";
        try {
            String name = ImageUtils.creatTimeName(Bitmap.CompressFormat.JPEG);
            File file = new File(albumPath, name);
            FileUtils.checkMkdirs(file);
            bufferedOs = new BufferedOutputStream(new FileOutputStream(file));
            bitmap.compress(Bitmap.CompressFormat.JPEG, 100, bufferedOs);

            // 其次把文件插入到系统图库
            MediaStore.Images.Media.insertImage(context.getContentResolver(),
                    file.getAbsolutePath(), name, null);
            Uri parse = Uri.parse(file.getAbsolutePath());
            // 最后通知图库更新
            context.sendBroadcast(new Intent(Intent.ACTION_MEDIA_SCANNER_SCAN_FILE, Uri.fromFile(file)));
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } finally {
            try {
                bufferedOs.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }


    /**
     * 保存图片
     *
     * @param bitmap
     * @param parentPath
     * @return
     */
    public static File saveBitmap(Bitmap bitmap, String parentPath) {
        Bitmap.CompressFormat compressFormat = Bitmap.CompressFormat.JPEG;
        return saveBitmap(bitmap, compressFormat, parentPath, ImageUtils.creatTimeName(compressFormat));
    }


    /**
     * 保存图片到本地
     *
     * @param bitmap
     * @param compressFormat
     * @param parentPath
     * @return
     */
    @Nullable
    public static File saveBitmap(Bitmap bitmap, Bitmap.CompressFormat compressFormat, String parentPath, String fileName) {
        FileOutputStream out = null;
        File parentFile = new File(parentPath);
        if (!parentFile.exists()) {
            parentFile.mkdirs();
        }

        File file = new File(parentFile, fileName);
        try {
            out = new FileOutputStream(file);
            bitmap.compress(compressFormat, 100, out);

        } catch (FileNotFoundException e) {
            return null;
        } finally {
            try {
                if (out != null) {
                    out.close();
                }
            } catch (IOException ignored) {
            }
        }
        return file;
    }


    /**
     * 计算 尺寸压缩比例
     *
     * @param options
     * @param reqWidth
     * @param reqHeight
     * @return
     */
    private static int calculateInSampleSize(BitmapFactory.Options options, int reqWidth, int reqHeight) {
        final int height = options.outHeight;
        final int width = options.outWidth;
        int inSampleSize = 1;

        if (height > reqHeight || width > reqWidth) {
            final int heightRatio = Math.round((float) height / (float) reqHeight);
            final int widthRatio = Math.round((float) width / (float) reqWidth);
            inSampleSize = heightRatio < widthRatio ? heightRatio : widthRatio;
        }

        final float totalPixels = width * height;
        final float totalReqPixelsCap = reqWidth * reqHeight * 2;

        while (totalPixels / (inSampleSize * inSampleSize) > totalReqPixelsCap) {
            inSampleSize++;
        }

        return inSampleSize;
    }
}
