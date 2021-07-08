package com.example.successcontribution.repository.response;

import androidx.lifecycle.LiveData;

import com.example.successcontribution.model.response.UserRest;

import java.util.List;

public class ListUserRestResponse {
    private final LiveData<List<UserRest>> data;
    private final LiveData<String> networkError;

    public ListUserRestResponse(LiveData<List<UserRest>> data, LiveData<String> networkError) {
        this.data = data;
        this.networkError = networkError;
    }

    public LiveData<List<UserRest>> getData() {
        return data;
    }

    public LiveData<String> getNetworkError() {
        return networkError;
    }
}
