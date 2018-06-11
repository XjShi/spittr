package com.spittr.service.impl;

import com.spittr.exception.InvalidParameterException;
import com.spittr.exception.NoPermissionException;
import com.spittr.exception.spitter.SpitterNotFoundException;
import com.spittr.mapper.SpittleMapper;
import com.spittr.pojo.Page;
import com.spittr.pojo.Spittle;
import com.spittr.service.CommentService;
import com.spittr.service.SpitterService;
import com.spittr.service.SpittleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class SpittleServiceImpl implements SpittleService {
    @Autowired
    private SpittleMapper spittleMapper;
    @Autowired
    private SpitterService spitterService;

    @Override
    public List<Spittle> getListByUsernameAndPage(String username, int pageIndex, int pageSize) {
        if (username != null && username.length() > 0 && !this.queryIfUserExistByUsername(username)) {
            throw new SpitterNotFoundException();
        }
        int start = 0;
        if (pageIndex >= 1 && pageSize > 0) {
            start = (pageIndex - 1) * pageSize;
        }
        return spittleMapper.selectByUsernameAndPage(username, start, pageSize);
    }

    @Override
    public Spittle getSpittleDetail(long spittleId) {
        return spittleMapper.selectOne(spittleId);
    }

    @Override
    public void saveSpittle(String username, String text, String attachment, boolean enabled) {
        if (!this.queryIfUserExistByUsername(username)) {
            throw new SpitterNotFoundException();
        }
        spittleMapper.insertOne(username, text, attachment, enabled);
    }

    @Override
    public boolean queryIfExistById(long spittleId) {
        return spittleMapper.selectSpittleCountById(spittleId) > 0;
    }

    @Override
    public Spittle getLastestOne(String username) {
        if (username.length() == 0)
            throw new InvalidParameterException("invalid username");
        return spittleMapper.getLatestOne(username);
    }

    @Override
    public boolean deleteSpittle(String username, long id) {
        boolean hasPermission = isSpitterHasChangePermission(username, id);
        if (!hasPermission) {
            throw new NoPermissionException("no permission to delete the spittle.");
        }
        spittleMapper.deleteSpittleById(id);
        return true;
    }

    @Override
    public boolean isSpitterHasChangePermission(String username, long id) {
        Spittle spittle = spittleMapper.selectOne(id);
        return spittle.getUser().getUsername().equals(username);
    }

    private boolean queryIfUserExistByUsername(String username) {
        return spitterService.queryIfExistsByName(username);
    }
}
