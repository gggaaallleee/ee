package com.gm.wj.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.gm.wj.entity.AdminMenu;
import com.gm.wj.entity.AdminPermission;
import com.gm.wj.entity.AdminRole;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
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
public class RoleControllerTest {

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
    public void testListRoles() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get("/api/admin/role").session(session))
                .andExpect(status().isOk());
    }

    @Test
    public void testUpdateRoleStatus() throws Exception {
        AdminRole role = new AdminRole();
        role.setId(9);
        role.setEnabled(false);

        mockMvc.perform(MockMvcRequestBuilders.put("/api/admin/role/status")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(new ObjectMapper().writeValueAsString(role))
                        .session(session))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.message").value("成功"));
    }

    @Test
    public void testEditRole() throws Exception {
        AdminRole role = new AdminRole();
        role.setId(19);
        role.setName("testRole");
        List<AdminMenu> adminMenuList=new ArrayList<>();
        role.setMenus(adminMenuList);
        List<AdminPermission> adminPermissionList=new ArrayList<>();
        role.setPerms(adminPermissionList);

        mockMvc.perform(MockMvcRequestBuilders.put("/api/admin/role")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(new ObjectMapper().writeValueAsString(role))
                        .session(session))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.result").value("修改角色信息成功"));
    }
}