package com.example.expense_tracker.exception;

import org.springframework.http.HttpStatus;

public class IllegalOperationException extends BaseException {
    public IllegalOperationException(String errorMessage) {
        super(HttpStatus.BAD_REQUEST.value(), errorMessage);
    }
}
