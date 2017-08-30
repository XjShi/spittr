package com.spittr.service.impl;

import com.spittr.mapper.SpittleAttachmentMapper;
import com.spittr.pojo.Attachment;
import com.spittr.service.AttachmentService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AttachmentServiceImpl implements AttachmentService {
    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    private SpittleAttachmentMapper dao;

    public int save(List<Attachment> attachmentList) {
        logger.info("save attachments: " + attachmentList.toString());
        return dao.insertBatch(attachmentList);
    }

    public int deleteOne(long attachmentId) {
        logger.info("delete attachment whose id is " + attachmentId);
        return dao.deleteOne(attachmentId);
    }
}
