package com.gm.wj.controller;

import com.gm.wj.entity.JotterArticle;
import com.gm.wj.entity.User;
import com.gm.wj.result.Result;
import com.gm.wj.result.ResultFactory;
import com.gm.wj.service.JotterArticleService;
import com.gm.wj.service.UserService;
import org.apache.shiro.SecurityUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.sql.Date;

@RestController
public class JotterController {
    @Autowired
    JotterArticleService jotterArticleService;
    @Autowired
    UserService userService;

    @PostMapping("api/admin/content/article")
    public Result saveArticle(@RequestBody @Valid JotterArticle article) {
        //获取数据库中的当前用户。mybatisdb
        String username = SecurityUtils.getSubject().getPrincipal().toString();
        User user = userService.findByUsername(username);
        //获取当前时间Date
        Date time = new Date(System.currentTimeMillis());
        article.setArticleDate(time);
        article.setAuthorId(user.getId());
        jotterArticleService.addOrUpdate(article);
        return ResultFactory.buildSuccessResult("保存成功");
    }

    @GetMapping("/api/article/{size}/{page}")
    public Result listArticles(@PathVariable("size") int size, @PathVariable("page") int page) {
        return ResultFactory.buildSuccessResult(jotterArticleService.list(page - 1, size));
    }
    @GetMapping("/api/article/author/{size}/{page}")
    public Result listArticlesByAuthorId(@PathVariable("size") int size, @PathVariable("page") int page) {
        return ResultFactory.buildSuccessResult(jotterArticleService.listByAuthorId(page - 1, size));
    }
    @GetMapping("/api/article/{id}")
    public Result getOneArticle(@PathVariable("id") int id) {
        //增加浏览量
        jotterArticleService.increaseViews(id);
        return ResultFactory.buildSuccessResult(jotterArticleService.findById(id));
    }

    @DeleteMapping("/api/admin/content/article/{id}")
    public Result deleteArticle(@PathVariable("id") int id) {
        jotterArticleService.delete(id);
        return ResultFactory.buildSuccessResult("删除成功");
    }
}
