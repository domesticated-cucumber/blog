package com.leverx.shishlo.blog.service;

import lombok.RequiredArgsConstructor;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class EmailServiceImpl implements EmailService {

    private final JavaMailSender javaMailSender;

    @Override
    public void sendMessage(String email, String token) {
        var message = new SimpleMailMessage();
        message.setTo(email);
        message.setSubject("Blog registration");
        message.setText(token);
        javaMailSender.send(message);
    }
}
