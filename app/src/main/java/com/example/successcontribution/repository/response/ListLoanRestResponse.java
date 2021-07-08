package com.example.successcontribution.repository.response;

import androidx.lifecycle.LiveData;

import com.example.successcontribution.model.response.LoanRest;

import java.util.List;

public class ListLoanRestResponse {
    private final LiveData<List<LoanRest>> data;
    private final LiveData<String> networkError;

    public ListLoanRestResponse(LiveData<List<LoanRest>> data, LiveData<String> networkError) {
        this.data = data;
        this.networkError = networkError;
    }

    public LiveData<List<LoanRest>> getData() {
        return data;
    }

    public LiveData<String> getNetworkError() {
        return networkError;
    }
}
