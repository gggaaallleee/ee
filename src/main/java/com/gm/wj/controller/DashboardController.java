package com.gm.wj.controller;

import com.gm.wj.result.Result;
import com.gm.wj.result.ResultFactory;
import com.gm.wj.service.DashboardService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class DashboardController {
    @Autowired
    DashboardService dashboardService;

    @GetMapping("/api/dashboard")
    public Result getAdminData() {
        return ResultFactory.buildSuccessResult(dashboardService.getDashboardData());
    }
}
