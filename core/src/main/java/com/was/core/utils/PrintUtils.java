package com.was.core.utils;


import android.graphics.Bitmap;
import android.net.Uri;

import java.util.Arrays;
import java.util.List;
import java.util.Map;

import okhttp3.Request;

/**
 * Created by Administrator on 2017/7/3.
 * <p>
 * 打印工具类
 */
public class PrintUtils {

    public static final String TAG = "PrintUtils";

    private static final int SUBSECTION_LENGTH = 4000; //分段打印的最大长度

    private PrintUtils() {

    }

    /**
     * 打印超长文本
     *
     * @param moreContent
     */
    public static void printMoreContent(String moreContent) {
        printMoreContent(TAG, moreContent);
    }


    /**
     * 打印超长文本
     */
    public static void printMoreContent(String tag, String moreContent) {

        if (moreContent.length() > SUBSECTION_LENGTH) { //字符数大于4000 就分段打印
            int count = moreContent.length() / SUBSECTION_LENGTH;

            for (int i = 0; i <= count; i++) {
                int maxLength = SUBSECTION_LENGTH * (i + 1);
                if (maxLength >= moreContent.length()) {
                    L.i(tag, moreContent.substring(SUBSECTION_LENGTH * i));
                } else
                    L.i(tag, moreContent.substring(SUBSECTION_LENGTH * i, maxLength));
            }
        } else
            L.i(tag, moreContent);
    }


    /**
     * 打印网络异常
     *
     * @param e
     */
    public static void printNetRequestException(Throwable e) {

        L.e("print", "------request exception-----\n" + " exception class :  " + e.getClass().getName()
                + "\n" + " exception message : " + e.getMessage());
    }


    /**
     * 打印网络请求的返回
     *
     * @param responseString
     */
    public static void printNetResponse(String responseString) {
        printMoreContent("print", responseString);
    }


    /**
     * 打印请求参数
     *
     * @param request
     * @param paramMap
     */
    public static void printNetRequestParams(Request request, Map<String, String> paramMap) {
        // get url  http://192.168.1.240:8010/api/Members/GetMemberInfo? mid=144118079817908224&timestamp=20180413135119
        // post url  http://192.168.1.240:8010/api/Members/GetMemberInfo

        // 打印请求参数接口
        StringBuilder urlSb = new StringBuilder();
        urlSb.append("----------");
        urlSb.append(request.url().url().toString());
        urlSb.append("----------\n");
        // 装进去 url
        StringBuilder sbLog = new StringBuilder(urlSb);

        if (paramMap.isEmpty()) {
            sbLog.append("----------------no  params    --------------------\n");
        } else {
            String[] keyArray = paramMap.keySet().toArray(new String[0]);
            Arrays.sort(keyArray, String.CASE_INSENSITIVE_ORDER);
            for (String name : keyArray) {
                sbLog.append(name).append(" : ").append(paramMap.get(name)).append("\n");
            }
        }
        sbLog.append("\n");
        PrintUtils.printMoreContent("print", sbLog.toString());
    }


    /**
     * 打印bitmap 信息
     *
     * @param bitmap
     */
    public static void printBitmapInfo(Bitmap bitmap) {
        if (bitmap == null) {
            return;
        }
        int width = bitmap.getWidth();
        int height = bitmap.getHeight();
        long size = bitmap.getHeight() * bitmap.getRowBytes();
        Bitmap.Config config = bitmap.getConfig();
        L.i(TAG, Utils.toStringBuilder("bitmap信息", "宽：", String.valueOf(width), "px,", "高：", String.valueOf(height), "px,", "内存大小：", FileUtils.getFileSize(size)));
    }

    /**
     * 打印 uri
     *
     * @param uri
     */
    public static void printUriInfo(Uri uri) {
        //uri基本形式 [scheme:]scheme-specific-part[#fragment]
        //scheme 方案  scheme-specific-part 方案的具体内容   authority 权威
        //绝对URI：以scheme组件起始的完整格式，如http://fsjohnhuang.cnblogs.com。表示以对标识出现的环境无依赖的方式引用资源。
        //相对URI：不以scheme组件起始的非完整格式，如fsjohnhuang.cnblogs.com。表示以对依赖标识出现的环境有依赖的方式引用资源。
        if (uri == null) {
            return;
        }
        //http://www.java2s.com:8080/yourpath/fileName.htm?stove=10&path=32&id=4#harvic
        String scheme = uri.getScheme();  //  http
        String schemeSpecificPart = uri.getSchemeSpecificPart(); //这里是：//www.java2s.com:8080/yourpath/fileName.htm?
        String fragment = uri.getFragment();// harvic
        String authority = uri.getAuthority();//www.java2s.com:8080
        String query = uri.getQuery();//stove=10&path=32&id=4
        String host = uri.getHost();//www.java2s.com
        int port = uri.getPort();//港口;  8080
        List<String> pathSegments = uri.getPathSegments();//getPath()是把path部分整个获取下
        String queryParameter = uri.getQueryParameter("");//getQuery()获取整个query字段：

        L.i(TAG, Utils.toStringBuilder("uri ---  scheme：", scheme, " schemeSpecificPart：", schemeSpecificPart
                , " authority：", authority, " query：", query));
    }


    /**
     * 打印  数组
     *
     * @param array
     */
    public static String printArrayInfo(String[] array) {

        StringBuilder sb = new StringBuilder();
        for (String str : array) {
            sb.append(str)
                    .append("  |  ");
        }
        L.i(TAG, sb.toString());
        return sb.toString();
    }
}
