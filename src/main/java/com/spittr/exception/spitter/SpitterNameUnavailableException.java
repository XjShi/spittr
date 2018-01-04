package com.spittr.exception.spitter;

import com.spittr.enums.ResponseCode;
import com.spittr.exception.AppWideException;

/**
 * Created by xjshi.
 */
public class SpitterNameUnavailableException extends AppWideException {
    public SpitterNameUnavailableException(String message, ResponseCode responseCode) {
        super(message, responseCode);
    }
}
