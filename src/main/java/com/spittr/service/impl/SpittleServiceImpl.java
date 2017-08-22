package com.spittr.service.impl;

import com.spittr.annotation.Authorization;
import com.spittr.exception.InvalidParameterException;
import com.spittr.exception.spitter.SpitterNotFoundException;
import com.spittr.mapper.SpittleMapper;
import com.spittr.pojo.Spittle;
import com.spittr.service.SpitterService;
import com.spittr.service.SpittleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;

@Service
public class SpittleServiceImpl implements SpittleService {
    @Autowired
    private SpittleMapper spittleMapper;
    @Autowired
    private SpitterService spitterService;

    public ArrayList<Spittle> getList() {
        return spittleMapper.selectAll();
    }

    public ArrayList<Spittle> getListByUsername(String username) {
        if (!this.queryIfUserExistByUsername(username)) {
            throw new SpitterNotFoundException();
        }
        return spittleMapper.selectByUsername(username);
    }

    public Spittle saveSpittle(Spittle spittle) {
        if (!this.queryIfUserExistByUsername(spittle.getUsername())) {
            throw new SpitterNotFoundException();
        }
        spittleMapper.insertSpittle(spittle);
        return spittle;
    }

    public boolean queryIfExistById(long spittleId) {
        return spittleMapper.selectSpittleCountById(spittleId) > 0;
    }

    public Spittle getLastestOne(String username) {
        if (username.length() == 0)
            throw new InvalidParameterException("invalid username");
        return spittleMapper.getLatestOne(username);
    }

    public void deleteSpittle(long id) {
        spittleMapper.deleteSpittleById(id);
    }

    private boolean queryIfUserExistByUsername(String username) {
        return spitterService.queryIfExistsByName(username);
    }
}
