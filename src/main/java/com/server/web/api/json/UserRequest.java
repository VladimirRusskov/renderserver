package com.server.web.api.json;

import lombok.Data;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

@Data
public class UserRequest {
    @Email
    @NotNull
    private String email;
    @NotNull
    @NotEmpty
    private String password;
}
