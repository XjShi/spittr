package com.spittr.exception;

import com.spittr.exception.spitter.SpitterHasExistException;
import com.spittr.exception.spitter.SpitterNotFoundException;
import com.spittr.exception.transfer.TransferPartErrorException;
import com.spittr.pojo.BaseResponse;
import com.spittr.pojo.Spitter;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class AppWideExceptionHandler {
    @ExceptionHandler(SpitterNotFoundException.class)
    public BaseResponse<Spitter> spitterNotFound(SpitterNotFoundException e) {
        return new BaseResponse<Spitter>(e.getResponseCode().getCode(), "spitter not found", null);
    }

    @ExceptionHandler(SpitterHasExistException.class)
    public BaseResponse<Spitter> spitterHasExist(SpitterHasExistException e) {
        return new BaseResponse<Spitter>(e.getResponseCode().getCode(), "spitter has exists", null);
    }

    @ExceptionHandler(InvalidParameterException.class)
    public BaseResponse invalidParameter(InvalidParameterException e) {
        return new BaseResponse(e.getResponseCode().getCode(), e.getMessage(), null);
    }

    @ExceptionHandler(TransferPartErrorException.class)
    public BaseResponse transferPartError(TransferPartErrorException e) {
        return new BaseResponse(e.getResponseCode().getCode(), e.getMessage(), null);
    }
}
