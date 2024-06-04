package com.gm.wj.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.gm.wj.entity.Book;
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
public class LibraryControllerTest {

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
    public void testListBooks() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get("/api/books"))
                .andExpect(status().isOk());
    }

    @Test
    public void testAddOrUpdateBooks() throws Exception {
        Book book = new Book();
        book.setCover("https://i.loli.net/2019/04/10/5cad63931ce27.jpg");
        book.setTitle("谋杀狄更斯");
        book.setAuthor("[美] 丹·西蒙斯");
        book.setDate("2019-5-1");
        book.setPress("上海文艺出版社");
        book.setAbs("狄更斯的那场意外灾难发生在1865年6月9日，那列搭载他的成功、平静、理智、手稿与情妇的火车一路飞驰，迎向铁道上的裂隙，突然触目惊心地坠落了。");

        mockMvc.perform(MockMvcRequestBuilders.post("/api/admin/content/books")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(new ObjectMapper().writeValueAsString(book))
                        .session(session))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.message").value("成功"));
    }

    @Test
    public void testDeleteBook() throws Exception {
        // 创建一本新的书籍
        Book book = new Book();
        book.setTitle("testTitle");
        book.setAuthor("testAuthor");
        mockMvc.perform(MockMvcRequestBuilders.post("/api/admin/content/books")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(new ObjectMapper().writeValueAsString(book))
                        .session(session))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.message").value("成功"))
                .andReturn();

        // 使用/api/search接口查找这本书
        MvcResult result = mockMvc.perform(MockMvcRequestBuilders.get("/api/search")
                        .param("keywords", "testTitle")
                        .session(session))
                .andExpect(status().isOk())
                .andReturn();
        String response = result.getResponse().getContentAsString();
        // 从搜索结果中获取书籍的id
        int id = JsonPath.parse(response).read("$.result[0].id");
        book.setId(id);
        mockMvc.perform(MockMvcRequestBuilders.post("/api/admin/content/books/delete")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(new ObjectMapper().writeValueAsString(book))
                        .session(session))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.message").value("成功"));
    }
}