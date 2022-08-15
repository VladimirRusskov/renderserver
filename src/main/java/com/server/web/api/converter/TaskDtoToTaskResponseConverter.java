package com.server.web.api.converter;

import com.server.web.api.json.TaskResponse;
import com.server.web.service.dto.TaskDTO;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

@Component
public class TaskDtoToTaskResponseConverter implements Converter<TaskDTO, TaskResponse> {
    @Override
    public TaskResponse convert(TaskDTO task) {
        return new TaskResponse().setId(task.getId()).setStatus(task.getStatus()).setEndTime(task.getEndTime());
    }
}