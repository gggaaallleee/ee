package com.gm.wj.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.gm.wj.entity.AdminRole;
import com.gm.wj.entity.User;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockHttpSession;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import java.util.ArrayList;
import java.util.List;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureMockMvc
public class UserControllerTest {

    @Autowired
    private TestRestTemplate restTemplate;
    @Autowired
    private MockMvc mockMvc;
    private MockHttpSession session;
    @Before
    public void login() throws Exception {
        MvcResult result = mockMvc.perform(MockMvcRequestBuilders.post("/api/login")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"username\":\"admin\", \"password\":\"123\"}"))
                .andReturn();

        session = (MockHttpSession) result.getRequest().getSession();
    }
    @Test
    public void testListUsers() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get("/api/admin/user").session(session))
                .andExpect(status().isOk());
    }

    @Test
    public void testUpdateUserStatus() throws Exception {
        User user = new User();
        user.setUsername("11");
        user.setEnabled(true);

        // 使用MockMvc发送PUT请求
        mockMvc.perform(MockMvcRequestBuilders.put("/api/admin/user/status")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(new ObjectMapper().writeValueAsString(user))
                        .session(session))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.message").value("成功"));
    }

    @Test
    public void testEditUserSuccess() throws Exception {
        List<AdminRole> adminRoleList=new ArrayList<>();
        User user = new User();
        user.setId(112);
        user.setUsername("12");
        user.setName("11111");
        user.setPhone("1212121");
        user.setEmail("test@example.com");
        user.setRoles( adminRoleList);
        mockMvc.perform(MockMvcRequestBuilders.put("/api/admin/user")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(new ObjectMapper().writeValueAsString(user))
                        .session(session))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.message").value("成功"));
    }


}