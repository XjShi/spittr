package com.spittr.service.impl;

import com.spittr.exception.InvalidParameterException;
import com.spittr.exception.spitter.SpitterHasExistException;
import com.spittr.mapper.SpitterMapper;
import com.spittr.pojo.Spitter;
import com.spittr.pojo.Spittle;
import com.spittr.service.SpitterService;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * Created by xjshi on 30/06/2017.
 */
@Service
public class SpitterServiceImpl implements SpitterService {
    private final org.slf4j.Logger logger = LoggerFactory.getLogger(this.getClass());
    @Autowired
    private SpitterMapper spitterMapper;

    public void save(Spitter spitter) {
        if (spitter.getUsername().length() == 0 || spitter.getPassword().length() == 0) {
            throw new InvalidParameterException("invalid username or password");
        }
        String username = spitter.getUsername();
        if (queryIfExistsByName(username)) {
            logger.info("register spitter: " + username + " has exists.");
            throw new SpitterHasExistException();
        }
        spitterMapper.insertSpitter(spitter);
    }

    public void updateEnabledStatus(String username, boolean enabled) {
        if (username.length() == 0) {
            throw new InvalidParameterException("invalid username");
        }
        Spitter spitter = new Spitter();
        spitter.setUsername(username);
        spitter.setEnabled(enabled);
        spitterMapper.updateEnableStatus(spitter);
    }

    public void updateAvatar(String username, String avatar) {
        if (username.length() == 0) {
            throw new InvalidParameterException("invalid username");
        }
        if (avatar.length() == 0) {
            throw new InvalidParameterException("invalid avatar");
        }
        spitterMapper.updateAvatar(avatar, username);
    }

    public boolean queryIfExistsByName(String username) {
        Integer count = spitterMapper.selectSpitterCountByUsername(username);
        return count != 0;
    }

    public Spitter getProfileById(String id, String validUsername) {
        Spitter spitter = spitterMapper.selectSpitterById(id);
        return getSafeProfile(spitter, validUsername);
    }

    public Spitter getProfileByUsername(String username, String validUsername) {
        Spitter spitter = spitterMapper.selectSpitterByUsername(username);
        return getSafeProfile(spitter, validUsername);
    }

    public Spitter getProfileByUsername(String username) {
        Spitter spitter = getProfileByUsername(username, "");
        spitter.setPassword(null);
        return spitter;
    }

    private Spitter getSafeProfile(Spitter spitter, String validUsername) {
        if (spitter != null && !validUsername.equals(spitter.getUsername())) {
            spitter.setPassword(null);
        }
        return spitter;
    }
}
