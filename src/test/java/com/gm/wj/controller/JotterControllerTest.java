package com.gm.wj.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.gm.wj.entity.JotterArticle;
import com.jayway.jsonpath.JsonPath;
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

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureMockMvc
public class JotterControllerTest {

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
    public void testAddOrUpdateArticle() throws Exception {
        JotterArticle article = new JotterArticle();
        article.setArticleTitle("testTitle");
        article.setArticleContentMd("testContent");
        article.setArticleAbstract("111");
        mockMvc.perform(MockMvcRequestBuilders.post("/api/admin/content/article")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(new ObjectMapper().writeValueAsString(article))
                        .session(session))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.message").value("成功"));
    }

    @Test
    public void testListArticles() throws Exception {
        int size = 20;
        int page = 1;

        mockMvc.perform(MockMvcRequestBuilders.get("/api/article/" + size + "/" + page))
                .andExpect(status().isOk());
    }

    @Test
    public void testDeleteArticle() throws Exception {
        int size = 20;
        int page = 1;

        // 获取当前用户的文章列表
        MvcResult result = mockMvc.perform(MockMvcRequestBuilders.get("/api/article/author/" + size + "/" + page)
                        .session(session))
                .andReturn();

        String response = result.getResponse().getContentAsString();
        // 从文章列表中获取第一篇文章的id
        int id = JsonPath.parse(response).read("$.result.content[0].id");

        mockMvc.perform(MockMvcRequestBuilders.delete("/api/admin/content/article/" + id)
                        .session(session))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.message").value("成功"));
    }
}