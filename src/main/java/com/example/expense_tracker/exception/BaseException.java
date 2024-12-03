package com.example.expense_tracker.exception;

import com.example.expense_tracker.utility.Constants;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Getter;

@Getter
@JsonInclude(JsonInclude.Include.NON_NULL)
public class BaseException extends RuntimeException {
    private final int statusCode;
    private final String statusMessage = Constants.STATUS_FAILED;
    private final String errorMessage;

    public BaseException(int statusCode, String errorMessage) {
        this.statusCode = statusCode;
        this.errorMessage = errorMessage;
    }
}
