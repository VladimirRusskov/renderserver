package com.server.web.service.impl;

import com.server.web.converter.UserToUserDtoConverter;
import com.server.web.entity.User;
import com.server.web.repository.UserRepository;
import com.server.web.security.UserRole;
import com.server.web.service.dto.UserDTO;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.stream.Collectors;
import java.util.stream.Stream;

import static com.server.web.security.UserRole.USER;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.mockito.Mockito.*;

@RunWith(MockitoJUnitRunner.class)
public class UserServiceImplTest {

    @InjectMocks
    UserServiceImpl subj;

    @Mock
    UserRepository userRepository;

    @Mock
    UserToUserDtoConverter converter;

    @Mock
    PasswordEncoder passwordEncoder;

    @Test
    public void findUserByEmail_ok() {
        User user = new User().setEmail("email");
        when(userRepository.findByEmail("email")).thenReturn(user);
        UserDTO userDTO = new UserDTO(1L, "email");
        when(converter.convert(user)).thenReturn(userDTO);
        assertEquals(userDTO, subj.findUserByEmail("email"));
        verify(userRepository, times(1)).findByEmail("email");
        verify(converter, times(1)).convert(user);
    }

    @Test
    public void findUserByEmail_failed() {
        when(userRepository.findByEmail("email")).thenReturn(null);
        assertNull(subj.findUserByEmail("email"));
        verify(userRepository, times(1)).findByEmail("email");
        verifyNoInteractions(converter);
    }

    @Test
    public void findUserByEmailAndPassword_ok() {
        User user = new User().setId(1L).setEmail("email").setPassword("password");
        when(userRepository.findByEmail("email")).thenReturn(user);
        when(passwordEncoder.matches("password", user.getPassword())).thenReturn(true);
        UserDTO userDTO = new UserDTO(1L, "email");
        when(converter.convert(user)).thenReturn(userDTO);
        assertEquals(userDTO, subj.findUserByEmailAndPassword("email", "password"));
        verify(userRepository, times(1)).findByEmail("email");
        verify(passwordEncoder, times(1)).matches("password", user.getPassword());
        verify(converter, times(1)).convert(user);
    }

    @Test
    public void findUserByEmailAndPassword_failed() {
        when(userRepository.findByEmail("email")).thenReturn(null);
        assertNull(subj.findUserByEmailAndPassword("email", "password"));
        verify(userRepository, times(1)).findByEmail("email");
        verifyNoInteractions(converter);
    }

    @Test
    public void addUser_ok() {
        User user = new User().setEmail("email").setPassword("password").setRoles(Stream.of(USER.name()).map(UserRole::valueOf).collect(Collectors.toSet()));
        UserDTO userDTO = new UserDTO(1L, "email");
        when(userRepository.findByEmail("email")).thenReturn(null);
        when(passwordEncoder.encode("password")).thenReturn("password");
        when(converter.convert(user)).thenReturn(userDTO);
        when(userRepository.saveAndFlush(user)).thenReturn(user);
        assertEquals(userDTO, subj.addUser("email", "password"));
        verify(userRepository, times(1)).findByEmail("email");
        verify(converter, times(1)).convert(user);
        verify(userRepository, times(1)).saveAndFlush(user);
    }

    @Test
    public void addUser_unique() {
        User user = new User().setEmail("email").setPassword("password");
        when(userRepository.findByEmail("email")).thenReturn(user);
        when(converter.convert(user)).thenReturn(new UserDTO(1L, "email"));
        assertNull(subj.addUser("email", "password"));
        verify(userRepository, times(1)).findByEmail("email");
        verify(converter, times(1)).convert(user);
        verify(userRepository, never()).saveAndFlush(user);
    }
}