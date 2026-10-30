package com.library.library_management.exception;

import java.util.HashMap;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import org.springframework.http.HttpStatus; // It provides HTTP status codes.
import org.springframework.http.ResponseEntity; // It creates the HTTP response.
import java.util.HashMap; // It creates a key-value map.
import java.util.Map; // It defines the Map structure.

@RestControllerAdvice // It handles exceptions from all controllers.
public class GlobalExceptionHandler {

   @ExceptionHandler(RuntimeException.class) // It handles RuntimeException errors.
        public ResponseEntity<Map<String, String>> handleRuntimeException(RuntimeException ex) {

            Map<String, String> response = new HashMap<>(); // It creates the error response.
            response.put("message", ex.getMessage()); // It adds the error message.

            return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST); // It returns JSON with 400 status.

    }
}