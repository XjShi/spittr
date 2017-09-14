package com.spittr.service.impl;

import com.spittr.config.RootConfig;
import com.spittr.pojo.Comment;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.util.Comparator;
import java.util.List;

import static java.util.stream.Collectors.toList;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

/**
 * Created by xjshi.
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = {RootConfig.class})
public class CommentServiceImplTest {
    @Autowired
    private CommentServiceImpl commentService;

    @Test
    public void comment() throws Exception {
        String text = "comment from unit test";
        Comment comment = commentService.comment("shixj", 15, text);
        assertEquals(text, comment.getText());
        assertEquals("shixj", comment.getUsername());
    }

    @Test
    public void queryIfCommentExistsById() throws Exception {
        boolean result = commentService.queryIfCommentExistsById(29);
        assertTrue(result);
        result = commentService.queryIfCommentExistsById(21);
        assertFalse(result);
    }

    @Test
    public void deleteComment() throws Exception {
        commentService.deleteComment("shixj", 16);
        boolean exists = commentService.queryIfCommentExistsById(16);
        assertFalse(exists);
    }

    @Test
    public void getComments() throws Exception {
        List<Comment> comments = commentService.getComments(1);
        assertEquals(9, comments.size());
    }

    @Test
    public void getCount() throws Exception {
        int count = commentService.getCount(1);
        assertEquals(9, count);
    }

    @Test
    public void getLatestComment() throws Exception {
        Comment comment = commentService.getLatestComment(1, "shixj");
        List<Comment> list = commentService.getComments(1);
        list = list.stream()
                .filter(c -> c.getUsername().equals("shixj"))
                .sorted(Comparator.comparing(Comment::getCreateTime).reversed())
                .collect(toList());
        assertEquals(list.get(0).getText(), comment.getText());
        assertEquals(list.get(0).getCreateTime(), comment.getCreateTime());
    }

}