package com.gm.wj.service;

import com.gm.wj.dao.AdminMenuDAO;
import com.gm.wj.entity.AdminMenu;
import com.gm.wj.entity.AdminRoleMenu;
import com.gm.wj.entity.AdminUserRole;
import com.gm.wj.entity.User;
import org.apache.shiro.SecurityUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class AdminMenuService {
    @Autowired
    AdminMenuDAO adminMenuDAO;
    @Autowired
    UserService userService;
    @Autowired
    AdminUserRoleService adminUserRoleService;
    @Autowired
    AdminRoleMenuService adminRoleMenuService;

    public List<AdminMenu> getAllByParentId(int parentId) {
        return adminMenuDAO.findAllByParentId(parentId);
    }

    public List<AdminMenu> getMenusByCurrentUser() {
        //获取数据库中的当前用户。
        String username = SecurityUtils.getSubject().getPrincipal().toString();
        User user = userService.findByUsername(username);

        //获取当前用户的角色ID
        List<Integer> rids = adminUserRoleService.listAllByUid(user.getId())
                .stream().map(AdminUserRole::getRid).collect(Collectors.toList());

        //获取这些角色的菜单项。
        List<Integer> menuIds = adminRoleMenuService.findAllByRid(rids)
                .stream().map(AdminRoleMenu::getMid).collect(Collectors.toList());
        List<AdminMenu> menus = adminMenuDAO.findAllById(menuIds).stream().distinct().collect(Collectors.toList());

        //调整菜单的结构。
        handleMenus(menus);
        return menus;
    }

    public List<AdminMenu> getMenusByRoleId(int rid) {
        List<Integer> menuIds = adminRoleMenuService.findAllByRid(rid)
                .stream().map(AdminRoleMenu::getMid).collect(Collectors.toList());
        List<AdminMenu> menus = adminMenuDAO.findAllById(menuIds);

        handleMenusForEdit(menus);
        return menus;
    }

    //处理用于编辑的菜单
    public void handleMenusForEdit(List<AdminMenu> menus) {
        menus.forEach(m -> {
            List<AdminMenu> children = getAllByParentId(m.getId());
            m.setChildren(children);
        });

        // 创建一个新的列表，用于存储所有的子菜单
        List<AdminMenu> allChildren = new ArrayList<>();
        menus.forEach(m -> allChildren.addAll(m.getChildren()));

        // 移除menus列表中所有存在于allChildren列表中的AdminMenu对象
        menus.removeIf(allChildren::contains);
    }

    //处理显示菜单
    public void handleMenus(List<AdminMenu> menus) {
        // 创建一个新的列表，用于存储所有的父菜单
        List<AdminMenu> parent = new ArrayList<>();
        // 遍历menus列表，找到所有ParentId不为0的父菜单
        menus.forEach(m -> {
            if (m.getParentId() != 0) {
                AdminMenu p = adminMenuDAO.findById(m.getParentId());
                if (p != null) {
                    parent.add(p);
                }
            }
        });
        // 将父菜单添加到menus列表中
        menus.addAll(parent);
        //去除重复的菜单
        List<AdminMenu> menus2 = menus.stream().distinct().collect(Collectors.toList());
        // 更新menus列表
        menus.clear();
        menus.addAll(menus2);

        menus.forEach(m -> {
            List<AdminMenu> children = getAllByParentId(m.getId());
            children.removeIf(c -> !menus.contains(c));
            m.setChildren(children);
        });

        menus.removeIf(m -> m.getParentId() != 0);
    }
}
