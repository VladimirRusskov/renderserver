package com.server.web.repository;

import com.server.web.entity.Task;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.time.LocalDateTime;
import java.util.List;

import static java.util.Arrays.asList;
import static org.junit.Assert.*;

@DataJpaTest
@RunWith(SpringRunner.class)
public class TaskRepositoryTest {

    @Autowired
    TaskRepository subj;

    @Before
    public void setUp() {
    }

    @Test
    public void findByUserId_ok() {
        List<Task> tasks = subj.findByUserId(1L);
        assertFalse(tasks.isEmpty());
        assertEquals(asList(new Task()
                .setId(1L)
                .setStartTime(LocalDateTime.parse("2022-08-16T11:29:40.316002"))
                .setEndTime(LocalDateTime.parse("2022-08-16T11:29:43.316002"))
                .setApplicationId(1L)
                .setUserId(1L)), tasks);
    }

    @Test
    public void findByUserId_isEmpty() {
        assertTrue(subj.findByUserId(2L).isEmpty());
    }

    @Test
    public void findByUserIdAndEndTimeBefore_ok() {
        List<Task> tasks = subj.findByUserIdAndEndTimeBefore(1L);
        assertFalse(tasks.isEmpty());
        assertEquals(asList(new Task()
                .setId(1L)
                .setStartTime(LocalDateTime.parse("2022-08-16T11:29:40.316002"))
                .setEndTime(LocalDateTime.parse("2022-08-16T11:29:43.316002"))
                .setApplicationId(1L)
                .setUserId(1L)), tasks);
    }

    @Test
    public void findByUserIdAndEndTimeBefore_isEmpty() {
        assertTrue(subj.findByUserIdAndEndTimeBefore(2L).isEmpty());
    }
}
