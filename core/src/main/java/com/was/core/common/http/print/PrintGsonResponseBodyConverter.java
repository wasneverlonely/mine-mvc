package com.was.core.common.http.print;

import com.google.gson.Gson;
import com.google.gson.TypeAdapter;
import com.was.core.utils.PrintUtils;

import java.io.IOException;

import okhttp3.ResponseBody;
import retrofit2.Converter;

/**
 * Created by Administrator on 2016/12/7.
 */
public class PrintGsonResponseBodyConverter<T> implements Converter<ResponseBody, T> {


    private final Gson gson;
    private final TypeAdapter<T> adapter;

    PrintGsonResponseBodyConverter(Gson gson, TypeAdapter<T> adapter) {
        this.gson = gson;
        this.adapter = adapter;
    }

    @Override
    public T convert(ResponseBody value) throws IOException {

        String responseValue = value.string();
        try {
            PrintUtils.printNetResponse(responseValue);
            return adapter.fromJson(responseValue);
        } finally {
            value.close();
        }
    }
}
