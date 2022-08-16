package com.server.web.service.impl;

import com.server.web.converter.TaskToTaskDtoConverter;
import com.server.web.entity.Task;
import com.server.web.repository.TaskRepository;
import com.server.web.service.dto.TaskDTO;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import java.time.LocalDateTime;
import java.util.Collections;

import static java.util.Arrays.asList;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.*;

@RunWith(MockitoJUnitRunner.class)
public class TaskServiceImplTest {

    @InjectMocks
    TaskServiceImpl subj;

    @Mock
    TaskRepository taskRepository;

    @Mock
    TaskToTaskDtoConverter converter;

    @Test
    public void getListTask_ok() {
        Task task = new Task().setId(1L).setUserId(1L).setApplicationId(1L);
        TaskDTO taskDTO = new TaskDTO().setId(1L).setStatus("RENDERING");
        when(taskRepository.findByUserId(1L)).thenReturn(asList(task));
        when(converter.convert(task)).thenReturn(taskDTO);
        assertEquals(asList(taskDTO), subj.getListTask(1L));
        verify(taskRepository, times(1)).findByUserId(1L);
        verify(converter, times(1)).convert(task);
    }

    @Test
    public void getListTask_isEmpty() {
        when(taskRepository.findByUserId(1L)).thenReturn(Collections.emptyList());
        assertTrue(subj.getListTask(1L).isEmpty());
        verify(taskRepository, times(1)).findByUserId(1L);
        verifyNoInteractions(converter);
    }

    @Test
    public void getListTaskHistoryChangeState_ok() {
        LocalDateTime time = LocalDateTime.now().minusMinutes(5);
        Task task = new Task().setId(1L).setUserId(1L).setApplicationId(1L).setEndTime(time);
        TaskDTO taskDTO = new TaskDTO().setId(1L).setStatus("COMPLETE").setEndTime(time);
        when(converter.convert(task)).thenReturn(taskDTO);
        when(taskRepository.findByUserIdAndEndTimeBefore(1L)).thenReturn(asList(task));
        assertEquals(asList(taskDTO), subj.getListTaskHistoryChangeState(1L));
        verify(taskRepository, times(1)).findByUserIdAndEndTimeBefore(1L);
        verify(converter, times(1)).convert(task);
    }

    @Test
    public void getListTaskHistoryChangeState_isEmpty() {
        when(taskRepository.findByUserIdAndEndTimeBefore(1L)).thenReturn(Collections.emptyList());
        assertTrue(subj.getListTaskHistoryChangeState(1L).isEmpty());
        verify(taskRepository, times(1)).findByUserIdAndEndTimeBefore(1L);
        verifyNoInteractions(converter);
    }
}