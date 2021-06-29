package com.leverx.shishlo.blog.controller;

import com.leverx.shishlo.blog.dto.ArticleDto;
import com.leverx.shishlo.blog.dto.TagCloudDto;
import com.leverx.shishlo.blog.service.ArticleService;
import com.leverx.shishlo.blog.service.TagService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequiredArgsConstructor
@Validated
@RequestMapping("tags")
public class TagRestController {

    private final TagService tagService;
    private final ArticleService articleService;

    @GetMapping("/articles")
    public ResponseEntity<List<ArticleDto>> findArticlesByTags(@RequestParam(value = "tags", required = false) List<Long> tags) {
        var articleDtoList = articleService.findArticlesByTags(tags).stream()
                .map(ArticleDto::new)
                .collect(Collectors.toList());
        return ResponseEntity.ok(articleDtoList);

    }

    @GetMapping("/tags-cloud")
    public ResponseEntity<TagCloudDto> getArticleCountByTag(Long id) {
        var tag = tagService.findById(id);
        var count = articleService.countArticlesByTag(tag);
        return ResponseEntity.ok(new TagCloudDto(tag.getName(), count));
    }
}
