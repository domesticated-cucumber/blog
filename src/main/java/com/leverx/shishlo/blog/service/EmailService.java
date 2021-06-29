package com.leverx.shishlo.blog.service;

import org.springframework.stereotype.Service;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;

@Service
public interface EmailService {

    void sendMessage(@Email String email, @NotBlank String message);
}
