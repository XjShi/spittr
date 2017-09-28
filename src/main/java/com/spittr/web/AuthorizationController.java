package com.spittr.web;

import com.spittr.annotation.Authorization;
import com.spittr.exception.spitter.SpitterNotFoundException;
import com.spittr.pojo.BaseResponse;
import com.spittr.pojo.Spitter;
import com.spittr.service.AuthorizationService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;

@RestController
@RequestMapping(value = "/auth")
public class AuthorizationController {
    private final Logger logger = LoggerFactory.getLogger(this.getClass());
    @Autowired
    private AuthorizationService authorizationService;

    @RequestMapping(method = RequestMethod.POST)
    public BaseResponse<Spitter> login(@RequestParam("username") String username,
                                       @RequestParam("password") String password,
                                       HttpServletResponse response) {
        logger.info("auth username: " + username + ", password: " + password + ".");
        Spitter spitter;
        try {
            spitter = authorizationService.login(username, password, response);
        } catch (SpitterNotFoundException e) {
            return new BaseResponse(null, "username or password incorrent.");
        }
        return new BaseResponse(spitter, "login successfully.");
    }

    @Authorization
    @RequestMapping(value = "/{username}", method = RequestMethod.DELETE)
    public BaseResponse<?> logout(@PathVariable("username") String username, HttpServletResponse response) {
        logger.info("logout: " + username);
        authorizationService.logout(username, null, response);
        return new BaseResponse(null, "logout finished.");
    }
}
