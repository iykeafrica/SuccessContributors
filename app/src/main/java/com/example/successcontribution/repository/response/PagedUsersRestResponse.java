package com.example.successcontribution.repository.response;

import androidx.lifecycle.LiveData;
import androidx.paging.PagedList;

import com.example.successcontribution.model.response.UserRest;

public class PagedUsersRestResponse {

    LiveData<PagedList<UserRest>> data;
    LiveData<String> networkError;

    public PagedUsersRestResponse(LiveData<PagedList<UserRest>> data, LiveData<String> networkError) {
        this.data = data;
        this.networkError = networkError;
    }

    public LiveData<PagedList<UserRest>> getData() {
        return data;
    }

    public LiveData<String> getNetworkError() {
        return networkError;
    }
}
