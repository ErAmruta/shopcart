package com.example.shopbackend.exception;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.time.Instant;
import java.util.Map;

@RestControllerAdvice
public class GlobalExceptionHandler {
  @ExceptionHandler(Exception.class)
  public ResponseEntity<?> all(Exception ex) {
    return ResponseEntity.status(500).body(Map.of("timestamp", Instant.now(), "error", ex.getMessage()));
  }
}
