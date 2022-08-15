package com.server.web.api.controller;

import com.server.web.api.converter.UserDtoToUserResponseConverter;
import com.server.web.api.json.UserRequest;
import com.server.web.api.json.UserResponse;
import com.server.web.service.dto.UserDTO;
import com.server.web.service.impl.UserServiceImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

import static org.springframework.http.ResponseEntity.ok;
import static org.springframework.http.ResponseEntity.status;

@RestController
@RequiredArgsConstructor
public class UserAPIController {
    private final UserServiceImpl userService;
    private final UserDtoToUserResponseConverter converter;

    @PostMapping("/auth")
    public ResponseEntity<UserResponse> auth(@RequestBody @Valid UserRequest request) {
        UserDTO user = userService.findUserByEmailAndPassword(request.getEmail(), request.getPassword());
        return user == null ? status(HttpStatus.UNAUTHORIZED).build() : ok(converter.convert(user));
    }

    @PostMapping("/registration")
    public ResponseEntity<UserResponse> registration(@RequestBody @Valid UserRequest request) {
        UserDTO user = userService.addUser(request.getEmail(), request.getPassword());
        return user == null ? status(HttpStatus.CONFLICT).build() : ok(converter.convert(user));
    }
}
