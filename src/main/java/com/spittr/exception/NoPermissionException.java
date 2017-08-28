package com.spittr.exception;

import com.spittr.enums.ResponseCode;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.OK)
public class NoPermissionException extends AppWideException {
    public NoPermissionException() {
        super(ResponseCode.NO_PERMISSION);
    }
    public NoPermissionException(String message) {
        super(message, ResponseCode.NO_PERMISSION);
    }
}
