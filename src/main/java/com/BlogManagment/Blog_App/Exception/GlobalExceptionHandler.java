package com.BlogManagment.Blog_App.Exception;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(UserException.class)
    public ResponseEntity<Map<String, Object>> handleUserException(UserException ex) {

        Map<String, Object> errorResponse = new HashMap<>();

        errorResponse.put("status", "failure");
        errorResponse.put("type", "User Exception");
        errorResponse.put("message", ex.getMessage());
        errorResponse.put("httpStatus", ex.getHttpStatus().value());
        errorResponse.put("timestamp", LocalDateTime.now());

        return new ResponseEntity<>(errorResponse, ex.getHttpStatus());
    }

    @ExceptionHandler(BlogException.class)
    public ResponseEntity<Map<String, Object>> handleBlogException(BlogException ex) {

        Map<String, Object> errorResponse = new HashMap<>();

        errorResponse.put("status", "failure");
        errorResponse.put("type", "Blog Exception");
        errorResponse.put("message", ex.getMessage());
        errorResponse.put("httpStatus", ex.getHttpStatus().value());
        errorResponse.put("timestamp", LocalDateTime.now());

        return new ResponseEntity<>(errorResponse, ex.getHttpStatus());
    }
}