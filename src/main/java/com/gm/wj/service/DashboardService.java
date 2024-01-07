package com.gm.wj.service;

import com.gm.wj.dao.JotterArticleDAO;
import com.gm.wj.dao.UserDAO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

@Service
public class DashboardService {
    @Autowired
    private UserService userService;
    @Autowired
    private JotterArticleService jotterArticleService;
    @Autowired
    private BookService bookService;
    public Map<String,Integer> getDashboardData(){
        Map<String,Integer> dashboardData = new HashMap<>();
        dashboardData.put("userCount", (int) userService.countUser());
        dashboardData.put("articleCount", (int) jotterArticleService.countArticle());
        dashboardData.put("bookCount", (int) bookService.countBook());
        dashboardData.put("viewsCount", (int) jotterArticleService.countViews());
        return dashboardData;
    }
}
