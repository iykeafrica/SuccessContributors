package com.example.successcontribution.repository.response;

import androidx.lifecycle.LiveData;

import com.example.successcontribution.model.response.LoanRest;

public class LoanRestResponse {
    private final LiveData<LoanRest> data;
    private final LiveData<String> networkError;

    public LoanRestResponse(LiveData<LoanRest> data, LiveData<String> networkError) {
        this.data = data;
        this.networkError = networkError;
    }

    public LiveData<LoanRest> getData() {
        return data;
    }

    public LiveData<String> getNetworkError() {
        return networkError;
    }
}
