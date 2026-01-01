package com.theo.tasktracker.repository;

import com.theo.tasktracker.entity.Task;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface TaskRepository extends JpaRepository<Task, Long> {

    List<Task> findByCompleted(boolean completed);
    List<Task> findByNameContainingIgnoreCase(String name);

    @Query("SELECT t FROM Task t WHERE t.completed = ?1 AND UPPER(t.name) LIKE UPPER(CONCAT('%', ?2, '%'))")
    List<Task> findByCompletedAndNameContainingIgnoreCase(boolean completed, String name);
}
