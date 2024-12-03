package com.example.expense_tracker.exception;

import org.springframework.http.HttpStatus;

public class ResourceNotFoundException extends BaseException {
    public ResourceNotFoundException(String errorMessage) {
        super(HttpStatus.NOT_FOUND.value(), errorMessage);
    }
}
