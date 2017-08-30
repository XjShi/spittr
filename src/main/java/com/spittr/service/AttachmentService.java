package com.spittr.service;

import com.spittr.pojo.Attachment;

import java.util.List;

public interface AttachmentService {
    int save(List<Attachment> attachmentList);
    int deleteOne(long attachmentId);
}
