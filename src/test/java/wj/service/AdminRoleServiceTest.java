package com.gm.wj.service;

import com.gm.wj.dao.AdminRoleDAO;
import com.gm.wj.entity.AdminRole;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.*;

@RunWith(SpringRunner.class)
@SpringBootTest
public class AdminRoleServiceTest {

    @Autowired
    private AdminRoleService adminRoleService;

    @MockBean
    AdminRoleDAO adminRoleDAO;

    @Autowired
    private AdminPermissionService adminPermissionService;

    @Autowired
    private AdminMenuService adminMenuService;
    @Test
    public void testListWithPermsAndMenus() {
        List<AdminRole> result = adminRoleService.listWithPermsAndMenus();
        assertNotNull(result);
    }

    @Test
    public void testAddOrUpdate() {
        AdminRole role = new AdminRole();
        adminRoleService.addOrUpdate(role);
        verify(adminRoleDAO, times(1)).save(role);
    }

    @Test
    public void testUpdateRoleStatus() {
        AdminRole role = new AdminRole();
        role.setId(1);
        role.setEnabled(true);

        AdminRole updatedRole = new AdminRole();
        updatedRole.setId(1);
        updatedRole.setEnabled(false);

        when(adminRoleDAO.findById(role.getId())).thenReturn(role);
        when(adminRoleDAO.save(updatedRole)).thenReturn(updatedRole);

        AdminRole result = adminRoleService.updateRoleStatus(updatedRole);

        assertEquals(false, result.isEnabled());
    }
}