package com.spittr.exception.transfer;

import com.spittr.enums.ResponseCode;
import com.spittr.exception.AppWideException;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
public class TransferPartErrorException extends AppWideException {
    public TransferPartErrorException() {
        super("upload failed",ResponseCode.FAILED);
    }
}
