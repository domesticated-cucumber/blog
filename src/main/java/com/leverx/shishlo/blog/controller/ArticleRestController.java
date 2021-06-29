package com.leverx.shishlo.blog.controller;

import com.leverx.shishlo.blog.dto.ArticleDto;
import com.leverx.shishlo.blog.dto.CommentDto;
import com.leverx.shishlo.blog.service.ArticleService;
import com.leverx.shishlo.blog.service.CommentService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import java.util.List;

import static java.util.stream.Collectors.toList;

@RestController
@RequestMapping("/articles")
@RequiredArgsConstructor
@Validated
public class ArticleRestController {

    private final ArticleService articleService;
    private final CommentService commentService;

    @PutMapping
    public ResponseEntity<Void> update(@NotEmpty @Valid @RequestBody ArticleDto articleDto) {
        articleService.update(articleDto);
        return ResponseEntity.ok().build();
    }

    @PostMapping
    public ResponseEntity<Long> save(@NotEmpty @Valid @RequestBody ArticleDto articleDto) {
        var id = articleService.save(articleDto);
        return ResponseEntity.ok(id);
    }

    @GetMapping
    public ResponseEntity<List<ArticleDto>> getAllPublicArticles(
            @RequestParam(value = "page", required = false, defaultValue = "0") @Min(0) Integer page,
            @RequestParam(value = "size", required = false, defaultValue = "10") @Min(1) Integer size,
            @RequestParam(value = "author", required = false) Long authorId,
            @RequestParam(value = "post_title", required = false) String postTitle,
            @RequestParam(value = "sort", required = false) String fieldName,
            @RequestParam(value = "order", required = false, defaultValue = "ASC") @NotBlank String order) {
        var publicArticles = articleService.searchByFilter(page, size, authorId, postTitle, fieldName, order).stream()
                .map(ArticleDto::new)
                .collect(toList());
        return ResponseEntity.ok(publicArticles);
    }

    @GetMapping("/my")
    public ResponseEntity<List<ArticleDto>> getAllArticlesOfAuthorizedUser() {
        var articlesOfAuthUser = articleService.findArticlesByUserId().stream()
                .map(ArticleDto::new)
                .collect(toList());
        return ResponseEntity.ok(articlesOfAuthUser);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable("id") Long id) {
        articleService.delete(id);
        return ResponseEntity.ok().build();
    }

    @PostMapping("/comments")
    public ResponseEntity<Long> saveComment(@RequestBody CommentDto commentDto) {
        var commentId = commentService.save(commentDto);
        return ResponseEntity.status(HttpStatus.CREATED).body(commentId);
    }


    @GetMapping("/{articleId}/comments")
    public ResponseEntity<List<CommentDto>> getCommentsByArticleId(
            @NotEmpty @PathVariable("articleId") Long articleId,
            @RequestParam(value = "page", required = false, defaultValue = "0") @Min(0) Integer page,
            @RequestParam(value = "size", required = false, defaultValue = "10") @Min(0) Integer size,
            @RequestParam(value = "author", required = false) Long authorId,
            @RequestParam(value = "sort", required = false) String fieldName,
            @RequestParam(value = "order", required = false, defaultValue = "ASC") @NotBlank String order) {
        var comments = commentService.searchByFilter(page, size, articleId, authorId, fieldName, order)
                .stream()
                .map(CommentDto::new)
                .collect(toList());
        return ResponseEntity.ok(comments);
    }

    @GetMapping("/comments/{id}")
    public ResponseEntity<CommentDto> getCommentById(@PathVariable("id") Long id) {
        var comment = commentService.findById(id);
        return ResponseEntity.ok(new CommentDto(comment));
    }

    @DeleteMapping("/comments/{id}")
    public ResponseEntity<Void> deleteComment(@PathVariable("id") Long id) {
        commentService.delete(id);
        return ResponseEntity.ok().build();
    }
}
