package com.was.core.common.http.print;

import com.google.gson.Gson;
import com.google.gson.TypeAdapter;
import com.google.gson.reflect.TypeToken;

import java.lang.annotation.Annotation;
import java.lang.reflect.Type;

import okhttp3.RequestBody;
import okhttp3.ResponseBody;
import retrofit2.Converter;
import retrofit2.Retrofit;

/**
 * Created by Administrator on 2016/12/7.
 * 修改一个打印方法  请求到的数据打印
 */
public class PrintGsonConverterFactory extends Converter.Factory {

    private final Gson gson;

    public static PrintGsonConverterFactory create() {
        return create(new Gson());
    }


    public static PrintGsonConverterFactory create(Gson gson) {
        return new PrintGsonConverterFactory(gson);
    }


    private PrintGsonConverterFactory(Gson gson) {
        if (gson == null) throw new NullPointerException("gson == null");
        this.gson = gson;
    }

    @Override
    public Converter<ResponseBody, ?> responseBodyConverter(Type type, Annotation[] annotations,
                                                            Retrofit retrofit) {
        TypeAdapter<?> adapter = gson.getAdapter(TypeToken.get(type));
        return new PrintGsonResponseBodyConverter<>(gson, adapter);
    }

    @Override
    public Converter<?, RequestBody> requestBodyConverter(Type type,
                                                          Annotation[] parameterAnnotations, Annotation[] methodAnnotations, Retrofit retrofit) {
        TypeAdapter<?> adapter = gson.getAdapter(TypeToken.get(type));
        return new PrintGsonRequestBodyConverter<>(gson, adapter);
    }

}
