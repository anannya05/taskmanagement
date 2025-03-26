package com.dev.taskmanagement.repository;

import com.dev.taskmanagement.model.Task;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TaskRepository extends JpaRepository<Task,Long> {

    Page<Task> findAll(Pageable pageable);

    Page<Task> findByStatus(String status, Pageable pageable);
}
