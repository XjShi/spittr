package com.spittr.exception;

import com.spittr.enums.ResponseCode;

public class AppWideException extends RuntimeException {
    private ResponseCode responseCode;

    public AppWideException() {
        super();
    }

    public AppWideException(ResponseCode responseCode) {
        this.responseCode = responseCode;
    }

    public AppWideException(String message, ResponseCode responseCode) {
        super(message);
        this.responseCode = responseCode;
    }

    public ResponseCode getResponseCode() {
        return responseCode;
    }
}
