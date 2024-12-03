package com.example.expense_tracker.exception;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@RestControllerAdvice
@Slf4j
public class GlobalExceptionHandler extends ResponseEntityExceptionHandler {
    @ExceptionHandler(BaseException.class)
    public ResponseEntity<ErrorResponse> baseException(BaseException e) {
        log.error("BaseException: {}", e.getErrorMessage(), e);
        ErrorResponse errorResponse = new ErrorResponse(e.getStatusCode(), e.getErrorMessage());
        return ResponseEntity.status(e.getStatusCode()).body(errorResponse);
    }
}
