package com.spittr.mapper;

import com.spittr.pojo.Attachment;

import java.util.List;

public interface SpittleAttachmentMapper {
    int insertBatch(List<Attachment> attachmentList);
    int deleteOne(long id);
}
