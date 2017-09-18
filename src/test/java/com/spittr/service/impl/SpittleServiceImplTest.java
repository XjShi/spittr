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
    public void getList() throws Exception {
        List<Spittle> spittleList = spittleService.getList();
        Assert.assertTrue(spittleList instanceof List);
    }

    @Test
    public void getListByUsername() throws Exception {
        List<Spittle> spittles = spittleService.getListByUsername("shixj");
        Iterator<Spittle> iterator = spittles.iterator();
        while (iterator.hasNext()) {
            assertEquals("shixj", iterator.next().getUsername());
        }
    }

    @Test
    public void getSpittleDetail() throws Exception {
        Spittle spittle = spittleService.getSpittleDetail(3);
        assertEquals(3, spittle.getId().longValue());
        assertTrue(spittle.getCommentList() instanceof List);
    }

    @Test
    public void saveSpittle() throws Exception {
        String spittleText = "spittle from unit test.";
        Spittle spittle = new Spittle("shixj", "spittle from unit test.", true);
        spittle = spittleService.saveSpittle(spittle);
        assertEquals(spittleText, spittle.getText());
    }

    @Test
    public void queryIfExistById() throws Exception {
        boolean result = spittleService.queryIfExistById(1);
        assertTrue(result);
        result = spittleService.queryIfExistById(2);
        assertFalse(result);
    }

    @Test
    public void getLastestOne() throws Exception {
        Spittle spittle = spittleService.getLastestOne("shixj");
        List<Spittle> spittleList = spittleService.getListByUsername("shixj");
        spittleList.sort(Comparator.comparing(Spittle::getPublishTime).reversed());
        assertEquals(spittleList.get(0).getPublishTime(), spittle.getPublishTime());
        assertEquals(spittleList.get(0).getText(), spittle.getText());
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