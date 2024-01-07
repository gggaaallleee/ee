package com.gm.wj.dao;

import com.gm.wj.entity.AdminMenu;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface AdminMenuDAO extends JpaRepository<AdminMenu, Integer> {
     AdminMenu findById(int id);
     List<AdminMenu> findAllByParentId(int parentId);
     @Query("SELECT a.parentId FROM AdminMenu a WHERE a.id = ?1")
     Integer findParentIdById(int id);
}

