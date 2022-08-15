package com.server.web.api.json;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.server.web.service.dto.TaskDTO;
import lombok.Data;
import lombok.experimental.Accessors;

import java.time.LocalDateTime;
import java.util.List;

@Data
@Accessors(chain = true)
@JsonInclude(JsonInclude.Include.NON_NULL)
public class TaskResponse {
    private Long id;
    private String status;
    @JsonInclude(JsonInclude.Include.NON_EMPTY)
    private List<TaskDTO> tasks;
    private LocalDateTime endTime;
}