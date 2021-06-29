package com.leverx.shishlo.blog.repository;

import com.leverx.shishlo.blog.entity.Tag;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TagRepository extends CrudRepository<Tag, Long> {

    List<Tag> findAllByIdIn(List<Long> ids);
}
