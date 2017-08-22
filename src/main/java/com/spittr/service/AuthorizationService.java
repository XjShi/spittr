package com.spittr.service;

import com.spittr.pojo.Spitter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public interface AuthorizationService {
    Spitter login(String username, String password, HttpServletResponse response);
    public void logout(String username, HttpServletRequest request, HttpServletResponse response);
}
