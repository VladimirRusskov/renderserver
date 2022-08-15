package com.server.web.service;

import com.server.web.service.dto.ApplicationDTO;

import java.util.List;

public interface ApplicationService {
    List<ApplicationDTO> getListApplication();

    long count();
}
