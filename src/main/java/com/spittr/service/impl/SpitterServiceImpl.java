package com.spittr.service.impl;

import com.spittr.enums.ResponseCode;
import com.spittr.exception.InvalidParameterException;
import com.spittr.exception.spitter.SpitterHasExistException;
import com.spittr.exception.spitter.SpitterNameUnavailableException;
import com.spittr.mapper.SpitterMapper;
import com.spittr.pojo.Spitter;
import com.spittr.service.SpitterService;
import io.jsonwebtoken.lang.Assert;
import org.apache.commons.lang3.StringUtils;
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

    public boolean save(Spitter spitter) {
        if (spitter.getUsername().length() == 0 || spitter.getPassword().length() == 0) {
            throw new InvalidParameterException("invalid username or password");
        }
        String username = spitter.getUsername();
        if (queryIfExistsByName(username)) {
            logger.info("register spitter: " + username + " has exists.");
            throw new SpitterHasExistException();
        }
        return spitterMapper.insertOne(spitter) > 0;
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

    public boolean validateStringForUsernamePurpose(String text) {
        if (StringUtils.containsWhitespace(text)) {
            throw new SpitterNameUnavailableException("username can't contain whitespace", ResponseCode.USERNAME_UNAVAILABLE);
        }
        return text.trim().length() > 0 && !StringUtils.isNumeric(text.trim());
    }

    public boolean queryIfExistsByName(String username) {
        Integer count = spitterMapper.selectCountByUsername(username);
        return count != 0;
    }

    public Spitter getProfileById(String id, String validUsername) {
        Spitter spitter = spitterMapper.selectById(id);
        return getSafeProfile(spitter, validUsername);
    }

    public Spitter getProfileByUsername(String username, String validUsername) {
        Spitter spitter = spitterMapper.selectByUsername(username);
        return getSafeProfile(spitter, validUsername);
    }

    public Spitter getProfileByUsername(String username) {
        Spitter spitter = getProfileByUsername(username, "");
        spitter.setPassword(null);
        return spitter;
    }

    private Spitter getSafeProfile(Spitter spitter, String validUsername) {
        if (spitter != null && (validUsername==null || !validUsername.equals(spitter.getUsername()))) {
            spitter.setPassword(null);
        }
        return spitter;
    }
}
