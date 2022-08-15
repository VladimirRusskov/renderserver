package com.server.web.api.converter;

import com.server.web.api.json.UserResponse;
import com.server.web.service.dto.UserDTO;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

@Component
public class UserDtoToUserResponseConverter implements Converter<UserDTO, UserResponse> {
    @Override
    public UserResponse convert(UserDTO user) {
        return new UserResponse(user.getId(), user.getEmail());
    }
}
