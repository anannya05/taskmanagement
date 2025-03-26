package com.dev.taskmanagement.service;

import com.dev.taskmanagement.exception.TaskNotFoundException;
import com.dev.taskmanagement.model.Task;
import com.dev.taskmanagement.repository.TaskRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import java.util.Optional;

@Service
public class TaskService {

    @Autowired
    private TaskRepository taskRepository;

    public Task addTask(Task task) {
        return taskRepository.save(task);
    }

    @Cacheable(value = "tasks", key = "#pageable.pageNumber")
    public Page<Task> getTasks(Pageable pageable){
        return taskRepository.findAll(pageable);
    }

    @Cacheable(value = "tasks_status", key = "#status + '-' + #pageable.pageNumber")
    public Page<Task> getTaskByStatus(String status, Pageable pageable) {
        return taskRepository.findByStatus(status, pageable);
    }

    @Cacheable(value = "task", key = "#id")
    public Task taskById(Long id) {
        return taskRepository.findById(id)
                .orElseThrow(() -> new TaskNotFoundException("Task with ID " + id + " not found"));
    }

    @CachePut(value = "task", key = "#id")
    public Task updateTask(Long id, Task updatedTask) {
        Task existingTask = taskRepository.findById(id)
                .orElseThrow(() -> new TaskNotFoundException("Task with ID " + id + " not found"));

        if (updatedTask.getTitle() != null) {
            existingTask.setTitle(updatedTask.getTitle());
        }
        if (updatedTask.getDescription() != null) {
            existingTask.setDescription(updatedTask.getDescription());
        }
        if (updatedTask.getStatus() != null) {
            existingTask.setStatus(updatedTask.getStatus().toUpperCase());
        }
        return taskRepository.save(existingTask);
    }

    @CacheEvict(value = "task", key = "#id")
    public void deleteTask(Long id) {
        if (!taskRepository.existsById(id)) {
            throw new TaskNotFoundException("Task with ID " + id + " not found");
        }
        taskRepository.deleteById(id);
    }
}
