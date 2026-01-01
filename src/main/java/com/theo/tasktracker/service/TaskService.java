package com.theo.tasktracker.service;

import com.theo.tasktracker.entity.Task;
import com.theo.tasktracker.repository.TaskRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class TaskService {
    private final TaskRepository repository;

    public List<Task> getAllTasks() { return repository.findAll(); }

    public Optional<Task> getTaskById(Long id) { return repository.findById(id); }

    public Task saveTask(Task task) { return repository.save(task); }

    public void deleteTask(Long id) { repository.deleteById(id); }

    public List<Task> getCompletedTasks() {
        return repository.findByCompleted(true);
    }
    public List<Task> searchTasks(String name) {
        return repository.findByNameContainingIgnoreCase(name);
    }
}