package com.server.web.api.controller;

import com.server.web.entity.User;
import com.server.web.repository.UserRepository;
import com.server.web.security.CustomUserDetails;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.context.SecurityContextHolder;

@RequiredArgsConstructor
public abstract class AbstractController {
    private final UserRepository userRepository;

    protected User currentUser() {
        try {
            CustomUserDetails user = (CustomUserDetails) SecurityContextHolder.getContext()
                    .getAuthentication()
                    .getPrincipal();
            return userRepository.findById(user.getId()).orElse(null);
        } catch (ClassCastException e) {
            return null;
        }

    }
}
