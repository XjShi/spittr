package com.spittr.web;

import com.spittr.config.RootConfig;
import com.spittr.config.SpittrWebAppInitializer;
import com.spittr.config.WebConfig;
import com.spittr.mapper.SpitterMapper;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockServletContext;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import javax.servlet.ServletContext;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

/**
 * Created by xjshi.
 */
public class SpitterControllerTest extends BaseTest {
    @Autowired
    private SpitterMapper spitterMapper;

    private String username = "Jack";
    private String password = username + "_password";

    @Before
    public void setup() throws Exception {
        super.setup();
        beforeSignup();
    }

    private void beforeSignup() {
        spitterMapper.deleteOne("Jack");
    }

    @Test
    public void givenWac_whenServletContext_thenItProvidesSpitterController() {
        ServletContext servletContext = webApplicationContext.getServletContext();
        Assert.assertNotNull(servletContext);
        Assert.assertTrue(servletContext instanceof MockServletContext);
        Assert.assertNotNull(webApplicationContext.getBean("spitterController"));
    }

    @Test
    public void getSpitterProfile() throws Exception {
        mockMvc.perform(get("/spitter/{param}", "elgae"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8))
                .andExpect(jsonPath("$.code").value(0))
                .andExpect(jsonPath("$.data.username").value("elgae"));
    }

    @Test
    public void signup() throws Exception {
        mockMvc.perform(post("/spitter").param("username", username).param("password", password))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8))
                .andExpect(jsonPath("$.code").value(0))
                .andExpect(jsonPath("$.data.username").value(username))
                .andExpect(jsonPath("$.data.password").value(password));
    }
}