package com.leverx.shishlo.blog.repository;

import com.leverx.shishlo.blog.entity.Comment;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CommentRepository extends CrudRepository<Comment, Long>, QuerydslPredicateExecutor<Comment> {
}
