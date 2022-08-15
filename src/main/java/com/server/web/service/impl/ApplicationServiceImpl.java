package com.server.web.service.impl;

import com.server.web.converter.ApplicationToApplicationDtoConverter;
import com.server.web.entity.Application;
import com.server.web.repository.ApplicationRepository;
import com.server.web.service.ApplicationService;
import com.server.web.service.dto.ApplicationDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ApplicationServiceImpl implements ApplicationService {
    private final ApplicationRepository repository;
    private final ApplicationToApplicationDtoConverter converter;

    @Override
    public List<ApplicationDTO> getListApplication() {
        return repository.findAll().stream().map(converter::convert).collect(Collectors.toList());
    }

    @Override
    public long count() {
        return repository.count();
    }
}
