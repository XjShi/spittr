package com.spittr.web;

import com.spittr.annotation.Authorization;
import com.spittr.enums.ResponseCode;
import com.spittr.pojo.BaseResponse;
import com.spittr.pojo.Comment;
import com.spittr.pojo.Spittle;
import com.spittr.service.CommentService;
import com.spittr.service.SpittleService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/spittle")
public class SpittleController {
    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    public static final int DEFAULT_SPITTLES_PER_PAGE = 25;
    private int spittlesPerPage = DEFAULT_SPITTLES_PER_PAGE;

    public int getSpittlesPerPage() {
        return spittlesPerPage;
    }

    public void setSpittlesPerPage(int spittlesPerPage) {
        this.spittlesPerPage = spittlesPerPage;
    }

    @Autowired
    private SpittleService spittleService;
    @Autowired
    private CommentService commentService;

    @RequestMapping(method = RequestMethod.GET)
    public BaseResponse<ArrayList<Spittle>> show() {
        return new BaseResponse<ArrayList<Spittle>>(ResponseCode.SUCCESS.getCode(),
                "get spittle list successfully",
                spittleService.getList());
    }

    @RequestMapping(value = "/{username}", method = RequestMethod.GET)
    public BaseResponse<ArrayList<Spittle>> showByUsername(@PathVariable String username) {
        return new BaseResponse<ArrayList<Spittle>>(ResponseCode.SUCCESS.getCode(),
                "get spittle list successfully",
                spittleService.getListByUsername(username));
    }

    @Authorization
    @RequestMapping(method = RequestMethod.POST)
    public BaseResponse<Spittle> post(@RequestParam("username") String username,
                                      @RequestParam("text") String text,
                                      @RequestParam("attachType") int attachType,
                                      @RequestParam("attachContent") String attachContent,
                                      @RequestParam("enabled") boolean enabled) {
        Spittle spittle = new Spittle(username, text, attachType, attachContent, enabled);
        spittleService.saveSpittle(spittle);
        spittle = spittleService.getLastestOne(username);
        return new BaseResponse<Spittle>(spittle);
    }

    @Authorization
    @RequestMapping(value = "/{id}", method = RequestMethod.DELETE)
    public BaseResponse<Spittle> delete(@PathVariable("id") long id) {
        spittleService.deleteSpittle(id);
        return new BaseResponse<Spittle>(null, "delete spittle successfully");
    }

    @Authorization
    @RequestMapping(value = "/{id}/comment", method = RequestMethod.POST)
    public BaseResponse<Spittle> reply(@PathVariable("id") long spittleId,
                                       @RequestParam("text") String text,
                                       @RequestParam("username") String username) {
        logger.info("comment spittle[" + spittleId + "]: " + text);
        commentService.comment(username, spittleId, text);
        return new BaseResponse<Spittle>(null, "comment successfully.");
    }

    @RequestMapping(value = "/{id}/comment", method = RequestMethod.GET)
    public BaseResponse<List<Comment>> show(@PathVariable("id") long spittleId) {
        logger.info("get all comments of spittle[" + spittleId + "].");
        List<Comment> comments = commentService.getComments(spittleId);
        return new BaseResponse<List<Comment>>(comments, "get comments successfully.");
    }

    @Authorization
    @RequestMapping(value = "/{id}/comment/{commentId}", method = RequestMethod.DELETE)
    public BaseResponse deleteComment(@PathVariable("id") long id,
                                      @PathVariable("commentId") long commentId) {
        logger.info("delete comment[" + commentId + "] of spittle[" + id + "].");
        commentService.deleteCommentById(commentId);
        return new BaseResponse(null, "delete comment successfully.");
    }
}
