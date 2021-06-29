package com.leverx.shishlo.blog.service;

import com.leverx.shishlo.blog.exception.BlogException;
import com.leverx.shishlo.blog.repository.TokenRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class TokenServiceImpl implements TokenService {

    private final TokenRepository tokenRepository;

    @Override
    public void save(Long userId, String token) {
        tokenRepository.save(userId, token);
    }

    @Override
    public Long checkTTL(String token) {
        return tokenRepository.checkTTL(token);
    }

    @Override
    public Long check(String token) {
        return tokenRepository.check(token).orElseThrow(() -> new BlogException("Token is not valid."));
    }
}
