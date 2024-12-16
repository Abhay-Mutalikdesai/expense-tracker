package com.example.expense_tracker.utility;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;

import java.io.Serializable;

@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
public class ErrorResponse implements Serializable {
    private final String statusMessage = Constants.STATUS_FAILED;
    private String errorMsg;

    public ErrorResponse(String errorMsg) {
        this.errorMsg = errorMsg;
    }
}
