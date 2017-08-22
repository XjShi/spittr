package com.spittr.service.impl;

import com.spittr.exception.InvalidParameterException;
import com.spittr.exception.spitter.SpitterNotFoundException;
import com.spittr.manager.TokenManager;
import com.spittr.mapper.SpitterMapper;
import com.spittr.pojo.Spitter;
import com.spittr.service.AuthorizationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@Service
public class AuthorizationServiceImpl implements AuthorizationService {
    @Autowired
    private SpitterMapper spitterMapper;
    @Autowired
    private TokenManager tokenManager;

    public Spitter login(String username, String password, HttpServletResponse response) {
        if (username.length() == 0 || password.length() == 0) {
            throw new InvalidParameterException("username or password incorrect.");
        }
        Spitter spitter = spitterMapper.selectSpitterByUsernameAndPassword(username, password);
        if (spitter != null) {
            tokenManager.addToken(username, response);
        } else {
            throw new SpitterNotFoundException();
        }
        return spitter;
    }

    public void logout(String username, HttpServletRequest request, HttpServletResponse response) {
        tokenManager.invalidate(username, response);
    }
}
