package com.gm.wj.entity;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.*;

import javax.persistence.*;
import java.util.List;

/**
 * Role entity.
 *
 * @author Evan
 * @date 2019/11
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@Table(name = "admin_role")
@ToString
@JsonIgnoreProperties({"handler", "hibernateLazyInitializer"})
public class AdminRole {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private int id;

    /**
     * 角色名称。
     */
    private String name;

    /**
     * 中文名称。
     */
    @Column(name = "name_zh")
    private String nameZh;

    /**
     * 角色状态。
     */
    private boolean enabled;


    /**
     * 用于存储当前角色拥有的权限的临时属性。
     */
    @Transient
    private List<AdminPermission> perms;

    /**
     * 用于存储当前角色拥有的菜单的临时属性。
     */
    @Transient
    private List<AdminMenu> menus;
}
