package com.gm.wj.service;

import com.gm.wj.entity.AdminPermission;
import com.gm.wj.service.AdminPermissionService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@RunWith(SpringRunner.class)
@SpringBootTest
public class AdminPermissionServiceTest {

    @Autowired
    private AdminPermissionService adminPermissionService;

    @Test
    public void testListPermsByRoleId() {
        int roleId = 1;
        List<AdminPermission> permissions = adminPermissionService.listPermsByRoleId(roleId);
        assertNotNull(permissions);
    }
}