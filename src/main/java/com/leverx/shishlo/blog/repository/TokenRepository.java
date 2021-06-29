package com.leverx.shishlo.blog.repository;

import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface TokenRepository {

    void save(Long userId, String token);

    Long checkTTL(String token);

    Optional<Long> check(String token);
}
