package com.example.expense_tracker.exception;

import com.example.expense_tracker.utility.Constants;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;

import java.io.Serializable;

@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
public class ErrorResponse implements Serializable {
    private int statusCode;
    private final String statusMessage = Constants.STATUS_FAILED;
    private String errorMessage;

    public ErrorResponse(int statusCode, String errorMessage) {
        this.statusCode = statusCode;
        this.errorMessage = errorMessage;
    }
}
