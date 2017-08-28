package com.spittr.enums;

public enum ResponseCode {
    FAILED(-1),
    SUCCESS(0),
    Invalid_Parameter(1),
    NOT_FOUND(4),
    HAS_EXISTS(5),
    NO_PERMISSION(6),
    AUTHORIZATION_REQUIRED(999);

    private int code;

    ResponseCode(int code) {
        this.code = code;
    }

    public int getCode() {
        return code;
    }
}