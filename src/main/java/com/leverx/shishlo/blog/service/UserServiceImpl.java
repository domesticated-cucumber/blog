package com.leverx.shishlo.blog.service;

import com.leverx.shishlo.blog.entity.User;
import com.leverx.shishlo.blog.entity.UserStatus;
import com.leverx.shishlo.blog.exception.BlogException;
import com.leverx.shishlo.blog.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.Optional;

import static com.leverx.shishlo.blog.utils.TokenUtil.createToken;
import static java.lang.String.format;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;

    private final TokenService tokenService;

    private final EmailService emailService;

    private final PasswordEncoder passwordEncoder;

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        return Optional.of(email)
                .map(userRepository::findByEmail)
                .map(Optional::get)
                .map(user -> org.springframework.security.core.userdetails.User.builder()
                        .username(user.getEmail())
                        .password(user.getPassword())
                        .authorities(user.getStatus().name())
                        .build())
                .orElseThrow(() -> new BlogException(format("User with email %s wasn't found", email)));
    }

    @Override
    public User findById(Long id) {
        return userRepository.findById(id)
                .orElseThrow(() -> new BlogException(format("User with id %d wasn't found", id)));
    }

    @Override
    public User findByEmail(String email) {
        return userRepository.findByEmail(email)
                .orElseThrow(() -> new BlogException(format("User with email %s wasn't found", email)));
    }

    @Override
    @Transactional
    public Long save(String firstName, String lastName, String email, String password) {
        var user = User.builder()
                .firstName(firstName)
                .lastName(lastName)
                .email(email)
                .password(passwordEncoder.encode(password))
                .status(UserStatus.NOT_ACTIVATED)
                .createdAt(LocalDate.now())
                .build();
        var createdUser = userRepository.save(user);
        var token = createToken(email);
        tokenService.save(user.getId(), token);
        emailService.sendMessage(email, token);
        return createdUser.getId();
    }

    @Override
    @Transactional
    public void activateUser(String token) {
        var userId = tokenService.check(token);
        userRepository.updateStatus(userId, UserStatus.ACTIVATED);
    }

    @Override
    @Transactional
    public void forgotPassword(String email) {
        var user = userRepository.findByEmail(email)
                .orElseThrow(() -> new BlogException(format("User with email %s wasn't found", email)));
        var token = createToken(email);
        tokenService.save(user.getId(), token);
        emailService.sendMessage(email, token);
    }

    @Override
    @Transactional
    public void resetPassword(String password, String token) {
        var userId = tokenService.check(token);
        userRepository.updatePassword(userId, passwordEncoder.encode(password));
    }

    @Override
    public Long checkTokenTTL(String token) {
        return tokenService.checkTTL(token);
    }
}

