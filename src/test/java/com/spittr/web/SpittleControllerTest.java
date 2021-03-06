package com.spittr.web;

import org.junit.Test;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import static com.spittr.manager.TokenManager.AUTHORIZATION;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;

/**
 * Created by xjshi.
 */
public class SpittleControllerTest extends BaseTest {
    private final String username = "shixj";
    private final String spittleText = "I come from spring test";
    private final long commentedSpittleId = 22;
    private final String commentContent = "comment from spring test";
    private final String token = "Bearer eyJhbGciOiJIUzUxMiJ9.eyJzdWIiOiJlbGdhZSIsImV4cCI6MTUwNjY4MjczNX0.6TNoADUFricb_BQHdT7BL3lwVWAZ59Q-2hPyZytT9hYh-jAhCNbjJsCOvi3IHWkRqRFH4atBzBt03c1VX6kxrg";

    @Test
    public void show() throws Exception {
        mockMvc.perform(get("/spittle"))
                .andExpect(jsonPath("$.data").exists())
                .andDo(print());
    }

    @Test
    public void showByUsername() throws Exception {
        mockMvc.perform(get("/spittle/{username}", username))
                .andExpect(jsonPath("$.data").isArray())
                .andDo(print());
    }

    @Test
    public void post() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.post("/spittle").param("text", spittleText).header(AUTHORIZATION, token))
                .andExpect(jsonPath("$.code").value(0))
                .andExpect(jsonPath("$.data.text").value(spittleText));
    }

    @Test
    public void delete() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.delete("/spittle/{id}", 24).header(AUTHORIZATION, token))
                .andDo(print())
                .andExpect(jsonPath("$.code").value(0));
    }

    @Test
    public void reply() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.post("/spittle/{id}/comment", commentedSpittleId).header(AUTHORIZATION, token)
                .param("text", commentContent))
                .andDo(print())
                .andExpect(jsonPath("$.code").value(0))
                .andExpect(jsonPath("$.data.text").value(commentContent));
    }

    @Test
    public void showComments() throws Exception {
        mockMvc.perform(get("/spittle/{id}/comment", 1))
                .andDo(print())
                .andExpect(jsonPath("$.code").value(0))
                .andExpect(jsonPath("$.data").isArray());
    }

    @Test
    public void deleteComment() throws Exception {
        mockMvc.perform((MockMvcRequestBuilders.delete("/spittle/{id}/comment/{commentId}", 18, 35))
                .header(AUTHORIZATION, token))
                .andExpect(jsonPath("$.code").value(0));
    }

}