package com.example.expense_tracker.utility;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Getter;

@Getter
@JsonInclude(JsonInclude.Include.NON_NULL)
public class SuccessResponse {
    private final String statusMsg = Constants.STATUS_SUCCESS;
    private final String successMsg;
    private final String id;

    public SuccessResponse(String id, String successMsg) {
        this.id = id;
        this.successMsg = successMsg;
    }
}
