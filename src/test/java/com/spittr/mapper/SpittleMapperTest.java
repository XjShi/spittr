package com.spittr.mapper;

import com.spittr.config.RootConfig;
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
import java.sql.Timestamp;
import java.util.Date;
import java.util.List;

/**
 * Created by xjshi.
 * 由于 LIKE 在SQL中是保留字，而在hsql中保留字用作列名时必须使用双引号扩起来，而mysql不能正确处理使用双引号扩起来的列名。
 * 完美的方案应该是修改表的列名，不过因为懒，我就不改了。如果你在进行测试时发现提示sql语法错误，可以据此进行相应更改。
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = {RootConfig.class})
@ActiveProfiles("dev")
@Sql(scripts = {"/sql/test-spitter-data.sql", "/sql/test-spittle-data.sql"})
public class SpittleMapperTest {
    @Autowired
    private SpittleMapper mapper;
    @Autowired
    private DataSource dataSource;
    private JdbcTemplate jdbcTemplate;

    @Before
    public void setUp() throws Exception {
        jdbcTemplate = new JdbcTemplate(dataSource);
    }

    @Test
    public void insertSpittle() throws Exception {
        int count = mapper.insertOne("test1", "spittle from unit test, user is test1", null, true);
        Assert.assertEquals(1, count);
        count = JdbcTestUtils.countRowsInTableWhere(jdbcTemplate, "spittle", "username='test1'");
        Assert.assertEquals(2, count);
    }

    @Test
    public void getLatestOne() throws Exception {
        String sql = "select * from spittle where username='test' order by created_at DESC limit 1";
        Spittle expectSpittle = getSpittleUseJdbcTemplate(sql);
        Spittle actualSpittle = mapper.getLatestOne("test");
        Assert.assertEquals(expectSpittle.getId(), actualSpittle.getId());
        Assert.assertEquals(expectSpittle.getUser().getUsername(), actualSpittle.getUser().getUsername());
        Assert.assertEquals(expectSpittle.getText(), actualSpittle.getText());
        Assert.assertEquals(expectSpittle.getPublishTime(), actualSpittle.getPublishTime());
    }

    @Test
    public void deleteSpittleById() throws Exception {
        String sql = "select * from spittle where username='test1'";
        Spittle spittle = getSpittleUseJdbcTemplate(sql);
        Assert.assertNotNull(spittle);
        int count = mapper.deleteSpittleById(spittle.getId());
        Assert.assertEquals(1, count);
        count = JdbcTestUtils.countRowsInTableWhere(jdbcTemplate, "spittle", "username='test1'");
        Assert.assertEquals(0, count);
    }

    @Test
    public void selectSpittleCountById() throws Exception {
        String sql = "select * from spittle where username='test' limit 1";
        Spittle spittle = getSpittleUseJdbcTemplate(sql);
        Assert.assertNotNull(spittle);;
        int count = mapper.selectSpittleCountById(spittle.getId());
        Assert.assertEquals(1, count);
    }

    @Test
    public void selectOne() throws Exception {
        String sql = "select * from spittle where username='test' limit 1";
        Spittle expect = getSpittleUseJdbcTemplate(sql);
        Spittle actual = mapper.selectOne(expect.getId());
        Assert.assertEquals(expect.getText(), actual.getText());
    }

    private Spittle getSpittleUseJdbcTemplate(String sql) {
        Spittle result = jdbcTemplate.query(sql, rs -> {
            Spittle spittle = null;
            if (rs.next()) {
                spittle = new Spittle();
                spittle.setId(rs.getLong("id"));
                spittle.getUser().setUsername(rs.getString("username"));
                spittle.setText(rs.getString("text"));
                spittle.setPublishTime(rs.getTimestamp("created_at"));
            }
            return spittle;
        });
        return result;
    }
}