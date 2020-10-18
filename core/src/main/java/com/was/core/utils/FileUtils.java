package com.was.core.utils;


import android.util.Log;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.text.DecimalFormat;

/**
 * Created by Administrator on 2017/3/16.
 * <p>
 * 操作文件的工具类
 */
public class FileUtils {

    private FileUtils() {
    }

    public static String TAG = "FileUtils";

    /**
     * 判断文件是否存在
     *
     * @param file
     * @return
     */
    public static boolean isFileExists(File file) {
        return file == null && file.exists();
    }


    /**
     * 创建给文件
     *
     * @param file
     */
    public static void fileMkdirs(File file) {
        if (file == null)
            return;

        if (!file.exists())
            file.mkdirs();
    }

    /**
     * 生成时间的名字   yyyyMMddHHmmss + currentDate
     *
     * @return
     */
    public static String creatTimeName() {
        long currentDate = System.currentTimeMillis();
        String formatDate = DateUtils.getFormatDate(currentDate, DateUtils.yyyyMMddHHmmss);
        return String.valueOf(currentDate);
//        return Utils.toStringBuilder(formatDate, "_", String.valueOf(currentDate));
    }


    /**
     * 得到文件名字的后缀   jpg
     *
     * @param path
     * @return
     */
    public static String getFileSuffix(String path) {
        int index = path.lastIndexOf(".");
        if (index == -1) {
            return "";
        }
        String suffix = path.substring(index, path.length());
        return suffix;
    }

    /**
     * 返回  文件名字
     *
     * @param path
     * @return
     */
    public static String getFileName(String path) {
        int start = path.lastIndexOf("/");
        int end = path.lastIndexOf(".");
        if (start == -1 || end == -1) {
            return "";
        }
        String fileName = path.substring(start + 1, end);
        return fileName;
    }

    /**
     * 检测文件名字是否一样   不一样修改成一样的
     *
     * @param file
     * @param newName
     * @return
     */
    static File rename(File file, String newName) {
        File newFile = new File(file.getParent(), newName);
        if (!newFile.equals(file)) {// 两个文件是否是一个路径
            if (newFile.exists()) {// 检测缓存文件是否存在  存在删除
                if (newFile.delete())
                    Log.e(TAG, "Delete old " + newName + " file");
            }
            if (file.renameTo(newFile)) // file 更改成newFile的名字
                Log.d(TAG, "Rename file to " + newName);
        }
        return newFile;
    }

    /**
     * 生成文件 必须是文件
     *
     * @param file
     * @return
     */
    public static File checkMkdirs(File file) {
        File parentFile = file.getParentFile();
        if (!parentFile.exists())
            parentFile.mkdirs();// 生成父目录

        return file;
    }

    /**
     * 拷贝文件
     *
     * @param sourceFile
     * @param targetFile
     * @return
     */
    public static boolean copy(File sourceFile, File targetFile) {
        if (sourceFile == null || targetFile == null) {
            return false;
        }

        checkMkdirs(targetFile); //
        try {
            FileInputStream fileInputStream = new FileInputStream(sourceFile);
            FileOutputStream fileOutputStream = new FileOutputStream(targetFile);
            save(fileInputStream, fileOutputStream);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        return true;
    }


    /**
     * 保存
     *
     * @param inputStream
     * @param outputStream
     * @return
     */
    public static boolean save(InputStream inputStream, OutputStream outputStream) {

        if (inputStream == null || outputStream == null) {
            return false;
        }
        BufferedInputStream bufferedIs = null;
        BufferedOutputStream bufferedOs = null;

        try {
            if (inputStream instanceof BufferedInputStream) {
                bufferedIs = (BufferedInputStream) inputStream;
            } else {
                bufferedIs = new BufferedInputStream(inputStream);
            }
            if (outputStream instanceof BufferedOutputStream) {
                bufferedOs = (BufferedOutputStream) outputStream;
            } else {
                bufferedOs = new BufferedOutputStream(outputStream);
            }

            byte[] buf = new byte[1024 * 4];
            int len;
            while (-1 != (len = bufferedIs.read(buf))) {
                bufferedOs.write(buf, 0, len);
                bufferedOs.flush();
            }
        } catch (FileNotFoundException e) {
            return false;
        } catch (IOException e) {
            return false;
        } finally {
            try {
                if (bufferedIs != null)
                    bufferedIs.close();

                if (bufferedOs != null)
                    bufferedOs.close();

            } catch (IOException e) {
                return false;
            }
        }
        return true;
    }


    /**
     * 输入流 转化输出流
     *
     * @param inputStream
     * @param outputStream
     * @return
     * @throws IOException
     */
    public static int copy(InputStream inputStream, OutputStream outputStream) throws IOException {
        if (inputStream == null || outputStream == null) {
            return -1;
        }

        byte[] buffer = new byte[1024 * 4];
        long count = 0;
        int n;
        while (-1 != (n = inputStream.read(buffer))) {
            outputStream.write(buffer, 0, n);
            count += n;
        }

        inputStream.close();
        outputStream.close();

        if (count > Integer.MAX_VALUE) return -1;
        return (int) count;
    }


    /**
     * 根据一个long 得到文件的大小
     *
     * @param size
     * @return
     */
    public static String getFileSize(long size) {
        if (size <= 0)
            return "0";
        final String[] units = new String[]{"B", "KB", "MB", "GB", "TB"};
        int digitGroups = (int) (Math.log10(size) / Math.log10(1024));
        return new DecimalFormat("#,##0.#").format(size / Math.pow(1024, digitGroups)) + " " + units[digitGroups];
    }

}
