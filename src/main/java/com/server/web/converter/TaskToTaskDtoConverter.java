package com.server.web.converter;

import com.server.web.entity.Task;
import com.server.web.service.dto.TaskDTO;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;

@Component
public class TaskToTaskDtoConverter implements Converter<Task, TaskDTO> {
    @Override
    public TaskDTO convert(Task task) {
        return new TaskDTO()
                .setId(task.getId())
                .setStatus(checkStatus(task.getEndTime()))
                .setEndTime(task.getEndTime());
    }

    private static String checkStatus(LocalDateTime end) {
        return LocalDateTime.now().isAfter(end) ? "COMPLETE" : "RENDERING";
    }
}
