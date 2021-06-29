package com.leverx.shishlo.blog.controller;

import com.leverx.shishlo.blog.dto.ResetDto;
import com.leverx.shishlo.blog.dto.UserDto;
import com.leverx.shishlo.blog.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;

@RestController
@RequestMapping("auth")
@RequiredArgsConstructor
public class AuthRestController {

    private final UserService userService;

    @PostMapping
    public ResponseEntity<Long> create(@RequestBody @Valid UserDto userDto) {
        var id = userService
                .save(userDto.getFirstName(), userDto.getLastName(), userDto.getEmail(), userDto.getPassword());
        return ResponseEntity.ok(id);
    }

    @GetMapping("/check_token/{token}")
    public ResponseEntity<Long> checkToken(@PathVariable("token") @NotBlank String token) {
        var ttl = userService.checkTokenTTL(token);
        return ResponseEntity.ok(ttl);
    }

    @PostMapping("/forgot_password")
    public ResponseEntity<Void> forgotPassword(@RequestBody @Email String email) {
        userService.forgotPassword(email);
        return ResponseEntity.ok().build();
    }

    @PostMapping("/reset")
    public ResponseEntity<Void> reset(@RequestBody @Valid ResetDto resetDto) {
        userService.resetPassword(resetDto.getPassword(), resetDto.getToken());
        return ResponseEntity.ok().build();
    }

    @GetMapping("/confirm/{token}")
    public ResponseEntity<Void> confirm(@PathVariable("token") @NotBlank String token) {
        userService.activateUser(token);
        return ResponseEntity.ok().build();
    }
}
