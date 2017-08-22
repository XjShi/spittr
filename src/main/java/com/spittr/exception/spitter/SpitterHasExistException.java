package com.spittr.exception.spitter;

import com.spittr.enums.ResponseCode;
import com.spittr.exception.AppWideException;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.OK)
public class SpitterHasExistException extends AppWideException {

    public SpitterHasExistException() {
        super(ResponseCode.HAS_EXISTS);
    }
}
