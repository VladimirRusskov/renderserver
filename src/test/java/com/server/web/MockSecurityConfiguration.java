package com.server.web;

import com.server.web.security.CustomGrantedAuthority;
import com.server.web.security.CustomUserDetails;
import com.server.web.security.UserRole;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.core.userdetails.UserDetailsService;

import java.util.Collections;

@Configuration
public class MockSecurityConfiguration {

    @Bean
    public UserDetailsService userDetailsService() {
        return username -> new CustomUserDetails(1L,
                "russkov@gmail.com",
                "password",
                Collections.singleton(new CustomGrantedAuthority(UserRole.USER)));
    }
}
