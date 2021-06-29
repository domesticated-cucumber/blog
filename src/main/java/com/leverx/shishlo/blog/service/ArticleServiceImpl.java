package com.leverx.shishlo.blog.service;

import com.leverx.shishlo.blog.dto.ArticleDto;
import com.leverx.shishlo.blog.entity.Article;
import com.leverx.shishlo.blog.entity.ArticleStatus;
import com.leverx.shishlo.blog.entity.Tag;
import com.leverx.shishlo.blog.entity.User;
import com.leverx.shishlo.blog.exception.BlogException;
import com.leverx.shishlo.blog.repository.ArticleRepository;
import com.leverx.shishlo.blog.utils.QPredicates;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.validation.constraints.NotNull;
import java.time.LocalDate;
import java.util.List;

import static com.leverx.shishlo.blog.entity.QArticle.article;
import static java.lang.String.format;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class ArticleServiceImpl implements ArticleService {

    private final ArticleRepository articleRepository;
    private final TagService tagService;
    private final UserService userService;


    @Override
    public Article findById(Long id) {
        return articleRepository.findById(id)
                .orElseThrow(() -> new BlogException(format("Article with id %d isn't found", id)));
    }

    @Override
    @Transactional
    public void update(ArticleDto articleDto) {
        var authUser = getAuthUser();
        if (authUser.getId() != articleDto.getUserId()) {
            throw new BlogException(HttpStatus.FORBIDDEN.name());
        }
        var article = articleRepository.findById(articleDto.getId())
                .orElseThrow(() -> new BlogException(format("Article with id %d isn't found", articleDto.getId())));
        var tags = tagService.getAllTagsByIds(articleDto.getTagIds());
        article.setTitle(articleDto.getTitle());
        article.setText(articleDto.getText());
        article.setStatus(ArticleStatus.valueOf(articleDto.getStatus()));
        article.setUpdatedAt(LocalDate.now());
        article.setTags(tags);
        articleRepository.save(article);
    }

    @Override
    @Transactional
    public Long save(ArticleDto articleDto) {
        var authUser = getAuthUser();
        var tags = tagService.getAllTagsByIds(articleDto.getTagIds());
        var article = Article.builder()
                .title(articleDto.getTitle())
                .text(articleDto.getText())
                .status(ArticleStatus.valueOf(articleDto.getStatus()))
                .createdAt(LocalDate.now())
                .user(authUser)
                .tags(tags)
                .build();
        return articleRepository.save(article).getId();
    }

    @Override
    @Transactional
    public void delete(Long id) {
        var authUser = getAuthUser();
        if (authUser.getId() != id) {
            throw new BlogException(HttpStatus.FORBIDDEN.name());
        }
        articleRepository.deleteById(id);
    }

    @Override
    public List<Article> findArticlesByTags(List<Long> tagIds) {
        var tags = tagService.getAllTagsByIds(tagIds);
        return articleRepository.findArticlesByTags(tags);
    }

    @Override
    public Page<Article> searchByFilter(@NotNull Integer skip, @NotNull Integer limit, Long authorId,
                                        String postTitle, String sort, @NotNull String order) {
        var pageable = PageRequest.of(skip, limit, Sort.by(Sort.Direction.valueOf(order.toUpperCase()), sort));
        var predicate = QPredicates.builder()
                .add(authorId, article.user.id::eq)
                .add(ArticleStatus.PUBLIC, article.status::eq)
                .add(postTitle, article.title::containsIgnoreCase)
                .buildAnd();
        return articleRepository.findAll(predicate, pageable);
    }

    @Override
    public List<Article> findArticlesByUserId() {
        var authUser = getAuthUser();
        return articleRepository.findArticlesByUserId(authUser.getId());
    }

    @Override
    public Long countArticlesByTag(Tag tag) {
        return articleRepository.countByTags(List.of(tag));
    }

    private User getAuthUser() {
        var authentication = SecurityContextHolder.getContext().getAuthentication();
        var email = authentication.getName();
        return userService.findByEmail(email);
    }
}
