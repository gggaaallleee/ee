package com.gm.wj.service;

import com.gm.wj.dao.JotterArticleDAO;
import com.gm.wj.entity.JotterArticle;
import com.gm.wj.redis.RedisService;
import com.gm.wj.service.JotterArticleService;
import com.gm.wj.util.MyPage;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Arrays;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.when;

@RunWith(SpringRunner.class)
@SpringBootTest
public class JotterArticleServiceTest {
    @Autowired
    private JotterArticleService jotterArticleService;

    @MockBean
    private JotterArticleDAO jotterArticleDAO;

    @MockBean
    private RedisService redisService;
    @Test
    public void testList() {
        JotterArticle article = new JotterArticle();
        article.setId(1);
        List<JotterArticle> articles = Arrays.asList(article);
        PageRequest pageRequest = PageRequest.of(0, 1);
        Page<JotterArticle> page = new PageImpl<>(articles, pageRequest, 1);
        when(jotterArticleDAO.findAll(Mockito.any(PageRequest.class))).thenReturn(page);
        when(redisService.get(Mockito.anyString())).thenReturn(null);

        MyPage<JotterArticle> result = jotterArticleService.list(1, 1);

        assertEquals(1, result.getContent().size());
        assertEquals(article, result.getContent().get(0));
    }
    @Test
    public void testFindById() {
        JotterArticle article = new JotterArticle();
        article.setId(1);

        when(redisService.get("article:" + article.getId())).thenReturn(null);
        when(jotterArticleDAO.findById(article.getId())).thenReturn(article);

        JotterArticle result = jotterArticleService.findById(article.getId());

        assertEquals(article, result);
    }
}