package com.was.core.common.http;

import com.was.core.utils.PrintUtils;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import okhttp3.FormBody;
import okhttp3.HttpUrl;
import okhttp3.Interceptor;
import okhttp3.MultipartBody;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

/**
 * 请求数据打印
 */
public class PrintReqeustInterceptor implements Interceptor {

    public final String GET = "GET";
    public final String POST = "POST";

    private static boolean isParamsDebug = true;// 是否输出请求参数

    @Override
    public Response intercept(Chain chain) throws IOException {
        Request request = chain.request();
        Map<String, String> paramMap = getParameterMap(request); // 获取参数
        printParamsLog(request, paramMap);// log打印网络请求参数
        Request signRequest = request.newBuilder().build();
        Response response = chain.proceed(signRequest);

        return response;
    }


    /**
     * 获取get post 请求参数 然后装进map
     *
     * @param request
     * @return
     */
    public Map<String, String> getParameterMap(Request request) {
        String method = request.method(); //获取请求类型
        if (method.equals(GET)) { // get请求
            return getGetReuqestParameter(request);
        } else if (method.equals(POST)) { // post请求
            return getPostRequestBody(request);
        }
        return new HashMap<>();
    }


    /**
     * 得到get请求参数
     *
     * @param request
     * @return
     */
    public Map<String, String> getGetReuqestParameter(Request request) {
        Map<String, String> parameterMap = new HashMap<>();
        HttpUrl url = request.url();
        for (int i = 0; i < url.querySize(); i++) {
            String encodedName = url.queryParameterName(i);
            String encodedValue = url.queryParameterValue(i);
            parameterMap.put(encodedName, encodedValue);
        }
        return parameterMap;
    }


    /**
     * 得到 post  body
     *
     * @param request
     * @return
     */
    public Map<String, String> getPostRequestBody(Request request) {
        Map<String, String> parameterMap = new HashMap<>();

        RequestBody body = request.body();
        if (body != null && body instanceof FormBody) {
            FormBody formBody = (FormBody) body;
            for (int i = 0; i < formBody.size(); i++) {
                String encodedName = formBody.encodedName(i);
                String encodedValue = HttpCodeUtils.decode(formBody.encodedValue(i));
                parameterMap.put(encodedName, encodedValue);
            }
        } else if (body != null && body instanceof MultipartBody) {
            MultipartBody multipartBody = (MultipartBody) body;
            List<MultipartBody.Part> parts = multipartBody.parts();
            for (MultipartBody.Part part : parts) {
            }
        }
        return parameterMap;
    }


    /**
     * @param name
     */
    public boolean interceptPrint(String name) {
        return true;
    }


    /**
     * log打印网络请求参数
     *
     * @param request
     * @param paramMap
     */
    private void printParamsLog(Request request, Map<String, String> paramMap) {
        if (isParamsDebug && isLimitPrint(request)) {
            PrintUtils.printNetRequestParams(request, paramMap);
        }
    }

    private boolean isLimitPrint(Request request) {
//        List<String> pathSegments = request.url().pathSegments();
//        int size = pathSegments.size();
//        if (pathSegments.get(size - 1).equals("queryUnreadMessageCount")) {
//            return true;
//        }
        return true;
    }

}
