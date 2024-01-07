package com.gm.wj.entity;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;
import lombok.ToString;

import javax.persistence.*;


@Data
@Entity
@Table(name = "admin_permission")
@ToString
@JsonIgnoreProperties({"handler", "hibernateLazyInitializer"})
public class AdminPermission {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private int id;

    /**
     * 权限名称；
     */
    private String name;

    /**
     * 许可说明（中文）
     */
    private String desc_;

    /**
     * 触发权限检查的路径。
     */
    private String url;
}
