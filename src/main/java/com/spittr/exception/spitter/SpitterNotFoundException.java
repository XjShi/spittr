package com.spittr.exception.spitter;

import com.spittr.enums.ResponseCode;
import com.spittr.exception.AppWideException;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.NOT_FOUND)
public class SpitterNotFoundException extends AppWideException {

    public SpitterNotFoundException() {
        super(ResponseCode.NOT_FOUND);
    }
}
