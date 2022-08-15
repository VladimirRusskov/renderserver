package com.server.web.converter;

import com.server.web.entity.User;
import com.server.web.service.dto.UserDTO;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

@Component
public class UserToUserDtoConverter implements Converter<User, UserDTO> {
    @Override
    public UserDTO convert(User user) {
        return new UserDTO(user.getId(), user.getEmail());
    }
}
