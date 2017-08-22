package com.spittr.mapper;

import com.spittr.pojo.Comment;
import org.apache.ibatis.annotations.Param;

import java.util.ArrayList;

public interface CommentMapper {
    void insertOne(Comment comment);
    void deleteById(@Param("commentId") long commentId);
    ArrayList selectBySpittleId(@Param("spittleId") long spittleId);
    int selectCommentCountByCommentId(@Param("commentId") long commentId);
}
