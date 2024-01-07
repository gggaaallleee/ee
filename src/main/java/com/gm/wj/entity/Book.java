package com.gm.wj.entity;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;
import lombok.ToString;

import javax.persistence.*;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;


@Data
@Entity
@Table(name = "book")
@ToString
@JsonIgnoreProperties({"handler","hibernateLazyInitializer"})
public class Book {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private int id;

    /**
     * 书名。
     */
    private String title;

    /**
     * 作者姓名。
     */
    private String author;

    /**
     * 出版日期。
     */
    private String date;


    private String press;

    /**
     * 这本书的摘要。
     */
    private String abs;

    /**
     * 这本书封面的网址。
     */
    private String cover;

    /**
     *类别id。
     */
    @ManyToOne
    @JoinColumn(name="cid")
    private Category category;
}
