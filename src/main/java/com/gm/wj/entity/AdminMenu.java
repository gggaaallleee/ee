package com.gm.wj.entity;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;
import lombok.ToString;

import javax.persistence.*;
import java.util.List;


@Data
@Entity
@Table(name = "admin_menu")
@ToString
@JsonIgnoreProperties({"handler", "hibernateLazyInitializer"})
public class AdminMenu {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private int id;

    /**
     * 菜单访问路径。
     */
    private String path;

    /**
     * 菜单名称。
     */
    private String name;

    /**
     * 菜单中文名称。
     */
    private String nameZh;

    /**
     * 菜单图标类（使用元素ui图标）。
     */
    private String iconCls;

    /**
     * 与菜单对应的前端组件名称。
     */
    private String component;

    /**
     * 父菜单。
     */
    @Column(name = "parent_id")
    private int parentId;

    /**
     * 用于存储子菜单的临时属性。
     */
    @Transient
    private List<AdminMenu> children;
}
