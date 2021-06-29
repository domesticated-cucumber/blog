package com.leverx.shishlo.blog.service;

import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;

import javax.validation.constraints.NotBlank;

@Service
@Validated
public interface TokenService {

    void save(Long userId, String token);

    Long checkTTL(@NotBlank String token);

    Long check(@NotBlank String token);
}
