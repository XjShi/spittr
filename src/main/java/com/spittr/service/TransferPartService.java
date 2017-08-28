package com.spittr.service;

import com.spittr.enums.AttachmentType;
import com.spittr.pojo.File;

import javax.servlet.http.Part;
import java.io.OutputStream;

public interface TransferPartService {
    /**
     * 保存part中的数据
     * @param part
     * @return 如果失败返回null
     */
    File transfer(Part part);

    /**
     * 下载文件
     * @param outputStream
     * @param type
     * @param filename
     */
    void download(OutputStream outputStream, AttachmentType type, String filename);
}
