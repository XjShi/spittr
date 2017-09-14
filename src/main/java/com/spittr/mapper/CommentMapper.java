package com.spittr.mapper;

import com.spittr.pojo.Comment;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface CommentMapper {
    int insertOne(Comment comment);
    int deleteComment(@Param("username") String username, @Param("commentId") long commentId);
    List<Comment> selectBySpittleId(@Param("spittleId") long spittleId);
    int selectCommentCountByCommentId(@Param("commentId") long commentId);
    int selectCount(@Param("spittleId") long spittleId);
    Comment selectLatestOne(@Param("spittleId") long spittleId, @Param("username") String username);
}
