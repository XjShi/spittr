package com.spittr.web;

import com.spittr.annotation.Authorization;
import com.spittr.manager.TokenManager;
import com.spittr.pojo.BaseResponse;
import com.spittr.pojo.Comment;
import com.spittr.service.CommentService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

/**
 * Created by xjshi.
 */
@RestController
@RequestMapping("/spittle/{id}/comment")
public class CommentController {
    private final Logger logger = LoggerFactory.getLogger(this.getClass());
    @Autowired
    private CommentService commentService;
    @Autowired
    private TokenManager tokenManager;

    @Authorization
    @RequestMapping(method = RequestMethod.POST)
    public BaseResponse<Comment> reply(@PathVariable("id") long spittleId,
                                       @RequestParam("text") String text,
                                       @RequestParam(value = "attachment", required = false) String attachment,
                                       HttpServletRequest request) {
        logger.info("comment spittle[" + spittleId + "]: " + text);
        String username = tokenManager.getValidUsername(request);
        Comment comment = new Comment(username, spittleId, text, attachment);
        comment = commentService.comment(comment);
        return new BaseResponse<Comment>(comment, "comment successfully.");
    }

    @RequestMapping(method = RequestMethod.GET)
    public BaseResponse<List<Comment>> show(@PathVariable("id") long spittleId) {
        logger.info("get all comments of spittle[" + spittleId + "].");
        List<Comment> comments = commentService.getComments(spittleId);
        return new BaseResponse<List<Comment>>(comments, "get comments successfully.");
    }

    @Authorization
    @RequestMapping(value = "/{commentId}", method = RequestMethod.DELETE)
    public BaseResponse deleteComment(@PathVariable("id") long id,
                                      @PathVariable("commentId") long commentId,
                                      HttpServletRequest request) {
        logger.info("delete comment[" + commentId + "] of spittle[" + id + "].");
        String username = tokenManager.getValidUsername(request);
        commentService.deleteComment(username, commentId);
        return new BaseResponse<Object>(null, "delete comment successfully.");
    }
}
