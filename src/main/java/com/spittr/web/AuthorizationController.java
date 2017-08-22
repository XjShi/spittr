package com.spittr.web;

import com.spittr.annotation.Authorization;
import com.spittr.exception.spitter.SpitterNotFoundException;
import com.spittr.pojo.BaseResponse;
import com.spittr.pojo.Spitter;
import com.spittr.service.AuthorizationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;

@RestController
@RequestMapping(value = "/auth")
public class AuthorizationController {
    @Autowired
    private AuthorizationService authorizationService;

    @RequestMapping(method = RequestMethod.POST)
    public BaseResponse login(@RequestParam("username") String username,
                              @RequestParam("password") String password,
                              HttpServletResponse response) {
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
    public BaseResponse logout(@PathVariable("username") String username, HttpServletResponse response) {
        authorizationService.logout(username, null, response);
        return new BaseResponse(null, "logout finished.");
    }
}
