package com.server.web.api.controller;

import com.server.web.api.json.ApplicationResponse;
import com.server.web.service.dto.ApplicationDTO;
import com.server.web.service.impl.ApplicationServiceImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

import static org.springframework.http.ResponseEntity.ok;
import static org.springframework.http.ResponseEntity.status;

@RestController
@RequiredArgsConstructor
public class ApplicationController {
    private final ApplicationServiceImpl applicationService;

    @GetMapping("/application/list")
    public ResponseEntity<ApplicationResponse> getAppList() {
        return ok(new ApplicationResponse(applicationService.getListApplication()));
    }
}
