package com.spittr.web;

import com.spittr.annotation.Authorization;
import com.spittr.enums.AttachmentType;
import com.spittr.pojo.BaseResponse;
import com.spittr.pojo.File;
import com.spittr.service.TransferPartService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.method.annotation.StreamingResponseBody;

import javax.servlet.http.Part;
import java.io.IOException;
import java.io.OutputStream;

@RestController
public class TransferController {
    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    private TransferPartService transferPartService;

    @Authorization
    @RequestMapping(value = "/upload", method = RequestMethod.POST)
    public BaseResponse<File> upload(@RequestPart("data") Part part) {
        logger.info("upload");
        File file = transferPartService.transfer(part);
        if (file == null) {
            return new BaseResponse<File>(null, "upload file failed.");
        } else {
            return new BaseResponse<File>(file, "upload file successfully.");
        }
    }

    @RequestMapping(value = "/download/{filetype}/{filename}", method = RequestMethod.GET)
    public StreamingResponseBody download(@PathVariable("filetype") final int filetype,
                                          @PathVariable("filename") final String filename) {
        logger.info("download: " + filetype + " " + filename);
        return new StreamingResponseBody() {
            public void writeTo(OutputStream outputStream) throws IOException {
                transferPartService.download(outputStream, AttachmentType.newAttachmentType(filetype), filename);
            }
        };
    }
}
