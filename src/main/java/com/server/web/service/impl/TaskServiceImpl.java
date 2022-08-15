package com.server.web.service.impl;

import com.server.web.converter.TaskToTaskDtoConverter;
import com.server.web.entity.Task;
import com.server.web.repository.ApplicationRepository;
import com.server.web.repository.TaskRepository;
import com.server.web.service.TaskService;
import com.server.web.service.dto.TaskDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Random;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class TaskServiceImpl implements TaskService {
    private final TaskRepository taskRepository;
    private final TaskToTaskDtoConverter converter;
    private final ApplicationRepository applicationRepository;

    @Override
    public List<TaskDTO> getListTask(Long userId) {
        return taskRepository.findByUserId(userId).stream().map(converter::convert).collect(Collectors.toList());
    }

    @Override
    public List<TaskDTO> getListTaskHistoryChangeState(Long userId) {
        return taskRepository.findByUserIdAndEndTimeBefore(userId, LocalDateTime.now()).stream().map(converter::convert).collect(Collectors.toList());
    }

    @Transactional
    @Override
    public TaskDTO addTask(Long userId, Long applicationId) {
        return applicationId > 0 && applicationId <= applicationRepository.count() ?
                converter.convert(taskRepository.saveAndFlush(new Task()
                        .setStartTime(LocalDateTime.now())
                        .setEndTime(LocalDateTime.now().plusMinutes(randomInt(1, 5)))
                        .setUserId(userId)
                        .setApplicationId(applicationId)))
                : null;
    }

    private static int randomInt(int min, int max) {
        return new Random().nextInt((max - min) + 1) + min;
    }
}
