package com.gm.wj.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.gm.wj.entity.User;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;


import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureMockMvc
public class LoginControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Test
    public void testLoginSuccess() throws Exception {
        User user = new User();
        user.setUsername("admin");
        user.setPassword("123");

        mockMvc.perform(MockMvcRequestBuilders.post("/api/login")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(new ObjectMapper().writeValueAsString(user)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.message").value("成功"));
    }

    @Test
    public void testLoginFailure() throws Exception {
        User user = new User();
        user.setUsername("wrongUsername");
        user.setPassword("wrongPassword");

        mockMvc.perform(MockMvcRequestBuilders.post("/api/login")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(new ObjectMapper().writeValueAsString(user)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.code").value(400))
                .andExpect(jsonPath("$.message").value("账号不存在"));
    }

    @Test
    public void testLoginNonExistentUser() throws Exception {
        User user = new User();
        user.setUsername("nonExistentUser");
        user.setPassword("123");

        mockMvc.perform(MockMvcRequestBuilders.post("/api/login")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(new ObjectMapper().writeValueAsString(user)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.message").value("账号不存在"));
    }
    @Test
    public void testLoginDisabledUser() throws Exception {
        User user = new User();
        user.setUsername("editor");
        user.setPassword("123");

        mockMvc.perform(MockMvcRequestBuilders.post("/api/login")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(new ObjectMapper().writeValueAsString(user)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.message").value("该用户已被禁用"));
    }


    @Test
    public void testRegisterSuccess() throws Exception {
        User user = new User();
        user.setUsername("testUser");
        user.setPassword("testPassword");
        user.setName("");
        user.setEmail("");
        user.setPhone("");
        mockMvc.perform(MockMvcRequestBuilders.post("/api/register")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(new ObjectMapper().writeValueAsString(user)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.message").value("成功"));
    }

    @Test
    public void testRegisterFailureEmptyUsernameOrPassword() throws Exception {
        User user = new User();
        user.setUsername("");
        user.setPassword("");

        mockMvc.perform(MockMvcRequestBuilders.post("/api/register")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(new ObjectMapper().writeValueAsString(user)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.message").value("用户名和密码不能为空"));
    }
    @Test
    public void testRegisterFailureUserExists() throws Exception {
        User user = new User();
        user.setUsername("editor");
        user.setPassword("testPassword");
        user.setName("");
        user.setEmail("");
        user.setPhone("");
        mockMvc.perform(MockMvcRequestBuilders.post("/api/register")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(new ObjectMapper().writeValueAsString(user)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.message").value("用户已存在"));
    }
}