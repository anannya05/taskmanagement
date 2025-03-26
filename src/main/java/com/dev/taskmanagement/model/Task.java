package com.dev.taskmanagement.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.LocalDateTime;
import java.util.Locale;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class Task {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long Id;

    @NotBlank(message = "Title is required")
    @Column(nullable = false)
    private String title;

    private String description;

    @NotNull(message = "Status is required")  // Ensures status is not null
    @Pattern(regexp = "(?i)PENDING|IN PROGRESS|COMPLETED", message = "Status must be PENDING, IN_PROGRESS, or COMPLETED")
    private String status;

    @CreationTimestamp
    @Column(updatable = false,nullable = false)
    private LocalDateTime createdAt;

    @UpdateTimestamp
    @Column(nullable = false)
    private LocalDateTime updatedAt;


    public void setStatus(String status) {
        this.status = status.toUpperCase();
    }
}
