package com.spittr.mapper;

import com.spittr.config.RootConfig;
import com.spittr.pojo.Comment;
import com.spittr.pojo.Spittle;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.jdbc.JdbcTestUtils;

import javax.sql.DataSource;
import java.util.List;

/**
 * Created by xjshi.
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = {RootConfig.class})
@ActiveProfiles("dev")
@Sql(scripts = {"/sql/test-spitter-data.sql", "/sql/test-spittle-data.sql", "/sql/test-comment-data.sql"})
public class CommentMapperTest {
    @Autowired
    private CommentMapper mapper;
    @Autowired
    private DataSource dataSource;
    private JdbcTemplate jdbcTemplate;

    @Before
    public void setUp() throws Exception {
        jdbcTemplate = new JdbcTemplate(dataSource);
    }

    @Test
    public void insertOne() throws Exception {
        Comment comment = new Comment("test", 1000, "comment from unit test", null);
        int count = mapper.insertOne(comment);
        Assert.assertEquals(1, count);
        count = JdbcTestUtils.countRowsInTable(jdbcTemplate, "comment");
        Assert.assertEquals(2, count);
    }

    @Test
    public void deleteComment() throws Exception {
        int count = mapper.deleteComment("test", 500);
        Assert.assertEquals(1, count);
        count = JdbcTestUtils.countRowsInTable(jdbcTemplate, "comment");
        Assert.assertEquals(0, count);
    }

    @Test
    public void selectBySpittleId() throws Exception {
        Comment comment = mapper.selectBySpittleId(1000).get(0);
        Assert.assertEquals(500, comment.getId());
    }

    @Test
    public void selectCommentCountByCommentId() throws Exception {
        int count = mapper.selectCommentCountByCommentId(500);
        Assert.assertEquals(1, count);
    }

    @Test
    public void selectCount() throws Exception {
        int count = mapper.selectCount(1000);
        Assert.assertEquals(1, count);
    }

    @Test
    public void selectLatestOne() throws Exception {
        String sql = "INSERT INTO comment(id, username, spittle_id, text) VALUES(501, 'test', 1000, 'comment text')";
        jdbcTemplate.execute(sql);
        Comment comment = mapper.selectLatestOne(1000, "test");
        Assert.assertEquals(501, comment.getId());
    }

}