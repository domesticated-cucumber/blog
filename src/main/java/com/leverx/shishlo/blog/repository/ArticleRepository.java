package com.leverx.shishlo.blog.repository;

import com.leverx.shishlo.blog.entity.Article;
import com.leverx.shishlo.blog.entity.Tag;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ArticleRepository extends CrudRepository<Article, Long>, QuerydslPredicateExecutor<Article> {

    List<Article> findArticlesByUserId(Long id);

    List<Article> findArticlesByTags(List<Tag> tags);

    Long countByTags(List<Tag> tags);
}