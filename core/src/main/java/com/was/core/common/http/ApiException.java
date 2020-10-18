package com.was.core.common.http;


/**
 * Created by Administrator on 2016/11/23.
 * 接口异常
 */
public class ApiException extends RuntimeException {

    private int resultCode;

    public ApiException(int resultCode, String detailMsg) {
        super(detailMsg);
        this.resultCode = resultCode;
    }

    public int getResultCode() {
        return resultCode;
    }
}
