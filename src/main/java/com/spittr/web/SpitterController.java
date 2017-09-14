package com.spittr.web;

import com.spittr.annotation.Authorization;
import com.spittr.exception.spitter.SpitterNotFoundException;
import com.spittr.manager.TokenManager;
import com.spittr.pojo.BaseResponse;
import com.spittr.pojo.Spitter;
import com.spittr.service.SpitterService;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.sql.Timestamp;
import java.util.Date;

@RestController
@RequestMapping(value = {"/spitter"})
public class SpitterController {
    private final org.slf4j.Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    private SpitterService spitterService;
    @Autowired
    private TokenManager tokenManager;

    @RequestMapping(value = "/{param}", method = RequestMethod.GET)
    public BaseResponse<Spitter> getSpitterProfile(@PathVariable String param,
                                                   HttpServletRequest request) {
        try {
            String s = null;
            s.length();
        } catch (Exception e) {
            throw new DuplicateKeyException("data access exception");
        }

        param = param.trim();
        Spitter spitter;
        if (spitterService.validateStringForUsernamePurpose(param)) {
            spitter = spitterService.getProfileByUsername(param, tokenManager.getValidUsername(request));
        } else {
            spitter = spitterService.getProfileById(param, tokenManager.getValidUsername(request));
        }
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

    /**
     * a spitter request to disable the user account.
     * @param request
     * @return
     */
    @Authorization
    @RequestMapping(value = "/{username}/disable", method = RequestMethod.PATCH)
    public BaseResponse<Spitter> disable(HttpServletRequest request) {
        String username = tokenManager.getValidUsername(request);
        logger.info("disable spitter: " + username);
        spitterService.updateEnabledStatus(username, false);
        return new BaseResponse<Spitter>(spitterService.getProfileByUsername(username),  "disable successfully");
    }

    /**
     * a spitter request to enable the user account.
     * A account in disabled status, user can login. But if user want to use it as normal, he must enable the account.
     * @param username
     * @return
     */
    @Authorization
    @RequestMapping(value = "/{username}/enable", method = RequestMethod.PATCH)
    public BaseResponse<Spitter> enable(@PathVariable String username) {
        logger.info("enable spitter: " + username);
        spitterService.updateEnabledStatus(username, true);
        return new BaseResponse<Spitter>(spitterService.getProfileByUsername(username), "enable successfully");
    }

    /**
     * update spitter's avatar.
     * @param avatar avatar url string.
     * @param request
     * @return
     */
    @Authorization
    @RequestMapping(value = "/{username}/avatar", method = RequestMethod.POST)
    public BaseResponse<Spitter> updateAvatar(@RequestParam("avatar") String avatar, HttpServletRequest request) {
        String username = tokenManager.getValidUsername(request);
        logger.info(username + " try to update avatar");
        spitterService.updateAvatar(username, avatar);
        return new BaseResponse<Spitter>(spitterService.getProfileByUsername(username), "update avatar successfully");
    }
}
