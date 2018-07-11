package com.spittr.service.impl;

import com.spittr.enums.AttachmentType;
import com.spittr.exception.transfer.TransferPartErrorException;
import com.spittr.pojo.File;
import com.spittr.service.TransferPartService;
import org.apache.commons.codec.digest.DigestUtils;
import org.apache.commons.io.FileSystemUtils;
import org.apache.commons.io.FileUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Service;

import javax.servlet.http.Part;
import java.io.*;
import java.util.Arrays;
import java.util.List;

@Service
@PropertySource("classpath:application.properties")
public class TransferPartServiceImpl implements TransferPartService {
    private final Logger logger = LoggerFactory.getLogger(this.getClass());
    @Autowired
    private Environment env;

    public File transfer(Part part) {
        if (!checkFreeSpace(part.getSize())) {
            throw new TransferPartErrorException();
        }
        String fileName = getHashedFilename(part.getSubmittedFileName());
        AttachmentType type = getAttachmentType(part.getContentType());
        String subDirName = getSubDirName(type).toLowerCase();
        String subDirPath = getBasePath() + subDirName;
        ensureDirectoryExists(subDirPath);
        logger.info("upload file subDirPath: " + subDirPath);

        try {
            part.write(subDirPath + "/" + fileName);
            return new File(fileName, type);
        } catch (IOException e) {
            throw new TransferPartErrorException();
        }
    }

    public void download(OutputStream outputStream, AttachmentType type, String filename) {
        String subDirName = getSubDirName(type);
        String fullPath = getBasePath() + subDirName + "/" + filename;
        java.io.File file = new java.io.File(fullPath);
        if (!file.exists()) {
            throw new RuntimeException("file not found: " + filename);
        }
        BufferedInputStream inputStream = null;
        try {
            inputStream = new BufferedInputStream(new FileInputStream(file));
            byte[] buffer = new byte[1048576];  //  1M
            while (inputStream.read(buffer) != -1) {
                outputStream.write(buffer);
            }
        } catch (FileNotFoundException e) {
            throw new RuntimeException("file not found: " + filename);
        } catch (IOException e) {
            throw new RuntimeException("io error: " + e.getMessage());
        } finally {
            try {
                if (inputStream != null)
                    inputStream.close();
            } catch (IOException e) {
                throw new RuntimeException();
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

    private String getHashedFilename(String originalFilename) {
        String[] comp = originalFilename.split("\\.");
        String name = DigestUtils.md5Hex(comp[0]);
        String extension = "";
        if (comp.length >= 2) {
            extension = "." + comp[1];
        }
        return name + extension;
    }

    private String getSubDirName(AttachmentType attachmentType) {
        return attachmentType.name().toLowerCase();
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
            return file.mkdirs();
        }
        return true;
    }

    private String getBasePath() {
        return env.getProperty("uploadfile.path");
    }
}
