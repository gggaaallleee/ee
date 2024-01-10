package com.gm.wj.service;

import com.gm.wj.dao.JotterArticleDAO;
import com.gm.wj.entity.*;
import com.gm.wj.redis.RedisService;
import com.gm.wj.util.MyPage;
import org.apache.shiro.SecurityUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;


@Service
public class JotterArticleService {
    @Autowired
    JotterArticleDAO jotterArticleDAO;
    @Autowired
    RedisService redisService;
    @Autowired
    UserService userService;
    @Autowired
    AdminUserRoleService adminUserRoleService;
    @Autowired
    AdminRolePermissionService adminRolePermissionService;
    // MyPage 是自定义的 Spring Data JPA Page 对象的替代
    public MyPage list(int page, int size) {
        MyPage<JotterArticle> articles;
        // 用户访问列表页面时按页缓存文章
        String key = "articlepage:" + page;
        Object articlePageCache = redisService.get(key);

        if (articlePageCache == null) {
            Sort sort = new Sort(Sort.Direction.DESC, "id");
            Page<JotterArticle> articlesInDb = jotterArticleDAO.findAll(PageRequest.of(page, size, sort));
            articles = new MyPage<>(articlesInDb);
            redisService.set(key, articles);
        } else {
            articles = (MyPage<JotterArticle>) articlePageCache;
        }
        return articles;
    }

    public MyPage listByAuthorId(int page, int size) {
        //获取数据库中的当前用户。
        String username = SecurityUtils.getSubject().getPrincipal().toString();
        User user = userService.findByUsername(username);

        //获取当前用户的角色ID
        List<Integer> rids = adminUserRoleService.listAllByUid(user.getId())
                .stream().map(AdminUserRole::getRid).collect(Collectors.toList());
        //如果为系统管理员
        if (rids.get(0) == 1) {
            return list(page, size);
        }
        MyPage<JotterArticle> articles;
        String key = "author:" + user.getId() + "articlepage:" + page;
        Object articlePageCache = redisService.get(key);

        if (articlePageCache == null) {
            Sort sort = new Sort(Sort.Direction.DESC, "id");
            Page<JotterArticle> articlesInDb = jotterArticleDAO.findByAuthorId(user.getId(), PageRequest.of(page, size, sort));
            articles = new MyPage<>(articlesInDb);
            redisService.set(key, articles);
        } else {
            articles = (MyPage<JotterArticle>) articlePageCache;
        }
        return articles;
    }


    public JotterArticle findById(int id) {
        JotterArticle article;
        // 用户访问具体文章时缓存单篇文章，通过 id 区分
        String key = "article:" + id;
        Object articleCache = redisService.get(key);

        if (articleCache == null) {
            article = jotterArticleDAO.findById(id);
            redisService.set(key, article);
        } else {
            article = (JotterArticle) articleCache;
        }
        return article;
    }

    public void addOrUpdate(JotterArticle article) {
        jotterArticleDAO.save(article);

        redisService.delete("article:" + article.getId());
        Set<String> keys = redisService.getKeysByPattern("*articlepage*");
        redisService.delete(keys);
    }

    public void delete(int id) {
        jotterArticleDAO.deleteById(id);
        // 删除当前选中的文章和所有文章页面的缓存
        redisService.delete("article:" + id);
        Set<String> keys = redisService.getKeysByPattern("*articlepage*");
        redisService.delete(keys);
    }

    public void increaseViews(int id) {
        JotterArticle article = jotterArticleDAO.findById(id);
        if (article != null) {
            article.setViews(article.getViews() + 1);
            jotterArticleDAO.save(article);
        }
    }
    public long countArticle() {
        return jotterArticleDAO.count();
    }
    public long countViews() {
        return jotterArticleDAO.countViews();
    }
}
