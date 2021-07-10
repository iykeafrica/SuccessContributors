package com.example.successcontribution.repository.response;

import androidx.lifecycle.LiveData;

import com.example.successcontribution.model.response.OperationStatusModel;

public class OperationStatusResponse {
    private final LiveData<OperationStatusModel> data;
    private final LiveData<String> networkError;

    public OperationStatusResponse(LiveData<OperationStatusModel> data, LiveData<String> networkError) {
        this.data = data;
        this.networkError = networkError;
    }

    public LiveData<OperationStatusModel> getData() {
        return data;
    }

    public LiveData<String> getNetworkError() {
        return networkError;
    }
}
