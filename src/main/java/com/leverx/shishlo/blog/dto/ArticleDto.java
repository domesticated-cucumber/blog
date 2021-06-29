package com.leverx.shishlo.blog.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.leverx.shishlo.blog.entity.Article;
import lombok.Data;
import org.jetbrains.annotations.NotNull;

import javax.validation.constraints.NotBlank;
import java.time.LocalDate;
import java.util.List;
import static com.fasterxml.jackson.annotation.JsonProperty.Access.READ_ONLY;

@Data
public class ArticleDto {

    private Long id;
    @NotBlank
    private String title;
    @NotBlank
    private String text;
    @NotBlank
    private String status;
    @JsonProperty(access = READ_ONLY)
    private LocalDate createdAt;
    @JsonProperty(access = READ_ONLY)
    private LocalDate updatedAt;
    private Long userId;
    private List<Long> tagIds;

    public ArticleDto(@NotNull Article article) {
        this.id = article.getId();
        this.title = article.getTitle();
        this.text = article.getText();
        this.createdAt = article.getCreatedAt();
        this.updatedAt = article.getUpdatedAt();
        this.status = article.getStatus().name();
        this.userId = article.getUser().getId();
    }
}
