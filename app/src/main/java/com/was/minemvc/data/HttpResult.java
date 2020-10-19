package com.was.minemvc.data;


/**
 * 网络请求返回固定类
 *
 * @param <T>
 */
public class HttpResult<T> {

    private int code;
    private String msg;
    private T content;

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public T getData() {
        return content;
    }

    public void setData(T data) {
        this.content = data;
    }
}
