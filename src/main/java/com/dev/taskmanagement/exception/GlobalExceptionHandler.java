package com.dev.taskmanagement.exception;

import com.dev.taskmanagement.dto.ApiResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import java.util.HashMap;
import java.util.Map;

@RestControllerAdvice
public class GlobalExceptionHandler {

    // Handle Validation Errors (e.g., missing title, incorrect status)
    // Handle MethodArgumentNotValidException (validation errors)
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ApiResponse> handleValidationExceptions(MethodArgumentNotValidException ex) {
        // Collect all validation errors
        Map<String, String> errors = new HashMap<>();
        BindingResult result = ex.getBindingResult();
        for (FieldError error : result.getFieldErrors()) {
            errors.put(error.getField(), error.getDefaultMessage());
        }
        // Return error response
        return ResponseEntity.badRequest().body(new ApiResponse(false, null, errors, "Validation failed", 400));
    }

    // Handle Task Not Found Exceptions
    @ExceptionHandler(TaskNotFoundException.class)
    public ResponseEntity<ApiResponse> handleTaskNotFound(TaskNotFoundException ex) {
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(
                new ApiResponse(
                        false, // success: false since it's an error
                        null, // No data returned
                        Map.of("error", "Task Not Found"), // Errors map
                        ex.getMessage(), // Error message from exception
                        HttpStatus.NOT_FOUND.value() // Status code 404
                )
        );
    }


    // Handle Illegal Arguments (e.g., invalid task ID or negative page number)
    @ExceptionHandler(IllegalArgumentException.class)
    public ResponseEntity<ApiResponse> handleIllegalArguments(IllegalArgumentException ex) {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(
                new ApiResponse(
                        false, // success: false since it's an error
                        null, // No data returned
                        Map.of("error", "Invalid Argument"), // Errors map
                        ex.getMessage(), // Error message from exception
                        HttpStatus.BAD_REQUEST.value() // Status code 400
                )
        );
    }


    // Handle All Other Exceptions (Generic)
    @ExceptionHandler(Exception.class)
    public ResponseEntity<ApiResponse> handleGlobalExceptions(Exception ex) {
        // Handle all unhandled exceptions globally
        Map<String, String> errors = new HashMap<>();
        errors.put("error", ex.getMessage());
        // Return error response
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body(new ApiResponse(false, null, errors, "Internal server error", 500));
    }
}
