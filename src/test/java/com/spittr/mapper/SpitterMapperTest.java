package com.spittr.mapper;

import com.spittr.config.RootConfig;
import com.spittr.pojo.Spitter;
import org.junit.Assert;
import org.junit.Before;
import org.junit.BeforeClass;
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

/**
 * Created by xjshi.
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = {RootConfig.class})
@ActiveProfiles("dev")
@Sql(scripts = "/sql/test-spitter-data.sql")
public class SpitterMapperTest {
    @Autowired
    private SpitterMapper mapper;
    @Autowired
    private DataSource dataSource;
    private JdbcTemplate jdbcTemplate;

    private final String username = "test";
    private final String password = "test-password";
    private static String foo = "foo";

    @BeforeClass
    public static void setUpClass() throws Exception {

    }

    @Before
    public void setUp() throws Exception {
        jdbcTemplate = new JdbcTemplate(dataSource);
    }

    @Test
    public void selectById() throws Exception {
        int count = JdbcTestUtils.countRowsInTableWhere(jdbcTemplate, "spitter", "name='test'");
        Assert.assertEquals(1, count);
    }

    @Test
    public void selectByUsername() throws Exception {
        Spitter spitter = mapper.selectByUsername(username);
        Assert.assertEquals(username, spitter.getUsername());
        Assert.assertEquals(password, spitter.getPassword());
    }

    @Test
    public void selectCountByUsername() throws Exception {
        int count = mapper.selectCountByUsername(username);
        int expect = JdbcTestUtils.countRowsInTableWhere(jdbcTemplate, "spitter", "name='test'");
        Assert.assertEquals(expect, count);
    }

    @Test
    public void selectByUsernameAndPassword() throws Exception {
        Spitter spitter = mapper.selectByUsernameAndPassword(username, "test-wrongpassword");
        Assert.assertNull(spitter);
        spitter = mapper.selectByUsernameAndPassword(username, password);
        Assert.assertEquals(username, spitter.getUsername());
        Assert.assertEquals(password, spitter.getPassword());
    }

    @Test
    public void getCountForAuth() throws Exception {
        int count = mapper.getCountForAuth(username, "wrong-password");
        Assert.assertEquals(0, count);
        count = mapper.getCountForAuth(username, password);
        Assert.assertEquals(1, count);
    }

    @Test
    public void insertOne() throws Exception {
        int count = JdbcTestUtils.countRowsInTableWhere(jdbcTemplate, "spitter", "name='test2'");
        Assert.assertEquals(0, count);

        Spitter spitter = new Spitter();
        spitter.setUsername("test2");
        spitter.setPassword("test2-password");
        count = mapper.insertOne(spitter);
        Assert.assertEquals(1, count);

        count = JdbcTestUtils.countRowsInTableWhere(jdbcTemplate, "spitter", "name='test2'");
        Assert.assertEquals(1, count);
    }

    @Test
    public void updateEnableStatus() throws Exception {
        Spitter spitter = new Spitter();
        spitter.setUsername("test");
        spitter.setEnabled(false);
        int count = mapper.updateEnableStatus(spitter);
        Assert.assertEquals(1, count);

        spitter.setEnabled(true);
        count = mapper.updateEnableStatus(spitter);
        Assert.assertEquals(1, count);
    }

    @Test
    public void updateAvatar() throws Exception {
        Spitter spitter = new Spitter();
        spitter.setUsername(username);
        spitter.setAvatar("http://www.thinkgeek.com/images/products/additional/large/htgo_exc_capt_america_hoodie_inuse.jpg");
        int count = mapper.updateEnableStatus(spitter);
        Assert.assertEquals(1, count);
    }

    @Test
    public void deleteOne() throws Exception {
        int count = mapper.deleteOne("test1");
        Assert.assertEquals(1, count);
        count = mapper.deleteOne("test1");
        Assert.assertEquals(0, count);
    }

}