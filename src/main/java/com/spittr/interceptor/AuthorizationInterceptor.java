package com.spittr.interceptor;

import com.spittr.annotation.Authorization;
import com.spittr.manager.TokenManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import static com.spittr.enums.ResponseCode.AUTHORIZATION_REQUIRED;

@Component
public class AuthorizationInterceptor extends HandlerInterceptorAdapter {
    static final long EXPIRATIONTIME = 1000 * 60 * 60 * 24 * 1; // 1 days
    static final String SECRET = "asdfajslkfjlkanzjfasidfjoalkj099232jkasfaj23klj";
    static final String TOKEN_PREFIX = "Bearer";
    static final String AUTHORIZATION = "Authorization";

    @Autowired
    private TokenManager tokenManager;

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        System.out.println(handler.getClass() + "\t" + handler);
        if (!(handler instanceof HandlerMethod)) {
            return true;
        }
        HandlerMethod handlerMethod = (HandlerMethod) handler;
        Authorization authorizationAnnotation = handlerMethod.getMethod().getAnnotation(Authorization.class);
        if (authorizationAnnotation == null) {
            return true;
        }
        boolean isValid = tokenManager.validate(request, response);
        if (!isValid) {
            response.setStatus(AUTHORIZATION_REQUIRED.getCode());
        }
        return isValid;
    }
}
