package com.leverx.shishlo.blog.service;

import com.leverx.shishlo.blog.dto.ArticleDto;
import com.leverx.shishlo.blog.entity.Article;
import com.leverx.shishlo.blog.entity.Tag;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;

import javax.validation.constraints.NotNull;
import java.util.List;

@Service
@Validated
public interface ArticleService {

    Article findById(@NotNull Long id);

    void update(@NotNull ArticleDto articleDto);

    Long save(@NotNull ArticleDto articleDto);

    Page<Article> searchByFilter(@NotNull Integer skip, @NotNull Integer limit, Long authorId, String postTitle, String sort, @NotNull String order);

    void delete(@NotNull Long id);

    List<Article> findArticlesByTags(@NotNull List<Long> tags);

    List<Article> findArticlesByUserId();

    Long countArticlesByTag(@NotNull Tag tag);
}
