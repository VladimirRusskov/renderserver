package com.server.web.api.controller;

import com.server.web.api.converter.TaskDtoToTaskResponseConverter;
import com.server.web.api.json.TaskRequest;
import com.server.web.api.json.TaskResponse;
import com.server.web.repository.UserRepository;
import com.server.web.service.dto.TaskDTO;
import com.server.web.service.impl.TaskServiceImpl;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import java.util.List;

import static org.springframework.http.ResponseEntity.ok;
import static org.springframework.http.ResponseEntity.status;

@RestController
public class TaskController extends AbstractController {
    private TaskServiceImpl taskService;
    private TaskDtoToTaskResponseConverter converter;

    public TaskController(UserRepository userRepository, TaskServiceImpl taskService, TaskDtoToTaskResponseConverter converter) {
        super(userRepository);
        this.taskService = taskService;
        this.converter = converter;
    }

    @GetMapping("/task/list")
    public ResponseEntity<TaskResponse> getTaskList() {
        List<TaskDTO> list = taskService.getListTask(currentUser().getId());
        return list.isEmpty() ? status(HttpStatus.NO_CONTENT).build() : ok(new TaskResponse().setTasks(list));
    }

    @GetMapping("/task/list/history")
    public ResponseEntity<TaskResponse> getTaskHistoryState() {
        List<TaskDTO> list = taskService.getListTaskHistoryChangeState(currentUser().getId());
        return list.isEmpty() ? status(HttpStatus.NO_CONTENT).build() : ok(new TaskResponse().setTasks(list));
    }

    @PostMapping("/task/add")
    public ResponseEntity<TaskResponse> addTask(@RequestBody @Valid TaskRequest request) {
        TaskDTO task = taskService.addTask(currentUser().getId(), request.getApplicationId());
        return task != null ? ok(converter.convert(task)) : status(HttpStatus.BAD_REQUEST).build();
    }
}
