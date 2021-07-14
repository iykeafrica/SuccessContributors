package com.example.successcontribution.repository.response;

import androidx.lifecycle.LiveData;
import androidx.paging.PagedList;

import com.example.successcontribution.model.response.LoanRest;

public class PagedLoansRestResponse {
    LiveData<PagedList<LoanRest>> data;
    LiveData<String> networkError;

    public PagedLoansRestResponse(LiveData<PagedList<LoanRest>> data, LiveData<String> networkError) {
        this.data = data;
        this.networkError = networkError;
    }

    public LiveData<PagedList<LoanRest>> getData() {
        return data;
    }

    public LiveData<String> getNetworkError() {
        return networkError;
    }
}
