package com.gm.wj.dao;

import com.gm.wj.entity.AdminPermission;
import org.springframework.data.jpa.repository.JpaRepository;


public interface AdminPermissionDAO extends JpaRepository<AdminPermission, Integer> {
    AdminPermission findById(int id);
}
