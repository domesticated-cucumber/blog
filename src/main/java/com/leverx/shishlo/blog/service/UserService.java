package com.leverx.shishlo.blog.service;

import com.leverx.shishlo.blog.entity.User;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@Service
@Validated
public interface UserService extends UserDetailsService {

    Long save(@NotBlank String firstName, @NotBlank String lastName, @Email String email, @NotBlank String password);

    void activateUser(@NotBlank String token);

    void forgotPassword(@Email String email);

    void resetPassword(@NotBlank String password, @NotBlank String token);

    Long checkTokenTTL(@NotBlank String token);

    User findById(@NotNull Long id);

    User findByEmail(@Email String email);
}
