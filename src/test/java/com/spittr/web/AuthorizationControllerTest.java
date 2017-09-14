package com.spittr.web;

import org.hamcrest.Description;
import org.hamcrest.Matcher;
import org.junit.Before;
import org.junit.Test;
import org.springframework.http.HttpStatus;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.ResultHandler;

import static com.spittr.enums.ResponseCode.AUTHORIZATION_REQUIRED;
import static com.spittr.manager.TokenManager.AUTHORIZATION;
import static com.spittr.manager.TokenManager.TOKEN_PREFIX;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * Created by xjshi.
 */
public class AuthorizationControllerTest extends BaseTest {
    private String username = "elgae";
    private String password = "elgae_password";
    private static String token;

    @Before
    public void setup() throws Exception {
        super.setup();
    }

    @Test
    public void login() throws Exception {
        mockMvc.perform(post("/auth").param("username", username).param("password", password))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.code").value(0))
                .andExpect(jsonPath("$.data.username").value(username))
                .andExpect(jsonPath("$.data.password").value(password))
                .andExpect(header().string(AUTHORIZATION, new Matcher<String>() {
                    public boolean matches(Object o) {
                        String str = (String) o;
                        return str.startsWith(TOKEN_PREFIX);
                    }

                    public void describeMismatch(Object o, Description description) {
                        description.appendText("was ").appendValue(o);
                    }

                    public void _dont_implement_Matcher___instead_extend_BaseMatcher_() {

                    }

                    public void describeTo(Description description) {

                    }
                }))
                .andDo(new ResultHandler() {
                    public void handle(MvcResult mvcResult) throws Exception {
                        token = mvcResult.getResponse().getHeader(AUTHORIZATION);
                        System.out.println("login produce token : " + token);
                    }
                })
                .andDo(print());
    }

    @Test
    public void logout() throws Exception {
        token = "Bearer eyJhbGciOiJIUzUxMiJ9.eyJzdWIiOiJlbGdhZSIsImV4cCI6MTUwNTQwODk0Mn0.mgvy8XoQryI4Vysm3FsSF4sZFec4o67s3GpHC3U3Y-FAXuZvAEfqJEAQHQqfRutdecoFUz1qPeRRfsLq-wKi_g";
        mockMvc.perform(delete("/auth/{username}", username).header(AUTHORIZATION, token))
                .andExpect(status().is(new Matcher<Integer>() {
                    public boolean matches(Object o) {
                        Integer status = (Integer) o;
                        return status == HttpStatus.OK.value() || status == AUTHORIZATION_REQUIRED.getCode();
                    }

                    public void describeMismatch(Object o, Description description) {

                    }

                    public void _dont_implement_Matcher___instead_extend_BaseMatcher_() {

                    }

                    public void describeTo(Description description) {

                    }
                }));
    }
}