package com.event.admin.ticket;

import jakarta.validation.ConstraintViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.util.HashMap;
import java.util.Map;

@ControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(Exception.class)
    public ResponseEntity<Map<Object, Object>> handleException(Exception ex) {
        var errors = new HashMap<>();
        errors.put("message", ex.getMessage());
        return new ResponseEntity<>(errors, HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @ExceptionHandler(IllegalArgumentException.class)
    public ResponseEntity<Map<Object, Object>> handleIllegalArgumentException(IllegalArgumentException ex) {
        var errors = new HashMap<>();
        errors.put("message", ex.getMessage());
        return new ResponseEntity<>(errors, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(ConstraintViolationException.class)
    public ResponseEntity<Map<Object, Object>> handleConstraintViolationException(ConstraintViolationException ex) {
        var errors = collectErrorsFrom(ex);
        return new ResponseEntity<>(errors, HttpStatus.BAD_REQUEST);
    }

    private static HashMap<Object, Object> collectErrorsFrom(ConstraintViolationException ex) {
        return ex.getConstraintViolations()
                .stream()
                .map(violation -> Map.entry(violation.getPropertyPath().toString(), violation.getMessage()))
                .collect(HashMap::new, (m, e) -> m.put(e.getKey(), e.getValue()), HashMap::putAll);
    }
}
