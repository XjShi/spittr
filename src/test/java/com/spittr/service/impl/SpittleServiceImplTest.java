package com.spittr.service.impl;

import com.spittr.config.RootConfig;
import com.spittr.exception.NoPermissionException;
import com.spittr.pojo.Spittle;
import com.spittr.service.SpittleService;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.util.Comparator;
import java.util.Iterator;
import java.util.List;

import static org.junit.Assert.*;

/**
 * Created by xjshi.
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = {RootConfig.class})
public class SpittleServiceImplTest {
    @Autowired
    private SpittleService spittleService;

    @Test
    public void getSpittleDetail() throws Exception {
        Spittle spittle = spittleService.getSpittleDetail(3);
        assertEquals(3, spittle.getId().longValue());
        assertTrue(spittle.getCommentList() instanceof List);
    }

    @Test
    public void saveSpittle() throws Exception {
        String spittleText = "spittle from unit test.";
        String username = "shixj";
        spittleService.saveSpittle(username, spittleText, null, true);
        assertEquals(spittleText, spittleService.getLastestOne(username).getText());
    }

    @Test
    public void queryIfExistById() throws Exception {
        boolean result = spittleService.queryIfExistById(1);
        assertTrue(result);
        result = spittleService.queryIfExistById(2);
        assertFalse(result);
    }

    @Test
    public void deleteSpittle() throws Exception {
        boolean result = spittleService.deleteSpittle("shixj", 19);
        assertTrue(result);
    }

    @Test(expected = NoPermissionException.class)
    public void deleteSpittleWithNoPermission() throws Exception {
        spittleService.deleteSpittle("shixjj", 19);
    }

    @Test
    public void isSpitterHasChangePermission() throws Exception {
        boolean result = spittleService.isSpitterHasChangePermission("shixj", 10);
        assertTrue(result);
        result = spittleService.isSpitterHasChangePermission("shixjj", 10);
        assertFalse(result);
    }

}