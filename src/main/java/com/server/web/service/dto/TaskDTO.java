package com.server.web.service.dto;

import lombok.Data;
import lombok.experimental.Accessors;

import java.time.LocalDateTime;

@Data
@Accessors(chain = true)
public class TaskDTO {
    private Long id;
    private String status;
    private LocalDateTime endTime;
}
