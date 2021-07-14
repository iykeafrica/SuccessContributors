package com.example.successcontribution.ui.viewmodel;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.Transformations;
import androidx.paging.PagedList;

import com.example.successcontribution.model.response.UserRest;
import com.example.successcontribution.repository.UsersRepository;
import com.example.successcontribution.repository.response.PagedUsersRestResponse;

import org.jetbrains.annotations.NotNull;


public class PagedUserRestViewModel extends AndroidViewModel {

    private final MutableLiveData<String> mQueryMutableLiveData;
    private final LiveData<PagedList<UserRest>> mPagedListLiveData;
    private final LiveData<String> mNetworkError;
    private final LiveData<PagedList<UserRest>> mPagedListByUserLiveData;
    private final LiveData<String> mNetworkByUserError;

    public PagedUserRestViewModel(@NonNull @NotNull Application application) {
        super(application);

        UsersRepository repository = new UsersRepository(application);
        mQueryMutableLiveData = new MutableLiveData<>();

        PagedUsersRestResponse pagedUsersRestResponse = repository.getAllUsers();
        mPagedListLiveData = pagedUsersRestResponse.getData();
        mNetworkError = pagedUsersRestResponse.getNetworkError();

        LiveData<PagedUsersRestResponse> liveData = Transformations.map(mQueryMutableLiveData,
                repository::getUser);

        mPagedListByUserLiveData = Transformations.switchMap(liveData,
                PagedUsersRestResponse::getData);

        mNetworkByUserError = Transformations.switchMap(liveData,
                PagedUsersRestResponse::getNetworkError);
    }

    public void setQueryMutableLiveData(String query) {
        mQueryMutableLiveData.setValue(query);
    }

    public LiveData<PagedList<UserRest>> getPagedListLiveData() {
        return mPagedListLiveData;
    }

    public LiveData<String> getNetworkError() {
        return mNetworkError;
    }

    public LiveData<PagedList<UserRest>> getPagedListByUserLiveData() {
        return mPagedListByUserLiveData;
    }

    public LiveData<String> getNetworkByUserError() {
        return mNetworkByUserError;
    }
}
