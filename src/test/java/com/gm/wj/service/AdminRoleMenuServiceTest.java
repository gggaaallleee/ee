package com.gm.wj.service;
import com.gm.wj.dao.AdminRoleMenuDAO;
import com.gm.wj.service.AdminRoleMenuService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.mockito.ArgumentMatchers.anyList;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

@RunWith(SpringRunner.class)
@SpringBootTest
public class AdminRoleMenuServiceTest {
    @Autowired
    AdminRoleMenuService adminRoleMenuService;

    @MockBean
    AdminRoleMenuDAO adminRoleMenuDAO;
    @Test
    public void testUpdateRoleMenu() {
        int rid = 100;
        Map<String, List<Integer>> menusIds = new HashMap<>();
        menusIds.put("menusIds", Arrays.asList(1, 2, 3));
        adminRoleMenuService.updateRoleMenu(rid, menusIds);
        verify(adminRoleMenuDAO, times(1)).deleteAllByRid(rid);
        verify(adminRoleMenuDAO, times(1)).saveAll(anyList());
    }
}