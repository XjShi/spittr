package com.spittr.exception;

import com.spittr.enums.ResponseCode;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.OK)
public class InvalidParameterException extends AppWideException {
    public InvalidParameterException(String message) {
        super(message, ResponseCode.Invalid_Parameter);
    }
}
