package com.gm.wj.service;
import com.gm.wj.dao.AdminRoleMenuDAO;
import com.gm.wj.dao.AdminRolePermissionDAO;
import com.gm.wj.entity.AdminPermission;
import com.gm.wj.entity.AdminRolePermission;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Arrays;
import java.util.List;

import static org.mockito.ArgumentMatchers.anyList;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

@RunWith(SpringRunner.class)
@SpringBootTest
public class AdminRolePermissionServiceTest {
    @Autowired
    AdminRolePermissionService adminRolePermissionService;

    @MockBean
    AdminRolePermissionDAO adminRolePermissionDAO;
    @Test
    public void testSavePermChanges() {
        int rid = 1;
        List<AdminPermission> perms = Arrays.asList(new AdminPermission(), new AdminPermission());
        adminRolePermissionService.savePermChanges(rid, perms);
        verify(adminRolePermissionDAO, times(1)).deleteAllByRid(rid);
        verify(adminRolePermissionDAO, times(1)).saveAll(anyList());
    }
}