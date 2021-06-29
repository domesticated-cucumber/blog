package com.leverx.shishlo.blog.utils;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class TokenUtil {

    private static final BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();

    public static String createToken(String email) {
        return encoder.encode(email);
    }
}
