package com.gm.wj.service;

import com.gm.wj.entity.AdminMenu;
import com.gm.wj.service.AdminMenuService;
import com.gm.wj.service.AdminRoleMenuService;
import com.gm.wj.service.AdminUserRoleService;
import com.gm.wj.service.UserService;
import org.apache.shiro.subject.Subject;
import org.apache.shiro.util.ThreadContext;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@RunWith(SpringRunner.class)
@SpringBootTest
public class AdminMenuServiceTest {

    @Autowired
    private AdminMenuService adminMenuService;
    @Autowired
    UserService userService;

    @Autowired
    AdminUserRoleService adminUserRoleService;

    @Autowired
    AdminRoleMenuService adminRoleMenuService;


    @Test
    public void testGetAllByParentId() {
        int parentId = 1;
        List<AdminMenu> expected = new ArrayList<>();
        AdminMenu adminMenu = new AdminMenu();
        adminMenu.setId(2);
        adminMenu.setPath("/admin/dashboard");
        adminMenu.setName("DashboardAdmin");
        adminMenu.setNameZh("运行情况");
        adminMenu.setComponent("dashboard/admin/index");
        adminMenu.setParentId(1);
        expected.add(adminMenu);
        List<AdminMenu> result = adminMenuService.getAllByParentId(parentId);
        assertEquals(expected, result);
    }

    @Test
    public void testGetMenusByCurrentUser() {
        List<AdminMenu> expected = new ArrayList<>();
        // 模拟 SecurityUtils.getSubject().getPrincipal().toString() 的返回值
        // 创建一个 Subject 的 spy 对象
        Subject subject = Mockito.spy(Subject.class);
        // 模拟 getPrincipal() 方法的返回值
        Mockito.doReturn("11").when(subject).getPrincipal();
        // 将 spy 对象设置为当前的 Subject
        ThreadContext.bind(subject);
        List<AdminMenu> result = adminMenuService.getMenusByCurrentUser();
        System.out.println(result);
        assertEquals(expected, result);
    }
}