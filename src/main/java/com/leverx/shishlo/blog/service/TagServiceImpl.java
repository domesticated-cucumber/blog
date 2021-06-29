package com.leverx.shishlo.blog.service;

import com.leverx.shishlo.blog.entity.Tag;
import com.leverx.shishlo.blog.exception.BlogException;
import com.leverx.shishlo.blog.repository.TagRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.validation.constraints.NotNull;
import java.util.List;

import static java.lang.String.format;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class TagServiceImpl implements TagService {

    private final TagRepository tagRepository;

    @Override
    public List<Tag> getAllTagsByIds(List<Long> ids) {
        return tagRepository.findAllByIdIn(ids);
    }

    @Override
    public Tag findById(@NotNull Long id) {
        return tagRepository.findById(id).orElseThrow(() -> new BlogException(format("Tag with id %d isn't found", id)));
    }

}
