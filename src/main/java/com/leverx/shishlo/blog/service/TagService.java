package com.leverx.shishlo.blog.service;

import com.leverx.shishlo.blog.entity.Tag;
import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;

import javax.validation.constraints.NotNull;
import java.util.List;

@Service
@Validated
public interface TagService {

    List<Tag> getAllTagsByIds(@NotNull List<Long> ids);

    Tag findById(@NotNull Long id);
}
