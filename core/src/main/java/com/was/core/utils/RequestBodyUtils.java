package com.was.core.utils;

import android.text.TextUtils;

import androidx.annotation.NonNull;

import java.io.File;
import java.util.Map;

import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;


public class RequestBodyUtils {

    public static final MediaType MULTIPART_FORM_DATA = MediaType.parse("multipart/form-data;charset=utf-8");
    public static final MediaType APPLICATION_URLENCODED = MediaType.parse("application/x-www-form-urlencoded;charset=utf-8");
    public static final MediaType APPLICATION_JSON = MediaType.parse("application/json;charset=utf-8");
    public static final MediaType TEXT_XML = MediaType.parse("text/xml;charset=utf-8");
    public static final MediaType APPLICATION_OCTET_STREAM = MediaType.parse("application/octet-stream;charset=utf-8");


    /**
     * 创建 part
     *
     * @param key
     * @param value
     * @param isFile
     * @return
     */
    public static MultipartBody.Part createPart(String key, String value, boolean isFile) {
        if (TextUtils.isEmpty(key) || TextUtils.isEmpty(value)) {
            throw new IllegalArgumentException("key  or value not empty");
        }
        if (isFile) {
            File file = new File(value);
            RequestBody body = createFileRequestBody(file.getAbsolutePath());
            return MultipartBody.Part.createFormData(key, file.getName(), body);
        } else {
            RequestBody body = createValueRequestBody(value);
            return MultipartBody.Part.create(body);
        }
    }


    /**
     * 创建 value requestBody
     *
     * @param value
     * @return
     */
    public static RequestBody createValueRequestBody(String value) {
        return MultipartBody.create(APPLICATION_URLENCODED, value);
    }


    /**
     * 创建 value requestBody
     *
     * @param path
     * @return
     */
    public static RequestBody createFileRequestBody(String path) {
        File file = new File(path);
        return createFileRequestBody(file);
    }

    /**
     * 创建 value requestBody
     *
     * @param file
     * @return
     */
    public static RequestBody createFileRequestBody(File file) {
        return MultipartBody.create(APPLICATION_OCTET_STREAM, file);

    }


    /**
     * 上传文件
     *
     * @param param
     * @param file
     * @return
     */
    public static MultipartBody file(String param, File file) {

        MultipartBody.Builder builder = new MultipartBody.Builder()
                .setType(MultipartBody.FORM);

        builder.addFormDataPart(param, file.getName(), RequestBody.create(APPLICATION_OCTET_STREAM, file));
        return builder.build();
    }


    /**
     * 上传文件
     *
     * @param param
     * @param path
     * @return
     */
    public static MultipartBody file(@NonNull String param, @NonNull String path) {
        if (TextUtils.isEmpty(path)) {
            throw new IllegalArgumentException("  path  is  null  ");
        }
        return file(param, new File(path));
    }


    /**
     * 上传文件多个文件
     *
     * @param paramMap
     * @return
     */
    public static MultipartBody files(Map<String, String> paramMap) {

        MultipartBody.Builder builder = new MultipartBody.Builder()
                .setType(MultipartBody.FORM);

        for (Map.Entry<String, String> entry : paramMap.entrySet()) {
            File file = new File(entry.getValue());
            builder.addFormDataPart(entry.getKey(), file.getName(), RequestBody.create(APPLICATION_OCTET_STREAM, file));
        }
        return builder.build();
    }


    /**
     * 混合参数
     *
     * @param fileMap
     * @param valueMap 上传tag   地址
     * @return
     */
    public static MultipartBody blend(Map<String, String> fileMap, Map<String, String> valueMap) {
        MultipartBody.Builder builder = new MultipartBody.Builder()
                .setType(MultipartBody.FORM);

        for (Map.Entry<String, String> entry : fileMap.entrySet()) {
            File file = new File(entry.getValue());
            builder.addFormDataPart(entry.getKey(), file.getName(), RequestBody.create(APPLICATION_OCTET_STREAM, file));
        }

        for (Map.Entry<String, String> entry : valueMap.entrySet()) {
            builder.addFormDataPart(entry.getKey(), entry.getValue());
        }
        return builder.build();
    }
}
