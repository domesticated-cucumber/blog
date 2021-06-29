package com.leverx.shishlo.blog.service;

import com.leverx.shishlo.blog.dto.CommentDto;
import com.leverx.shishlo.blog.entity.Comment;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;

import javax.validation.constraints.NotNull;

@Service
@Validated
public interface CommentService {

    Long save(@NotNull CommentDto commentDto);

    Page<Comment> searchByFilter(@NotNull Integer page, @NotNull Integer size, Long articleId, Long authorId,
                                 String sort, @NotNull String order);

    void delete(@NotNull Long id);

    Comment findById(@NotNull Long id);
}
