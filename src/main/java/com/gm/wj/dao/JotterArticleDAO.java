package com.gm.wj.dao;

import com.gm.wj.entity.JotterArticle;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;


public interface JotterArticleDAO  extends JpaRepository<JotterArticle,Integer> {
    JotterArticle findById(int id);
    Page<JotterArticle> findByAuthorId(int authorId, Pageable pageable);
    @Query("SELECT SUM(j.views) FROM JotterArticle j")
    long countViews();
}
