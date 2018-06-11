package com.spittr.service.impl;

import com.spittr.config.RootConfig;
import com.spittr.pojo.Spitter;
import com.spittr.service.SpitterService;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.sql.Timestamp;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

/**
 * Created by xjshi.
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = {RootConfig.class})
public class SpitterServiceImplTest {
    @Autowired
    private SpitterService spitterService;

    private String username = "test";

    @Test
    public void save() throws Exception {
        Spitter spitter = new Spitter(username, "test-password", true, new Timestamp(new java.util.Date().getTime()));
        boolean success = spitterService.save(spitter);
        assertTrue(success);
    }

    @Test
    public void updateEnabledStatus() throws Exception {
        spitterService.updateEnabledStatus(username, false);
        Spitter spitter = spitterService.getProfileByUsername(username);
        Assert.assertEquals(username, spitter.getUsername());
        assertFalse(spitter.isEnabled());
    }

    @Test
    public void updateAvatar() throws Exception {
        String avatar = "http://image.beekka.com/blog/2014/bg2014052201.png";
        spitterService.updateAvatar(username, avatar);
        Spitter spitter= spitterService.getProfileByUsername(username);
        Assert.assertEquals(spitter.getAvatar(), avatar);
    }

    @Test
    public void validateStringForUsernamePurpose() throws Exception {
        boolean result = spitterService.validateStringForUsernamePurpose("abc");
        assertTrue(result);
        result = spitterService.validateStringForUsernamePurpose("");
        assertFalse(result);
        result = spitterService.validateStringForUsernamePurpose("123455");
        assertFalse(result);
    }

    @Test
    public void queryIfExistsByName() throws Exception {
        boolean result = spitterService.queryIfExistsByName("test");
        assertTrue(result);
        result = spitterService.queryIfExistsByName("test-adafsjln9u90");
        assertFalse(result);
    }

}