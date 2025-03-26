package com.dev.taskmanagement.controller;

import com.dev.taskmanagement.dto.ApiResponse;
import com.dev.taskmanagement.dto.TaskPageResponse;
import com.dev.taskmanagement.model.Task;
import com.dev.taskmanagement.service.TaskService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URI;
import java.util.Map;

@RestController
@RequestMapping("/api")
@CrossOrigin()
public class TaskController {

    @Autowired
    TaskService taskService;

    @PostMapping("/tasks")
    public ResponseEntity<ApiResponse> addTask(@Valid @RequestBody Task task, UriComponentsBuilder uriBuilder) {
        Task newTask = taskService.addTask(task);
        URI location = uriBuilder.path("/tasks/{id}").buildAndExpand(newTask.getId()).toUri();
        return ResponseEntity.created(location)
                .body(new ApiResponse(true, newTask, null, "Task created successfully", 201));
    }

    @GetMapping("/tasks")
    public ResponseEntity<ApiResponse> getTasks(
            @PageableDefault(size = 5, sort = "createdAt", direction = Sort.Direction.DESC) Pageable pageable) {
        Page<Task> tasks = taskService.getTasks(pageable);
        ApiResponse response = new ApiResponse(
                true, new TaskPageResponse(tasks), Map.of(), "Tasks retrieved successfully", 200
        );
        return ResponseEntity.ok(response);
    }

    @GetMapping(value = "/tasks", params = "status")
    public ResponseEntity<ApiResponse> getTaskByStatus(
            @RequestParam String status,
            @PageableDefault(size = 5, sort = "createdAt", direction = Sort.Direction.DESC) Pageable pageable) {

        status = status.toUpperCase();
        if (!status.matches("PENDING|IN_PROGRESS|COMPLETED")) {
            throw new IllegalArgumentException("Status must be PENDING, IN_PROGRESS, or COMPLETED");
        }

        Page<Task> tasks = taskService.getTaskByStatus(status, pageable);
        return ResponseEntity.ok(new ApiResponse(true, new TaskPageResponse(tasks), Map.of(), "Tasks retrieved successfully", 200));
    }

    @GetMapping("/tasks/{id}")
    public ResponseEntity<ApiResponse> taskById(@PathVariable Long id) {
        Task task = taskService.taskById(id);
        return ResponseEntity.ok(new ApiResponse(true, task, Map.of(), "Task retrieved successfully", 200));
    }

    @PutMapping("/tasks/{id}")
    public ResponseEntity<ApiResponse> updateTask(@PathVariable Long id, @Valid @RequestBody Task updatedTask) {
        Task updated = taskService.updateTask(id, updatedTask);
        return ResponseEntity.ok(new ApiResponse(true, updated, Map.of(), "Task updated successfully", 200));
    }

    @DeleteMapping("/tasks/{id}")
    public ResponseEntity<ApiResponse> deleteTask(@PathVariable Long id) {
        taskService.deleteTask(id);
        return ResponseEntity.ok(new ApiResponse(true, null, Map.of(), "Task deleted successfully", 200));
    }
}
