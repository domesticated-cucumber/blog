package com.leverx.shishlo.blog.service;

import com.leverx.shishlo.blog.dto.CommentDto;
import com.leverx.shishlo.blog.entity.Comment;
import com.leverx.shishlo.blog.entity.User;
import com.leverx.shishlo.blog.exception.BlogException;
import com.leverx.shishlo.blog.repository.CommentRepository;
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

import static com.leverx.shishlo.blog.entity.QComment.comment;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class CommentServiceImpl implements CommentService {

    private final UserService userService;
    private final ArticleService articleService;
    private final CommentRepository commentRepository;

    @Override
    public Comment findById(Long id) {
        return commentRepository.findById(id)
                .orElseThrow(() -> new BlogException(String.format("Comment with id %d isn't found", id)));
    }

    @Override
    public Page<Comment> searchByFilter(@NotNull Integer skip, @NotNull Integer limit, Long articleId, Long authorId,
                                        String sort, @NotNull String order) {
        var pageable = PageRequest.of(skip, limit, Sort.by(Sort.Direction.valueOf(order.toUpperCase()), sort));
        var predicate = QPredicates.builder()
                .add(articleId, comment.article.id::eq)
                .add(authorId, comment.user.id::eq)
                .buildAnd();
        return commentRepository.findAll(predicate, pageable);
    }

    @Override
    @Transactional
    public Long save(CommentDto commentDto) {
        var user = userService.findById(commentDto.getUserId());
        var article = articleService.findById(commentDto.getArticleId());
        var comment = Comment.builder()
                .message(commentDto.getMessage())
                .createdAt(LocalDate.now())
                .article(article)
                .user(user)
                .build();
        return commentRepository.save(comment).getId();
    }

    @Override
    @Transactional
    public void delete(Long id) {
        var authUser = getAuthUser();
        if (authUser.getId() != id) {
            throw new BlogException(HttpStatus.FORBIDDEN.name());
        }
        commentRepository.deleteById(id);
    }

    private User getAuthUser() {
        var authentication = SecurityContextHolder.getContext().getAuthentication();
        var email = authentication.getName();
        return userService.findByEmail(email);
    }
}
