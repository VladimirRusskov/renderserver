package com.server.web.service.impl;

import com.server.web.converter.UserToUserDtoConverter;
import com.server.web.entity.User;
import com.server.web.repository.UserRepository;
import com.server.web.security.UserRole;
import com.server.web.service.UserService;
import com.server.web.service.dto.UserDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static com.server.web.security.UserRole.USER;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {
    private final UserRepository userRepository;
    private final UserToUserDtoConverter converter;
    private final PasswordEncoder passwordEncoder;

    @Override
    public UserDTO findUserByEmail(String email) {
        return Optional.ofNullable(userRepository.findByEmail(email)).map(converter::convert).orElse(null);
    }

    @Override
    public UserDTO findUserByEmailAndPassword(String email, String password) {
        return Optional.ofNullable(userRepository.findByEmail(email)).filter(value -> passwordEncoder.matches(password, value.getPassword())).map(converter::convert).orElse(null);
    }

    @Override
    @Transactional
    public UserDTO addUser(String email, String password) {
        return Optional.ofNullable(findUserByEmail(email)).isPresent() ? null :
                Optional.of(userRepository.saveAndFlush(new User()
                                .setEmail(email)
                                .setPassword(passwordEncoder.encode(password))
                                .setRoles(Stream.of(USER.name()).map(UserRole::valueOf).collect(Collectors.toSet()))))
                        .map(converter::convert).orElse(null);
    }
}
