package com.spittr.service;

import com.spittr.pojo.Comment;

import java.util.List;

public interface CommentService {
    void comment(String username, long spittleId, String text);
    void deleteCommentById(long commentId);
    List<Comment> getComments(long spittleId);
}
