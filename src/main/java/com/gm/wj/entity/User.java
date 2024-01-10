package com.gm.wj.entity;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.*;

import javax.persistence.*;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;
import java.util.List;

@Data //Lombok库的注解，为类提供读写属性
@NoArgsConstructor //Lombok的注解，为类提供一个无参的构造方法
@AllArgsConstructor //Lombok的注解，为类提供一个包含所有变量的构造方法
@Builder  //Lombok的注解，为类提供一个内部的Builder，可以使用Builder模式构建对象
@Entity //JPA的注解，声明这个类对应了一个数据库表
@Table(name = "user") //JPA的注解，指定对应的数据库表名
@ToString //Lombok的注解，为类提供一个toString()方法
@JsonIgnoreProperties({"handler","hibernateLazyInitializer"}) //Jackson库的注解，用于在JSON序列化/反序列化时忽略指定的属性。这里忽略的是"handler"和"hibernateLazyInitializer"这两个属性，这两个属性是由于使用了JPA的延迟加载导致的
public class User {

    @Id  //JPA的注解，用于标识实体类的主键
    @GeneratedValue(strategy = GenerationType.IDENTITY) //JPA的注解，用于标识主键的生成策略。GenerationType.IDENTITY表示主键由数据库自动生成（主要是自动增长型）
    @Column(name = "id") //JPA的注解，用于指定实体类属性与数据库表中字段的映射关系。这里name = "id"表示这个属性映射到数据库表的"id"字段
    private int id;

    /**
     * Username.
     */
    @NotEmpty(message = "用户名不能为空")
    private String username;

    /**
     * Password.
     */
    private String password;

    /**
     * Salt for encoding password.
     */
    private String salt;

    /**
     * Real name.
     */
    private String name;

    /**
     * Phone number.
     */
    private String phone;

    /**
     * 电子邮件地址。
     *
     *电子邮件地址可以为空，但如果存在，则应该是正确的。
     */
    @Email(message = "请输入正确的邮箱")
    private String email;

    /**
     * User status.
     */
    private boolean enabled;

    /**
     * Transient property for storing role owned by current user.
     */
    @Transient
    private List<AdminRole> roles;

}

