package com.example.expense_tracker.common;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;

@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
public class APIResponse {
    private String statusMsg = Constants.STATUS_SUCCESS;
    private String successMsg;
    private String errorMsg;
    private String id;

    APIResponse(String id, String successMsg) {
        this.successMsg = successMsg;
        this.id = id;
    }

    APIResponse(String errorMsg) {
        this.statusMsg = Constants.STATUS_FAILED;
        this.errorMsg = errorMsg;
    }
}
