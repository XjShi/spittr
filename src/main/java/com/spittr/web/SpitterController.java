package com.spittr.web;

import com.spittr.annotation.Authorization;
import com.spittr.exception.spitter.SpitterNotFoundException;
import com.spittr.pojo.BaseResponse;
import com.spittr.pojo.Spitter;
import com.spittr.service.SpitterService;
import com.spittr.service.TransferPartService;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.sql.Timestamp;
import java.util.Date;

@RestController
@RequestMapping(value = {"/spitter"})
public class SpitterController {
    private final org.slf4j.Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    private SpitterService spitterService;
    @Autowired
    private TransferPartService transferPartService;

    @RequestMapping(value = "/{param}", method = RequestMethod.GET)
    public BaseResponse<Spitter> getSpitterProfile(@PathVariable String param) {
        Spitter spitter;
        if (StringUtils.isNumeric(param))
            spitter = spitterService.getProfileById(param);
        else
            spitter = spitterService.getProfileByUsername(param);
        if (spitter == null)
            throw new SpitterNotFoundException();
        return new BaseResponse<Spitter>(spitter, "query profile successfully.");
    }

    @RequestMapping(method = RequestMethod.POST)
    public BaseResponse<Spitter> signup(@RequestParam("username") String username,
                                        @RequestParam("password") String password) {
        logger.info("register spitter: username is " + username + ", password is " + password);
        Spitter spitter = new Spitter(username, password, true, new Timestamp(new Date().getTime()));
        spitterService.save(spitter);
        logger.info("register spitter: " + username + " success.");
        return new BaseResponse<Spitter>(spitter, "spitter " + username + " sign up successfully");
    }

    @Authorization
    @RequestMapping(value = "/{username}/disable", method = RequestMethod.PATCH)
    public BaseResponse<Spitter> disable(@PathVariable String username) {
        logger.info("disable spitter: " + username);
        spitterService.updateEnabledStatus(username, false);
        return new BaseResponse<Spitter>(spitterService.getProfileByUsername(username), "disable successfully");
    }

    @Authorization
    @RequestMapping(value = "/{username}/enable", method = RequestMethod.PATCH)
    public BaseResponse<Spitter> enable(@PathVariable String username) {
        logger.info("enable spitter: " + username);
        spitterService.updateEnabledStatus(username, true);
        return new BaseResponse<Spitter>(spitterService.getProfileByUsername(username), "enable successfully");
    }

    @Authorization
    @RequestMapping(value = "/{username}/avatar", method = RequestMethod.POST)
    public BaseResponse<Spitter> updateAvatar(@PathVariable String username, @RequestParam("avatar") String avatar) {
        logger.info(username + " try to update avatar");
        spitterService.updateAvatar(username, avatar);
        return new BaseResponse<Spitter>(spitterService.getProfileByUsername(username), "update avatar successfully");
    }
}
