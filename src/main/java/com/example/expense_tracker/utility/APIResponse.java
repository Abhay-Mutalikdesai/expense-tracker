package com.example.expense_tracker.utility;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;

@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
public class APIResponse {
    private String statusMsg = Constants.STATUS_SUCCESS;
    private String successMsg;
    private String errorMsg;
    private String id;

    public APIResponse(String id, String successMsg) {
        this.id = id;
        this.successMsg = successMsg;
    }

    public APIResponse(String errorMsg) {
        this.statusMsg = Constants.STATUS_FAILED;
        this.errorMsg = errorMsg;
    }
}
