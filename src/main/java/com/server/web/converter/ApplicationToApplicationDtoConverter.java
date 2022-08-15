package com.server.web.converter;

import com.server.web.entity.Application;
import com.server.web.service.dto.ApplicationDTO;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

@Component
public class ApplicationToApplicationDtoConverter implements Converter<Application, ApplicationDTO> {
    @Override
    public ApplicationDTO convert(Application application) {
        return new ApplicationDTO(application.getId(), application.getName());
    }
}
