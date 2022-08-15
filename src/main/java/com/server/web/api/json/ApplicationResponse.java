package com.server.web.api.json;

import com.server.web.service.dto.ApplicationDTO;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.List;

@Data
@AllArgsConstructor
public class ApplicationResponse {
    private List<ApplicationDTO> applications;
}
