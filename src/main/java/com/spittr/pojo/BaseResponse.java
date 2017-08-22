package com.spittr.pojo;

import com.spittr.enums.ResponseCode;

public class BaseResponse<T> {
    private int code;
    private String message;
    private T data;

    public BaseResponse(T data) {
        this(data, "");
    }

    public BaseResponse(T data, String message) {
        this(ResponseCode.SUCCESS.getCode(), message, data);
    }

    public BaseResponse(int code, String message, T data) {
        this.code = code;
        this.message = message;
        this.data = data;
    }

    public int getCode() {
        return code;
    }

    public String getMessage() {
        return message;
    }

    public T getData() {
        return data;
    }
}
