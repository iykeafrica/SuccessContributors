package com.example.successcontribution.repository;

import android.app.Application;

import androidx.lifecycle.LiveData;
import androidx.paging.DataSource;
import androidx.paging.LivePagedListBuilder;
import androidx.paging.PagedList;

import com.example.successcontribution.model.response.UserRest;
import com.example.successcontribution.repository.response.PagedUsersRestResponse;

public class UsersRepository {
    private static final String TAG = UsersRepository.class.getSimpleName();
    private final UsersBoundaryCallback mBoundaryCallback;
    public static final int STORAGE_PAGE_SIZE = 5;

    public UsersRepository(Application application) {
        mBoundaryCallback = new UsersBoundaryCallback(application);
    }

    public PagedUsersRestResponse getAllUsers() {
        LiveData<String> networkError = mBoundaryCallback.getNetworkError();

        DataSource.Factory<Integer, UserRest> factory = mBoundaryCallback.getAllUsers();

        PagedList.Config config = new PagedList.Config.Builder()
                .setPageSize(STORAGE_PAGE_SIZE)
                .build();

        LiveData<PagedList<UserRest>> data = new LivePagedListBuilder<>(factory, config)
                .setBoundaryCallback(mBoundaryCallback)
                .build();

        return new PagedUsersRestResponse(data, networkError);
    }

    public PagedUsersRestResponse getUser(String query) {
        LiveData<String> networkError = mBoundaryCallback.getNetworkError();

        DataSource.Factory<Integer, UserRest> factory = mBoundaryCallback.getUser(query);

        PagedList.Config config = new PagedList.Config.Builder()
                .setPageSize(STORAGE_PAGE_SIZE)
                .build();

        LiveData<PagedList<UserRest>> data = new LivePagedListBuilder<>(factory, config)
                .setBoundaryCallback(mBoundaryCallback)
                .build();

        return new PagedUsersRestResponse(data, networkError);
    }

}
