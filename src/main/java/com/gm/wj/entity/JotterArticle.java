package com.gm.wj.entity;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;
import lombok.ToString;

import javax.persistence.*;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.sql.Date;


@Data
@Entity
@Table(name = "jotter_article")
@ToString
@JsonIgnoreProperties({"handler","hibernateLazyInitializer"})
public class JotterArticle {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    @NotNull(message = "id 不能为 null")
    private int id;

    /**
     * 文章标题。
     */
    @NotEmpty(message = "文章标题不能为空")
    private String articleTitle;

    /**
     * 文章内容渲染后为html。
     */
    private String articleContentHtml;

    /**
     * 文章内容的markdown源码。
     */
    private String articleContentMd;

    /**
     * 文章摘要。
     */
    private String articleAbstract;

    /**
     * 文章封面图。
     */
    private String articleCover;

    /**
     * 文章发布日期。
     */
    private Date articleDate;
    @Column(name = "author_id")
    private int authorId;
    private int views;
}
