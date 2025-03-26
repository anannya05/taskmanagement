package com.dev.taskmanagement.dto;


import lombok.Data;

import java.time.LocalDateTime;
import java.util.Map;

@Data
public class ApiResponse {
        private boolean success;
        private Object data;
        private Map<String, String> errors;
        private String message;
        private int status;
        private LocalDateTime timestamp;

        public ApiResponse(boolean success, Object data, Map<String, String> errors, String message, int status) {
            this.success = success;
            this.data = data;
            this.errors = errors;
            this.message = message;
            this.status = status;
            this.timestamp = LocalDateTime.now();
        }

        // Getters and Setters
}

