package com.server.web.repository;

import com.server.web.entity.Task;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.time.LocalDateTime;
import java.util.List;

public interface TaskRepository extends JpaRepository<Task, Long> {
    List<Task> findByUserId(Long userId);

    @Query(value = "select t from Task t where t.userId = ?1 and t.endTime <= ?2 order by t.endTime desc")
    List<Task> findByUserIdAndEndTimeBefore(Long userId, LocalDateTime now);
}