package com.server.web.service;

import com.server.web.service.dto.TaskDTO;

import java.util.List;

public interface TaskService {
    List<TaskDTO> getListTask(Long userId);

    List<TaskDTO> getListTaskHistoryChangeState(Long userId);

    TaskDTO addTask(Long userId, Long applicationId);
}