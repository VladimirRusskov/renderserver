package com.server.web.service;

import com.server.web.service.dto.UserDTO;

public interface UserService {
    UserDTO findUserByEmail(String email);

    UserDTO findUserByEmailAndPassword(String email, String password);

    UserDTO addUser(String email, String password);
}
