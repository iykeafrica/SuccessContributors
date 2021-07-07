package com.example.successcontribution.repository.response;

import androidx.lifecycle.LiveData;

import okhttp3.Headers;

public class HeaderResponse {

    private final LiveData<Headers> data;
    private final LiveData<String> networkError;

    public HeaderResponse(LiveData<Headers> data, LiveData<String> networkError) {
        this.data = data;
        this.networkError = networkError;
    }

    public LiveData<Headers> getData() {
        return data;
    }

    public LiveData<String> getNetworkError() {
        return networkError;
    }
}
