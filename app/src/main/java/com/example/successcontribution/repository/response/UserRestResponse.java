package com.example.successcontribution.repository.response;

import androidx.lifecycle.LiveData;

import com.example.successcontribution.model.response.UserRest;

public class UserRestResponse {
    private final LiveData<UserRest> data;
    private final LiveData<String> networkError;

    public UserRestResponse(LiveData<UserRest> data, LiveData<String> networkError) {
        this.data = data;
        this.networkError = networkError;
    }

    public LiveData<UserRest> getData() {
        return data;
    }

    public LiveData<String> getNetworkError() {
        return networkError;
    }
}
