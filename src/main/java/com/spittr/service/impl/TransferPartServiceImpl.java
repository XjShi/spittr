package com.spittr.service.impl;

import com.spittr.enums.AttachmentType;
import com.spittr.exception.transfer.TransferPartErrorException;
import com.spittr.pojo.File;
import com.spittr.service.TransferPartService;
import org.apache.commons.codec.digest.DigestUtils;
import org.apache.commons.io.FileSystemUtils;
import org.apache.commons.io.FileUtils;
import org.springframework.stereotype.Service;

import javax.servlet.http.Part;
import java.io.*;
import java.util.Arrays;
import java.util.List;

@Service
public class TransferPartServiceImpl implements TransferPartService {
    private final String basePath = "/Users/xjshi/Downloads/tmp/";

    public File transfer(Part part) {
        if (!checkFreeSpace(part.getSize())) {
            throw new TransferPartErrorException();
        }
        String fileName = DigestUtils.md5Hex(part.getSubmittedFileName());
        AttachmentType type = getAttachmentType(part.getContentType());
        String subDirName = getSubDirName(type).toLowerCase();
        String subDirPath = basePath + subDirName;
        ensureDirectoryExists(subDirPath);

        try {
            part.write(subDirPath + "/" + fileName);
            return new File(fileName, type);
        } catch (IOException e) {
            throw new TransferPartErrorException();
        }
    }

    public void download(OutputStream outputStream, AttachmentType type, String filename) {
        String subDirName = getSubDirName(type);
        String fullPath = basePath + subDirName + "/" + filename;
        java.io.File file = new java.io.File(fullPath);
        if (!file.exists()) {
            return;
        }
        BufferedInputStream inputStream = null;
        try {
            inputStream = new BufferedInputStream(new FileInputStream(file));
            byte[] buffer = new byte[1048576];  //  1M
            while (inputStream.read(buffer) != -1) {
                outputStream.write(buffer);
            }
        } catch (FileNotFoundException e) {

        } catch (IOException e) {

        } finally {
            try {
                if (inputStream != null)
                    inputStream.close();
            } catch (IOException e) {

            }
        }
    }

    private boolean checkFreeSpace(long fileSize) {
        long freeSpace = 0;
        try {
            freeSpace = FileSystemUtils.freeSpaceKb();
        } catch (IOException e) {
            return false;
        }
        return freeSpace - fileSize > FileUtils.ONE_MB;
    }

    private String getSubDirName(AttachmentType attachmentType) {
        return attachmentType.name();
    }

    private AttachmentType getAttachmentType(String contentType) {
        List<String> prefixs = Arrays.asList("image", "audio", "video");
        String prefix = contentType.split("/")[0];
        int index = prefixs.indexOf(prefix);
        return AttachmentType.newAttachmentType(index + 1);
    }

    private boolean ensureDirectoryExists(String path) {
        java.io.File file = new java.io.File(path);
        if (!file.exists()) {
            return file.mkdir();
        }
        return true;
    }
}
