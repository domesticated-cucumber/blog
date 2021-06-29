package com.leverx.shishlo.blog.dto;

import com.leverx.shishlo.blog.entity.Comment;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.jetbrains.annotations.NotNull;

import javax.validation.constraints.NotBlank;
import java.time.LocalDate;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CommentDto {

    private Long id;
    @NotBlank
    private String message;
    @NotBlank
    private LocalDate createdAt;
    private Long userId;
    private Long articleId;

    public CommentDto(@NotNull Comment comment) {
        this.id = comment.getId();
        this.message = comment.getMessage();
        this.createdAt = comment.getCreatedAt();
        this.userId = comment.getUser().getId();
        this.articleId = comment.getArticle().getId();
    }
}
