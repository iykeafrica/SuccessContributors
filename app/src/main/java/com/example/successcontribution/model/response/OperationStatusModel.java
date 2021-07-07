package com.example.successcontribution.model.response;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class OperationStatusModel {

    @SerializedName("operationResult")
    @Expose
    private String operationResult;

    @SerializedName("operationName")
    @Expose
    private String operationName;

    public String getOperationResult() {
        return operationResult;
    }

    public void setOperationResult(String operationResult) {
        this.operationResult = operationResult;
    }

    public String getOperationName() {
        return operationName;
    }

    public void setOperationName(String operationName) {
        this.operationName = operationName;
    }
}
